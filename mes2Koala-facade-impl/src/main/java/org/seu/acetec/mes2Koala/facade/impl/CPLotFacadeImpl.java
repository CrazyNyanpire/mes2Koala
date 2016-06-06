package org.seu.acetec.mes2Koala.facade.impl;

import net.sf.json.JSONArray;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.application.CPLotApplication;
import org.seu.acetec.mes2Koala.application.CPLotNodeOperationApplication;
import org.seu.acetec.mes2Koala.application.CPWaferApplication;
import org.seu.acetec.mes2Koala.core.domain.CPLot;
import org.seu.acetec.mes2Koala.facade.CPLotFacade;
import org.seu.acetec.mes2Koala.facade.dto.CPLotDTO;
import org.seu.acetec.mes2Koala.facade.dto.CPNodeDTO;
import org.seu.acetec.mes2Koala.facade.dto.FTNodeDTO;
import org.seu.acetec.mes2Koala.facade.impl.assembler.CPLotAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.CPNodeAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.CPWaferAssembler;
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
	private CPLotNodeOperationApplication cpLotNodeOperationApplication;

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
			conditionVals.add(cpLotDTO.getCreateTimestampEnd());
		}
		if (cpLotDTO.getLastModifyTimestamp() != null) {
			jpql.append(" and _cpLot.lastModifyTimestamp between ? and ? ");
			conditionVals.add(cpLotDTO.getLastModifyTimestamp());
			conditionVals.add(cpLotDTO.getLastModifyTimestampEnd());
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
		if (cpLotDTO.getHoldState() != null) {
			jpql.append(" and _cpLot.holdState like ?");
			conditionVals.add(cpLotDTO.getHoldState());
		}
		if (cpLotDTO.getCurrentState() != null) {
			jpql.append(" and _cpLot.currentState like ?");
			conditionVals.add(cpLotDTO.getCurrentState());
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
	public InvokeResult startCPNode(Long processId) {
		try {
			cpLotNodeOperationApplication.startCPNode(processId);
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
	public InvokeResult endCPNode(Long processId) {
		try {
			cpLotNodeOperationApplication.endCPNode(processId);
			return InvokeResult.success("出站成功");
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return InvokeResult.failure(ex.getMessage());
		}
	}
	
	@Transactional
	@Override
	public InvokeResult endCPNodeIncoming(Long processId, JSONArray wafers) {
		try {
			cpLotNodeOperationApplication.endCPNodeIncoming(processId, wafers);
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
	public InvokeResult endFailTestNode(Long processId,CPNodeDTO cpNodeDTO) {
		try {
			cpLotNodeOperationApplication.endFail(processId,CPNodeAssembler.toEntity(cpNodeDTO));
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
	 */
	@Override
	public List<CPLotDTO> getChildsLot(Long id) {
		List<CPLotDTO> listDTOs = CPLotAssembler.toDTOs(application
				.find("select T from CPLot T where T.parentIntegrationIds = (select a.parentIntegrationIds from CPLot a where a.id ='" + id + "') and "
						+ "T.parentIntegrationIds != null and (T.showFlag = false or T.showFlag is null) and "
						+ "T.wmsTestId = (select b.wmsTestId from CPLot b where b.id ='" + id + "') "));
		for (int i=0;i<listDTOs.size();i++) {
			listDTOs.get(i).setcPWaferDTOs(CPWaferAssembler.toDTOs(cpWaferApplication
					.find("select a from CPWafer a where a.cpLot.id ='" + listDTOs.get(i).getId() + "'")));
		}
		return listDTOs;
	}
}
