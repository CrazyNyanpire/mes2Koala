package org.seu.acetec.mes2Koala.facade.impl;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.application.CPLotApplication;
import org.seu.acetec.mes2Koala.application.CPReworkApplication;
import org.seu.acetec.mes2Koala.application.IncrementNumberApplication;
import org.seu.acetec.mes2Koala.core.common.BeanUtils;
import org.seu.acetec.mes2Koala.core.domain.CPLot;
import org.seu.acetec.mes2Koala.core.domain.CPRework;
import org.seu.acetec.mes2Koala.core.domain.CPReworkItem;
import org.seu.acetec.mes2Koala.facade.CPReworkFacade;
import org.seu.acetec.mes2Koala.facade.CPReworkItemFacade;
import org.seu.acetec.mes2Koala.facade.dto.CPReworkDTO;
import org.seu.acetec.mes2Koala.facade.dto.CPReworkItemDTO;
import org.seu.acetec.mes2Koala.facade.impl.assembler.CPReworkAssembler;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Named
public class CPReworkFacadeImpl implements CPReworkFacade {

	@Inject
	private CPReworkApplication application;

	@Inject
	private CPReworkItemFacade cpReworkItemFacade;

	@Inject
	private CPLotApplication cpLotapplication;

	@Inject
	private IncrementNumberApplication incrementNumberApplication;

	private QueryChannelService queryChannel;

	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(
					QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}

	public InvokeResult getCPRework(Long id) {
		return InvokeResult
				.success(CPReworkAssembler.toDTO(application.get(id)));
	}

	public InvokeResult creatCPRework(CPReworkDTO cpReworkDTO) {
		CPLot internalLot = cpLotapplication.get(cpReworkDTO.getLotId());

		// String reworkNo =
		// this.createReworkNo(cpLot.getCpLot().getCustomerCPLot().getCustomerNumber());
		// cpReworkDTO.setReworkNo(reworkNo);
		Set<CPReworkItem> set = new HashSet<CPReworkItem>();
		// cpReworkDTO.setCpReworkItems(list);
		CPRework cpRework = new CPRework();
		cpRework = CPReworkAssembler.toEntity(cpReworkDTO);
		cpRework.setReworkLot(internalLot);
		cpRework.setLotNo(internalLot.getInternalLotNumber());
		for (CPReworkItemDTO cpReworkItemDTO : cpReworkDTO.getReworkItems()) {
			CPReworkItem cpReworkItem = new CPReworkItem();
			BeanUtils.copyProperties(cpReworkItemDTO, cpReworkItem);
			cpReworkItem.setCreateEmployNo(cpReworkDTO.getCreateEmployNo());
			cpReworkItem.setCreateTimestamp(cpReworkDTO.getCreateTimestamp());
			cpReworkItem.setLastModifyEmployNo(cpReworkDTO
					.getLastModifyEmployNo());
			cpReworkItem.setLastModifyTimestamp(cpReworkDTO
					.getLastModifyTimestamp());
			cpReworkItem.setCpRework(cpRework);
			set.add(cpReworkItem);
		}
		cpRework.setReworkItems(set);
		application.create(cpRework);
		return InvokeResult.success();
	}

	public InvokeResult updateCPRework(CPReworkDTO cpReworkDTO) {
		application.update(CPReworkAssembler.toEntity(cpReworkDTO));
		return InvokeResult.success();
	}

	public InvokeResult removeCPRework(Long id) {
		application.remove(application.get(id));
		return InvokeResult.success();
	}

	public InvokeResult removeCPReworks(Long[] ids) {
		Set<CPRework> cpReworks = new HashSet<CPRework>();
		for (Long id : ids) {
			cpReworks.add(application.get(id));
		}
		application.removeAll(cpReworks);
		return InvokeResult.success();
	}

	public List<CPReworkDTO> findAllCPRework() {
		return CPReworkAssembler.toDTOs(application.findAll());
	}

	public Page<CPReworkDTO> pageQueryCPRework(CPReworkDTO queryVo,
			int currentPage, int pageSize) {
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select _cpRework from CPRework _cpRework   where 1=1 ");
		if (queryVo.getCreateTimestamp() != null) {
			jpql.append(" and _cpRework.createTimestamp between ? and ? ");
			conditionVals.add(queryVo.getCreateTimestamp());
			conditionVals.add(queryVo.getCreateTimestampEnd());
		}
		if (queryVo.getLastModifyTimestamp() != null) {
			jpql.append(" and _cpRework.lastModifyTimestamp between ? and ? ");
			conditionVals.add(queryVo.getLastModifyTimestamp());
			conditionVals.add(queryVo.getLastModifyTimestampEnd());
		}
		if (queryVo.getCreateEmployNo() != null
				&& !"".equals(queryVo.getCreateEmployNo())) {
			jpql.append(" and _cpRework.createEmployNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCreateEmployNo()));
		}
		if (queryVo.getLastModifyEmployNo() != null
				&& !"".equals(queryVo.getLastModifyEmployNo())) {
			jpql.append(" and _cpRework.lastModifyEmployNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getLastModifyEmployNo()));
		}
		if (queryVo.getReworkRCNo() != null
				&& !"".equals(queryVo.getReworkRCNo())) {
			jpql.append(" and _cpRework.reworkRCNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getReworkRCNo()));
		}
		if (queryVo.getReworkCustomer() != null
				&& !"".equals(queryVo.getReworkCustomer())) {
			jpql.append(" and _cpRework.reworkCustomer like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getReworkCustomer()));
		}
		if (queryVo.getReworkDate() != null) {
			jpql.append(" and _cpRework.reworkDate between ? and ? ");
			conditionVals.add(queryVo.getReworkDate());
			conditionVals.add(queryVo.getReworkDateEnd());
		}
		if (queryVo.getReworkDepartment() != null
				&& !"".equals(queryVo.getReworkDepartment())) {
			jpql.append(" and _cpRework.reworkDepartment like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getReworkDepartment()));
		}
		if (queryVo.getReworkEquipment() != null
				&& !"".equals(queryVo.getReworkEquipment())) {
			jpql.append(" and _cpRework.reworkEquipment like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getReworkEquipment()));
		}
		if (queryVo.getReworkNo() != null && !"".equals(queryVo.getReworkNo())) {
			jpql.append(" and _cpRework.reworkNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getReworkNo()));
		}
		if (queryVo.getReasonExplanation() != null
				&& !"".equals(queryVo.getReasonExplanation())) {
			jpql.append(" and _cpRework.reasonExplanation like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getReasonExplanation()));
		}
		if (queryVo.getReasonOther() != null
				&& !"".equals(queryVo.getReasonOther())) {
			jpql.append(" and _cpRework.reasonOther like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getReasonOther()));
		}
		if (queryVo.getReasonReasons() != null
				&& !"".equals(queryVo.getReasonReasons())) {
			jpql.append(" and _cpRework.reasonReasons like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getReasonReasons()));
		}
		if (queryVo.getSummary() != null && !"".equals(queryVo.getSummary())) {
			jpql.append(" and _cpRework.summary like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getSummary()));
		}
		Page<CPRework> pages = getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.setPage(currentPage, pageSize).pagedList();

		return new Page<CPReworkDTO>(pages.getStart(), pages.getResultCount(),
				pageSize, CPReworkAssembler.toDTOs(pages.getData()));
	}

	@Override
	public Page<CPReworkItemDTO> findReworkItemsByCPRework(Long id,
			int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InvokeResult approveCPRework(CPReworkDTO cpReworkDTO) {
		CPRework cpRework = application.get(cpReworkDTO.getId());
		cpRework.setApprove(cpReworkDTO.getApprove());
		cpRework.setApprovePerson(cpReworkDTO.getLastModifyEmployNo());
		cpRework.setApproveDate(cpRework.getLastModifyTimestamp());
		cpRework.setLastModifyEmployNo(cpReworkDTO.getLastModifyEmployNo());
		cpRework.setLastModifyTimestamp(cpRework.getLastModifyTimestamp());
		application.update(cpRework);
		return InvokeResult.success();
	}

	@Override
	public InvokeResult createReworkNo(Long id) {
		return InvokeResult.success(incrementNumberApplication
				.nextReworkNumber(cpLotapplication.get(id)));
	}

}
