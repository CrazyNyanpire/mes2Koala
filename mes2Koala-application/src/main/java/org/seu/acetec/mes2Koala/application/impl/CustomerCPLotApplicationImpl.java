package org.seu.acetec.mes2Koala.application.impl;

import net.sf.json.JSONObject;

import org.seu.acetec.mes2Koala.application.*;
import org.seu.acetec.mes2Koala.core.common.BeanUtils;
import org.seu.acetec.mes2Koala.core.domain.*;
import org.seu.acetec.mes2Koala.core.enums.CPWaferState;
import org.seu.acetec.mes2Koala.core.enums.CustomerLotState;
import org.seu.acetec.mes2Koala.core.enums.TestTypeForWms;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author 阙宇翔
 * @version 2016/2/27
 */
@Named
@Transactional
public class CustomerCPLotApplicationImpl extends
        GenericMES2ApplicationImpl<CustomerCPLot> implements
        CustomerCPLotApplication {

    private static String ZERO = "0";
    @Inject
    CPLotApplication cpLotApplication;
    @Inject
    CPProcessApplication cpProcessApplication;
    @Inject
    CPInfoApplication cpInfoApplication;
    @Inject
    IncrementNumberApplication incrementNumberApplication;
    @Inject
    WMSClientApplication wmsClientApplication;
    @Inject
    private CPRuncardTemplateApplication cpRuncardTemplateApplication;
    
    @Override
    public CustomerCPLot findByWmsId(String wmsId) {
        return findOne("select _c from CustomerCPLot _c where _c.wmsId = ?",
                wmsId);
    }

    @Override
    public List<CustomerCPLot> findByParentSeparationId(Long parentSeparationId) {
        return find(
                "select _c from CustomerCPLot _c where _c.parentSeparationId = ?",
                parentSeparationId);
    }

    @Override
    public CustomerCPLot findByParentIntegrationId(String parentIntegrationId) {
        return findOne(
                "select _c from CustomerCPLot _c where _c.parentIntegrationId = ?",
                parentIntegrationId);
    }

    private CPRuncard createCPRuncard(CPRuncardTemplate cpRuncardTemplate){
        CPRuncard cpRuncard = new CPRuncard();
        cpRuncard.setIQC(cpRuncardTemplate.getIQC());
        cpRuncard.setFQC(cpRuncardTemplate.getFQC());
        cpRuncard.setCP1(cpRuncardTemplate.getCP1());
        cpRuncard.setCP1_Before_Bake(cpRuncardTemplate.getCP1_Before_Bake());
        cpRuncard.setCP1_After_Bake(cpRuncardTemplate.getCP1_After_Bake());
        cpRuncard.setCP1_DT(cpRuncardTemplate.getCP1_DT());

        cpRuncard.setCP2(cpRuncardTemplate.getCP2());
        cpRuncard.setCP2_Before_Bake(cpRuncardTemplate.getCP2_Before_Bake());
        cpRuncard.setCP2_After_Bake(cpRuncardTemplate.getCP2_After_Bake());
        cpRuncard.setCP2_DT(cpRuncardTemplate.getCP2_DT());

        cpRuncard.setCP3(cpRuncardTemplate.getCP3());
        cpRuncard.setCP3_Before_Bake(cpRuncardTemplate.getCP3_Before_Bake());
        cpRuncard.setCP3_After_Bake(cpRuncardTemplate.getCP3_After_Bake());
        cpRuncard.setCP3_DT(cpRuncardTemplate.getCP3_DT());

        cpRuncard.setCP4(cpRuncardTemplate.getCP4());
        cpRuncard.setCP4_Before_Bake(cpRuncardTemplate.getCP4_Before_Bake());
        cpRuncard.setCP4_After_Bake(cpRuncardTemplate.getCP4_After_Bake());
        cpRuncard.setCP4_DT(cpRuncardTemplate.getCP4_DT());


        cpRuncard.setPacking(cpRuncardTemplate.getPacking());
        cpRuncard.setOQC(cpRuncardTemplate.getOQC());
        cpRuncard.setTotalSite(cpRuncardTemplate.getTotalSite());

        cpRuncard.setKeyProductionAuthorization(cpRuncardTemplate
                .getKeyProductionAuthorization());
        cpRuncard.setAssistProductionAuthorization(cpRuncardTemplate
                .getAssistProductionAuthorization());
        cpRuncard.setKeyQuantityAuthorization(cpRuncardTemplate
                .getKeyQuantityAuthorization());
        cpRuncard.setAssistQuantityAuthorization(cpRuncardTemplate
                .getAssistQuantityAuthorization());
        cpRuncard.setKeyTDEAuthorization(cpRuncardTemplate
                .getKeyTDEAuthorization());
        cpRuncard.setAssistTDEAuthorization(cpRuncardTemplate
                .getAssistTDEAuthorization());


        CPSpecialFormTemplate cpSpecialFormTemplateSource = cpRuncardTemplate
                .getCpSpecialFormTemplate();
        CPSpecialFormTemplate cpSpecialFormTemplateTarget = new CPSpecialFormTemplate();

        cpSpecialFormTemplateTarget.setMCP_Cover1Sheet(cpSpecialFormTemplateSource.getMCP_Cover1Sheet());
        cpSpecialFormTemplateTarget.setMCP_Cover1SheetStatus(cpSpecialFormTemplateSource.getMCP_Cover1SheetStatus());
        cpSpecialFormTemplateTarget.setSheet1(cpSpecialFormTemplateSource.getSheet1());
        cpSpecialFormTemplateTarget.setSheet1Status(cpSpecialFormTemplateSource.getSheet1Status());
        cpSpecialFormTemplateTarget.setSheet2(cpSpecialFormTemplateSource.getSheet2());
        cpSpecialFormTemplateTarget.setSheet2Status(cpSpecialFormTemplateSource.getSheet2Status());
        cpSpecialFormTemplateTarget.setCP1Sheet(cpSpecialFormTemplateSource.getCP1Sheet());
        cpSpecialFormTemplateTarget.setCP1SheetStatus(cpSpecialFormTemplateSource.getCP1SheetStatus());
        cpSpecialFormTemplateTarget.setCP2Sheet(cpSpecialFormTemplateSource.getCP2Sheet());
        cpSpecialFormTemplateTarget.setCP2SheetStatus(cpSpecialFormTemplateSource.getCP2SheetStatus());

        cpSpecialFormTemplateTarget.setMap_Shift1Sheet(cpSpecialFormTemplateSource.getMap_Shift1Sheet());
        cpSpecialFormTemplateTarget.setMap_Shift1SheetStatus(cpSpecialFormTemplateSource.getMap_Shift1SheetStatus());
        cpRuncard.setCpSpecialFormTemplate(cpSpecialFormTemplateTarget);
        return cpRuncard;
    }

    /**
     * @param customerCPLotId 需要单的批次
     * @param cpLot           前端回传的cpLot
     * @version 2015/3/29 YuxiangQue
     * @version 2016/4/18 Eva 下单完成之后，添加领料信息至WMS
     */
    @Override
    public void order(Long customerCPLotId, CPLot cpLot, Long cpInfoId) {
        CustomerCPLot customerCPLot = get(customerCPLotId);
        cpLot.setCustomerCPLot(customerCPLot);
        cpLot.setShipmentProductNumber(customerCPLot.getCustomerProductNumber());
        // 绑定Wafer
        List<CPCustomerWafer> cpCustomerWafers = customerCPLot.getCpCustomerWafers();
        List<CPWafer> cpWafers = new ArrayList<>();
        for (CPCustomerWafer cpCustomerWafer : cpCustomerWafers) {
            CPWafer cpWafer = new CPWafer();
            cpWafer.setCpCustomerWafer(cpCustomerWafer);
            cpWafer.setState(CPWaferState.UNPASS);
            cpWafer.setInternalWaferCode(cpCustomerWafer.getWaferCode() + "-"
                    + cpCustomerWafer.getWaferIndex());
            cpWafer.setCpLot(cpLot);
            cpWafer.setPass(ZERO);
            cpWafer.setFail(ZERO);
            cpWafer.setCustomerOffset(ZERO);
            cpWafer.setInternalOffset(ZERO);
            cpWafers.add(cpWafer);
        }
        cpLot.setCpWafers(cpWafers);
        cpLot.setQuantity((long) cpWafers.size());

        cpLotApplication.createCheckedCPLot(cpLot); // 创建CPLot，检查前端回传的编号
        //add by lcn 2016.6.1  绑定cpruncard
        CPInfo cpInfo = null;
        if(cpInfoId != null){
        	cpInfo = this.cpInfoApplication.get(cpInfoId);
        }else{
        	cpInfo = customerCPLot.getCpInfo();
        }
        CPRuncardTemplate cpRuncardTemplate = cpRuncardTemplateApplication
                .findByInternalProductId(cpInfo.getId());
        CPRuncard cpRuncard = createCPRuncard(cpRuncardTemplate);
        cpRuncard.setCpLot(cpLot);
        cpRuncard.save();


        cpProcessApplication.createCPProcess(cpLot.getId());
        customerCPLot.setState(CustomerLotState.Ordered);
        customerCPLot.setCpInfo(cpInfo);
        update(customerCPLot);
        // 下单成功，调用WMS接口，生成领料信息
        CPTestLot cpTestLot = ConverttoWMSCPLot(customerCPLot, cpLot);
        cpLot.setWmsTestId(cpTestLot.getID());
        JSONObject obj = JSONObject.fromObject(cpTestLot);
        String lotjson = "[" + obj.toString() + "]";
//        wmsClientApplication.orderLots(
//                TestTypeForWms.getStringValue(TestTypeForWms.CP), lotjson);
    }

    private CPTestLot ConverttoWMSCPLot(CustomerCPLot customerCPLot, CPLot cpLot) {
        CPTestLot cpTestLot = new CPTestLot();
        cpTestLot.setID(UUID.randomUUID().toString());
        cpTestLot.setACETEC_LOT(cpLot.getInternalLotNumber());
        cpTestLot.setCUS_CODE(customerCPLot.getCustomerNumber());
        cpTestLot.setCUS_LOT(customerCPLot.getCustomerLotNumber());
        cpTestLot.setCUS_PPO(customerCPLot.getCustomerPPO());
        cpTestLot.setIN_ID(customerCPLot.getWmsId());
        cpTestLot.setIN_PARTNUM(customerCPLot.getCustomerProductNumber());
        cpTestLot.setMASK_NAME(customerCPLot.getMaskName());
        cpTestLot.setOUT_PARTNUM(cpLot.getShipmentProductNumber());
        cpTestLot.setQUANTITY(cpLot.getQuantity());
        cpTestLot.setSIZE(customerCPLot.getSize());
        return cpTestLot;
    }

    private Long innerOrder(CustomerCPLot customerCPLot) {

        // 获取 cpLotNumber 和 cpRCNumber
        String cpLotNumber = incrementNumberApplication
                .nextCPLotNumber(customerCPLot);
        String cpRCNumber = incrementNumberApplication
                .nextRCNumber(customerCPLot);

        // 根据客批创建内部批次
        CPLot cpLot = new CPLot();
        cpLot.setInternalLotNumber(cpLotNumber);
        cpLot.setRcNumber(cpRCNumber);
        cpLot.setCustomerCPLot(customerCPLot);
        cpLot.setShipmentProductNumber(customerCPLot.getCustomerProductNumber());
        // 绑定Wafer
        List<CPCustomerWafer> cpCustomerWafers = customerCPLot
                .getCpCustomerWafers();
        List<CPWafer> cpWafers = new ArrayList<>();
        for (CPCustomerWafer cpCustomerWafer : cpCustomerWafers) {
            CPWafer cpWafer = new CPWafer();
            cpWafer.setCpCustomerWafer(cpCustomerWafer);
            cpWafer.setState(CPWaferState.UNPASS);
            cpWafer.setInternalWaferCode(cpCustomerWafer.getWaferCode() + "-"
                    + cpCustomerWafer.getWaferIndex());
            cpWafer.setCpLot(cpLot);
            cpWafer.setPass(ZERO);
            cpWafer.setFail(ZERO);
            cpWafer.setCustomerOffset(ZERO);
            cpWafer.setInternalOffset(ZERO);
            cpWafers.add(cpWafer);
        }
        cpLot.setCpWafers(cpWafers);
        cpLot.setQuantity((long) cpWafers.size());
        cpLotApplication.create(cpLot);


        // add by lcn 2016.6.1
        CPInfo cpInfo = cpLot.getCustomerCPLot().getCpInfo();
        CPRuncardTemplate cpRuncardTemplate = cpRuncardTemplateApplication
                .findByInternalProductId(cpInfo.getId());
        CPRuncard cpRuncard = createCPRuncard(cpRuncardTemplate);
        cpRuncard.setCpLot(cpLot);
        cpRuncard.save();

        // TODO
        cpProcessApplication.createCPProcess(cpLot.getId()); // 创建Process
        customerCPLot.setState(CustomerLotState.Ordered); // 如果下单成功则改变客批状态

        // 下单成功，调用WMS接口，生成领料信息
        CPTestLot cpTestLot = ConverttoWMSCPLot(customerCPLot, cpLot);
        cpLot.setWmsTestId(cpTestLot.getID());
        JSONObject obj = JSONObject.fromObject(cpTestLot);
        String lotjson = "[" + obj.toString() + "]";
//        wmsClientApplication.orderLots(
//                TestTypeForWms.getStringValue(TestTypeForWms.CP), lotjson);
        return cpLot.getId();
    }

    /**
     * @param customerCPLotIds 需要批量下单的批次
     * @param messages         下单产生的信息
     * @version 2015/3/29 YuxiangQue
     */
    @Override
    public List<Long> batchOrder(Long[] customerCPLotIds,
                           Map<String, Integer> messages) {

        // 过滤订单，未下单或未知状态的，有绑定的process才可下单
        List<CustomerCPLot> customerCPLots = new ArrayList<CustomerCPLot>();
        for (Long customerCPLotId : customerCPLotIds) {
            CustomerCPLot customerCPLot = get(customerCPLotId);
            if (customerCPLot.getState() == CustomerLotState.Ordered)
                continue;
            // TODO
            // CPInfo cpInfo = customerCPLot.getCpInfo();
            // if (cpInfo == null)
            // continue;
            // ProcessTemplate processTemplate = cpInfo.getProcessTemplate();
            // if (processTemplate == null)
            // continue;
            // if (Strings.isNullOrEmpty(processTemplate.getContent()))
            // continue;
            customerCPLots.add(customerCPLot);
        }

        // 下单逻辑
        List<Long> cpLotIds = new ArrayList<Long>(); // 记录成功下单的Id
        // 循环调用单批下单
        try {
            for (CustomerCPLot customerFTLot : customerCPLots) {
                Long cpLotId = innerOrder(customerFTLot);
                cpLotIds.add(cpLotId);
            }
        } catch (RuntimeException ex) {
            throw new RuntimeException("批量下单过程意外中断，已成功下单" + cpLotIds.size()
                    + "个批次，" + ex.getMessage());
        }
        updateAll(customerCPLots); // 更新下单状态

        // 记录下单结果
        messages.put("select", customerCPLotIds.length); // 选择的个数
        messages.put("orders", customerCPLots.size()); // 下单的个数
        messages.put("combine", 0); // 合批的个数

        return cpLotIds;
    }

    /**
     * LotNumber生成规则：LotNumber=客户批号；重工后缀加R(N)，例如R2代表第二次重工
     *
     * @param customerCPLotId
     * @return
     */
    @Override
    public String peekLotNumber(Long customerCPLotId) {
        return incrementNumberApplication.peekCPLotNumber(get(customerCPLotId));
    }

    @Override
    public String peekRCNumber(Long customerCPLotId) {
        return incrementNumberApplication.peekRCNumber(get(customerCPLotId));
    }

    @Override
    public List<CPCustomerWafer> getCPCustomerWafer(Long customerCPLotId) {
        @SuppressWarnings("unchecked")
        List<CPCustomerWafer> list = (List<CPCustomerWafer>) getQueryChannelService()
                .createJpqlQuery(
                        "select _c from CPCustomerWafer _c where _c.customerCPLot.id = ?")
                .setParameters(customerCPLotId).list();
        return list;
    }

    @Override
    public void rollbackWMSTestInfo(String linetype, String wmsTestId,
                                    Long quantity) {
        wmsClientApplication.deleteOrderLot(linetype, wmsTestId, quantity);
    }

    /**
     * @param orderWithOutWMS 需要单的批次
     * @param cpLot           前端回传的cpLot
     * @version 2016/4/26 harlow
     */
    @Override
    public void orderWithOutWMS(Long customerCPLotId, CPLot cpLot) {
        CustomerCPLot customerCPLot = get(customerCPLotId);
        cpLot.setCustomerCPLot(customerCPLot);
        cpLotApplication.createCheckedCPLot(cpLot, false, false); // 创建CPLot，检查前端回传的编号
        cpProcessApplication.createCPProcess(cpLot.getId());
        customerCPLot.setState(CustomerLotState.Ordered);
        update(customerCPLot);
    }

	/**
	 * 删除下单
	 *
	 * @param cpLot
	 */
	public void deleteOrder(CPLot cpLot) {
		CustomerCPLot customerLot = cpLot.getCustomerCPLot();
		// 删除runcard,runcard创建有BUG未生成Runcard暂不删除
		//this.cpRuncardApplication.deleteRuncardByLotId(cpLot.getId());
		cpProcessApplication.deleteCPProcess(cpLot.getId()); // 创建Process
		customerLot.setState(CustomerLotState.Unordered);
		// customerLot.setShipmentNumber(ftLot.getShipmentProductNumber());//修改出货型号
		update(customerLot);
	}
}
