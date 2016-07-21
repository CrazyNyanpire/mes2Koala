package org.seu.acetec.mes2Koala.facade.impl;

import com.google.common.base.Strings;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.openkoala.koala.commons.InvokeResult;
import org.openkoala.organisation.facade.EmployeeFacade;
import org.openkoala.organisation.facade.dto.EmployeeDTO;
import org.seu.acetec.mes2Koala.application.*;
import org.seu.acetec.mes2Koala.core.common.BeanUtils;
import org.seu.acetec.mes2Koala.core.domain.*;
import org.seu.acetec.mes2Koala.core.domain.Process;
import org.seu.acetec.mes2Koala.core.enums.SBLQuality;
import org.seu.acetec.mes2Koala.facade.*;
import org.seu.acetec.mes2Koala.facade.common.SenderMailUtils;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.facade.impl.assembler.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.inject.Inject;
import javax.inject.Named;

import java.text.SimpleDateFormat;
import java.util.*;

@Named
public class FTLotNodeOptionFacadeImpl implements FTLotNodeOptionFacade {

    @Inject
    FTLotNodeOperationApplication ftLotNodeOperationApplication;
    @Inject
    private FTProcessApplication ftProcessApplication;
    @Inject
    private FTInfoApplication ftInfoApplication;
    @Inject
    private FTLotApplication ftLotApplication;
    @Inject
    private FTNodeApplication ftNodeApplication;
    @Inject
    private FTResultApplication ftResultApplication;
    @Inject
    private ReelDiskApplication reelDiskApplication;
    @Inject
    private FTLotApplication fTLotApplication;
    @Inject
    private FTQDNApplication fTQDNApplication;
    @Inject
    private SampleShippingApplication sampleShippingApplication;
    @Inject
    private FTLotOptionLogApplication ftLotOptionLogApplication;
    @Inject
    private FTQDNFacade fTQDNFacade;
    @Inject
    private IncrementNumberApplication incrementNumberApplication;
    @Inject
    private FTLotFacade ftLotFacade;
    @Inject
    private ExcelFacade excelFacade;
    @Inject
    private CustomerFTLotFacade customerFTLotFacade;
    private QueryChannelService queryChannel;
    @Inject
    private EmployeeFacade employeeFacade;
    @Inject
    private SenderMailUtils senderMailUtils;

    @Inject
    private PlaceHolderApplication placeHolderApplication;
    
    private static final  String mailTitle = "FT异常单通知";
    private static final  String mailContent1 = "你有1个FT品质异常单待处理（请在4小时内作出处理）,单号为：";
    private static final  String mailContent2 = ",具体位置为MES2系统下[生产管理]->[品质异常单]，请查收！";

    private QueryChannelService getQueryChannelService() {
        if (queryChannel == null) {
            queryChannel = InstanceFactory.getInstance(
                    QueryChannelService.class, "queryChannel");
        }
        return queryChannel;
    }

    /**
     * @param ftLotIds
     * @return
     * @version 2015/3/29 YuxiangQue
     */
    @Transactional
    @Override
    public InvokeResult integrate(Long[] ftLotIds) {
        try {
            ftLotNodeOperationApplication.integrate(ftLotIds);
            return InvokeResult.success();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 回滚所有操作
            return InvokeResult.failure(ex.getMessage());
        }
    }

    /**
     * @param ftLotId
     * @param separateQuantities
     * @return
     * @version 2015/3/29 YuxiangQue
     */
    @Transactional
    @Override
    public InvokeResult separate(Long ftLotId, Long[] separateQuantities) {
        try {
            ftLotNodeOperationApplication.separate(ftLotId, separateQuantities);
            return InvokeResult.success();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 回滚所有操作
            return InvokeResult.failure(ex.getMessage());
        }
    }


    @Override
    public InvokeResult hold(FTQDNDTO fTQDNDTO) {
        // 0.查询lot是否有未处理的QDN单
        // List<FTQDNDTO> list = fTQDNFacade.findAllDoingFTQDNByLotId(fTQDNDTO
        // .getLotId());
        // if (list != null && list.size() > 0) {
        // return InvokeResult.failure("QDN单:" + list.get(0).getQdnNo()
        // + " 未完结！");
        // }
        fTQDNDTO.setLotId(fTQDNDTO.getId());
        fTQDNDTO.setId(null);
        FTLot ftLot = fTLotApplication.get(fTQDNDTO.getLotId());
        // 1.建立QDN单
        FTQDN fTQDN = new FTQDN();
        BeanUtils.copyProperties(fTQDNDTO, fTQDN);
        Process processDTO = ftProcessApplication.findFTProcessByFTLotId(ftLot
                .getId());
        fTQDN.setFtLot(ftLot);
        fTQDN.setFlowNow(ftLot.getCurrentState());
        fTQDN.setFlow(processDTO.getContent());
        fTQDN.setCustomerName(ftLot.getCustomerFTLot().getFtInfo()
                .getCustomerDirect().getChineseName());
        fTQDN.setQdnNo(incrementNumberApplication.nextQdnNumber());
        fTQDN.setStatus(0);
        fTQDN.setType("FT异常单");
        fTQDNApplication.create(fTQDN);
        // 2.记录批次操作记录
        FTLotOptionLog fTLotOptionLog = new FTLotOptionLog();
        BeanUtils.copyProperties(fTQDNDTO, fTLotOptionLog);
        fTLotOptionLog.setFtLot(ftLot);
        fTLotOptionLog.setRemark(fTQDNDTO.getNote());
        fTLotOptionLog.setFlownow(fTQDN.getFlowNow());
        fTLotOptionLog.setOptType("开Hold");
        ftLotOptionLogApplication.creatFTLotOptionLog(fTLotOptionLog);
        // 3.改变Lot状态
        ftLot.setLastModifyTimestamp(fTQDNDTO.getLastModifyTimestamp());
        ftLot.setLastModifyEmployNo(ftLot.getLastModifyEmployNo());
        ftLot.setHoldState(InternalLot.HOLD_STATE_HOLD);
        fTLotApplication.update(ftLot);
        // 4.给部门负责人发送邮件
        EmployeeDTO toPerson = employeeFacade.getEmployeeById(Long.parseLong(fTQDNDTO.getToPerson()));
        String mailaddress = toPerson.getEmail();
        senderMailUtils.sendMailHelper(mailTitle, mailContent1 + fTQDN.getQdnNo() + mailContent2 , mailaddress);
        return InvokeResult.success();
    }

    @Override
    public InvokeResult unhold(FTLotDTO fTLotDTO) {
        // 1.查询lot是否有未处理的QDN单
        List<FTQDNDTO> list = fTQDNFacade.findAllDoingFTQDNByLotId(fTLotDTO
                .getId());
        if (list != null && list.size() > 0) {
            return InvokeResult.failure("QDN单:" + list.get(0).getQdnNo()
                    + " 未完结！");
        }
        // 2.如果没有未处理的QDN单改变lot状态取消Hold标志
        FTLot fTLot = fTLotApplication.get(fTLotDTO.getId());
        fTLot.setLastModifyTimestamp(fTLotDTO.getLastModifyTimestamp());
        fTLot.setLastModifyEmployNo(fTLot.getLastModifyEmployNo());
        fTLot.setHoldState(InternalLot.HOLD_STATE_UNHOLD);
        fTLotApplication.update(fTLot);
        // 3.记录解Hold日志
        FTLotOptionLog fTLotOptionLog = new FTLotOptionLog();
        BeanUtils.copyProperties(fTLotDTO, fTLotOptionLog);
        fTLotOptionLog.setId(null);
        fTLotOptionLog.setFtLot(fTLot);
        fTLotOptionLog.setRemark("解Hold");
        fTLotOptionLog.setFlownow(fTLot.getCurrentState());
        fTLotOptionLog.setOptType("解Hold");
        ftLotOptionLogApplication.creatFTLotOptionLog(fTLotOptionLog);
        // TODO Auto-generated method stub
        return InvokeResult.success("成功解Hold!");
    }

    @Override
    public InvokeResult futureHold(FTQDNDTO fTQDNDTO) {
        fTQDNDTO.setLotId(fTQDNDTO.getId());
        fTQDNDTO.setId(null);
        FTLot ftLot = fTLotApplication.get(fTQDNDTO.getLotId());
        // 1.建立QDN单
        FTQDN fTQDN = new FTQDN();
        BeanUtils.copyProperties(fTQDNDTO, fTQDN);
        Process processDTO = ftProcessApplication.findFTProcessByFTLotId(ftLot
                .getId());
        fTQDN.setFtLot(ftLot);
        fTQDN.setFlow(processDTO.getContent());
        if (fTQDNDTO.getFlowNow() == null) {
            fTQDN.setFlowNow(ftLot.getCurrentState());
        } else {
            fTQDN.setFlowNow(fTQDNDTO.getFlowNow());
        }
        fTQDN.setCustomerName(ftLot.getCustomerFTLot().getFtInfo()
                .getCustomerDirect().getChineseName());
        fTQDN.setQdnNo(incrementNumberApplication.nextQdnNumber());
        fTQDN.setStatus(0);
        fTQDN.setType("FT异常单");
        fTQDNApplication.create(fTQDN);
        // 2.记录批次操作记录
        FTLotOptionLog fTLotOptionLog = new FTLotOptionLog();
        BeanUtils.copyProperties(fTQDNDTO, fTLotOptionLog);
        fTLotOptionLog.setFtLot(ftLot);
        fTLotOptionLog.setRemark(fTQDNDTO.getNote());

        fTLotOptionLog.setFlownow(processDTO.getName());
        fTLotOptionLog.setOptType("Future Hold");
        ftLotOptionLogApplication.creatFTLotOptionLog(fTLotOptionLog);
        // 3.改变Lot状态
        ftLot.setLastModifyTimestamp(fTQDNDTO.getLastModifyTimestamp());
        ftLot.setLastModifyEmployNo(ftLot.getLastModifyEmployNo());
        ftLot.setHoldState(InternalLot.HOLD_STATE_FUTURE_HOLD);// future hold
        ftLot.setIsFuture(true);
        ftLot.setFutureFlow(fTQDN.getFlowNow());
        fTLotApplication.update(ftLot);
        // 4.给部门负责人发送邮件
        EmployeeDTO toPerson = employeeFacade.getEmployeeById(Long.parseLong(fTQDNDTO.getToPerson()));
        String mailaddress = toPerson.getEmail();
        senderMailUtils.sendMailHelper(mailTitle, mailContent1 + fTQDNDTO.getQdnNo() + mailContent2 , mailaddress);
        return InvokeResult.success();
    }

    @Override
    public InvokeResult sampleShipping(SampleShippingDTO sampleShippingDTO) {
        sampleShippingDTO.setFtLotId(sampleShippingDTO.getId());
        sampleShippingDTO.setVersion(0);
        sampleShippingDTO.setId(null);
        FTLot ftLot = fTLotApplication.get(sampleShippingDTO.getFtLotId());
        // 1.建立小样品出货记录
        SampleShipping sampleShipping = new SampleShipping();
        BeanUtils.copyProperties(sampleShippingDTO, sampleShipping);
        sampleShipping.setFtLot(ftLot);
        sampleShippingApplication.create(sampleShipping);
        // 2.记录批次操作记录
        FTLotOptionLog fTLotOptionLog = new FTLotOptionLog();
        BeanUtils.copyProperties(sampleShippingDTO, fTLotOptionLog);
        fTLotOptionLog.setFtLot(ftLot);
        fTLotOptionLog.setRemark(sampleShippingDTO.getNote());
        fTLotOptionLog.setFlownow(ftLot.getCurrentState());
        fTLotOptionLog.setOptType("Future Hold");
        ftLotOptionLogApplication.creatFTLotOptionLog(fTLotOptionLog);
        // 3.创建ReelDisk实体
        this.createSampleShippingReelDisk(ftLot, sampleShippingDTO);
        return InvokeResult.success();
    }
/*
 * 2016-7-13 updated by Eva Datecode取值错误,入中转库之后无reelcode
 */
    private InvokeResult createSampleShippingReelDisk(FTLot ftLot,
    		SampleShippingDTO sampleShippingDTO) {
    	FTInfo fTInfo = ftInfoApplication.get(ftLot.getCustomerFTLot()
    			.getFtInfo().getId());
    	Date packagingTime = new Date();
    	String partNumber = ftLot.getCustomerFTLot().getFtInfo()
    			.getCustomerProductNumber();
    	ReelDisk reelDisk = new ReelDisk();
    	String reelCode = incrementNumberApplication.nextReelCode(fTInfo);
    	reelDisk.setReelCode(reelCode);
    	BeanUtils.copyProperties(sampleShippingDTO, reelDisk);
    	reelDisk.setId(null);
    	reelDisk.setPartNumber(partNumber);
    	reelDisk.setPackagingTime(packagingTime);
    	reelDisk.setDateCode(ftLot.getCustomerFTLot().getDateCode());
    	reelDisk.setQuality(sampleShippingDTO.getQuality());
    	reelDisk.setRemark(ReelDisk.REEL_REMARK_LITTLE_SAMPLE);
    	if (Integer.valueOf(fTInfo.getReelQty()) > reelDisk.getQuantity()) {
    		reelDisk.setIsFull(ReelDisk.REEL_DISK_NO);
    	} else {
    		reelDisk.setIsFull(ReelDisk.REEL_DISK_YES);
    	}
    	reelDisk.setLogic(0);
    	reelDisk.setFromReelCode("");
    	reelDisk.setQuantity(sampleShippingDTO.getQty());
    	reelDisk.setFtLot(ftLot);
    	reelDisk.setApprove(ReelDisk.REEL_DISK_UNAPPROVED);
    	reelDisk.setStatus(ReelDisk.REEL_DISK_STATUS_IN_HOUSE);
    	reelDiskApplication.create(reelDisk);
    	return InvokeResult.success();
    }

    @Override
    public InvokeResult optionLog(FTLotDTO fTLotDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public InvokeResult splitLot(FTLotDTO fTLotDTO) {
        // 1.获取母批实体
        FTLot parentInternalLot = fTLotApplication.get(fTLotDTO.getId());
        FTLot parentLot = parentInternalLot;
        // if (parentLot.getFtLot().getHoldState().equals("1")) {
        // return InvokeResult.failure("该批次已下单");
        // }
        // 2.保存分批出来的子批信息
        String[] qtyStr = fTLotDTO.getSplitQty().split(",");
        int childIndex = 0;
        List<Long> splitLotIds = new ArrayList<Long>();
        try {
            // 遍历分批数组，每一批都从相同的母批中分出
            for (String qty : qtyStr) {
                childIndex++;
                InvokeResult invokeResult;
                // 2.1生成childInternalLot
                FTLotDTO childFTLotDTO = FTLotAssembler
                        .toDTO(parentInternalLot);
                childFTLotDTO.setCheckLotNo(false);
                // invokeResult = customerFTLotFacade
                // .getExpectedLotNumber(parentInternalLot
                // .getCustomerFTLot().getCustomerFTLot().getId());
                // if (invokeResult.isHasErrors()) {
                // return invokeResult;
                // }
                childFTLotDTO.setInternalLotNumber(parentInternalLot
                        .getInternalLotNumber().concat("-" + childIndex));

                invokeResult = customerFTLotFacade
                        .getExpectedRCNumber(parentInternalLot.getCustomerFTLot()
                                .getId());
                if (invokeResult.isHasErrors()) {
                    return invokeResult;
                }
                childFTLotDTO.setRcNumber(invokeResult.getData().toString());
                childFTLotDTO.setId(null);
                childFTLotDTO.setParentSeparationId(parentInternalLot.getId());
                childFTLotDTO
                        .setMaterialType(((CustomerFTLot) parentInternalLot
                                .getCustomerFTLot()).getMaterialType());
                getFTLotChild(parentLot, qty, childFTLotDTO);
                parentLot.setQty(parentLot.getQty() - childFTLotDTO.getQty());
                invokeResult = ftLotFacade.createCheckedFTLot(childFTLotDTO);
                if (invokeResult.isHasErrors()) {
                    return invokeResult;
                }
                splitLotIds
                        .add(Long.valueOf(invokeResult.getData().toString()));
            }
            if (parentLot.getQty() != 0) {
                throw new Exception();
            }
            // 修改母批信息
            parentLot.setLogic(1);
            parentLot.setQty(0L);
            // 持久化操作
            // fTLotApplication.createCheckedFTLot(childLots);
            fTLotApplication.update(parentLot);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return InvokeResult.failure("分批失败。" + e.getMessage());
        }


        for (Long id : splitLotIds) {
            ftProcessApplication.createFTProcess(id, fTLotDTO.getId());
        }
        return InvokeResult.success();
    }

    private void getFTLotChild(FTLot parentLot, String qty,
                               FTLotDTO childFTLotDTO) {
        BeanUtils.copyProperties(parentLot, childFTLotDTO);
        childFTLotDTO.setId(null);
        childFTLotDTO.setQty(Long.valueOf(qty));
    }

    @Override
    public InvokeResult mergeLot(FTLotDTO fTLotDTO) {
        // 1.获取合批母批实体
        String ftLotIds = fTLotDTO.getMergeIds();

        String[] mergeIds = ftLotIds.split(",");
        List<InternalLot> parentInternalLots = new ArrayList<InternalLot>();
        int qty = 0;
        Long parentId = null;
        Long mergeId;
        try {
            for (String id : mergeIds) {
                FTLot parentInternalLot = fTLotApplication.get(Long.valueOf(id));
                parentInternalLots.add(parentInternalLot);
                qty += parentInternalLot.getQty();
                if (parentId != null
                        && parentId != parentInternalLot
                        .getParentSeparationId()) {
                    throw new Exception("母批不同不能合批！");
                } else
                    parentId = parentInternalLot.getParentSeparationId();
            }
            // 2.保存合批出来的批次信息
            // 获取合批前批次信息
            FTLot parentInternalLot = fTLotApplication
                    .get(parentInternalLots.get(0).getParentSeparationId());
            InvokeResult invokeResult;
            // 2.1生成childInternalLot
            FTLotDTO childInternalDTO = FTLotAssembler.toDTO(parentInternalLot);
            childInternalDTO.setCheckLotNo(false);
            childInternalDTO.setInternalLotNumber(parentInternalLot
                    .getInternalLotNumber());

            invokeResult = customerFTLotFacade
                    .getExpectedRCNumber(parentInternalLot.getCustomerFTLot()
                            .getId());
            if (invokeResult.isHasErrors()) {
                return invokeResult;
            }
            childInternalDTO.setRcNumber(invokeResult.getData().toString());
            childInternalDTO.setId(null);
            childInternalDTO.setParentIntegrationIds(ftLotIds);
            // TODO: 测试
            childInternalDTO.setMaterialType(parentInternalLot.getCustomerFTLot().getMaterialType());
            getFTLotChild(parentInternalLot, String.valueOf(qty),
                    childInternalDTO);
            invokeResult = ftLotFacade.createCheckedFTLot(childInternalDTO);
            if (invokeResult.isHasErrors()) {
                return invokeResult;
            }
            mergeId = Long.valueOf(invokeResult.getData().toString());
            // 修改母批信息
            for (InternalLot parentLot : parentInternalLots) {
                FTLot ftLot = (FTLot) parentLot;
                ftLot.setLogic(1);
                ftLot.setQty(0L);
                fTLotApplication.update(ftLot);
                // internalLotApplication.updateInternalLot(parentLot);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return InvokeResult.failure("合批失败。" + e.getMessage());
        }
        if (mergeId == null) {
            return InvokeResult.failure("合批失败。");
        }
        ftProcessApplication.createFTProcess(mergeId, Long.valueOf(ftLotIds.split(",")[0]));
        return InvokeResult.success(mergeId);
    }


    @Override
    public InvokeResult getFTResultByLotId(Long id, String labelType) {
        FTLot ftLot = fTLotApplication.get(id);
        String jpql = "select o from FTProcess o right join o.ftLot e where e.id=?";
        List<FTProcess> processList = getQueryChannelService()
                .createJpqlQuery(jpql)
                .setParameters(new Object[]{ftLot.getId()}).list();
        if (processList.size() < 1) {
            return InvokeResult.failure("未找到Process！");
        }
        FTProcess process = processList.get(0);
        for (FTNode ftNode : process.getFtNodes()) {
            if (ftNode instanceof FTFinishNode) {
                FTFinishNode ftFinsh = (FTFinishNode) ftNode;
                Map<String, Object> map = new HashMap<>();
                map.put("productNo", ftLot.getShipmentProductNumber());
                map.put("ppo", ftLot.getCustomerFTLot().getCustomerPPO());
                map.put("customerNo", ftLot.getCustomerFTLot()
                        .getCustomerNumber());
                map.put("ftLotNo", ftLot.getInternalLotNumber());
                // TODO: 测试
                map.put("taxType",
                        ((CustomerFTLot) ftLot.getCustomerFTLot()).getTaxType());
                List<Label> labels = ftLot.getCustomerFTLot()
                        .getFtInfo().getLabels();
                for (Label label : labels) {
                    if (label.getLabelType().startsWith(labelType)) {
                        map.put("labelName", label.getLabelName());
                        break;
                    }
                }
                map.put("binInfo", ftFinsh.getResult());
                return InvokeResult.success(map);
            }
        }
        return InvokeResult.failure("获取测试结果失败！");
    }

    /**
     * 切换等待入站状态1变为已入站2
     * 入站，会顺序查找整个Process上的所有站点，
     * 跳过已经出站的站点，找到第一个没进站的站点，进站，
     * 对于其他情况作为异常状态处理
     *
     * @param processId
     * @return
     * @version 2016/3/3 YuxiangQue
     */
    @Transactional
    @Override
    public InvokeResult startFTNode(Long processId, FTProcessDTO ftProcessDTO) {
        try {
            ftLotNodeOperationApplication.start(processId, (FTProcess) FTProcessAssembler.toEntity(ftProcessDTO));
            return InvokeResult.success("进站成功");
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return InvokeResult.failure(ex.getMessage());
        }
    }

    /**
     * 出站
     * 1. 首先检查外部的DTO中的state状态，保证不会又客户端修改状态
     * 2. 然后检查状态必须是进站而没出战
     * 3. 接着对Test站点进行良率卡控
     * 4. 最后更新出站信息，更新出站信息也是顺序查找，找到一个没出站的站点，对于其他状态作为异常处理
     *
     * @param ftNodeDTO 当前所在站点
     * @param processId
     * @return
     * @version 2016/3/6 YuxiangQue
     * @version 2016/3/29 YuxiangQue 挪入Application
     */
    @Transactional
    @Override
    public InvokeResult endFTNode(Long processId, FTNodeDTO ftNodeDTO) {
        try {
            ftLotNodeOperationApplication.end(processId, FTNodeAssembler.toEntity(ftNodeDTO));
            return InvokeResult.success("出站成功");
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return InvokeResult.failure(ex.getMessage());
        }
    }

    /**
     * 良率放行
     *
     * @param processId
     * @return
     * @version 2016/3/28 LiuHaobo
     * @version 2016/3/29 YuxiangQue 挪入Application
     */
    @Transactional
    @Override
    public InvokeResult endFailTestNode(Long processId, FTProcessDTO ftProcessDTO) {
        try {
            ftLotNodeOperationApplication.endFail(processId, (FTProcess) FTProcessAssembler.toEntity(ftProcessDTO));
            return InvokeResult.success("出站成功");
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return InvokeResult.failure(ex.getMessage());
        }
    }

    private InvokeResult doSaveFTNode(FTLot ftLot, FTNode original, FTNodeDTO updateProps) {
    	//该实体用于取用操作记录中所需的时间和操作人
    	FTNode updatePropsEntity =FTNodeAssembler.toEntity(updateProps);

        if (original instanceof FTComposedTestNode) {  // 组合测试站点
            // TODO: 测试
            FT_TestDTO dto = updateProps.getFtTestDTO();
            if (dto == null) {
                return InvokeResult.failure("保存失败：奇怪的错误");
            }

            List<FT_ResultDTO> resultDTOs = new ArrayList<>();
            resultDTOs.addAll(dto.getEqcList());
            resultDTOs.addAll(dto.getFtList());
            resultDTOs.addAll(dto.getLatList());
            resultDTOs.addAll(dto.getRtList());
            List<FTResult> ftResults = FTResultAssembler.toEntities(resultDTOs);
            ftResults.add(FTResultAssembler.toEntity(dto.getFinalYield()));
//            for (FTResult ftResult : ftResults) {
//                recalculateFTResultYieldAndFail(ftResult, original.getSbls());
//            }
            // 操作记录
            ftResultApplication.updateAll(ftResults);
            updateExtraOnSaveFTNode(ftLot, original);
            ftLotOptionLogApplication.createSaveFTNode(ftLot, original, updatePropsEntity); // 操作记录
            return InvokeResult.success();
        } else if (original instanceof FTBakingNode) {  // FTBakingNode
            FTBakingNode changedNode = FTBakingNodeAssembler.toEntity(updateProps.getFtBakingDTO());
            if (changedNode == null) {
                return InvokeResult.failure("保存失败：奇怪的错误");
            }
            BeanUtils.copyProperties(changedNode, original, "id", "version", "turn", "ftProcess", "state", "sbls");
        } else if (original instanceof FTGuTestNode) {
            FTGuTestNode changedNode = FTGuTestNodeAssembler.toEntity(updateProps.getFtGuTestDTO());
            if (changedNode == null) { // FTGuTestNode
                return InvokeResult.failure("保存失败：奇怪的错误");
            }
            BeanUtils.copyProperties(changedNode, original, "id", "version", "turn", "ftProcess", "state", "sbls");
        } else if (original instanceof FTPassNode) { // FTPassNode
            FTPassNode changedNode = FTPassNodeAssembler.toEntity(updateProps.getFtPassNodeDTO());
            if (changedNode == null) {
                return InvokeResult.failure("保存失败：奇怪的错误");
            }
            BeanUtils.copyProperties(changedNode, original, "id", "version", "turn", "ftProcess", "state", "sbls");
        } else if (original instanceof FTFinishNode) { // FTFinishNode
            FTFinishNode changedNode = FTFinishNodeAssembler.toEntity(updateProps.getFtFinishDTO());
            if (changedNode == null) {
                return InvokeResult.failure("保存失败：奇怪的错误");
            }
            BeanUtils.copyProperties(changedNode, original, "id", "version", "turn", "ftProcess", "state", "sbls");
        } else if (original instanceof FTIQCNode) {  // FTIQCNode
            FTIQCNode changedNode = FTIQCNodeAssembler.toEntity(updateProps.getFtIQCDTO());
            if (changedNode == null) {
                return InvokeResult.failure("保存失败：奇怪的错误");
            }
            BeanUtils.copyProperties(changedNode, original, "id", "version", "turn", "ftProcess", "state", "sbls");
        } else {
            return InvokeResult.failure("保存失败：未知结点");
        }
        ftLotOptionLogApplication.createSaveFTNode(ftLot, original, updatePropsEntity); // 操作记录
        updateExtraOnSaveFTNode(ftLot, original);
        ftNodeApplication.update(original);
        return InvokeResult.success();
    }


    /**
     * 不检查状态随意保存FTNode
     *
     * @param processId
     * @param updateProps
     * @return
     * @version 2016/5/11 YuxiangQue
     */
    @Override
    public InvokeResult saveUncheckedFTNode(Long processId, FTNodeDTO updateProps) {
        FTNode original = ftNodeApplication.get(updateProps.getId());
        if (original == null) {
            return InvokeResult.failure("保存失败：未找到需要保存的站点！");
        }
        FTLot ftLot = ftLotApplication.findByProcessId(processId);
        return doSaveFTNode(ftLot, original, updateProps);
    }

    /**
     * 保存FTResult结果
     *
     * @param processId
     * @param updateProps
     * @return
     * @version 2016/3/6 YuxiangQue
     */
    @Override
    public InvokeResult saveFTNode(Long processId, FTNodeDTO updateProps) {
        FTLot ftLot = ftLotApplication.findByProcessId(processId);
        if (Objects.equals(ftLot.getHoldState(), FTLot.HOLD_STATE_HOLD)) {
            return InvokeResult.success("保存失败：开HOLD");
        }
        List<FTNode> startedFTNodes = ftNodeApplication.findStartedFTNodeByFTLotId(ftLot.getId());
        if (startedFTNodes.size() != 1) {
            return InvokeResult.failure("保存失败：未找到需要保存的站点！");
        }
        FTNode startedFTNode = startedFTNodes.get(0);
        return doSaveFTNode(ftLot, startedFTNode, updateProps);
    }

    /**
     * @param id
     * @return
     * @version 2016/3/17 HongYu
     */
    @Override
    public InvokeResult findCurrentSblByLotId(Long id) {
        FTProcess result = ftProcessApplication.findFTProcessByFTLotId(id);
        return result != null ? InvokeResult.success(FTProcessAssembler
                .CurrentSbltoDTO(result)) : InvokeResult.failure("");
    }


    /**
     * 数量统计
     * 数量统计规则：
     * Pass：pass数取最后一个测试站点中finalyield的pass数
     * Fail Bin：每个测试站点Bin别相同，数量统计则将各个测试站点Bin别数量相加
     * 每个测试站点Bin别不同，数量统计中直接统计各个站点各个Bin别数量
     *
     * @param processId
     * @return
     */
    @Override
    public InvokeResult quantityStatistics(Long processId) {
        FTProcess process = ftProcessApplication.get(processId);
        List<FTNode> ftComposedTestNodes = ftNodeApplication.findFTCompostedTestNodesDescByProcessId(processId);
        if (ftComposedTestNodes.size() < 1) {
            return InvokeResult.success();
        }

        // 统计passbins和failbins
        List<FTFinishNodeStatisticsBinDTO> passBins = new ArrayList<>();
        List<FTFinishNodeStatisticsBinDTO> failBins = new ArrayList<>();

        int lossSum = 0;
        int otherSum = 0;
        int backUpSum = 0;
        int markFSum = 0;

        for (FTNode ftComposedTestNode : ftComposedTestNodes) {
            // 只统计最后一个finalYield
            FTResult finalYield = ftComposedTestNode.getResult();
            List<SBL> sbls = ftComposedTestNode.getSbls();

            lossSum += Integer.parseInt(finalYield.getLoss());
            otherSum += Integer.parseInt(finalYield.getOther());
            backUpSum += Integer.parseInt(finalYield.getBackUp());
            markFSum += Integer.parseInt(finalYield.getMarkF());

            // 查询每个组合测试中的所有SBL，根据SBL的fail、pass信息提取对应的bin别

            int[] finalYieldBinValus = FTResult.getBins(finalYield);

            for (SBL sbl : sbls) {
                String binType = sbl.getType();
                binType = binType.startsWith("Bin") ? binType.substring(3) : binType;
                int binIndex = Integer.valueOf(binType) - 1;

                int binValue = finalYieldBinValus[binIndex];
                FTFinishNodeStatisticsBinDTO temp = new FTFinishNodeStatisticsBinDTO();
                temp.setSite(sbl.getSite());
                temp.setType(binType);
                temp.setValue(String.valueOf(binValue));

                if (Objects.equals(sbl.getQuality(), SBLQuality.FAIL)) {  // 处理 fail bin
                    temp.setQuality(SBLQuality.getStringValue(SBLQuality.FAIL));
                    failBins.add(temp);

                } else { // 处理 pass bin
                    temp.setQuality(SBLQuality.getStringValue(SBLQuality.PASS));
                    passBins.add(temp);
                }
            }
        }

        // 合并passbins和failbins
        passBins.addAll(failBins);
        Map<String, List<FTFinishNodeStatisticsBinDTO>> typeBinMap = new HashMap<>();
        for (FTFinishNodeStatisticsBinDTO bin : passBins) {
            if (!typeBinMap.containsKey(bin.getType()))
                typeBinMap.put(bin.getType(), new ArrayList<FTFinishNodeStatisticsBinDTO>());
            typeBinMap.get(bin.getType()).add(bin);
        }

        List<FTFinishNodeStatisticsBinDTO> mergedBins = new ArrayList<>();
        for (Map.Entry<String, List<FTFinishNodeStatisticsBinDTO>> typeBinEntry : typeBinMap.entrySet()) {
            FTFinishNodeStatisticsBinDTO mergedBin = new FTFinishNodeStatisticsBinDTO();
            int value = 0;
            String site = "";
            String quality = "";
            for (FTFinishNodeStatisticsBinDTO ftFinishNodeStatisticsBinDTO : typeBinEntry.getValue()) {
                site = ftFinishNodeStatisticsBinDTO.getSite();
                quality = ftFinishNodeStatisticsBinDTO.getQuality();
                value += Integer.valueOf(ftFinishNodeStatisticsBinDTO.getValue());
            }
            mergedBin.setType(typeBinEntry.getKey());
            mergedBin.setSite(site);
            mergedBin.setQuality(quality);
            mergedBin.setValue(String.valueOf(value));
            mergedBins.add(mergedBin);
        }

        // 按照bin别排序
        Collections.sort(mergedBins);

        FTFinishNodeStatisticsDTO ftFinishNodeStatisticsDTO = new FTFinishNodeStatisticsDTO();
        ftFinishNodeStatisticsDTO.setBins(mergedBins);
        ftFinishNodeStatisticsDTO.setLoss(String.valueOf(lossSum));
        ftFinishNodeStatisticsDTO.setOther(String.valueOf(otherSum));
        ftFinishNodeStatisticsDTO.setMarkF(String.valueOf(markFSum));
        ftFinishNodeStatisticsDTO.setBackUp(String.valueOf(backUpSum));
        return InvokeResult.success(ftFinishNodeStatisticsDTO);
    }

    private void updateExtraOnSaveFTNode(FTLot ftLot, FTNode ftNode) {
        String nodeName = ftNode.getName();
        assert !Strings.isNullOrEmpty(nodeName);

        // 测试节点
        if (ftNode instanceof FTComposedTestNode) {
            FTComposedTestNode ftComposedTestNode = (FTComposedTestNode) ftNode;
            List<FTResult> ftResults = new ArrayList<>();

            List<SBL> sbls = ftComposedTestNode.getSbls();
            if (sbls.size() == 0)
                return;

            // 所有需要更新yield的ftResults
            ftResults.add(ftComposedTestNode.getResult());
            List<FTTest> ftTests = ftComposedTestNode.getTests();
            for (FTTest ftTest : ftTests) {
                ftResults.add(ftTest.getResult());
            }
//            for (FTResult ftResult : ftResults) {
//                recalculateFTResultYieldAndFail(ftResult, sbls);
//            }
        } else {

            // 对于非测试节点只需要更新finalYield
            FTResult finalYield = ftNode.getResult();
            List<SBL> sbls = ftNode.getSbls();
            if (sbls.size() == 0)
                return;
//            recalculateFTResultYieldAndFail(finalYield, sbls);
        }
    }

    /**
     * 计算yield和fail并更新
     *
     * @param ftResult
     * @param sbls
     * @return
     */
    private void recalculateFTResultYieldAndFail(FTResult ftResult, List<SBL> sbls) {
        // 获取总量
        int total = Integer.parseInt(ftResult.getResultSum());

        // 统计所有pass品的bin数目和
        int pass = 0;
        int fail = 0;
        int[] binValues = FTResult.getBins(ftResult);
        fail = fail + Integer.parseInt(ftResult.getMarkF()) + Integer.parseInt(ftResult.getOther());
        for (SBL sbl : sbls) {
            // bin下标
            String binType = sbl.getType();
            binType = binType.startsWith("Bin") ? binType.substring(3) : binType;
            int binIndex = Integer.parseInt(binType) - 1;
            
            int binValue = binValues[binIndex];
            if (binValue == -1)
                continue;

            if (Objects.equals(SBLQuality.FAIL, sbl.getQuality())) {
                fail += binValue;   // 统计fail
            } else {
                pass += binValue;   // 统计pass
            }
        }
        if (total >= 1) {
            ftResult.setYield(String.valueOf(pass * 1.0 / total));
        }
        ftResult.setFail(String.valueOf(fail));
    }

    @Override
    public InvokeResult checkImportedReelCode(String filename, Long ftProcessId) {
        if (filename == null || ftProcessId == null) throw new IllegalArgumentException();
        FTProcess process = ftProcessApplication.get(ftProcessId);
        FTLot ftLot = process.getFtLot();
        Long ftLotId = ftLot.getId();
        return excelFacade.importReelCodeImpl(getClass(), filename, ftLotId);
    }
}
