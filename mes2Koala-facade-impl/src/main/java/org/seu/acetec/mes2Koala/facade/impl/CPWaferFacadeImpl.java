package org.seu.acetec.mes2Koala.facade.impl;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.text.MessageFormat;

import javax.inject.Inject;
import javax.inject.Named;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.utils.Page;
import org.dayatang.querychannel.QueryChannelService;
import org.openkoala.koala.commons.InvokeResult;
import org.openkoala.organisation.facade.EmployeeFacade;
import org.openkoala.organisation.facade.dto.EmployeeDTO;
import org.seu.acetec.mes2Koala.facade.common.SenderMailUtils;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.facade.dto.vo.EmployeeVo;
import org.seu.acetec.mes2Koala.facade.impl.assembler.CPWaferAssembler;
import org.seu.acetec.mes2Koala.facade.sbl.SBLClient;
import org.seu.acetec.mes2Koala.facade.CPWaferFacade;
import org.seu.acetec.mes2Koala.application.CPLotApplication;
import org.seu.acetec.mes2Koala.application.CPNodeApplication;
import org.seu.acetec.mes2Koala.application.CPSBLTemplateApplication;
import org.seu.acetec.mes2Koala.application.CPWaferApplication;
import org.seu.acetec.mes2Koala.application.TskInfoApplication;
import org.seu.acetec.mes2Koala.application.bean.SaveBaseBean;
import org.seu.acetec.mes2Koala.core.common.BeanUtils;
import org.seu.acetec.mes2Koala.core.domain.*;
import org.seu.acetec.mes2Koala.core.enums.CPNodeState;
import org.seu.acetec.mes2Koala.core.enums.CPWaferCheck;
import org.seu.acetec.mes2Koala.core.enums.CPWaferState;
import org.springframework.transaction.annotation.Transactional;

@Named
public class CPWaferFacadeImpl implements CPWaferFacade {

	@Inject
	private CPWaferApplication application;

	@Inject
	private CPNodeApplication cpNodeApplication;

	@Inject
	private CPLotApplication cpLotApplication;

	@Inject
	private TskInfoApplication tskInfoApplication;

	@Inject
	private SBLClient sblClient;
	
	@Inject
	private CPSBLTemplateApplication cpSBLTemplateApplication;
	
	@Inject
	private SenderMailUtils senderMailUtils;
	
	@Inject
    private EmployeeFacade employeeFacade;

	private QueryChannelService queryChannel;

	private static String ZERO = "0";
	
	private static final String mailTitle = "CP测试中wafer良率过低异常通知";
	private static final String mailContent = "你有1个CP测试中连续3片wafer良率过低的异常通知,异常waferId为：";

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
	@Transactional
	public InvokeResult getCPWaferInfoByNode(Long cpLotId, Long nodeId) {
		String jpql = "select o from CPWafer o where o.cpLot.id = ? ";
		@SuppressWarnings("unchecked")
		List<CPWafer> result = (List<CPWafer>) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { cpLotId })
				.list();
		CPNode cpNode = cpNodeApplication.get(nodeId);
		List<CPWaferDTO> resultDTOs = new ArrayList<CPWaferDTO>();
		List<CPWaferCheckLog> cpwcl = this.findCPWaferCheck(cpLotId, nodeId);
		CPLot cpLot = this.cpLotApplication.get(cpLotId);

		String lotNum = cpLot.getInternalLotNumber();
		if (cpLot.getSourceParentSeparationNo() != null
				&& !"".equals(cpLot.getSourceParentSeparationNo())) {
			lotNum = cpLot.getSourceParentSeparationNo();
		}
		lotNum = MessageFormat.format("%{0}%", lotNum);

		List<TskInfo> tskInfoList = tskInfoApplication
				.find("select o from TskInfo o where o.lotNum like ? and o.testSite = ?",
						lotNum, cpNode.getName());
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
			if (cpLot.getCurrentState().indexOf(cpNode.getName()) < 0
					&& ("IQC".equalsIgnoreCase(cpNode.getName()) || "FQC"
							.equalsIgnoreCase(cpNode.getName()))) {
				cpWaferDTO.setIsCheck(this.findNodeCheckStatus(cpwcl, cpWafer ,cpNode));
			}
			if (tskInfoList != null && tskInfoList.size() > 0) {
				cpWaferDTO = this.getTskInfoByWaferId(tskInfoList, cpWaferDTO);
			}
			resultDTOs.add(cpWaferDTO);
		}
		return InvokeResult.success(resultDTOs);
	}

	private CPWaferDTO getTskInfoByWaferId(List<TskInfo> tskInfoList,
			CPWaferDTO cpWaferDTO) {
		for (TskInfo tskInfo : tskInfoList) {
			if (cpWaferDTO.getInternalWaferCode().equalsIgnoreCase(
					tskInfo.getWaferId())) {
				cpWaferDTO.setPass(String.valueOf(tskInfo.getPassDice()));
				cpWaferDTO.setFail(String.valueOf(tskInfo.getFailDice()));
				cpWaferDTO.setYield(tskInfo.getYield());
				cpWaferDTO.setState(1);
				break;
			} else {
				cpWaferDTO.setPass(ZERO);
				cpWaferDTO.setFail(ZERO);
				cpWaferDTO.setYield(ZERO);
			}
		}
		return cpWaferDTO;
	}

	private Integer findNodeCheckStatus(List<CPWaferCheckLog> cpwcl,
			CPWafer cpWafer) {
		for (CPWaferCheckLog cpWaferCheckLog : cpwcl) {
			if (cpWaferCheckLog.getCpWafer().getId().longValue() == cpWafer
					.getId().longValue()) {
				return CPWaferCheck.CHECKED.ordinal();
			}
		}
		return CPWaferCheck.UNCHECKED.ordinal();
	}
	
	private Integer findNodeCheckStatus(List<CPWaferCheckLog> cpwcl,CPWafer cpWafer, CPNode cpNode) {
		for (CPWaferCheckLog cpWaferCheckLog : cpwcl) {
			if (cpWaferCheckLog.getCpWafer().getId().longValue() == cpWafer
					.getId().longValue()) {
				return CPWaferCheck.CHECKED.ordinal();
			}
		}
		String jpql = "select o from CPWafer o where o.internalWaferCode = ? ";
		@SuppressWarnings("unchecked")
		List<CPWafer> result = (List<CPWafer>) getQueryChannelService()
				.createJpqlQuery(jpql)
				.setParameters(new Object[] { cpWafer.getInternalWaferCode() }).list();
		for (CPWafer cpWafer1 : result) {
			List<CPNode> node = cpWafer1.getCpLot().getCpProcess().getCpNodes();
			CPNode nowNode=null;
			for (CPNode cpNode1 : node) {
				if(cpNode1.getName().equals(cpNode.getName()))
				{
					nowNode=cpNode1;
				}	
			}
			if(nowNode==null)continue;
			List<CPWaferCheckLog> cpwcl1 = this.findCPWaferCheck(cpWafer1.getCpLot().getId(),
					nowNode.getId());
			for (CPWaferCheckLog cpWaferCheckLog : cpwcl1) {
				if (cpWaferCheckLog.getCpWafer().getId().longValue() == cpWafer1
						.getId().longValue()) {
					return CPWaferCheck.CHECKED.ordinal();
				}
			}
		}
		return CPWaferCheck.UNCHECKED.ordinal();
	}

	private List<CPWaferCheckLog> findCPWaferCheck(Long cpLotId, Long nodeId) {
		String jpql = "select o from CPWaferCheckLog o where o.cpLot.id = ? and o.cpNode.id = ?";
		@SuppressWarnings("unchecked")
		List<CPWaferCheckLog> result = (List<CPWaferCheckLog>) getQueryChannelService()
				.createJpqlQuery(jpql)
				.setParameters(new Object[] { cpLotId, nodeId }).list();
		return result;
	}

	@Override
	public InvokeResult saveCheck(String ids, CPWaferDTO cPWaferDTO) {
		SaveBaseBean sbb = new SaveBaseBean();
		BeanUtils.copyProperties(cPWaferDTO, sbb);
		application.saveCheck(ids, CPWaferCheck.CHECKED, sbb);
		return InvokeResult.success();
	}

	@Transactional
	public List<CPWaferDTO> getCPWaferInfoByNode(CPLot cpLot, CPNode cpNode) {
		List<CPWafer> result = cpLot.getCpWafers();
		List<CPWaferDTO> resultDTOs = new ArrayList<CPWaferDTO>();
		List<CPWaferCheckLog> cpwcl = this.findCPWaferCheck(cpLot.getId(),
				cpNode.getId());
		String lotNum = cpLot.getInternalLotNumber();
		if (cpLot.getSourceParentSeparationNo() != null
				&& !"".equals(cpLot.getSourceParentSeparationNo())) {
			lotNum = cpLot.getSourceParentSeparationNo();
		}
		lotNum = MessageFormat.format("%{0}%", lotNum);
		List<TskInfo> tskInfoList = tskInfoApplication
				.find("select o from TskInfo o where o.lotNum like ? and o.testSite = ?",
						lotNum, cpNode.getName());
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
			if (cpLot.getCurrentState().indexOf(cpNode.getName()) < 0
					&& ("IQC".equalsIgnoreCase(cpNode.getName()) || "FQC"
							.equalsIgnoreCase(cpNode.getName()))) {
				cpWaferDTO.setIsCheck(this.findNodeCheckStatus(cpwcl, cpWafer, cpNode));
			}
			if (tskInfoList != null && tskInfoList.size() > 0) {
				cpWaferDTO = this.getTskInfoByWaferId(tskInfoList, cpWaferDTO);
			}
			resultDTOs.add(cpWaferDTO);
		}
		return resultDTOs;
	}

	@Transactional
	public boolean checkCPWaferSbl(CPLot cpLot, String nodeName) {
		String jsonRes = this.sblClient.cpInfoForJson(
				cpLot.getInternalLotNumber(), nodeName,
				String.valueOf(cpLot.getCustomerCPLot().getCpInfo().getId()));
		JSONObject jsonObject = JSONObject.fromObject(jsonRes);
		this.application.changeStatusSBL(jsonObject.getJSONArray("BYWAFER"),
				cpLot.getId());
		if (jsonObject.get("BYLOT") != null) {
			if ("PASS".equalsIgnoreCase(jsonObject.get("BYLOT").toString())) {
				return true;
			}
		}
		return false;
	}

	@Transactional
	public List<CPWaferDTO> getCPWaferYieldByLot(CPLot cpLot) {
		List<CPWafer> result = cpLot.getCpWafers();
		List<CPWaferDTO> resultDTOs = new ArrayList<CPWaferDTO>();
		String lotNum = cpLot.getInternalLotNumber();
		if (cpLot.getSourceParentSeparationNo() != null
				&& !"".equals(cpLot.getSourceParentSeparationNo())) {
			lotNum = cpLot.getSourceParentSeparationNo();
		}
		lotNum = MessageFormat.format("%{0}%", lotNum);
		List<TskInfo> tskInfoList = tskInfoApplication
				.find("select o from TskInfo o where o.lotNum like ? order by o.testSite",
						lotNum);
		for (CPWafer cpWafer : result) {
			CPWaferDTO cpWaferDTO = new CPWaferDTO();
			cpWaferDTO = CPWaferAssembler.toDTO(cpWafer);
			if (tskInfoList != null && tskInfoList.size() > 0) {
				cpWaferDTO = this.getTskInfosByWaferId(tskInfoList, cpWaferDTO);
			}
			resultDTOs.add(cpWaferDTO);
		}
		return resultDTOs;
	}

	private CPWaferDTO getTskInfosByWaferId(List<TskInfo> tskInfoList,
			CPWaferDTO cpWaferDTO) {
		for (TskInfo tskInfo : tskInfoList) {
			if (cpWaferDTO.getInternalWaferCode().equalsIgnoreCase(
					tskInfo.getWaferId())) {
				cpWaferDTO.setPass(this.getStringArray(cpWaferDTO.getPass(),
						String.valueOf(tskInfo.getPassDice())));
				cpWaferDTO.setFail(this.getStringArray(cpWaferDTO.getFail(),
						String.valueOf(tskInfo.getFailDice())));
				cpWaferDTO.setYield(this.getStringArray(cpWaferDTO.getYield(),
						String.valueOf(tskInfo.getYield())));
				cpWaferDTO.setState(1);
			}
		}
		return cpWaferDTO;
	}

	private String getStringArray(String str, String addStr) {
		return str == null || "0".equals(ZERO) ? String.valueOf(addStr) + ","
				: str + addStr + ",";
	}

	@Transactional
	public List<CPWaferDTO> getCPWaferYieldByLotId(Long id) {
		return this.getCPWaferYieldByLot(this.cpLotApplication.get(id));
	}

	@Override
	public InvokeResult getCPWaferCheck(Long cpLotId, Long nodeId) {
		CPNode cpNode = cpNodeApplication.get(nodeId);
	    CPLot cpLot = CPLot.get(CPLot.class,cpLotId);
		String lotNum = cpLot.getInternalLotNumber();
		String jpql = "select o from CPWafer o where o.cpLot.id = ? ";
		@SuppressWarnings("unchecked")
		List<CPWafer> result = (List<CPWafer>) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { cpLotId })
				.list();
		int count = 0;
		if (cpNode.getName().startsWith("CP")) {
			CPTestingNode cpTestingNode = CPTestingNode.get(CPTestingNode.class, cpNode.getId());
			if (cpTestingNode.getTestProgram().getTestSys().contains("3360")) {
				List<TskInfo> tskInfoList = tskInfoApplication
						.find("select o from TskInfo o where o.lotNum like ? and o.testSite like ? order by o.waferId",
								lotNum,cpTestingNode.getName());
				if (tskInfoList.size() < 3) {
					return InvokeResult.success();
				}
				List<CPSBLTemplate> cpSBLTemplates = cpSBLTemplateApplication.findByInternalProductId(cpLot.getCustomerCPLot().getCpInfo().getId());
				for (int p = 0 ; p < cpSBLTemplates.size() ; p++) {
					if (cpSBLTemplates.get(0) == null) {
						throw new RuntimeException("请先维护SBL信息");
					}
					if ("by wafer".equals(cpSBLTemplates.get(p).getTestRange())) {
						for (int q = 0 ; q < tskInfoList.size() ; q++) {
							String yield = tskInfoList.get(q).getYield().substring(0, tskInfoList.get(q).getYield().indexOf("%"));
							double yieldDouble = Double.parseDouble(yield);
							if (cpSBLTemplates.get(p).getUpperLimit() > yieldDouble) {
								count++;
								if (count > 2) {
									String employeejpql = "SELECT b.USER_ACCOUNT FROM ks_authorizations a join ks_actors  b on a.ACTOR_ID = b.ID WHERE a.AUTHORITY_ID = (SELECT ID FROM ks_authorities WHERE `NAME` = 'PE')";
									List employeeresult = getQueryChannelService()
											.createSqlQuery(employeejpql).list();
									List<EmployeeVo> employees = employeeFacade.findEmployeeByAccount(employeeresult);
									for (int i = 0 ; i < employees.size() ; i++ ) {
										long employeeid = employees.get(i).getId();
										EmployeeDTO toPerson = employeeFacade.getEmployeeById(employeeid);
										senderMailUtils.sendMailHelper(mailTitle,
												mailContent + tskInfoList.get(q-2).getWaferId() +","
												            + tskInfoList.get(q-1).getWaferId() +","
												            + tskInfoList.get(q).getWaferId(), toPerson.getEmail());
									}
									return InvokeResult.failure("连续3片wafer良率过低请检查:"+tskInfoList.get(q-2).getWaferId()+","+
											                    tskInfoList.get(q-1).getWaferId()+","+
											                    tskInfoList.get(q).getWaferId());
								}
							} else {
								count = 0;
							}
						}
					}
				}
				if (tskInfoList.size() == result.size()) {
					return InvokeResult.failure("TestingEnd-3360");
				}
			} else {
				return InvokeResult.failure("TestingEnd");
			}
		}
		return InvokeResult.success();
	}
}
