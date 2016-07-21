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
import org.seu.acetec.mes2Koala.application.CPRuncardTemplateApplication;
import org.seu.acetec.mes2Koala.application.CPWaferApplication;
import org.seu.acetec.mes2Koala.application.CustomerCPLotApplication;
import org.seu.acetec.mes2Koala.application.DataCompensationApplication;
import org.seu.acetec.mes2Koala.application.IncrementNumberApplication;
import org.seu.acetec.mes2Koala.application.ProductionScheduleApplication;
import org.seu.acetec.mes2Koala.application.bean.SaveBaseBean;
import org.seu.acetec.mes2Koala.core.common.BeanUtils;
import org.seu.acetec.mes2Koala.core.domain.CPLot;
import org.seu.acetec.mes2Koala.core.domain.CPLotOptionLog;
import org.seu.acetec.mes2Koala.core.domain.CPNode;
import org.seu.acetec.mes2Koala.core.domain.CPProcess;
import org.seu.acetec.mes2Koala.core.domain.CPProductionSchedule;
import org.seu.acetec.mes2Koala.core.domain.CPQDN;
import org.seu.acetec.mes2Koala.core.domain.CPRuncard;
import org.seu.acetec.mes2Koala.core.domain.CPRuncardTemplate;
import org.seu.acetec.mes2Koala.core.domain.CPTestingNode;
import org.seu.acetec.mes2Koala.core.domain.CPWafer;
import org.seu.acetec.mes2Koala.core.domain.CustomerCPLot;
import org.seu.acetec.mes2Koala.core.domain.CustomerFTLot;
import org.seu.acetec.mes2Koala.core.domain.DataCompensation;
import org.seu.acetec.mes2Koala.core.domain.InternalLot;
import org.seu.acetec.mes2Koala.core.domain.Label;
import org.seu.acetec.mes2Koala.core.domain.Process;
import org.seu.acetec.mes2Koala.core.domain.TestProgram;
import org.seu.acetec.mes2Koala.core.enums.CPNodeState;
import org.seu.acetec.mes2Koala.core.enums.CPWaferState;
import org.seu.acetec.mes2Koala.facade.CPLotFacade;
import org.seu.acetec.mes2Koala.facade.CPLotNodeOperationFacade;
import org.seu.acetec.mes2Koala.facade.CPQDNFacade;
import org.seu.acetec.mes2Koala.facade.CPWaferFacade;
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
import org.springframework.transaction.annotation.Transactional;
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

	private static final String mailTitle = "CP异常单通知";
	private static final String mailContent1 = "你有1个CP品质异常单待处理（请在4小时内作出处理）,单号为：";
	private static final String mailContent2 = ",具体位置为MES2系统下[生产管理]->[CP品质异常单]，请查收！";
	private static final String lotID = "Lot ID";
	private static final String waferID = "Wafer ID";
	private static final String smicProdID = "SMIC Prod ID";
	private static final String testProgram = "Test Program";
	private static final String testerID = "Tester ID";
	private static final String operatorID = "Operator ID";
	private static final String startTime = "Start Time";
	private static final String endTime = "End Time";
	private static final String notchSide = "Notch side";
	private static final String sortID = "Sort ID";
	private static final String binDefinitionFile = "Bin Definition File";
	private static final String gridXmax = "Grid Xmax";
	private static final String gridYmax = "Grid Ymax";
	private static final String fabSite = "Fab Site";
	private static final String testSite = "Test Site";
	private static final String probeCardID = "Probe Card ID";
	private static final String loadBoardID = "Load Board ID";
	private static final String romCode = "ROM Code";
	private static final String xDieSize = "X Die Size";
	private static final String yDieSize = "Y Die Size";
	private static final String xStreet = "X Street";
	private static final String yStreet = "Y Street";
	private static final String customerCode = "Customer Code";
	private static final String customerPartID = "Customer PartID";
	private static final String rawData = "RawData";
	private static final String dataEnd = "DataEnd";
	private static final String server = "192.168.1.35";
	private static final String ftpUserName = "file001";
	private static final String ftpUserPwd = "admin001";
	private static final String localDirectory = "D:/TSK/";
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
	@Inject
	private CPWaferApplication cpWaferApplication;

	@Inject
	private CPRuncardTemplateApplication cpRuncardTemplateApplication;

	@Inject
	private CustomerCPLotApplication customerCPLotApplication;

	@Inject
	private CPWaferFacade cpWaferFacade;

	@Override
	public InvokeResult startCPNode(Long processId, CPNodeDTO cpNodeDTO) {
		SaveBaseBean sbb = new SaveBaseBean();
		BeanUtils.copyProperties(cpNodeDTO, sbb);
		try {
			cpLotNodeOperationApplication.startCPNode(processId, sbb);
			return InvokeResult.success();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			return InvokeResult.failure(ex.getMessage());
		}
	}

	@Override
	public InvokeResult saveCPNode(Long processId, CPNodeDTO cpNodeDTO) {
		try {
			cpLotNodeOperationApplication.saveCPNode(processId,
					CPNodeAssembler.toEntity(cpNodeDTO));
			return InvokeResult.success();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			return InvokeResult.failure(ex.getMessage());
		}
	}

	@Override
	public InvokeResult endCPNode(Long processId, CPNodeDTO cpNodeDTO) {
		try {
			SaveBaseBean sbb = new SaveBaseBean();
			BeanUtils.copyProperties(cpNodeDTO, sbb);
			cpLotNodeOperationApplication.endCPNode(processId, sbb);
			return InvokeResult.success();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			return InvokeResult.failure(ex.getMessage());
		}
	}

	/**
	 * 跳站 1. 首先检查外部的DTO中的state状态，保证不会又客户端修改状态 2. 然后检查状态必须是进站而没出战 3.
	 * 接着对Test站点进行良率卡控 4. 最后更新出站信息，更新出站信息也是顺序查找，找到一个没出站的站点，对于其他状态作为异常处理
	 *
	 * @param processId
	 * @return
	 * @version 2016/4/14 HongYu
	 */
	@Override
	public InvokeResult jumpCPNode(Long processId, CPNodeDTO cpNodeDTO) {
		try {
			cpLotNodeOperationApplication.jumpCPNode(processId,
					CPNodeAssembler.toEntity(cpNodeDTO),
					cpNodeDTO.getTargetNode());
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
			CPProcess processDTO = cpProcessApplication.findByCPLotId(cpLot
					.getId());
			cpQDN.setCpLot(cpLot);
			cpQDN.setFlowNow(cpLot.getCurrentState());
			cpQDN.setFlow(processDTO.getContent());
			cpQDN.setCustomerName(cpLot.getCustomerCPLot().getCpInfo()
					.getCustomerDirect().getChineseName());
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
			EmployeeDTO toPerson = employeeFacade.getEmployeeById(Long
					.parseLong(cpqdndto.getToPerson()));
			String mailaddress = toPerson.getEmail();
			// String mailaddress = "yu.hong@acetecsemi.net";
			senderMailUtils
					.sendMailHelper(mailTitle, mailContent1 + cpQDN.getQdnNo()
							+ mailContent2, mailaddress);
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
			List<CPQDNDTO> list = cpQDNFacade.findAllDoingQDNByLotId(cpLotDTO
					.getId());
			if (list != null && list.size() > 0) {
				return InvokeResult.failure("QDN单:" + list.get(0).getQdnNo()
						+ " 未完结！");
			}
			// 2.如果没有未处理的QDN单改变lot状态取消Hold标志
			CPLot cpLot = cpLotApplication.get(cpLotDTO.getId());
			if (!cpLot.getHoldState().equals(InternalLot.HOLD_STATE_HOLD)
					&& !cpLot.getHoldState().equals(
							InternalLot.HOLD_STATE_FUTURE_HOLD)) {
				return InvokeResult.failure("Lot未Hold，请确认！");
			}
			CPProcess process = cpProcessApplication.findByCPLotId(cpLot
					.getId());
			List<CPNode> cpNodes = process.getCpNodes();
			Collections.sort(cpNodes);
			for (int index = 0; index < cpNodes.size(); index++) {
				CPNode cpNode = cpNodes.get(index);
				switch (cpNode.getState()) {
				case ENDED: // 已经出站的跳过
					break;
				case STARTED: // 进站了没出站
					// 更新当前站点的状态
					cpLot.setCurrentState("正在" + cpNode.getName());
					break;
				case TO_START:
					// 更新当前站点的状态
					cpLot.setCurrentState("待" + cpNode.getName());
					break;
				default: // 其他状态
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
		CPProcess processDTO = cpProcessApplication
				.findByCPLotId(cpLot.getId());
		cpQDN.setCpLot(cpLot);
		cpQDN.setFlow(processDTO.getContent());
		if (cpQDNDTO.getFlowNow() == null) {
			cpQDN.setFlowNow(cpLot.getCurrentState());
		} else {
			cpQDN.setFlowNow(cpQDNDTO.getFlowNow());
		}
		cpQDN.setCustomerName(cpLot.getCustomerCPLot().getCpInfo()
				.getCustomerDirect().getChineseName());
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
		cpLot.setIsFuture(true);
		cpLot.setFutureFlow(cpQDN.getFlowNow());
		// cpLot.setHoldState(InternalLot.HOLD_STATE_FUTURE_HOLD);
		cpLotApplication.update(cpLot);
		// 4.给部门负责人发送邮件
		EmployeeDTO toPerson = employeeFacade.getEmployeeById(Long
				.parseLong(cpQDNDTO.getToPerson()));
		String mailaddress = toPerson.getEmail();
		// String mailaddress = "yu.hong@acetecsemi.net";
		senderMailUtils.sendMailHelper(mailTitle,
				mailContent1 + cpQDN.getQdnNo() + mailContent2, mailaddress);
		return InvokeResult.success();
	}

	@Override
	@Transactional
	@Scope("prototype")
	public InvokeResult separateCPLot(Long id, List<CPLotDTO> cpLotDTOs,
			CPLotDTO cpLotDTO) {
		try {
			SaveBaseBean sbb = new SaveBaseBean();
			BeanUtils.copyProperties(cpLotDTO, sbb);
			boolean flag = true;
			CPLot cpLot = cpLotApplication.get(id);
			if (cpLot.getLogic() != null && cpLot.getLogic().intValue() == 1) {
				throw new RuntimeException(cpLot.getInternalLotNumber()
						+ "已分批，请刷新页面！");
			}
			cpLotNodeOperationApplication.separateCPLotCheck(cpLot);
			cpLot.setShowFlag(true);
			BeanUtils.copyProperties(sbb, cpLot);
			cpLotApplication.update(cpLot);
			CPLotDTO cpLotTempDTO = new CPLotDTO();
			// cpLotDTO.setcPWaferDTOs(CPWaferAssembler.toDTOs(cpWaferApplication
			// .find("select a from CPWafer a where a.cpLot.id ='" + id +
			// "'")));
			// List<CPWafer> cpwaferlisttemp = cpLot.getCpWafers();
			List<CPWafer> cpwaferlisttemp = (cpWaferApplication
					.find("select a from CPWafer a where a.cpLot.id ='" + id
							+ "'"));
			for (int i = 0; i < cpLotDTOs.size(); i++) {
				cpLotTempDTO = cpLotDTOs.get(i);
				CPLot newCpLot = new CPLot();
				BeanUtils.copyProperties(cpLot, newCpLot);
				BeanUtils.copyProperties(sbb, newCpLot);
				newCpLot.setId(null);
				CPProcess cpProcess = new CPProcess();
				BeanUtils.copyProperties(newCpLot.getCpProcess(), cpProcess);
				BeanUtils.copyProperties(sbb, cpProcess);
				newCpLot.setParentIntegrationIds(null);
				newCpLot.setParentSeparationId(cpLot.getId());
				newCpLot.setInternalLotNumber(cpLotTempDTO
						.getInternalLotNumber());
				newCpLot.setDiskContent(cpLotTempDTO.getDiskContent());
				newCpLot.setCurrentState(cpLotTempDTO.getCurrentState());
				newCpLot.setShipmentProductNumber(cpLotTempDTO
						.getShipmentProductNumber());
				// cpLot.setId(null);
				newCpLot.setShowFlag(null);
				if (cpLot.getSourceParentSeparationId() != null) {
					newCpLot.setSourceParentSeparationId(cpLot
							.getSourceParentSeparationId());
					newCpLot.setSourceParentSeparationNo(cpLot
							.getSourceParentSeparationNo());
				} else {
					newCpLot.setSourceParentSeparationId(cpLot.getId());
					newCpLot.setSourceParentSeparationNo(cpLot
							.getInternalLotNumber());
				}
				List<CPWafer> cpwaferlist = new ArrayList<CPWafer>();
				newCpLot.setCpWafers(null);
				for (int j = 0; j < cpLotTempDTO.getcPWaferDTOs().size(); j++) {
					JSON json = JSONObject.fromObject(cpLotTempDTO
							.getcPWaferDTOs().get(j));
					MorphDynaBean m = (MorphDynaBean) JSONSerializer
							.toJava(json);
					JSONObject jSONObject = JSONObject.fromObject(m);
					CPWaferDTO cpWaferDTO = (CPWaferDTO) JSONObject.toBean(
							jSONObject, CPWaferDTO.class);
					for (int a = 0; a < cpwaferlisttemp.size(); a++) {
						if (cpwaferlisttemp.get(a).getInternalWaferCode()
								.equals(cpWaferDTO.getInternalWaferCode())) {
							// cpwaferlisttemp.get(a).setId(null);
							// cpwaferlisttemp.get(a).setCpLot(newCpLot);
							CPWafer newCPWafer = new CPWafer();
							BeanUtils.copyProperties(cpwaferlisttemp.get(a),
									newCPWafer);
							newCPWafer.setCpLot(newCpLot);
							newCPWafer.setId(null);
							BeanUtils.copyProperties(sbb, newCPWafer);
							cpwaferlist.add(newCPWafer);
						}
					}
				}
				newCpLot.setCpWafers((List<CPWafer>) cpwaferlist);
				newCpLot.setQuantity((long) ((List<CPWafer>) cpwaferlist)
						.size());
				List<CPNode> cpNodes = new ArrayList<CPNode>();
				for (int q = 0; q < newCpLot.getCpProcess().getCpNodes().size(); q++) {
					CPNode cpNode = newCpLot.getCpProcess().getCpNodes().get(q);
					CPNode newCpNode = new CPNode();
					BeanUtils.copyProperties(cpNode, newCpNode);
					BeanUtils.copyProperties(sbb, newCpNode);
					newCpNode.setId(null);
					if (cpNode instanceof CPTestingNode) {
						CPTestingNode cpTestingNode = new CPTestingNode();
						BeanUtils.copyProperties(newCpLot.getCpProcess()
								.getCpNodes().get(q), cpTestingNode, "id");
						BeanUtils.copyProperties(sbb, newCpNode);
						TestProgram testProgram = new TestProgram();
						if (cpTestingNode.getTestProgram() != null) {
							BeanUtils.copyProperties(
									cpTestingNode.getTestProgram(),
									testProgram, "id");
							BeanUtils.copyProperties(sbb, newCpNode);
							cpTestingNode.setTestProgram(testProgram);
							//2016/7/11 Hongyu add
							testProgram.setCpTestingNode(cpTestingNode);
						}
						cpTestingNode.setCpProcess(cpProcess);
						cpNodes.add(cpTestingNode);
						for (CPProductionSchedule cpProductionSchedule : ((CPTestingNode) cpLot
								.getCpProcess().getCpNodes().get(q))
								.getCpProductionSchedules()) {
							cpProductionSchedule.setLogic(1);
							productionScheduleApplication
									.update(cpProductionSchedule);
						}
						cpTestingNode.setCpProductionSchedules(null);
					} else {
						newCpNode.setCpProcess(cpProcess);
						cpNodes.add(newCpNode);
					}
				}
				cpProcess.setCpNodes(cpNodes);
				cpProcess.setId(null);
				cpProcess.setCpLot(newCpLot);
				newCpLot.setCpProcess(cpProcess);
				// cpLot.getCpProcess().setId(null);

				cpLotApplication.create(newCpLot); // 创建CPLot
				CPRuncardTemplate cpRuncardTemplate = cpRuncardTemplateApplication
						.findByInternalProductId(newCpLot.getCustomerCPLot()
								.getCpInfo().getId());
				CPRuncard cpRuncard = customerCPLotApplication
						.createCPRuncard(cpRuncardTemplate);
				cpRuncard.setCpLot(newCpLot);
				BeanUtils.copyProperties(sbb, cpRuncard);
				cpRuncard.setCreateEmployNo(sbb.getLastModifyEmployNo());
				cpRuncard.setCreateTimestamp(sbb.getLastModifyTimestamp());
				cpRuncard.save();

				for (CPNode cpNode : newCpLot.getCpProcess().getCpNodes()) {
					if (cpNode instanceof CPTestingNode) {
						productionScheduleApplication.createNewCpSchedule(null,
								(CPTestingNode) cpNode);
					}
				}
				flag = false;
				// 操作记录
				CPLotOptionLog cpLotOptionLog = new CPLotOptionLog();
				BeanUtils.copyProperties(cpLotDTOs.get(0), cpLotOptionLog);
				cpLotOptionLog.setCpLot(newCpLot);
				cpLotOptionLog.setOptType("分批");
				cpLotOptionLog.setRemark("分批");
				cpLotOptionLog.setFlownow(cpLotTempDTO.getCurrentState());
				BeanUtils.copyProperties(sbb, cpLotOptionLog);
				cpLotOptionLogApplication.creatCPLotOptionLog(cpLotOptionLog);
			}
			return InvokeResult.success();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			return InvokeResult.failure(ex.getMessage());
		}
	}

	@Override
	@Transactional
	public InvokeResult mergeLot(String[] id, CPLotDTO cpLotDTO) {
		try {
			List<CPWafer> cpwaferlist = new ArrayList();
			String isSameState = "";
			String parentIntegrationIds = "";
			for (int i = 0; i < id.length; i++) {
				CPLot cpLot = cpLotApplication.get(Long.valueOf(id[i]));
				if (cpLot.getLogic() != null
						&& cpLot.getLogic().intValue() == 1) {
					throw new RuntimeException(cpLot.getInternalLotNumber()
							+ "已合批，请刷新页面！");
				}
				CPProcess process = cpLot.getCpProcess(); // 找到流程
				if (Objects.equals(cpLot.getHoldState(), CPLot.HOLD_STATE_HOLD)) {
					throw new RuntimeException("分批失败：开HOLD");
				}
				// 出站更新信息
				List<CPNode> cpNodes = process.getCpNodes();
				Collections.sort(cpNodes);
				if ("".equals(isSameState)) {
					isSameState = cpLot.getCurrentState();
				} else if (!isSameState.equals(cpLot.getCurrentState())) {
					throw new RuntimeException("合批失败：选择的批次状态不一致");
				}
				for (int index = 0; index < cpNodes.size(); index++) {
					CPNode cpNode = cpNodes.get(index);
					switch (cpNode.getState()) {
					case STARTED: // 进站了没出站
						// 进站过程中不能分批

						// throw new RuntimeException("合批失败：进站后未出站不能合批");
					default: // 未经过站点
						break;
					}
				}
				for (CPNode cpNode : process.getCpNodes()) {
					if (cpNode instanceof CPTestingNode) {
						CPTestingNode cpTestingNode = (CPTestingNode) cpNode;
						for (CPProductionSchedule cpProductionSchedule : ((CPTestingNode) cpNode)
								.getCpProductionSchedules()) {
							cpProductionSchedule.setLogic(1);
							productionScheduleApplication
									.update(cpProductionSchedule);
						}
					}
				}
				// List<CPWafer> cpwaferlisttemp = cpWaferApplication
				// .find("select a from CPWafer a where a.cpLot.id ='" +
				// Long.valueOf(id[i]) + "'");
				cpwaferlist.addAll(cpWaferApplication
						.find("select a from CPWafer a where a.cpLot.id ='"
								+ Long.valueOf(id[i]) + "'"));
				cpLot.setShowFlag(true);
				cpLotApplication.update(cpLot);
				parentIntegrationIds = cpLot.getId() + ",";
			}
			CPLot newcpLot = new CPLot();
			CPLot cpLot = cpLotApplication.get(Long.valueOf(id[0]));
			BeanUtils.copyProperties(cpLotApplication.get(Long.valueOf(id[0])),
					newcpLot, "id");
			// newcpLot.setShipmentProductNumber(newcpLot.getShipmentProductNumber().substring(0,newcpLot.getShipmentProductNumber().lastIndexOf("-")));
			CPProcess cpProcess = new CPProcess();
			BeanUtils.copyProperties(newcpLot.getCpProcess(), cpProcess, "id");

			if (cpLotDTO.getInternalLotNumber().equals(
					newcpLot.getParentIntegrationIds())) {
				newcpLot.setParentIntegrationIds(null);
			}
			// newcpLot.setId(null);
			List<CPNode> cpNodes = new ArrayList<CPNode>();
			for (int q = 0; q < newcpLot.getCpProcess().getCpNodes().size(); q++) {
				CPNode cpNode = newcpLot.getCpProcess().getCpNodes().get(q);
				CPNode newCpNode = new CPNode();
				BeanUtils.copyProperties(cpNode, newCpNode);
				newCpNode.setId(null);
				if (cpNode instanceof CPTestingNode) {
					CPTestingNode cpTestingNode = new CPTestingNode();
					BeanUtils.copyProperties(newcpLot.getCpProcess()
							.getCpNodes().get(q), cpTestingNode, "id");
					TestProgram testProgram = new TestProgram();
					if (cpTestingNode.getTestProgram() != null) {
						BeanUtils.copyProperties(
								cpTestingNode.getTestProgram(), testProgram,
								"id");
						cpTestingNode.setTestProgram(testProgram);
						//2016/7/11 Hongyu add
						testProgram.setCpTestingNode(cpTestingNode);
					}
					cpTestingNode.setCpProcess(cpProcess);
					cpNodes.add(cpTestingNode);
					cpTestingNode.setCpProductionSchedules(null);
				} else {
					newCpNode.setCpProcess(cpProcess);
					cpNodes.add(newCpNode);
				}
			}
			List<CPWafer> cpWaferList = new ArrayList<CPWafer>();
			for (int a = 0; a < cpwaferlist.size(); a++) {
				CPWafer newCPWafer = new CPWafer();
				BeanUtils.copyProperties(cpwaferlist.get(a), newCPWafer, "id");
				newCPWafer.setCpLot(newcpLot);
				cpWaferList.add(newCPWafer);
			}
			newcpLot.setCpWafers(cpWaferList);
			newcpLot.setQuantity((long) (cpwaferlist.size()));

			cpProcess.setCpNodes(cpNodes);
			cpProcess.setCpLot(newcpLot);
			newcpLot.setCpProcess(cpProcess);
			BeanUtils.copyProperties(cpLotDTO, newcpLot);
			newcpLot.setParentIntegrationIds(parentIntegrationIds);
			cpLotApplication.create(newcpLot);
			CPRuncardTemplate cpRuncardTemplate = cpRuncardTemplateApplication
					.findByInternalProductId(newcpLot.getCustomerCPLot()
							.getCpInfo().getId());
			CPRuncard cpRuncard = customerCPLotApplication
					.createCPRuncard(cpRuncardTemplate);
			cpRuncard.setCpLot(newcpLot);
			cpRuncard.setCreateEmployNo(cpLotDTO.getLastModifyEmployNo());
			cpRuncard.setCreateTimestamp(cpLotDTO.getLastModifyTimestamp());
			cpRuncard.setLastModifyEmployNo(cpLotDTO.getLastModifyEmployNo());
			cpRuncard.setLastModifyTimestamp(cpLotDTO.getLastModifyTimestamp());
			cpRuncard.save();
			for (CPNode cpNode : newcpLot.getCpProcess().getCpNodes()) {
				if (cpNode instanceof CPTestingNode) {
					productionScheduleApplication.createNewCpSchedule(null,
							(CPTestingNode) cpNode);
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
	public InvokeResult integrateCPLots(Long[] cpLotIds, CPNodeDTO cpNodeDTO) {
		try {
			SaveBaseBean sbb = new SaveBaseBean();
			BeanUtils.copyProperties(cpNodeDTO, sbb);
			cpLotNodeOperationApplication.integrateCPLots(cpLotIds, sbb);
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

		HSSFCell cell = row.createCell(0);
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
		for (int i = 0; i < cpLotDTOs.size(); i++) {
			CPLotDTO cpLot = (CPLotDTO) cpLotDTOs.get(i);
			row = sheet.createRow((int) i + 1);
			HSSFCell datacell = row.createCell(0);
			datacell.setCellValue(cpLot.getCurrentState());
			datacell.setCellStyle(style);
			datacell = row.createCell(1);
			datacell.setCellValue(cpLot.getCustomerCPLotDTO()
					.getCustomerLotNumber());
			datacell.setCellStyle(style);
			datacell = row.createCell(2);
			datacell.setCellValue(cpLot.getInternalLotNumber());
			datacell.setCellStyle(style);
			datacell = row.createCell(3);
			datacell.setCellValue(cpLot.getCustomerCPLotDTO().getPackingLot());
			datacell.setCellStyle(style);
			datacell = row.createCell(4);
			datacell.setCellValue(cpLot.getCustomerCPLotDTO()
					.getCustomerNumber());
			datacell.setCellStyle(style);
			datacell = row.createCell(5);
			datacell.setCellValue(cpLot.getCustomerCPLotDTO()
					.getInternalProductDTO().getCustomerProductNumber());
			datacell.setCellStyle(style);
			datacell = row.createCell(6);
			datacell.setCellValue(cpLot.getCustomerCPLotDTO()
					.getInternalProductDTO().getShipmentProductNumber());
			datacell.setCellStyle(style);
			datacell = row.createCell(7);
			datacell.setCellValue(cpLot.getCustomerCPLotDTO()
					.getInternalProductDTO().getShipmentProductNumber());
			datacell.setCellStyle(style);
			datacell = row.createCell(8);
			datacell.setCellValue(cpLot.getQuantity() != null ? cpLot
					.getQuantity() : 0);
			datacell.setCellStyle(style);
			datacell = row.createCell(9);
			datacell.setCellValue("");
			datacell.setCellStyle(style);
			datacell = row.createCell(10);
			datacell.setCellValue("");
			datacell.setCellStyle(style);
			datacell = row.createCell(11);
			datacell.setCellValue(cpLot.getCustomerCPLotDTO().getMaskName());
			datacell.setCellStyle(style);
		}
		// 第六步，将文件存到指定位置
		try {
			// 获取类文件所在的路径，为获取web应用路径做准备
			String classPath = this.getClass().getClassLoader().getResource("")
					.getPath();
			// 获取模板路径与导出文件的路径
			String templatePath = classPath.substring(0,
					classPath.indexOf("WEB-INF"))
					+ "excel/";
			String exportPath = templatePath + "export/";
			FileOutputStream fout = new FileOutputStream(exportPath + fileName);
			wb.write(fout);
			fout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public InvokeResult getLabelInfo(Long id) {
		try {
			CPLot cpLot = cpLotApplication.get(id);
			Map<String, Object> map = new HashMap<>();
			List<Label> labels = cpLot.getCustomerCPLot().getCpInfo()
					.getLabels();
			for (Label label : labels) {
				map.put("labelName", label.getLabelName());
			}
			map.put("productNo", cpLot.getShipmentProductNumber());
			map.put("ppo", cpLot.getCustomerCPLot().getCustomerPPO());
			map.put("customerNo", cpLot.getCustomerCPLot().getCustomerNumber());
			map.put("cpLotNo", cpLot.getInternalLotNumber());
			map.put("waferSize", cpLot.getCustomerCPLot().getCpInfo()
					.getWaferSize());
			map.put("grossDie", cpLot.getCustomerCPLot().getCpInfo()
					.getGrossDie());
			map.put("warningType", cpLot.getCustomerCPLot().getCpInfo()
					.getWarningType());
			map.put("productRequire", cpLot.getCustomerCPLot().getCpInfo()
					.getProductRequire());
			map.put("touchQty", cpLot.getCustomerCPLot().getCpInfo()
					.getTouchQty());
			map.put("maskName", cpLot.getCustomerCPLot().getMaskName());
			map.put("internalLotNumber", cpLot.getCustomerCPLot().getCustomerLotNumber());
			map.put("customerName", cpLot.getCustomerCPLot().getCpInfo().getCustomerDirect().getEnglishName());
			map.put("qty", cpLot.getQuantity());
			List<CPWaferDTO> cpWaferDTOs = this.getCPWafers(cpLot);
			map.put("cPWaferDTOs", cpWaferDTOs);
			map.put("goodDie", this.getGoodDie(cpWaferDTOs));
			return InvokeResult.success(map);
		} catch (Exception e) {
			e.printStackTrace();
			return InvokeResult.failure("系统异常，标签打印失败");
		}
	}

	private String getGoodDie(List<CPWaferDTO> cpWaferDTOs) {
		int goodNum = 0;
		for (CPWaferDTO cpWaferDTO : cpWaferDTOs) {
			goodNum += Integer.valueOf(cpWaferDTO.getPass());
		}
		return String.valueOf(goodNum);
	}

	@Transactional
	private List<CPWaferDTO> getCPWafers(CPLot cpLot) {
		CPNode testingCPNode = null;
		for (int i =0 ; i < cpLot.getCpProcess().getCpNodes().size() ; i ++) {
			cpLot.getCpProcess().getCpNodes().get(i).getCreateEmployNo();
			if (cpLot.getCpProcess().getCpNodes().get(i).getName().indexOf("CP") == 0) {
				testingCPNode = cpLot.getCpProcess().getCpNodes().get(i);
			}
		}
		
		if (testingCPNode != null) {
			return cpWaferFacade.getCPWaferInfoByNode(cpLot, testingCPNode);
		} else {
			throw new RuntimeException("批次" + cpLot.getInternalLotNumber()
					+ "没有测试站点！");
		}
	}

	@Override
	public InvokeResult dataCompensationChk(Long id) {
		try {
			CPLot cpLot = cpLotApplication.get(id);
			if (!"1409-SMI-SH".equals(cpLot.getCustomerCPLot()
					.getCustomerNumber())) {
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

			if (targetNodeTurn == 0) {
				throw new RuntimeException("数据补偿失败：无FQC站点");
			}

			for (int index = 0; index < cpNodes.size(); index++) {
				CPNode cpNode = cpNodes.get(index);
				switch (cpNode.getState()) {
				case STARTED: // 进站了没出站
					cunrrentNodeTurn = cpNode.getTurn();
					if (cunrrentNodeTurn <= targetNodeTurn) {
						throw new RuntimeException("数据补偿失败：请在FQC站点后数据补偿");
					}
				case TO_START: // 来到这个站点
					cunrrentNodeTurn = cpNode.getTurn();
					if (cunrrentNodeTurn <= targetNodeTurn) {
						throw new RuntimeException("数据补偿失败：请在FQC站点后数据补偿");
					}
				default: // 未经过站点
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
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String data;
			String rawDataInfo = "";
			String dataName = "";
			String dataVal = "";
			int flag = 0;
			DataCompensation dataCompensation = new DataCompensation();
			while ((data = reader.readLine()) != null) {
				data = data.trim();
				if (!"RawData".equals(data) && !"DataEnd".equals(data)
						&& flag == 0) {
					dataName = data.substring(0, data.indexOf(":"));
					dataVal = data.substring(data.indexOf(":") + 1);
					switch (dataName) {
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
				} else if ("RawData".equals(data) || "DataEnd".equals(data)) {
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
}
