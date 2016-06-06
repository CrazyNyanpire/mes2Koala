package org.seu.acetec.mes2Koala.facade.impl;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.text.MessageFormat;

import javax.inject.Inject;
import javax.inject.Named;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.utils.Page;
import org.dayatang.querychannel.QueryChannelService;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.facade.impl.assembler.CPWaferAssembler;
import org.seu.acetec.mes2Koala.facade.CPWaferFacade;
import org.seu.acetec.mes2Koala.application.CPNodeApplication;
import org.seu.acetec.mes2Koala.application.CPWaferApplication;
import org.seu.acetec.mes2Koala.core.common.BeanUtils;
import org.seu.acetec.mes2Koala.core.domain.*;
import org.seu.acetec.mes2Koala.core.enums.CPNodeState;
import org.seu.acetec.mes2Koala.core.enums.CPWaferCheck;
import org.seu.acetec.mes2Koala.core.enums.CPWaferState;

@Named
public class CPWaferFacadeImpl implements CPWaferFacade {

	@Inject
	private CPWaferApplication application;

	@Inject
	private CPNodeApplication cpNodeApplication;

	private QueryChannelService queryChannel;

	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(
					QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}

	public InvokeResult getCPWafer(Long id) {
		return InvokeResult.success(CPWaferAssembler.toDTO(application
				.getCPWafer(id)));
	}

	public InvokeResult creatCPWafer(CPWaferDTO cPWaferDTO) {
		application.creatCPWafer(CPWaferAssembler.toEntity(cPWaferDTO));
		return InvokeResult.success();
	}

	public InvokeResult updateCPWafer(CPWaferDTO cPWaferDTO) {
		application.updateCPWafer(CPWaferAssembler.toEntity(cPWaferDTO));
		return InvokeResult.success();
	}

	public InvokeResult removeCPWafer(Long id) {
		application.removeCPWafer(application.getCPWafer(id));
		return InvokeResult.success();
	}

	public InvokeResult removeCPWafers(Long[] ids) {
		Set<CPWafer> cPWafers = new HashSet<CPWafer>();
		for (Long id : ids) {
			cPWafers.add(application.getCPWafer(id));
		}
		application.removeCPWafers(cPWafers);
		return InvokeResult.success();
	}

	public List<CPWaferDTO> findAllCPWafer() {
		return CPWaferAssembler.toDTOs(application.findAllCPWafer());
	}

	public Page<CPWaferDTO> pageQueryCPWafer(CPWaferDTO queryVo,
			int currentPage, int pageSize) {
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select _cPWafer from CPWafer _cPWafer   left join _cPWafer.cpProcess  left join _cPWafer.customerCPLot  where 1=1 ");
		if (queryVo.getCreateTimestamp() != null) {
			jpql.append(" and _cPWafer.createTimestamp between ? and ? ");
			conditionVals.add(queryVo.getCreateTimestamp());
			conditionVals.add(queryVo.getCreateTimestampEnd());
		}
		if (queryVo.getLastModifyTimestamp() != null) {
			jpql.append(" and _cPWafer.lastModifyTimestamp between ? and ? ");
			conditionVals.add(queryVo.getLastModifyTimestamp());
			conditionVals.add(queryVo.getLastModifyTimestampEnd());
		}
		if (queryVo.getCreateEmployNo() != null
				&& !"".equals(queryVo.getCreateEmployNo())) {
			jpql.append(" and _cPWafer.createEmployNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCreateEmployNo()));
		}
		if (queryVo.getLastModifyEmployNo() != null
				&& !"".equals(queryVo.getLastModifyEmployNo())) {
			jpql.append(" and _cPWafer.lastModifyEmployNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getLastModifyEmployNo()));
		}
		if (queryVo.getMap() != null && !"".equals(queryVo.getMap())) {
			jpql.append(" and _cPWafer.map like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getMap()));
		}
		if (queryVo.getState() != null && !"".equals(queryVo.getState())) {
			jpql.append(" and _cPWafer.state like ?");
			conditionVals
					.add(MessageFormat.format("%{0}%", queryVo.getState()));
		}
		if (queryVo.getInternalWaferCode() != null
				&& !"".equals(queryVo.getInternalWaferCode())) {
			jpql.append(" and _cPWafer.internalWaferCode like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getInternalWaferCode()));
		}
		if (queryVo.getPass() != null && !"".equals(queryVo.getPass())) {
			jpql.append(" and _cPWafer.pass like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getPass()));
		}
		if (queryVo.getFail() != null && !"".equals(queryVo.getFail())) {
			jpql.append(" and _cPWafer.fail like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getFail()));
		}
		if (queryVo.getCustomerOffset() != null
				&& !"".equals(queryVo.getCustomerOffset())) {
			jpql.append(" and _cPWafer.customerOffset like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCustomerOffset()));
		}
		if (queryVo.getInternalOffset() != null
				&& !"".equals(queryVo.getInternalOffset())) {
			jpql.append(" and _cPWafer.internalOffset like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getInternalOffset()));
		}
		Page<CPWafer> pages = getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.setPage(currentPage, pageSize).pagedList();

		return new Page<CPWaferDTO>(pages.getStart(), pages.getResultCount(),
				pageSize, CPWaferAssembler.toDTOs(pages.getData()));
	}

	public Page<CPWaferDTO> findCpWafersByCPWafer(Long id, int currentPage,
			int pageSize) {
		List<CPWaferDTO> result = new ArrayList<CPWaferDTO>();
		String jpql = "select e from CPWafer o right join o.cpWafers e where o.id=?";
		Page<CPWafer> pages = getQueryChannelService().createJpqlQuery(jpql)
				.setParameters(new Object[] { id })
				.setPage(currentPage, pageSize).pagedList();
		for (CPWafer entity : pages.getData()) {
			CPWaferDTO dto = new CPWaferDTO();
			try {
				BeanUtils.copyProperties(dto, entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			result.add(dto);
		}
		return new Page<CPWaferDTO>(Page.getStartOfPage(currentPage, pageSize),
				pages.getResultCount(), pageSize, result);
	}

	public CPProcessDTO findCpProcessByCPWafer(Long id) {
		String jpql = "select e from CPWafer o right join o.cpProcess e where o.id=?";
		CPProcess result = (CPProcess) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.singleResult();
		CPProcessDTO dto = new CPProcessDTO();
		if (result != null) {
			try {
				BeanUtils.copyProperties(dto, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	public CustomerCPLotDTO findCustomerCPLotByCPWafer(Long id) {
		String jpql = "select e from CPWafer o right join o.customerCPLot e where o.id=?";
		CustomerCPLot result = (CustomerCPLot) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.singleResult();
		CustomerCPLotDTO dto = new CustomerCPLotDTO();
		if (result != null) {
			try {
				BeanUtils.copyProperties(dto, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	@Override
	public CPLotDTO findCpLotByCPWafer(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CPCustomerWaferDTO findCpCustomerWaferByCPWafer(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InvokeResult changeStatusPassed(String ids) {
		String[] idArray = ids.split(",");
		for (String id : idArray) {
			application.changeStatus(Long.valueOf(id), CPWaferState.PASSED);
		}
		return InvokeResult.success();
	}

	@Override
	public InvokeResult getCPWaferInfoByNode(Long cpLotId, Long nodeId) {
		String jpql = "select o from CPWafer o where o.cpLot.id = ? ";
		@SuppressWarnings("unchecked")
		List<CPWafer> result = (List<CPWafer>) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { cpLotId })
				.list();
		CPNode cpNode = cpNodeApplication.get(nodeId);
		List<CPWaferDTO> resultDTOs = new ArrayList<CPWaferDTO>();
		for (CPWafer cpWafer : result) {
			CPWaferDTO cpWaferDTO = new CPWaferDTO();
			cpWaferDTO = CPWaferAssembler.toDTO(cpWafer);
			switch (cpNode.getState()) {
			case UNREACHED:
			case TO_START:
				cpWaferDTO.setState(0);
				break;
			case ENDED:
				cpWaferDTO.setState(1);
				break;
			default:
				break;
			}
			resultDTOs.add(cpWaferDTO);
		}
		return InvokeResult.success(resultDTOs);
	}

	@Override
	public InvokeResult saveCheck(String ids) {
		application.saveCheck(ids, CPWaferCheck.CHECKED);
		return InvokeResult.success();
	}

}
