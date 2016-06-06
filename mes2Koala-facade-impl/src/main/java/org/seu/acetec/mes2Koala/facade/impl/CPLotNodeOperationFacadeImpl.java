package org.seu.acetec.mes2Koala.facade.impl;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import net.sf.ezmorph.bean.MorphDynaBean;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openkoala.koala.commons.InvokeResult;
import org.openkoala.organisation.facade.EmployeeFacade;
import org.openkoala.organisation.facade.dto.EmployeeDTO;
import org.openkoala.security.org.facade.OAUserFacade;
import org.seu.acetec.mes2Koala.application.CPLotApplication;
import org.seu.acetec.mes2Koala.application.CPLotNodeOperationApplication;
import org.seu.acetec.mes2Koala.application.CPLotOptionLogApplication;
import org.seu.acetec.mes2Koala.application.CPProcessApplication;
import org.seu.acetec.mes2Koala.application.CPQDNApplication;
import org.seu.acetec.mes2Koala.application.DataCompensationApplication;
import org.seu.acetec.mes2Koala.application.IncrementNumberApplication;
import org.seu.acetec.mes2Koala.application.ProductionScheduleApplication;
import org.seu.acetec.mes2Koala.core.common.BeanUtils;
import org.seu.acetec.mes2Koala.core.domain.CPLot;
import org.seu.acetec.mes2Koala.core.domain.CPLotOptionLog;
import org.seu.acetec.mes2Koala.core.domain.CPNode;
import org.seu.acetec.mes2Koala.core.domain.CPProcess;
import org.seu.acetec.mes2Koala.core.domain.CPProductionSchedule;
import org.seu.acetec.mes2Koala.core.domain.CPQDN;
import org.seu.acetec.mes2Koala.core.domain.CPTestingNode;
import org.seu.acetec.mes2Koala.core.domain.CPWafer;
import org.seu.acetec.mes2Koala.core.domain.CustomerCPLot;
import org.seu.acetec.mes2Koala.core.domain.CustomerFTLot;
import org.seu.acetec.mes2Koala.core.domain.DataCompensation;
import org.seu.acetec.mes2Koala.core.domain.InternalLot;
import org.seu.acetec.mes2Koala.core.domain.Label;
import org.seu.acetec.mes2Koala.core.domain.Process;
import org.seu.acetec.mes2Koala.core.enums.CPNodeState;
import org.seu.acetec.mes2Koala.core.enums.CPWaferState;
import org.seu.acetec.mes2Koala.facade.CPLotFacade;
import org.seu.acetec.mes2Koala.facade.CPLotNodeOperationFacade;
import org.seu.acetec.mes2Koala.facade.CPQDNFacade;
import org.seu.acetec.mes2Koala.facade.ExcelFacade;
import org.seu.acetec.mes2Koala.facade.common.SenderMailUtils;
import org.seu.acetec.mes2Koala.facade.dto.CPInfoDTO;
import org.seu.acetec.mes2Koala.facade.dto.CPLotDTO;
import org.seu.acetec.mes2Koala.facade.dto.CPNodeDTO;
import org.seu.acetec.mes2Koala.facade.dto.CPQDNDTO;
import org.seu.acetec.mes2Koala.facade.dto.CPWaferDTO;
import org.seu.acetec.mes2Koala.facade.dto.TSKInfoDTO;
import org.seu.acetec.mes2Koala.facade.impl.assembler.CPLotAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.CPNodeAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.CPQDNAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.CPWaferAssembler;
import org.springframework.context.annotation.Scope;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Convert;

/**
 * @author yuxiangque
 * @version 2016/3/28
 */
@Named
public class CPLotNodeOperationFacadeImpl implements CPLotNodeOperationFacade {
	
    private static final  String mailTitle = "CP异常单通知";
    private static final  String mailContent1 = "你有1个CP品质异常单待处理（请在4小时内作出处理）,单号为：";
    private static final  String mailContent2 = ",具体位置为MES2系统下[生产管理]->[CP品质异常单]，请查收！";
    private static final  String lotID = "Lot ID";
    private static final  String waferID = "Wafer ID";
    private static final  String smicProdID = "SMIC Prod ID";
    private static final  String testProgram = "Test Program";
    private static final  String testerID = "Tester ID";
    private static final  String operatorID = "Operator ID";
    private static final  String startTime = "Start Time";
    private static final  String endTime = "End Time";
    private static final  String notchSide = "Notch side";
    private static final  String sortID = "Sort ID";
    private static final  String binDefinitionFile = "Bin Definition File";
    private static final  String gridXmax = "Grid Xmax";
    private static final  String gridYmax = "Grid Ymax";
    private static final  String fabSite = "Fab Site";
    private static final  String testSite = "Test Site";
    private static final  String probeCardID = "Probe Card ID";
    private static final  String loadBoardID = "Load Board ID";
    private static final  String romCode = "ROM Code";
    private static final  String xDieSize = "X Die Size";
    private static final  String yDieSize = "Y Die Size";
    private static final  String xStreet = "X Street";
    private static final  String yStreet = "Y Street";
    private static final  String customerCode = "Customer Code";
    private static final  String customerPartID = "Customer PartID";
    private static final  String rawData = "RawData";
    private static final  String dataEnd = "DataEnd";
    private static final  String server = "192.168.1.35";
    private static final  String ftpUserName = "file001";
    private static final  String ftpUserPwd = "admin001";
    private static final  String localDirectory = "D:/TSK/";
    @Inject
    CPLotNodeOperationApplication cpLotNodeOperationApplication;
    @Inject
    CPLotApplication cpLotApplication;
    @Inject
    CPProcessApplication cpProcessApplication;
    @Inject
    ProductionScheduleApplication productionScheduleApplication;
    @Inject
    private IncrementNumberApplication incrementNumberApplication;
    @Inject
    private CPQDNApplication cpQDNApplication;
    @Inject
    private CPLotOptionLogApplication cpLotOptionLogApplication;
    @Inject
    private OAUserFacade oaUserFacade;
    @Inject
    private SenderMailUtils senderMailUtils;
    @Inject
    private CPLotFacade cpLotFacade;
    @Inject
    private CPQDNFacade cpQDNFacade;
    @Inject
    private DataCompensationApplication dataCompensationApplication;
    @Inject
    private EmployeeFacade employeeFacade;
    
    @Override
    public InvokeResult startCPNode(Long processId) {
        try {
            cpLotNodeOperationApplication.startCPNode(processId);
            return InvokeResult.success();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return InvokeResult.failure(ex.getMessage());
        }
    }

    @Override
    public InvokeResult saveCPNode(Long processId, CPNodeDTO cpNodeDTO) {
        try {
            cpLotNodeOperationApplication.saveCPNode(processId, CPNodeAssembler.toEntity(cpNodeDTO));
            return InvokeResult.success();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return InvokeResult.failure(ex.getMessage());
        }
    }

    @Override
    public InvokeResult endCPNode(Long processId) {
        try {
            cpLotNodeOperationApplication.endCPNode(processId);
            return InvokeResult.success();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return InvokeResult.failure(ex.getMessage());
        }
    }
    
    /**
     * 跳站
     * 1. 首先检查外部的DTO中的state状态，保证不会又客户端修改状态
     * 2. 然后检查状态必须是进站而没出战
     * 3. 接着对Test站点进行良率卡控
     * 4. 最后更新出站信息，更新出站信息也是顺序查找，找到一个没出站的站点，对于其他状态作为异常处理
     *
     * @param processId
     * @return
     * @version 2016/4/14 HongYu
     */
    @Override
    public InvokeResult jumpCPNode(Long processId,CPNodeDTO cpNodeDTO) {
        try {
            cpLotNodeOperationApplication.jumpCPNode(processId,CPNodeAssembler.toEntity(cpNodeDTO),cpNodeDTO.getTargetNode());
            return InvokeResult.success();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return InvokeResult.failure(ex.getMessage());
        }
    }

    @Override
    public InvokeResult hold(CPQDNDTO cpqdndto) {
        try {
        	cpqdndto.setLotId(cpqdndto.getId());
        	cpqdndto.setId(null);
            CPLot cpLot = cpLotApplication.get(cpqdndto.getLotId());
            // 1.建立QDN单
            CPQDN cpQDN = new CPQDN();
            BeanUtils.copyProperties(cpqdndto, cpQDN);
            CPProcess processDTO = cpProcessApplication.findByCPLotId(cpLot.getId());
            cpQDN.setCpLot(cpLot);
            cpQDN.setFlowNow(cpLot.getCurrentState());
            cpQDN.setFlow(processDTO.getContent());
            cpQDN.setCustomerName(cpLot.getCustomerCPLot().getCpInfo().getCustomerDirect().getChineseName());
            cpQDN.setQdnNo(incrementNumberApplication.nextQdnNumber());
            cpQDN.setStatus(0);
            cpQDN.setIsFuture(false);
            cpQDN.setType("CP异常单");
            cpQDNApplication.creatCPQDN(cpQDN);
            // 2.记录批次操作记录
            CPLotOptionLog cpLotOptionLog = new CPLotOptionLog();
            BeanUtils.copyProperties(cpqdndto, cpLotOptionLog);
            cpLotOptionLog.setCpLot(cpLot);
            cpLotOptionLog.setRemark(cpqdndto.getNote());
            cpLotOptionLog.setFlownow(cpQDN.getFlowNow());
            cpLotOptionLog.setOptType("开Hold");
            cpLotOptionLogApplication.creatCPLotOptionLog(cpLotOptionLog);
            // 3.改变Lot状态
            cpLot.setLastModifyTimestamp(cpqdndto.getLastModifyTimestamp());
            cpLot.setLastModifyEmployNo(cpLot.getLastModifyEmployNo());
            cpLot.setHoldState(InternalLot.HOLD_STATE_HOLD);
            cpLot.setCurrentState("Hold");
            cpLotApplication.update(cpLot);
            // 4.给部门负责人发送邮件
            EmployeeDTO toPerson = employeeFacade.getEmployeeById(Long.parseLong(cpqdndto.getToPerson()));
            String mailaddress = toPerson.getEmail();
            //String mailaddress = "yu.hong@acetecsemi.net";
            senderMailUtils.sendMailHelper(mailTitle, mailContent1 + cpQDN.getQdnNo() + mailContent2 , mailaddress);
            return InvokeResult.success();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return InvokeResult.failure(ex.getMessage());
        }
    }

    @Override
    public InvokeResult unhold(CPLotDTO cpLotDTO) {
        try {
            // 1.查询lot是否有未处理的QDN单
            List<CPQDNDTO> list = cpQDNFacade.findAllDoingQDNByLotId(cpLotDTO.getId());
            if (list != null && list.size() > 0) {
                return InvokeResult.failure("QDN单:" + list.get(0).getQdnNo()
                        + " 未完结！");
            }
            // 2.如果没有未处理的QDN单改变lot状态取消Hold标志
            CPLot cpLot = cpLotApplication.get(cpLotDTO.getId());
            if ( !cpLot.getHoldState().equals(InternalLot.HOLD_STATE_HOLD) && !cpLot.getHoldState().equals(InternalLot.HOLD_STATE_FUTURE_HOLD)){
            	return InvokeResult.failure("Lot未Hold，请确认！");
            }
            CPProcess process = cpProcessApplication.findByCPLotId(cpLot.getId());
            List<CPNode> cpNodes = process.getCpNodes();
            Collections.sort(cpNodes);
            for (int index = 0; index < cpNodes.size(); index++) {
                CPNode cpNode = cpNodes.get(index);
                switch (cpNode.getState()) {
                    case ENDED:  // 已经出站的跳过
                        break;
                    case STARTED:   // 进站了没出站
                        // 更新当前站点的状态
                        cpLot.setCurrentState("正在" +  cpNode.getName());
                        break;
                    case TO_START:
                        // 更新当前站点的状态
                        cpLot.setCurrentState("待" +  cpNode.getName());
                        break;	
                    default:  // 其他状态
                    	break;
                }
            }
            cpLot.setLastModifyTimestamp(cpLotDTO.getLastModifyTimestamp());
            cpLot.setLastModifyEmployNo(cpLotDTO.getLastModifyEmployNo());
            cpLot.setHoldState(InternalLot.HOLD_STATE_UNHOLD);
            cpLotApplication.update(cpLot);
            // 3.记录解Hold日志
            CPLotOptionLog cpLotOptionLog = new CPLotOptionLog();
            BeanUtils.copyProperties(cpLotDTO, cpLotOptionLog);
            cpLotOptionLog.setId(null);
            cpLotOptionLog.setCpLot(cpLot);
            cpLotOptionLog.setRemark("解Hold");
            cpLotOptionLog.setFlownow(cpLot.getCurrentState());
            cpLotOptionLog.setOptType("解Hold");
            cpLotOptionLog.setSlectInfo(cpLotDTO.getSelectInfo());
            cpLotOptionLog.setReworkInfo(cpLotDTO.getReworkInfo());
            cpLotOptionLogApplication.creatCPLotOptionLog(cpLotOptionLog);

            return InvokeResult.success("成功解Hold!");
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return InvokeResult.failure(ex.getMessage());
        }
    }

    @Override
    public InvokeResult futureHold(CPQDNDTO cpQDNDTO) {
    	cpQDNDTO.setLotId(cpQDNDTO.getId());
    	cpQDNDTO.setId(null);
        CPLot cpLot = cpLotApplication.get(cpQDNDTO.getLotId());
        // 1.建立QDN单
        CPQDN cpQDN = new CPQDN();
        BeanUtils.copyProperties(cpQDNDTO, cpQDN);
        CPProcess processDTO = cpProcessApplication.findByCPLotId(cpLot.getId());
        cpQDN.setCpLot(cpLot);
        cpQDN.setFlow(processDTO.getContent());
        if (cpQDNDTO.getFlowNow() == null) {
            cpQDN.setFlowNow(cpLot.getCurrentState());
        } else {
            cpQDN.setFlowNow(cpQDNDTO.getFlowNow());
        }
        cpQDN.setCustomerName(cpLot.getCustomerCPLot().getCpInfo().getCustomerDirect().getChineseName());
        cpQDN.setQdnNo(incrementNumberApplication.nextQdnNumber());
        cpQDN.setStatus(0);
        cpQDN.setIsFuture(true);
        cpQDN.setType("CP异常单");
        cpQDNApplication.creatCPQDN(cpQDN);
        // 2.记录批次操作记录
        CPLotOptionLog cpLotOptionLog = new CPLotOptionLog();
        BeanUtils.copyProperties(cpQDNDTO, cpLotOptionLog);
        cpLotOptionLog.setCpLot(cpLot);
        cpLotOptionLog.setRemark(cpQDNDTO.getNote());

        cpLotOptionLog.setFlownow(processDTO.getName());
        cpLotOptionLog.setOptType("Future Hold");
        cpLotOptionLogApplication.creatCPLotOptionLog(cpLotOptionLog);
        // 3.改变Lot状态
        cpLot.setLastModifyTimestamp(cpQDNDTO.getLastModifyTimestamp());
        cpLot.setLastModifyEmployNo(cpLot.getLastModifyEmployNo());
        //cpLot.setHoldState(InternalLot.HOLD_STATE_FUTURE_HOLD);
        cpLotApplication.update(cpLot);
        // 4.给部门负责人发送邮件
        EmployeeDTO toPerson = employeeFacade.getEmployeeById(Long.parseLong(cpQDNDTO.getToPerson()));
        String mailaddress = toPerson.getEmail();
        //String mailaddress = "yu.hong@acetecsemi.net";
        senderMailUtils.sendMailHelper(mailTitle, mailContent1 + cpQDN.getQdnNo() + mailContent2 , mailaddress);
        return InvokeResult.success();
    }
    
    @Override
    @Scope("prototype")
    public InvokeResult separateCPLot(Long id, List<CPLotDTO> cpLotDTOs) {
        try {
        	boolean flag = true;
        	CPLot cpLot = cpLotApplication.get(id);
        	cpLotNodeOperationApplication.separateCPLotCheck(cpLot);
        	cpLot.setShowFlag(true);
        	cpLotApplication.update(cpLot);
        	CPLotDTO cpLotDTO = new CPLotDTO();
        	List<CPWafer> cpwaferlisttemp = new ArrayList();
    		cpwaferlisttemp = cpLot.getCpWafers();
        	for (int i=0 ;i < cpLotDTOs.size();i++) {
        		cpLotDTO = cpLotDTOs.get(i);
        		cpLot.setParentIntegrationIds(cpLot.getParentIntegrationIds() != null ? cpLot.getParentIntegrationIds() :cpLot.getInternalLotNumber());
        		cpLot.setInternalLotNumber(cpLotDTO.getInternalLotNumber());
        		cpLot.setDiskContent(cpLotDTO.getDiskContent());
        		cpLot.setCurrentState(cpLotDTO.getCurrentState());
        		cpLot.setShipmentProductNumber(cpLotDTO.getShipmentProductNumber());
        		cpLot.setId(null);
        		cpLot.setShowFlag(null);
        		List cpwaferlist = new ArrayList();
        		cpLot.setCpWafers(null);
        		for (int j=0;j<cpLotDTO.getcPWaferDTOs().size();j++){
        	    	JSON json = JSONObject.fromObject(cpLotDTO.getcPWaferDTOs().get(j)); 
        	    	MorphDynaBean m = (MorphDynaBean) JSONSerializer.toJava(json);
        	    	JSONObject  jSONObject  = JSONObject.fromObject(m);
        	    	CPWaferDTO cpWaferDTO = (CPWaferDTO) JSONObject.toBean(jSONObject,CPWaferDTO.class);
        	    	for (int a = 0;a <cpwaferlisttemp.size();a++ ){
        	    		if (cpwaferlisttemp.get(a).getInternalWaferCode().equals(cpWaferDTO.getInternalWaferCode())) {
        	    			cpwaferlisttemp.get(a).setId(null);
                	    	cpwaferlist.add(cpwaferlisttemp.get(a));
        	    		}
        	    	}
        		}
        		cpLot.setCpWafers((List<CPWafer>)cpwaferlist);
        		cpLot.setQuantity((long)((List<CPWafer>)cpwaferlist).size());
        		cpLot.getCpProcess().setId(null);
        		for (int q=0;q<cpLot.getCpProcess().getCpNodes().size();q++){
        		    cpLot.getCpProcess().getCpNodes().get(q).setId(null);
        		    if (cpLot.getCpProcess().getCpNodes().get(q) instanceof CPTestingNode) {
        		    	CPTestingNode cpTestingNode = (CPTestingNode)cpLot.getCpProcess().getCpNodes().get(q);
        		    	cpTestingNode.getTestProgram().setId(null);
        		    }
        		}
        		cpLotApplication.create(cpLot); // 创建CPLot
        		for (CPNode cpNode : cpLot.getCpProcess().getCpNodes()) {
                    if (cpNode instanceof CPTestingNode) {
                    	if (flag == true) {
                    		for ( CPProductionSchedule cpProductionSchedule : ((CPTestingNode) cpNode).getCpProductionSchedules()){
                        		cpProductionSchedule.setLogic(1);
                        		productionScheduleApplication.update(cpProductionSchedule);
                        	}
                    	} 
                        productionScheduleApplication.createNewCpSchedule(null, cpNode.getId());
                    }
                }
        		flag = false;
            	// 操作记录
    			CPLotOptionLog cpLotOptionLog = new CPLotOptionLog();
    			BeanUtils.copyProperties(cpLotDTOs.get(0), cpLotOptionLog);
    			cpLotOptionLog.setCpLot(cpLot);
    			cpLotOptionLog.setOptType("分批");
    			cpLotOptionLog.setRemark("分批");
    			cpLotOptionLog.setFlownow(cpLotDTO.getCurrentState());
    			cpLotOptionLogApplication.creatCPLotOptionLog(cpLotOptionLog);
        	}
            return InvokeResult.success();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return InvokeResult.failure(ex.getMessage());
        }
    }
    
    @Override
	public InvokeResult mergeLot(String[] id, CPLotDTO cpLotDTO) {
    	try {
    		List<CPWafer> cpwaferlist = new ArrayList();
    		
        	for (int i=0 ; i<id.length;i++){
        		CPLot cpLot = cpLotApplication.get(Long.valueOf(id[i]));
        		CPProcess process = cpLot.getCpProcess(); // 找到流程
        		if (Objects.equals(cpLot.getHoldState(), CPLot.HOLD_STATE_HOLD)) {
        			throw new RuntimeException("分批失败：开HOLD");
        		}
        		// 出站更新信息
        		List<CPNode> cpNodes = process.getCpNodes();
        		Collections.sort(cpNodes);
        		for (int index = 0; index < cpNodes.size(); index++) {
        			CPNode cpNode = cpNodes.get(index);
        			switch (cpNode.getState()) {
        			case STARTED:   // 进站了没出站
        				 //进站过程中不能分批
        			     throw new RuntimeException("合批失败：进站后未出站不能合批");
        			default:  // 未经过站点
        				break;
        			}
        		}
        		for (CPNode cpNode : process.getCpNodes()) {
            		if (cpNode instanceof CPTestingNode) {
            			CPTestingNode cpTestingNode = (CPTestingNode)cpNode;
            			for ( CPProductionSchedule cpProductionSchedule : ((CPTestingNode) cpNode).getCpProductionSchedules()){
                    		cpProductionSchedule.setLogic(1);
                    		productionScheduleApplication.update(cpProductionSchedule);
                    	}
            		}
            	}
        		cpwaferlist.addAll(cpLot.getCpWafers());
        		cpLot.setShowFlag(true);
            	cpLotApplication.update(cpLot);
        	}
        	CPLot newcpLot = cpLotApplication.get(Long.valueOf(id[0]));
        	//newcpLot.setShipmentProductNumber(newcpLot.getShipmentProductNumber().substring(0,newcpLot.getShipmentProductNumber().lastIndexOf("-")));
        	newcpLot.getCpProcess().setId(null);
        	if ( cpLotDTO.getInternalLotNumber().equals(newcpLot.getParentIntegrationIds())) {
        		newcpLot.setParentIntegrationIds(null);
        	}
        	newcpLot.setId(null);
    		for (int q=0;q<newcpLot.getCpProcess().getCpNodes().size();q++){
    			newcpLot.getCpProcess().getCpNodes().get(q).setId(null);
    			if (newcpLot.getCpProcess().getCpNodes().get(q) instanceof CPTestingNode) {
    		    	CPTestingNode cpTestingNode = (CPTestingNode)newcpLot.getCpProcess().getCpNodes().get(q);
    		    	cpTestingNode.getTestProgram().setId(null);
    		    }
    		}
    		for (int a=0; a<cpwaferlist.size();a++){
    			cpwaferlist.get(a).setId(null);
    			cpwaferlist.get(a).setCpLot(newcpLot);;
    			cpwaferlist.get(a).getCpLot().setCpProcess(newcpLot.getCpProcess());
    		} 
        	newcpLot.setCpWafers(cpwaferlist);
        	newcpLot.setQuantity((long)(cpwaferlist.size()));
        	
        	BeanUtils.copyProperties(cpLotDTO, newcpLot);
        	cpLotApplication.create(newcpLot);
        	for (CPNode cpNode : newcpLot.getCpProcess().getCpNodes()) {
                if (cpNode instanceof CPTestingNode) {
                    productionScheduleApplication.createNewCpSchedule(null, cpNode.getId());
                }
            }
        	// 操作记录
    		CPLotOptionLog cpLotOptionLog = new CPLotOptionLog();
    		BeanUtils.copyProperties(cpLotDTO, cpLotOptionLog);
    		cpLotOptionLog.setCpLot(newcpLot);
    		cpLotOptionLog.setOptType("合批");
    		cpLotOptionLog.setRemark("合批");
    		cpLotOptionLog.setFlownow(newcpLot.getCurrentState());
    		cpLotOptionLogApplication.creatCPLotOptionLog(cpLotOptionLog);
        	return InvokeResult.success();
    	} catch (RuntimeException ex) {
            ex.printStackTrace();
            return InvokeResult.failure(ex.getMessage());
        }
	}

    @Override
    public InvokeResult integrateCPLots(Long[] cpLotIds) {
        try {
            cpLotNodeOperationApplication.integrateCPLots(cpLotIds);
            return InvokeResult.success();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return InvokeResult.failure(ex.getMessage());
        }
    }

	@Override
	public void exportExcel(List<Object> cpLotDTOs, String fileName) {
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("CP产品信息情报");
        sheet.setDefaultColumnWidth(16);
        sheet.setDefaultRowHeightInPoints(20);
        // 第三步，在sheet中添加表头第0行t
        HSSFRow row = sheet.createRow((int) 0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);

        HSSFCell  cell = row.createCell(0);
        cell.setCellValue("状态");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("客户批号");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("内部批号");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("封装厂批号");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("客户编号");
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellValue("产品编号");
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellValue("客户型号");
        cell.setCellStyle(style);
        cell = row.createCell(7);
        cell.setCellValue("出货型号");
        cell.setCellStyle(style);
        cell = row.createCell(8);
        cell.setCellValue("数量");
        cell.setCellStyle(style);
        cell = row.createCell(9);
        cell.setCellValue("片号");
        cell.setCellStyle(style);
        cell = row.createCell(10);
        cell.setCellValue("测试设备");
        cell.setCellStyle(style);
        cell = row.createCell(11);
        cell.setCellValue("MASK Name");
        cell.setCellStyle(style);

	     // 第五步，写入实体数据 实际应用中这些数据从数据库得到，   
	     for (int i = 0; i < cpLotDTOs.size(); i++)  
	     {  
	    	 CPLotDTO cpLot = (CPLotDTO) cpLotDTOs.get(i);
	         row = sheet.createRow((int) i + 1);   
	         HSSFCell datacell = row.createCell(0);
	         datacell.setCellValue(cpLot.getCustomerCPLotDTO().getState());
	         datacell.setCellStyle(style);
	         datacell = row.createCell(1);
	         datacell.setCellValue(cpLot.getCustomerCPLotDTO().getCustomerLotNumber()); 
	         datacell.setCellStyle(style);
	         datacell = row.createCell(2);
	         datacell.setCellValue(cpLot.getInternalLotNumber()); 
	         datacell.setCellStyle(style);
	         datacell = row.createCell(3);
             datacell.setCellValue(cpLot.getCustomerCPLotDTO().getPackingLot());
             datacell.setCellStyle(style);
	         datacell = row.createCell(4);
	         datacell.setCellValue(cpLot.getCustomerCPLotDTO().getCustomerNumber());
	         datacell.setCellStyle(style);
	         datacell = row.createCell(5);
	         datacell.setCellValue(cpLot.getCustomerCPLotDTO().getInternalProductDTO().getCustomerProductNumber()); 
	         datacell.setCellStyle(style);
	         datacell = row.createCell(6);
	         datacell.setCellValue(cpLot.getCustomerCPLotDTO().getInternalProductDTO().getShipmentProductNumber());
	         datacell.setCellStyle(style);
	         datacell = row.createCell(7);
	         datacell.setCellValue(cpLot.getCustomerCPLotDTO().getInternalProductDTO().getShipmentProductNumber());
	         datacell.setCellStyle(style);
	         datacell = row.createCell(8);
	         datacell.setCellValue(cpLot.getDiskContent() !=null ? cpLot.getQuantity() : 0);
	         datacell.setCellStyle(style);
	         datacell = row.createCell(9);
             datacell.setCellValue(cpLot.getCustomerCPLotDTO().getPackingLot());
             datacell.setCellStyle(style);
	         datacell = row.createCell(10);
	         datacell.setCellValue("");
	         datacell.setCellStyle(style);
	         datacell = row.createCell(11);
	         datacell.setCellValue(cpLot.getCustomerCPLotDTO().getMaskName());
	         datacell.setCellStyle(style);
	     }  
	     // 第六步，将文件存到指定位置   
	     try  
	     {  
	 		 //获取类文件所在的路径，为获取web应用路径做准备
	 		 String classPath = this.getClass().getClassLoader().getResource("").getPath();
	 		 //获取模板路径与导出文件的路径
	 		 String templatePath = classPath.substring(0, classPath.indexOf("WEB-INF")) + "excel/";
	 		 String exportPath = templatePath + "export/";
	         FileOutputStream fout = new FileOutputStream(exportPath + fileName);  
	         wb.write(fout);
	         fout.close();
	     }  
	     catch (IOException e)  
	     {  
	         e.printStackTrace();  
	     }  
	}

	@Override
	public InvokeResult getLabelInfo(Long id) {
		try{
			CPLot cpLot = cpLotApplication.get(id);
			Map<String, Object> map = new HashMap<>();
			List<Label> labels = cpLot.getCustomerCPLot()
	                .getCpInfo().getLabels();
	        for (Label label : labels) {
	           map.put("labelName", label.getLabelName());
	        }
	        map.put("productNo", cpLot.getShipmentProductNumber());
	        map.put("ppo", cpLot.getCustomerCPLot().getCustomerPPO());
	        map.put("customerNo", cpLot.getCustomerCPLot()
	                .getCustomerNumber());
	        map.put("cpLotNo", cpLot.getInternalLotNumber());
	        map.put("waferSize", cpLot.getCustomerCPLot().getCpInfo().getWaferSize());
	        map.put("grossDie", cpLot.getCustomerCPLot().getCpInfo().getGrossDie());
	        map.put("warningType", cpLot.getCustomerCPLot().getCpInfo().getWarningType());
	        map.put("productRequire", cpLot.getCustomerCPLot().getCpInfo().getProductRequire());
	        map.put("touchQty", cpLot.getCustomerCPLot().getCpInfo().getTouchQty());
	        map.put("maskName", cpLot.getCustomerCPLot().getMaskName());
	        map.put("internalLotNumber", cpLot.getInternalLotNumber());
	        map.put("cpWafers", cpLot.getCpWafers());
	        
			return InvokeResult.success(map);
		}catch(Exception e) {
			return InvokeResult.failure("系统异常，标签打印失败");
		}
	}

	@Override
	public InvokeResult dataCompensationChk(Long id) {
		try{
			CPLot cpLot = cpLotApplication.get(id);
			if (!"1409-SMI-SH".equals(cpLot.getCustomerCPLot().getCustomerNumber())) {
				throw new RuntimeException("数据补偿失败；请检查客户信息！");
			} 
			CPProcess process = cpLot.getCpProcess(); // 找到流程
			if (Objects.equals(cpLot.getHoldState(), CPLot.HOLD_STATE_HOLD)) {
				throw new RuntimeException("数据补偿失败：开HOLD");
			}
			// 取FQC站点turn
			List<CPNode> cpNodes = process.getCpNodes();
			Collections.sort(cpNodes);
			int targetNodeTurn = 0;
			int cunrrentNodeTurn = 0;
			for (int i = 0; i < cpNodes.size(); i++) {
				if ("FQC".equals(cpNodes.get(i).getName())) {
					targetNodeTurn = cpNodes.get(i).getTurn();
				}
			}
	        
			if ( targetNodeTurn == 0 ) {
				throw new RuntimeException("数据补偿失败：无FQC站点");
			}
			
			for (int index = 0; index < cpNodes.size(); index++) {
				CPNode cpNode = cpNodes.get(index);
				switch (cpNode.getState()) {
				case STARTED:   // 进站了没出站
					 cunrrentNodeTurn = cpNode.getTurn();
					 if (cunrrentNodeTurn <= targetNodeTurn) {
						 throw new RuntimeException("数据补偿失败：请在FQC站点后数据补偿");
					 }
				case TO_START:  //来到这个站点
				  	 cunrrentNodeTurn = cpNode.getTurn();
					 if (cunrrentNodeTurn <= targetNodeTurn) {
						 throw new RuntimeException("数据补偿失败：请在FQC站点后数据补偿");
					 }
				default:  // 未经过站点
					break;
				}
			}
			return InvokeResult.success(CPLotAssembler.toDTO(cpLot));
		} catch (RuntimeException ex) {
            ex.printStackTrace();
            return InvokeResult.failure(ex.getMessage());
        }
	}

	@Override
	public InvokeResult dataCompensation(String filePath) {
		File file = new File(filePath);
		BufferedReader  reader = null; 
	  try {
			 reader = new BufferedReader(new FileReader(file));
			 String data;
			 String rawDataInfo = "";
			 String dataName = "";
			 String dataVal = "";
			 int flag = 0;
			 DataCompensation dataCompensation = new DataCompensation();
			 while((data = reader.readLine())!= null)
			 {
				 data = data.trim();
				 if (!"RawData".equals(data) && !"DataEnd".equals(data) && flag == 0 ) {
					 dataName = data.substring(0, data.indexOf(":"));
					 dataVal = data.substring(data.indexOf(":") + 1);
					 switch(dataName) {
					    case lotID:
					    	dataCompensation.setLotID(dataVal);
					    	break;
					    case waferID:
					    	dataCompensation.setWaferID(dataVal);
					    	break;
					    case smicProdID:
					    	dataCompensation.setSmicProdID(dataVal);
					    	break;
					    case testProgram:
					    	dataCompensation.setTestProgram(dataVal);
					    	break;
					    case testerID:
					    	dataCompensation.setTesterID(dataVal);
					    	break;
					    case operatorID:
					    	dataCompensation.setOperatorID(dataVal);
					    	break;
					    case startTime:
					    	dataCompensation.setStartTime(dataVal);
					    	break;
					    case endTime:
					    	dataCompensation.setEndTime(dataVal);
					    	break;
					    case notchSide:
					    	dataCompensation.setNotchSide(dataVal);
					    	break;
					    case sortID:
					    	dataCompensation.setSortID(dataVal);
					    	break;
					    case binDefinitionFile:
					    	dataCompensation.setBinDefinitionFile(dataVal);
					    	break;
					    case gridXmax:
					    	dataCompensation.setGridXmax(dataVal);
					    	break;
					    case gridYmax:
					    	dataCompensation.setGridYmax(dataVal);
					    	break;
					    case fabSite:
					    	dataCompensation.setFabSite(dataVal);
					    	break;
					    case testSite:
					    	dataCompensation.setTestSite(dataVal);
					    	break;
					    case probeCardID:
					    	dataCompensation.setProbeCardID(dataVal);
					    	break;
					    case loadBoardID:
					    	dataCompensation.setLoadBoardID(dataVal);
					    	break;
					    case romCode:
					    	dataCompensation.setRomCode(dataVal);
					    	break;
					    case xDieSize:
					    	dataCompensation.setxDieSize(dataVal);
					    	break;
					    case yDieSize:
					    	dataCompensation.setyDieSize(dataVal);
					    	break;
					    case xStreet:
					    	dataCompensation.setxStreet(dataVal);
					    	break;
					    case yStreet:
					    	dataCompensation.setyStreet(dataVal);
					    	break;
					    case customerCode:
					    	dataCompensation.setCustomerCode(dataVal);
					    	break;
					    case customerPartID:
					    	dataCompensation.setCustomerPartID(dataVal);
					    	break;
					    case rawData:
					    	break;
					    case dataEnd:
					    	break;
					    default:
					    	break;
					  }
				 } else if ("RawData".equals(data) || "DataEnd".equals(data)){
					 flag = 1;
				 } else {
					 if (rawDataInfo == "") {
						 rawDataInfo = data;
					 } else {
						 rawDataInfo = rawDataInfo + "|" + data;
					 }
				 }
			 }
			 dataCompensation.setRawData(rawDataInfo);
			 dataCompensationApplication.creatDataCompensation(dataCompensation);
			 reader.close();
		   } catch (FileNotFoundException e) {
			  e.printStackTrace();
			  return InvokeResult.failure(e.getMessage());
		   } catch (IOException e) {
		 	  e.printStackTrace();
		 	  return InvokeResult.failure(e.getMessage());
		   } finally {  
              if (reader != null) {  
                try {  
                     reader.close();  
                 } catch (IOException e1) {  
                 	 e1.printStackTrace();
                 	return InvokeResult.failure(e1.getMessage());
                 }  
              }  
           }  
      return InvokeResult.success();
	}

	@Override
	public InvokeResult getTskFileNames(String upDown,String directory) {
		 FTPClient ftpClient = new FTPClient();
		 List fileLists = new ArrayList();
		 Map<String, Object> map = new HashMap<>();
	     
		 try {
			ftpClient.connect(server);
			ftpClient.login(ftpUserName, ftpUserPwd);
			int reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
			    throw new RuntimeException("连接FTP服务器失败!");
			}
            ftpClient.changeWorkingDirectory("/" + "map"+ "/" + upDown);
            FTPFile[] ftpFiles = ftpClient.listFiles();
			for (int i = 0 ; i < ftpFiles.length ; i++) {
				String[] directoryName = new String[ftpFiles.length];
				FTPFile file = ftpFiles[i];
				if (file.isDirectory() ) {
					if (directory == null || directory == "") {
						if (!file.getName().contains("..") && !file.getName().contains(".")) {
							fileLists.add(file.getName());
							//directoryName[i] = file.getName();
						}
					} else {
						if (!file.getName().contains("..") && !file.getName().contains(".") 
								&& file.getName().contains(directory)) {
							fileLists.add(file.getName());
							//directoryName[i] = file.getName();
						}
					}
				}
			}
			map.put("directoryName", fileLists);
			return InvokeResult.success(map);
		} catch (SocketException e) {
			e.printStackTrace();
			return InvokeResult.failure(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			return InvokeResult.failure(e.getMessage());
		}
	}
	
	@Override
	public InvokeResult resolveFile(String upDown,String directoryName) {
		 FTPClient ftpClient = new FTPClient();
         List<TSKInfoDTO> tskinfos = new ArrayList<TSKInfoDTO>();
         FileInputStream fileIn = null;
         DataInputStream in = null;
		 try {
			ftpClient.connect(server);
			ftpClient.login(ftpUserName, ftpUserPwd);
			int reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
			    throw new RuntimeException("连接FTP服务器失败!");
			}
            ftpClient.changeWorkingDirectory("/" + "map"+ "/" + upDown +  "/" + directoryName);
            FTPFile[] ftpFiles = ftpClient.listFiles();
			for (int i = 0 ; i < ftpFiles.length ; i++) {
				TSKInfoDTO tskinfo = new TSKInfoDTO();
				FTPFile file = ftpFiles[i];
				if (!file.getName().endsWith(".xtr") && !file.getName().endsWith(".inf") 
						&& !file.getName().contains("=")
						&& !file.getName().endsWith(".BCT") && !file.getName().endsWith(".DAT") ) {
					File folder = new File(localDirectory + upDown + "/" + directoryName);
					if (!folder.exists()) {
						folder.mkdirs();
					}
					ftpClient.retrieveFile( new String(file.getName().getBytes(), "iso-8859-1"), 
							new DataOutputStream(new FileOutputStream(localDirectory + upDown + "/" + directoryName + "/" +file.getName())));
					File f = new File(localDirectory + upDown + "/" + directoryName + "/" +file.getName());
					tskinfo.setFileName(file.getName());
					byte[] readBuff = new byte[(int)f.length()];
					fileIn = new FileInputStream(localDirectory + upDown + "/" + directoryName + "/" +file.getName());
				    in = new DataInputStream(fileIn); 
				    in.read(readBuff);
				    String operator_Name = getByte(readBuff,1,20);  
				    String device_Name = getByte(readBuff,21,36);
				    int Wafer_Size = getIntBinary(readBuff,37,38);
				    int Machine_No = getIntBinary(readBuff,39,40);
				    int index_Size_X = getIntBinary(readBuff,41,44);
				    int index_Size_Y = getIntBinary(readBuff,45,48);
				    int standard_Orientation_Flat_Direction = getIntBinary(readBuff,49,50);
				    int final_Editing_Machine = getIntBinary(readBuff,51,51);
				    int map_Version = getIntBinary(readBuff,52,52);
				    long columnsize = getIntBinary(readBuff,53,54);
				    long rowsize = getIntBinary(readBuff,55,56);
				    if ("P7241D".equals(device_Name))
		            {
		                columnsize += 2L;
		                rowsize += 2L;
				    	//columnsize = (int) (columnsize + 2L);
				    	//rowsize = (int) (rowsize + 2L);
		            }
				    int map_Data_Form = getIntBinary(readBuff,57,60);
				    String wafer_ID = getByte(readBuff,61,81);
				    int number_of_Probing = getIntBinary(readBuff,82,82);
				    String lot_No = getByte(readBuff,83,100);
				    int cassette_No = getIntBinary(readBuff,101,102);
				    int slot_No = getIntBinary(readBuff,103,104);
				    int x_axis_coordinates_increase_direction = getIntBinary(readBuff,105,105);
				    int y_axis_coordinates_increase_direction = getIntBinary(readBuff,106,106);
				    int reference_die_setting_procedures = getIntBinary(readBuff,107,107);
				    int reserved_108 = getIntBinary(readBuff,108,108);
				    int target_die_position_X = getIntBinary(readBuff,109,112);
				    int target_die_position_Y = getIntBinary(readBuff,113,116);
				    int refdieX = getIntBinary(readBuff,117,118);
				    if (refdieX > 20)
		            {
		                refdieX = 1;
		            }
				    int refdieY = getIntBinary(readBuff,119,120);
				    int probing_start_position = getIntBinary(readBuff, 121, 121);
				    int probing_direction = getIntBinary(readBuff, 122, 122);
				    int reserved_123_124 = getIntBinary(readBuff, 123, 124);
				    int distance_X_to_wafer_center_die_origin = getIntBinary(readBuff, 125, 128);
				    int distance_Y_to_wafer_center_die_origin = getIntBinary(readBuff, 129, 132);
				    int coordinator_X_of_wafer_center_die = getIntBinary(readBuff, 133, 136);
				    int coordinator_Y_of_wafer_center_die = getIntBinary(readBuff, 137, 140);
				    int first_Die_Coordinator_X = getIntBinary(readBuff, 141, 144);
				    String startTime_Year = getByte(readBuff, 149, 150);
				    String startTime_Month = getByte(readBuff, 151, 152);
				    String startTime_Day = getByte(readBuff, 153, 154);
				    String startTime_Hour = getByte(readBuff, 155, 156);
				    String startTime_Minute = getByte(readBuff, 157, 158);
				    int startTime_Reserve = getIntBinary(readBuff, 159, 160);
				    String endTime_Year = getByte(readBuff, 161, 162);
				    String endTime_Month = getByte(readBuff, 163, 164);
				    String endTime_Day = getByte(readBuff, 165, 166);
				    String endTime_Hour = getByte(readBuff, 167, 168);
				    String endTime_Minute = getByte(readBuff, 169, 170);
				    int endTime_Reserve = getIntBinary(readBuff, 171, 172);
		            String loadTime_Year = getByte(readBuff, 173, 174);
		            String loadTime_Month = getByte(readBuff, 175, 176);
		            String loadTime_Day = getByte(readBuff, 177, 178);
		            String loadTime_Hour = getByte(readBuff, 179, 180);
		            String loadTime_Minute = getByte(readBuff, 181, 182);
		            int loadTime_Reserve = getIntBinary(readBuff, 183, 184);
		            String unLoadTime_Year = getByte(readBuff, 185, 186);
		            String unLoadTime_Month = getByte(readBuff, 187, 188);
		            String unLoadTime_Day = getByte(readBuff, 189, 190);
		            String unLoadTime_Hour = getByte(readBuff, 191, 192);
		            String unLoadTime_Minute = getByte(readBuff, 193, 194);
		            int unLoadTime_Reserve = getIntBinary(readBuff, 195, 196);
		            int machine_No_1 = getIntBinary(readBuff, 197, 200);
		            int machine_No_2 = getIntBinary(readBuff, 201, 204);
		            int special_Characters = getIntBinary(readBuff, 205, 208);
		            int testing_End_Information = getIntBinary(readBuff, 209, 209);
		            int reserve_210 = getIntBinary(readBuff, 210, 210);
		            int tested_dice = getIntBinary(readBuff, 211, 212);
		            int tested_pass_dice = getIntBinary(readBuff, 213, 214);
		            int tested_fail_dice = getIntBinary(readBuff, 215, 216);
		            int test_Die_Information_Address = getIntBinary(readBuff, 217, 220);
		            int number_of_line_category_data = getIntBinary(readBuff, 221, 224);
		            int line_category_address = getIntBinary(readBuff, 225, 228);
		            String map_File_Configuration = byteToBinary(readBuff, 229, 230);
		            int max_Multi_Site = getIntBinary(readBuff, 231, 232);
		            int max_Categories = getIntBinary(readBuff, 233, 234);
		            int reserve_235236 = getIntBinary(readBuff, 235, 236);
		            int nTotalChips = (int)(columnsize * rowsize);
		            int Byte_of_Header_Information = 236;
		            int Byte_of_Extended_Header_Information = 172;

		            // Map_File_Configuration
		            int availability_of_Header_Information = mid(map_File_Configuration, 16, 17);
		            int availability_of_Test_Result_Information_per_Die = mid(map_File_Configuration, 15, 16);
		            int availability_of_Line_Category_Information = mid(map_File_Configuration, 14, 15);
		            int availability_of_Extension_Header_Information = mid(map_File_Configuration, 13, 14);
		            int availability_of_Test_Result_Information_per_Extension_Die = mid(map_File_Configuration, 12, 13);
		            int availability_of_Extension_Line_Category_Information = mid(map_File_Configuration, 11, 12);
		            int tsk_File_Size = (availability_of_Header_Information * Byte_of_Header_Information
                            + availability_of_Test_Result_Information_per_Die * nTotalChips * 6
                            + availability_of_Line_Category_Information * nTotalChips * 8);
		            int map[][][] = new int[(int) (rowsize + 1L)][ (int) (columnsize + 1L)][2];
		            long r_y = 0;
		            for (int p = 1; p <= rowsize; p++)
		            {
		                for (int q = 1; q <= columnsize; q++)
		                {
		                    int startPos = (int) (((test_Die_Information_Address) + (6 * (((p - 1) * ((int)columnsize)) + q - 1))) + 1);
		                    int length = (int) (((test_Die_Information_Address) + (6 * (((p - 1) * ((int)columnsize)) + q - 1))) + 6);
		                    String onedieresult = byteToBinary(readBuff, startPos, length).toString();
		                    int x = Integer.parseInt(onedieresult.substring(8, 17),2);
		                    int y = Integer.parseInt(onedieresult.substring(24, 33), 2);
		                    int category = Integer.parseInt(onedieresult.substring(43, 49), 2);
		                    long testDieVal = Long.parseLong(onedieresult.substring(17, 19), 2);
		                    long passDieVal = Long.parseLong(onedieresult.substring(1, 3), 2);
		                    boolean isTestDie;
		                    boolean isPassDie;

		                    if (testDieVal == 1)
		                    {
		                        isTestDie = true;
		                    }
		                    else
		                    {
		                    	isTestDie = false;
		                    }
		                    if (passDieVal == 1)
		                    {
		                    	isPassDie = true;
		                    }
		                    else
		                    {
		                    	isPassDie = false;
		                    }

		                    if (r_y >= 511 && q == (int)columnsize)
		                    {
		                        r_y++;
		                    }
		                    else if (r_y < 511)
		                    {
		                        r_y = y;
		                    }

		                    if (isTestDie)
		                    {
		                        int m = 0;
		                        int n = 0;
		                        if (refdieY <= 1)
		                        {
		                            n = (int)((x - refdieX)) + 1;
		                           m = (int)((r_y - refdieY)) + 1;
		                        }
		                        else
		                        {
		                            n = x;
		                          m = (int)r_y;
		                        }
		                        map[m][n][(int)(0L)] = (int)category;

		                        if (isPassDie)
		                        {
		                        	map[m][n][(int)(1L)] = 4;
		                        }
		                        else
		                        {
		                        	map[m][n][(int)(1L)] = 3;
		                        }
		                    }
		                }
		            }
		            int passbin2 = 0;
		            int passbin3 = 0;
		            int passbin4 = 0;
		            int num3 = 0;
		            int num4 = 0;
		            int num7 = 2;
		            int num8 = 0;
		            int num9 = 0;
		            for (num3 = 0; num3 < rowsize; num3++)
		            {
		                for (num4 = 0; num4 < columnsize; num4++)
		                {
		                    if ((map[num3][num4][1] != 0L) && (((int)map[num3][num4][0]) == num7))
		                    {
		                        num8++;
		                    }
		                    if ((map[num3][num4][1] != 0L) && (((int)map[num3][num4][0]) == 3))
		                    {
		                        num9++;
		                    }
		                    if ((map[num3][num4][1] != 0L) && (((int)map[num3][num4][0]) == 4))
		                    {
		                      passbin4++;
		                    }
		                }
		            }
		            passbin2 = num8;
		            passbin3 = num9;
		            int tested_die = 0;
		            int pass_die = 0;
		            int fail_die = 0;
		            int gooddie=0;
		            for (int x = 0; x < (int) (rowsize + 1L); x++)
		            {
		                for (int y = 0; y < (int) (columnsize + 1L); y++)
		                {
		                    int category = map[x][y][1];
		                    int binNum = map[x][y][0]; 
		                    switch (category)
		                    {
		                        case 0://not test die
		                            break;

		                        case 4://pass die
		                            tested_die++;
		                            pass_die++;
		                            break;

		                        case 3://fail die
		                            tested_die++;
		                            fail_die++;
		                            break;

		                        default:
		                            break;
		                    }
		                }
		            }
		            tskinfo.setTotal_Dice(tested_die);
		            tskinfo.setTotal_Pass_Dice(pass_die);
		            tskinfo.setTotal_Fail_Dice(fail_die);
		            if (tested_die != 0 && pass_die != 0)
		            {
		            	tskinfo.setTotal_Yield((String.format("%g",(Float.parseFloat(String.valueOf(pass_die)) * 100) / Integer.parseInt(String.valueOf(tested_die)))) + "%");
		            }
		            tskinfo.setPassbin2(String.valueOf(passbin2));
		            tskinfo.setPassbin3(String.valueOf(passbin3));
		            tskinfo.setPassbin4(String.valueOf(passbin4));
		            tskinfo.setWafer_ID(wafer_ID);
		            tskinfo.setLot_No(lot_No);
		            tskinfo.setDevice_Name(device_Name);
		            tskinfo.setOperator_Name(operator_Name);
		            tskinfo.setIndex_X(index_Size_X);
		            tskinfo.setIndex_Y(index_Size_Y);
		            if ((startTime_Year != null && startTime_Year != "") 
		            		&& (startTime_Month != null && startTime_Month != "")
		            		&& (startTime_Day != null && startTime_Day != "")
		            		&& (startTime_Hour != null && startTime_Hour != "")
		            		&& (startTime_Minute != null && startTime_Minute != "")) {
		            	tskinfo.setStart_Time(startTime_Year + "-" + startTime_Month + "-" 
		            		+ startTime_Day + "-" + startTime_Hour + "-" + startTime_Minute);
		            }
		            if ((endTime_Year != null && endTime_Year != "") 
		            		&& (endTime_Month != null && endTime_Month != "")
		            		&& (endTime_Day != null && endTime_Day != "")
		            		&& (endTime_Hour != null && endTime_Hour != "")
		            		&& (endTime_Minute != null && endTime_Minute != "")) {
		            	tskinfo.setEnd_Time(endTime_Year + "-" + endTime_Month + "-" 
			            		+ endTime_Day + "-" + endTime_Hour + "-" + endTime_Minute);
		            }
		            if ((loadTime_Year != null && loadTime_Year != "") 
		            		&& (loadTime_Month != null && loadTime_Month != "")
		            		&& (loadTime_Day != null && loadTime_Day != "")
		            		&& (loadTime_Hour != null && loadTime_Hour != "")
		            		&& (loadTime_Minute != null && loadTime_Minute != "")) {
		            	tskinfo.setLoad_Time(loadTime_Year + "-" + loadTime_Month + "-" 
			            		+ loadTime_Day + "-" + loadTime_Hour + "-" + loadTime_Minute);
		            }
		            if ((unLoadTime_Year != null && unLoadTime_Year != "") 
		            		&& (unLoadTime_Month != null && unLoadTime_Month != "")
		            		&& (unLoadTime_Day != null && unLoadTime_Day != "")
		            		&& (unLoadTime_Hour != null && unLoadTime_Hour != "")
		            		&& (unLoadTime_Minute != null && unLoadTime_Minute != "")) {
		            	tskinfo.setUnLoad_Time(unLoadTime_Year + "-" + unLoadTime_Month + "-" 
			            		+ unLoadTime_Day + "-" + unLoadTime_Hour + "-" + unLoadTime_Minute);
		            }
		            //gooddie = pass_die - Num(ACETEC_VI_FAIL) - Num(CLIENT_VI_FAIL);
		            tskinfo.setGood_Die(gooddie);
		            //this.total_die += tested_die;
		            //this.total_wafer++;
		            //this.total_passdie += pass_die;
		            //this.total_faildie += fail_die;
		           
		            //this.passbin2 += this.mapFile.passbin2;
		            //this.passbin3 += this.mapFile.passbin3;
		            //this.passbin4 += this.mapFile.passbin4;
		            tskinfos.add(tskinfo);
				}
			}
			return InvokeResult.success(tskinfos);
		} catch (SocketException e) {
			e.printStackTrace();
			return InvokeResult.failure(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			return InvokeResult.failure(e.getMessage());
		} finally {  
              if (in != null || fileIn != null) {  
                try {  
                	in.close();
		            fileIn.close(); 
                 } catch (IOException e1) {  
                 	 e1.printStackTrace();
                 	return InvokeResult.failure(e1.getMessage());
                 }  
              }  
        }  
	}
	
	private int getIntBinary(byte[] bts, int begin, int end)
    {
		String str = "0";
        for (int i = begin - 1; i < end; i++)
        {
        	String str2 = byte2bits(bts[i]);
        	int length = 8 - str2.length();
            for (int j = 0 ; j < length ; j++) {
            	str2 = "0" + str2;
            }
            str = str + str2;
        }
        return (int)Long.parseLong(str,2);
    }
	
	private String getByte(byte[] srcArr, int startPos, int endPos)
    {
        String str = new String(srcArr,(startPos-1),(endPos-startPos+1));
        return str.trim();
    }
	
	private String byteToBinary(byte[] srcArr, int startPos, int endPos)
    {
        String str = "0";
        for (int i = startPos - 1; i < endPos; i++)
        {
            	//int z = srcArr[i];
                String str2 = byte2bits(srcArr[i]);
                int length = 8 - str2.length();
                for (int j = 0 ; j < length ; j++) {
                	str2 = "0" + str2;
                }
                str = str + str2;
        }
        return str;
    }
	
	private int mid(String str, int start, int length)
    {
		return Integer.parseInt(str.substring(start, length));
    }
	
	public static String byte2bits(byte b) {

		int z = b;
		z |= 256;
		String str = Integer.toBinaryString(z);
		int len = str.length();
		return str.substring(len - 8, len);
	}

	@Override
	public InvokeResult mapCreate(String upDown, String directoryName,
			String fileName, String mapPath) {
		FTPClient ftpClient = new FTPClient();
		FileInputStream fileIn = null;
        DataInputStream in = null;
		try {
			ftpClient.connect(server);
			ftpClient.login(ftpUserName, ftpUserPwd);
			int reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
			    throw new RuntimeException("连接FTP服务器失败!");
			}
            ftpClient.changeWorkingDirectory("/" + "map"+ "/" + upDown +  "/" + directoryName);
            File folder = new File(localDirectory + upDown + "/" + directoryName);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			ftpClient.retrieveFile( new String(fileName.getBytes(), "iso-8859-1"), 
					new DataOutputStream(new FileOutputStream(localDirectory + upDown + "/" + directoryName + "/" + fileName)));
			File f = new File(localDirectory + upDown + "/" + directoryName + "/" + fileName);
			byte[] readBuff = new byte[(int)f.length()];
			fileIn = new FileInputStream(localDirectory + upDown + "/" + directoryName + "/" + fileName);
		    in = new DataInputStream(fileIn); 
		    in.read(readBuff);
		    String operator_Name = getByte(readBuff,1,20);  
		    String device_Name = getByte(readBuff,21,36);
		    int Wafer_Size = getIntBinary(readBuff,37,38);
		    int Machine_No = getIntBinary(readBuff,39,40);
		    int index_Size_X = getIntBinary(readBuff,41,44);
		    int index_Size_Y = getIntBinary(readBuff,45,48);
		    int standard_Orientation_Flat_Direction = getIntBinary(readBuff,49,50);
		    int final_Editing_Machine = getIntBinary(readBuff,51,51);
		    int map_Version = getIntBinary(readBuff,52,52);
		    long columnsize = getIntBinary(readBuff,53,54);
		    long rowsize = getIntBinary(readBuff,55,56);
		    int map_Data_Form = getIntBinary(readBuff,57,60);
		    String wafer_ID = getByte(readBuff,61,81);
		    int number_of_Probing = getIntBinary(readBuff,82,82);
		    String lot_No = getByte(readBuff,83,100);
		    int cassette_No = getIntBinary(readBuff,101,102);
		    int slot_No = getIntBinary(readBuff,103,104);
		    int x_axis_coordinates_increase_direction = getIntBinary(readBuff,105,105);
		    int y_axis_coordinates_increase_direction = getIntBinary(readBuff,106,106);
		    int reference_die_setting_procedures = getIntBinary(readBuff,107,107);
		    int reserved_108 = getIntBinary(readBuff,108,108);
		    int target_die_position_X = getIntBinary(readBuff,109,112);
		    int target_die_position_Y = getIntBinary(readBuff,113,116);
		    int refdieX = getIntBinary(readBuff,117,118);
		    if (refdieX > 20)
            {
                refdieX = 1;
            }
		    int refdieY = getIntBinary(readBuff,119,120);
		    int probing_start_position = getIntBinary(readBuff, 121, 121);
		    int probing_direction = getIntBinary(readBuff, 122, 122);
		    int reserved_123_124 = getIntBinary(readBuff, 123, 124);
		    int distance_X_to_wafer_center_die_origin = getIntBinary(readBuff, 125, 128);
		    int distance_Y_to_wafer_center_die_origin = getIntBinary(readBuff, 129, 132);
		    int coordinator_X_of_wafer_center_die = getIntBinary(readBuff, 133, 136);
		    int coordinator_Y_of_wafer_center_die = getIntBinary(readBuff, 137, 140);
		    int first_Die_Coordinator_X = getIntBinary(readBuff, 141, 144);
		    String startTime_Year = getByte(readBuff, 149, 150);
		    String startTime_Month = getByte(readBuff, 151, 152);
		    String startTime_Day = getByte(readBuff, 153, 154);
		    String startTime_Hour = getByte(readBuff, 155, 156);
		    String startTime_Minute = getByte(readBuff, 157, 158);
		    int startTime_Reserve = getIntBinary(readBuff, 159, 160);
		    String endTime_Year = getByte(readBuff, 161, 162);
		    String endTime_Month = getByte(readBuff, 163, 164);
		    String endTime_Day = getByte(readBuff, 165, 166);
		    String endTime_Hour = getByte(readBuff, 167, 168);
		    String endTime_Minute = getByte(readBuff, 169, 170);
		    int endTime_Reserve = getIntBinary(readBuff, 171, 172);
            String loadTime_Year = getByte(readBuff, 173, 174);
            String loadTime_Month = getByte(readBuff, 175, 176);
            String loadTime_Day = getByte(readBuff, 177, 178);
            String loadTime_Hour = getByte(readBuff, 179, 180);
            String loadTime_Minute = getByte(readBuff, 181, 182);
            int loadTime_Reserve = getIntBinary(readBuff, 183, 184);
            String unLoadTime_Year = getByte(readBuff, 185, 186);
            String unLoadTime_Month = getByte(readBuff, 187, 188);
            String unLoadTime_Day = getByte(readBuff, 189, 190);
            String unLoadTime_Hour = getByte(readBuff, 191, 192);
            String unLoadTime_Minute = getByte(readBuff, 193, 194);
            int unLoadTime_Reserve = getIntBinary(readBuff, 195, 196);
            int machine_No_1 = getIntBinary(readBuff, 197, 200);
            int machine_No_2 = getIntBinary(readBuff, 201, 204);
            int special_Characters = getIntBinary(readBuff, 205, 208);
            int testing_End_Information = getIntBinary(readBuff, 209, 209);
            int reserve_210 = getIntBinary(readBuff, 210, 210);
            int tested_dice = getIntBinary(readBuff, 211, 212);
            int tested_pass_dice = getIntBinary(readBuff, 213, 214);
            int tested_fail_dice = getIntBinary(readBuff, 215, 216);
            int test_Die_Information_Address = getIntBinary(readBuff, 217, 220);
            int number_of_line_category_data = getIntBinary(readBuff, 221, 224);
            int line_category_address = getIntBinary(readBuff, 225, 228);
            String map_File_Configuration = byteToBinary(readBuff, 229, 230);
            int max_Multi_Site = getIntBinary(readBuff, 231, 232);
            int max_Categories = getIntBinary(readBuff, 233, 234);
            int reserve_235236 = getIntBinary(readBuff, 235, 236);
            int nTotalChips = (int)(columnsize * rowsize);
            int Byte_of_Header_Information = 236;
            int Byte_of_Extended_Header_Information = 172;

            // Map_File_Configuration
            int availability_of_Header_Information = mid(map_File_Configuration, 16, 17);
            int availability_of_Test_Result_Information_per_Die = mid(map_File_Configuration, 15, 16);
            int availability_of_Line_Category_Information = mid(map_File_Configuration, 14, 15);
            int availability_of_Extension_Header_Information = mid(map_File_Configuration, 13, 14);
            int availability_of_Test_Result_Information_per_Extension_Die = mid(map_File_Configuration, 12, 13);
            int availability_of_Extension_Line_Category_Information = mid(map_File_Configuration, 11, 12);
            int tsk_File_Size = (availability_of_Header_Information * Byte_of_Header_Information
                   + availability_of_Test_Result_Information_per_Die * nTotalChips * 6
                   + availability_of_Line_Category_Information * nTotalChips * 8);
            long map[][][] = new long[(int) (rowsize + 1L)][(int) (columnsize + 1L)][2];
            long r_y = 0;
            StringBuilder builder = new StringBuilder();
            for (int p = 1; p <= rowsize; p++)
            {
            	builder.append("<tr>\r");
                for (int q = 1; q <= columnsize; q++)
                {
                    int startPos = (int) (((test_Die_Information_Address) + (6 * (((p - 1) * ((int)columnsize)) + q - 1))) + 1);
                    int length = (int) (((test_Die_Information_Address) + (6 * (((p - 1) * ((int)columnsize)) + q - 1))) + 6);
                    String onedieresult = byteToBinary(readBuff, startPos, length).toString();
                    int x = Integer.parseInt(onedieresult.substring(8, 17),2);
                    int y = Integer.parseInt(onedieresult.substring(24, 33), 2);
                    int category = Integer.parseInt(onedieresult.substring(43, 49), 2);
                    long testDieVal = Long.parseLong(onedieresult.substring(17, 19), 2);
                    long passDieVal = Long.parseLong(onedieresult.substring(1, 3), 2);
                    boolean isTestDie;
                    boolean isPassDie;

                    if (testDieVal == 1)
                    {
                        isTestDie = true;
                    }
                    else
                    {
                    	isTestDie = false;
                    }
                    if (passDieVal == 1)
                    {
                    	isPassDie = true;
                    }
                    else
                    {
                    	isPassDie = false;
                    }

                    if (r_y >= 511 && q == (int)columnsize)
                    {
                        r_y++;
                    }
                    else if (r_y < 511)
                    {
                        r_y = y;
                    }

                    if (isTestDie)
                    {
                        int m = 0;
                        int n = 0;
                        if (refdieY <= 1)
                        {
                            n = (int)((x - refdieX)) + 1;
                           m = (int)((r_y - refdieY)) + 1;
                        }
                        else
                        {
                            n = x;
                          m = (int)r_y;
                        }
                        map[m][n][(int)(0L)] = category;

                        if (isPassDie)
                        {
                        	map[m][n][(int)(1L)] = 4;
                        	builder.append(String.format("<td class=\"b\" title=\"%d  [PASS]  (X:%s Y:%s)\">%d", map[m][n][0], String.valueOf(m), String.valueOf(n), map[m][n][0]));
                        }
                        else
                        {
                        	map[m][n][(int)(1L)] = 3;
                        	builder.append(String.format("<td class=\"r\" title=\"%d  [FAIL]  (X:%s Y:%s)\">%d", map[m][n][0], String.valueOf(m), String.valueOf(n), map[m][n][0]));
                        }
                    } else {
                    	builder.append("<td>&nbsp;");
                    }
                    builder.append("</td>\r");
                }
                builder.append("</tr>\r");
            }
            int tested_die = 0;
            int pass_die = 0;
            int fail_die = 0;
            for (int i = 0; i < (int) (rowsize + 1L); i++)
            {
                for (int j = 0; j < (int) (columnsize + 1L); j++)
                {
                    int category = (int)map[i][j][1];
                    switch (category)
                    {
                        case 0://not test die
                            break;

                        case 4://pass die
                            tested_die++;
                            pass_die++;
                            break;

                        case 3://fail die
                            tested_die++;
                            fail_die++;
                            break;

                        default:
                            break;
                    }
                }
            }
            if (tested_die != 0 && pass_die != 0)
            {
                String aaa = String.format("%g",(Float.parseFloat(String.valueOf(pass_die)) * 100) / Integer.parseInt(String.valueOf(tested_die))) + "%";
            }

            String str2 = "";
            FileReader reader = new FileReader(mapPath + "/" + "map.html");
            //Application.StartupPath + @"\map.html")
            BufferedReader  reader2 = new BufferedReader(reader);
            String str3 = "";
            while ((str3 = reader2.readLine()) != null)
            {
                str2 = str2 + str3 + "\r";
            }

            str2 = str2.replace("{%body%}", builder.toString());
            
            ArrayList list = new ArrayList();
            int num3 = 0;
            int num4 = 0;
            int num5 = 0;
            int num6 = 0;
            int num7 = 0;
            int num8 = 0;
            while (num3 < rowsize)
            {
                num4 = 0;
                while (num4 < columnsize)
                {
                    boolean flag = true;
                    if (map[num3][num4][1] != 0L)
                    {
                        for (int i = 0; i < list.size(); i++)
                        {
                        	num7 = (int)list.get(i);
                            if (((int)map[num3][num4][0]) == num7)
                            {
                                flag = false;
                            }
                        }
                        if (flag)
                        {
                            list.add((int)map[num3][num4][0]);
                        }
                    }
                    num4++;
                }
                num3++;
            }
            //list.add(e);
            for (int j = 0 ; j < list.size() ; j++)
            {
            	num7 = (int)list.get(j);
                num8 = 0;
                for (num3 = 0; num3 < rowsize; num3++)
                {
                    for (num4 = 0; num4 < columnsize; num4++)
                    {
                        if ((map[num3][num4][1] != 0L) && (((int)map[num3][num4][0]) == num7))
                        {
                            num8++;
                        }
                    }
                }
                double num9 = (((double)num8) / ((double)tested_dice)) * 100.0;
                //item.Text = num7.ToString();
                //item.SubItems.Add(num8.ToString());
                //item.SubItems.Add(num9.ToString("0.##") + "%");
                //this.listView2.Items.Add(item);
            }
			return InvokeResult.success(str2);
		} catch (SocketException e) {
			e.printStackTrace();
			return InvokeResult.failure(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			return InvokeResult.failure(e.getMessage());
		} finally {  
              if (in != null || fileIn != null) {  
                try {  
                	in.close();
		            fileIn.close(); 
                 } catch (IOException e1) {  
                 	 e1.printStackTrace();
                 	return InvokeResult.failure(e1.getMessage());
                 }  
              }  
        }  
	}
}
