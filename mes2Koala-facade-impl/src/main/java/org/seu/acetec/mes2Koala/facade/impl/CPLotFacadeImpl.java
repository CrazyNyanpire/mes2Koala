package org.seu.acetec.mes2Koala.facade.impl;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.application.CPInfoApplication;
import org.seu.acetec.mes2Koala.application.CPLotApplication;
import org.seu.acetec.mes2Koala.application.CPLotNodeOperationApplication;
import org.seu.acetec.mes2Koala.application.CPLotOptionLogApplication;
import org.seu.acetec.mes2Koala.application.CPProcessApplication;
import org.seu.acetec.mes2Koala.application.CPProductionScheduleApplication;
import org.seu.acetec.mes2Koala.application.CPRuncardApplication;
import org.seu.acetec.mes2Koala.application.CPRuncardTemplateApplication;
import org.seu.acetec.mes2Koala.application.CPWaferApplication;
import org.seu.acetec.mes2Koala.application.CustomerCPLotApplication;
import org.seu.acetec.mes2Koala.application.ProductionScheduleApplication;
import org.seu.acetec.mes2Koala.application.bean.SaveBaseBean;
import org.seu.acetec.mes2Koala.core.common.BeanUtils;
import org.seu.acetec.mes2Koala.core.domain.CPInfo;
import org.seu.acetec.mes2Koala.core.domain.CPLot;
import org.seu.acetec.mes2Koala.core.domain.CPNode;
import org.seu.acetec.mes2Koala.core.domain.CPProcess;
import org.seu.acetec.mes2Koala.core.domain.CPRuncard;
import org.seu.acetec.mes2Koala.core.domain.CPRuncardTemplate;
import org.seu.acetec.mes2Koala.core.domain.CPTestingNode;
import org.seu.acetec.mes2Koala.core.domain.CustomerCPLot;
import org.seu.acetec.mes2Koala.core.domain.ProcessTemplate;
import org.seu.acetec.mes2Koala.core.enums.CPNodeState;
import org.seu.acetec.mes2Koala.facade.CPLotFacade;
import org.seu.acetec.mes2Koala.facade.CPWaferFacade;
import org.seu.acetec.mes2Koala.facade.dto.CPLotDTO;
import org.seu.acetec.mes2Koala.facade.dto.CPNodeDTO;
import org.seu.acetec.mes2Koala.facade.dto.FTNodeDTO;
import org.seu.acetec.mes2Koala.facade.impl.assembler.CPLotAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.CPNodeAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.CPWaferAssembler;
import org.seu.acetec.mes2Koala.infra.MyDateUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.inject.Inject;
import javax.inject.Named;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 阙宇翔
 * @version 2016/2/14
 */
@Named
public class CPLotFacadeImpl implements CPLotFacade {

	@Inject
	private CPLotApplication application;

	@Inject
	private CPWaferApplication cpWaferApplication;

	@Inject
	private CPInfoApplication cpInfoApplication;

	@Inject
	private CPLotNodeOperationApplication cpLotNodeOperationApplication;

	@Inject
	private CPProcessApplication cpProcessApplication;

	@Inject
	private CPRuncardTemplateApplication cpRuncardTemplateApplication;

	@Inject
	private CustomerCPLotApplication customerCPLotApplication;

	@Inject
	private CPLotOptionLogApplication cpLotOptionLogApplication;

	@Inject
	private CPRuncardApplication cpRunCardApplication;

	@Inject
	private ProductionScheduleApplication productionScheduleApplication;

	@Inject
	private CPProductionScheduleApplication cpProductionScheduleApplication;
	
	@Inject
	private CPWaferFacade cpWaferFacade;

	private QueryChannelService queryChannel;

	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(
					QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}

	@Override
	public InvokeResult getCPLot(Long id) {
		CPLotDTO cpLotDTO = CPLotAssembler.toDTO(application.get(id));
		cpLotDTO.setcPWaferDTOs(CPWaferAssembler.toDTOs(cpWaferApplication
				.find("select a from CPWafer a where a.cpLot.id ='" + id + "'")));
		return InvokeResult.success(cpLotDTO);
	}

	@Override
	public InvokeResult createCPLot(CPLotDTO cpLotDTO) {
		application.create(CPLotAssembler.toEntity(cpLotDTO));
		return InvokeResult.success();
	}

	@Override
	public InvokeResult updateCPLot(CPLotDTO cpLotDTO) {
		application.update(CPLotAssembler.toEntity(cpLotDTO));
		return InvokeResult.success();
	}

	@Override
	public InvokeResult removeCPLot(Long id) {
		application.remove(application.get(id));
		return InvokeResult.success();
	}

	@Override
	public InvokeResult removeCPLots(Long[] ids) {
		Set<CPLot> cpLots = new HashSet<>();
		for (Long id : ids) {
			cpLots.add(application.get(id));
		}
		application.removeAll(cpLots);
		return InvokeResult.success();
	}

	@Override
	public List<CPLotDTO> findAllCPLot() {
		return CPLotAssembler.toDTOs(application.findAll());
	}

	@Override
	public Page<CPLotDTO> pageQueryCPLot(CPLotDTO cpLotDTO, int currentPage,
			int pageSize) {
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select _cpLot from CPLot _cpLot where 1=1 ");
		jpql.append("and (_cpLot.cpProcess.isTransferStorage = false or _cpLot.cpProcess.isTransferStorage is null) ");
		if (cpLotDTO.getCreateTimestamp() != null) {
			jpql.append(" and _cpLot.createTimestamp between ? and ? ");
			conditionVals.add(cpLotDTO.getCreateTimestamp());
			conditionVals.add(MyDateUtils.addHours(
					cpLotDTO.getCreateTimestampEnd(), 24));
		}
		if (cpLotDTO.getLastModifyTimestamp() != null) {
			jpql.append(" and _cpLot.lastModifyTimestamp between ? and ? ");
			conditionVals.add(cpLotDTO.getLastModifyTimestamp());
			conditionVals.add(MyDateUtils.addHours(
					cpLotDTO.getLastModifyTimestampEnd(), 24));
		}
		if (cpLotDTO.getCreateEmployNo() != null
				&& !"".equals(cpLotDTO.getCreateEmployNo())) {
			jpql.append(" and _cpLot.createEmployNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					cpLotDTO.getCreateEmployNo()));
		}
		if (cpLotDTO.getLastModifyEmployNo() != null
				&& !"".equals(cpLotDTO.getLastModifyEmployNo())) {
			jpql.append(" and _cpLot.lastModifyEmployNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					cpLotDTO.getLastModifyEmployNo()));
		}
		if (cpLotDTO.getHoldState() != null
				&& !"".equals(cpLotDTO.getHoldState())) {
			jpql.append(" and _cpLot.holdState like ?");
			conditionVals.add(cpLotDTO.getHoldState());
		}
		if (cpLotDTO.getCurrentState() != null
				&& !"".equals(cpLotDTO.getCurrentState())) {
			switch (cpLotDTO.getCurrentState()) {
			case "进行批次":
				jpql.append(" and _cpLot.currentState <> '已经完成' ");
				break;
			case "完结批次":
				jpql.append(" and _cpLot.currentState = '已经完成' ");
				break;
			default:
				jpql.append(" and _cpLot.currentState like ?");
				conditionVals.add(MessageFormat.format("%{0}%",
						cpLotDTO.getCurrentState()));
			}

		}
		if (cpLotDTO.getDiskContent() != null
				&& !"".equals(cpLotDTO.getDiskContent())) {
			jpql.append(" and _cpLot.diskContent like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					cpLotDTO.getDiskContent()));
		}
		if (cpLotDTO.getInternalLotNumber() != null
				&& !"".equals(cpLotDTO.getInternalLotNumber())) {
			jpql.append(" and _cpLot.internalLotNumber like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					cpLotDTO.getInternalLotNumber()));
		}
		jpql.append(" and (_cpLot.showFlag = false or _cpLot.showFlag is null)");
		jpql.append(" order by _cpLot.createTimestamp desc");
		Page<CPLot> pages = getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.setPage(currentPage, pageSize).pagedList();
		return new Page<>(pages.getStart(), pages.getResultCount(), pageSize,
				CPLotAssembler.toDTOs(pages.getData()));

	}

	/**
	 * 切换等待入站状态1变为已入站2 入站，会顺序查找整个Process上的所有站点， 跳过已经出站的站点，找到第一个没进站的站点，进站，
	 * 对于其他情况作为异常状态处理
	 *
	 * @param processId
	 * @return
	 * @version 2016/4/12 harlow
	 */
	@Transactional
	@Override
	public InvokeResult startCPNode(Long processId, CPLotDTO cpLotDTO) {
		try {
			SaveBaseBean sbb = new SaveBaseBean();
			BeanUtils.copyProperties(cpLotDTO, sbb);
			cpLotNodeOperationApplication.startCPNode(processId, sbb);
			return InvokeResult.success("进站成功");
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return InvokeResult.failure(ex.getMessage());
		}
	}

	/**
	 * 出站 1. 首先检查外部的DTO中的state状态，保证不会又客户端修改状态 2. 然后检查状态必须是进站而没出战 3.
	 * 接着对Test站点进行良率卡控 4. 最后更新出站信息，更新出站信息也是顺序查找，找到一个没出站的站点，对于其他状态作为异常处理
	 *
	 * @param ftNodeDTO
	 *            当前所在站点
	 * @param processId
	 * @return
	 * @version 2016/4/12 harlow
	 */
	@Transactional
	@Override
	public InvokeResult endCPNode(Long processId, CPLotDTO cpLotDTO) {
		try {
			SaveBaseBean sbb = new SaveBaseBean();
			BeanUtils.copyProperties(cpLotDTO, sbb);
			//未启用SBL验证
			//this.checkSBL(processId);
			cpLotNodeOperationApplication.endCPNode(processId, sbb);
			return InvokeResult.success("出站成功");
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return InvokeResult.failure(ex.getMessage());
		}
	}
	
	private void checkSBL(Long processId){
		CPProcess process = cpProcessApplication.get(processId); // 找到流程
		CPLot cpLot = process.getCpLot();
		if(cpWaferFacade.checkCPWaferSbl(cpLot, process.getNowNode().getName())){
			throw new RuntimeException("SBL未通过！");
		}
	}

	@Transactional
	@Override
	public InvokeResult endCPNodeIncoming(Long processId, JSONArray wafers,
			CPLotDTO cpLotDTO) {
		try {
			SaveBaseBean sbb = new SaveBaseBean();
			BeanUtils.copyProperties(cpLotDTO, sbb);
			cpLotNodeOperationApplication.endCPNodeIncoming(processId, wafers,
					sbb);
			return InvokeResult.success("出站成功");
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return InvokeResult.failure(ex.getMessage());
		}
	}

	/**
	 * 良率放行
	 *
	 * @param processId
	 * @return
	 * @version 2016/4/12 harlow
	 */
	@Transactional
	@Override
	public InvokeResult endFailTestNode(Long processId, CPNodeDTO cpNodeDTO) {
		try {
			cpLotNodeOperationApplication.endFail(processId,
					CPNodeAssembler.toEntity(cpNodeDTO));
			return InvokeResult.success("出站成功");
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return InvokeResult.failure(ex.getMessage());
		}
	}

	/**
	 * 保存CPResult结果
	 *
	 * @param processId
	 * @param cpNodeDTO
	 * @return
	 * @version 2016/4/12 harlow
	 */
	@Override
	public InvokeResult saveCPNode(Long processId, FTNodeDTO ftNodeDTO) {
		return InvokeResult.success();
	}

	/**
	 * 取同一母批的全部子批信息
	 *
	 * @param lotId
	 * @return
	 * @version 2016/4/26 HongYu
	 * @version 2016/6/20 HongYu
	 */
	@Override
	public List<CPLotDTO> getChildsLot(Long id) {
		CPLot cpLot = this.application.get(id);
		if (cpLot.getSourceParentSeparationId() == null) {
			return null;
		}
		StringBuffer jpql = new StringBuffer(
				"select T from CPLot T where T.sourceParentSeparationId = ? ");
		jpql.append(" and (T.showFlag = false or T.showFlag is null)");
		List<CPLotDTO> listDTOs = CPLotAssembler.toDTOs(application.find(
				jpql.toString(), cpLot.getSourceParentSeparationId()));
		for (int i = 0; i < listDTOs.size(); i++) {
			listDTOs.get(i).setcPWaferDTOs(
					CPWaferAssembler.toDTOs(cpWaferApplication
							.find("select a from CPWafer a where a.cpLot.id ='"
									+ listDTOs.get(i).getId() + "'")));
		}
		return listDTOs;
	}

	@Override
	@Transactional
	public InvokeResult changePid(CPLotDTO cpLotDTO, String ids) {
		String[] idsArray = ids.split(",");
		try {
			for (String idStr : idsArray) {
				if (StringUtils.isNotEmpty(idStr)) {
					cpLotDTO.setId(Long.valueOf(idStr));
					this.changePid(cpLotDTO);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return InvokeResult.failure(e.getMessage());
		}
		return InvokeResult.success();
	}

	@Transactional
	public InvokeResult changePid(CPLotDTO cpLotDTO) {
		CPLot cpLot = this.application.get(cpLotDTO.getId());
		CustomerCPLot customerCPLot = cpLot.getCustomerCPLot();
		CPInfo cpInfo = this.cpInfoApplication.get(cpLotDTO.getCpInfoId());
		// 判断客户产品型号或产品版本是否一致
		if (!cpInfo.getCustomerProductNumber().equals(
				customerCPLot.getCpInfo().getCustomerProductNumber())
				|| !cpInfo.getCustomerProductRevision().equals(
						customerCPLot.getCpInfo().getCustomerProductRevision())) {
			throw new RuntimeException("客户产品型号或产品版本不一致，不能更换PID！");
		}
		// 判断选择的pid和lot原有的PID是否一致
		if (cpInfo.getInternalProductNumber().equals(
				customerCPLot.getCpInfo().getInternalProductNumber())) {
			throw new RuntimeException("选择的PID与Lot中PID相同不需要更换PID！");
		}
		customerCPLot.setCpInfo(cpInfo);
		List<CPNode> oldCpNodes = cpLot.getCpProcess().getCpNodes();
		// 删除原Process的关联
		CPProcess cpProcess = cpLot.getCpProcess();
		cpProcess.setLotNo(cpLot.getInternalLotNumber());
		cpProcess.setCpLot(null);
		cpProcess.save();
		// 删除原runcard关联
		CPRuncard oldCPRuncard = cpRunCardApplication.findByCPLotId(cpLot
				.getId());
		oldCPRuncard.setCpLot(null);
		oldCPRuncard.setLotNo(cpLot.getInternalLotNumber());
		oldCPRuncard.save();
		// 获取更新的Nodes
		ProcessTemplate processTemplate = cpInfo.getProcessTemplate();
		CPProcess process = CPProcess.instanceTemplate(processTemplate);
		String[] nodeNames = cpProcessApplication
				.extractNodeNamesByProcessTemplateContent(process.getContent());// 测试站点
		List<CPNode> newCpNodes = cpProcessApplication.generateCPNodes(process,
				0, nodeNames);
		process.setCpLot(cpLot);
		process = this.checkPassNodes(oldCpNodes, newCpNodes, process);

		// 保存RunCard
		CPRuncardTemplate cpRuncardTemplate = cpRuncardTemplateApplication
				.findByInternalProductId(cpInfo.getId());
		CPRuncard cpRuncard = customerCPLotApplication
				.createCPRuncard(cpRuncardTemplate);
		cpRuncard.setCpLot(cpLot);
		cpRuncard.setCreateEmployNo(cpLotDTO.getLastModifyEmployNo());
		cpRuncard.setCreateTimestamp(cpLotDTO.getLastModifyTimestamp());
		cpRuncard.setLastModifyEmployNo(cpLotDTO.getLastModifyEmployNo());
		cpRuncard.setLastModifyTimestamp(cpLotDTO.getLastModifyTimestamp());
		cpRuncard.save();

		cpLot.setCpProcess(process);
		cpLot.setLastModifyEmployNo(cpLotDTO.getLastModifyEmployNo());
		cpLot.setLastModifyTimestamp(cpLotDTO.getLastModifyTimestamp());
		cpLot.save();
		this.createCpProductionSchedule(cpLot.getCpProcess().getCpNodes());
		// 保存日志
		cpLotOptionLogApplication.createCPNode(cpLot, cpLot.getCpProcess()
				.getNowNode(), cpLot.getCurrentState(),
				"PID变更为:" + cpInfo.getInternalProductNumber());
		return InvokeResult.success();
	}

	/**
	 * 创建新的排产
	 * @param cpNodes
	 */
	private void createCpProductionSchedule(List<CPNode> cpNodes) {
		for (CPNode cpNode : cpNodes) {
			if (cpNode.getState().ordinal() == CPNodeState.UNREACHED.ordinal()) {
				// 新节点是测试站点新建排产
				if (cpNode instanceof CPTestingNode) {
					productionScheduleApplication.createNewCpSchedule(null,
							(CPTestingNode) cpNode);
				}
			}
		}
	}

	private CPProcess checkPassNodes(List<CPNode> oldCpNodes,
			List<CPNode> newCpNodes, CPProcess cpProcess) {
		List<CPNode> createCpNodes = new ArrayList<CPNode>();
		for (int i = 0; i < newCpNodes.size(); i++) {
			// 到达未测试站点跳出循环
			if (oldCpNodes.get(i).getState().ordinal() == CPNodeState.UNREACHED
					.ordinal()) {
				newCpNodes.get(i).setCpProcess(cpProcess);
				// 旧节点是测试站点删除排产
				if (oldCpNodes.get(i) instanceof CPTestingNode) {
					cpProductionScheduleApplication
							.deleteProductionScheduleByNodeId(oldCpNodes.get(i)
									.getId());
				}
				createCpNodes.add(newCpNodes.get(i));
				continue;
			} else {
				oldCpNodes.get(i).setCpProcess(cpProcess);
				createCpNodes.add(oldCpNodes.get(i));
			}
			if (oldCpNodes.get(i).getState().ordinal() == CPNodeState.STARTED
					.ordinal()
					|| oldCpNodes.get(i).getState().ordinal() == CPNodeState.TO_START
							.ordinal()) {
				cpProcess.setNowNode(oldCpNodes.get(i));
			}
			// 如果已测试站点不一致
			if (!oldCpNodes.get(i).getName()
					.equals(newCpNodes.get(i).getName())) {
				throw new RuntimeException("新PID与原流程中已测试站点不一致不能变动PID！");
			}
		}
		cpProcess.setCpNodes(createCpNodes);
		return cpProcess;
	}
}
