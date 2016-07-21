package org.seu.acetec.mes2Koala.facade.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
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
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.facade.impl.assembler.CPSBLTemplateAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.SBLTemplateAssembler;
import org.seu.acetec.mes2Koala.facade.sbl.SBLClient;
import org.seu.acetec.mes2Koala.facade.CPSBLTemplateFacade;
import org.seu.acetec.mes2Koala.application.CPLotApplication;
import org.seu.acetec.mes2Koala.application.CPSBLTemplateApplication;
import org.seu.acetec.mes2Koala.application.InternalProductApplication;
import org.seu.acetec.mes2Koala.core.domain.*;
import org.springframework.transaction.annotation.Transactional;

@Named
@Transactional
public class CPSBLTemplateFacadeImpl implements CPSBLTemplateFacade {

	@Inject
	private CPSBLTemplateApplication application;

	@Inject
	private CPLotApplication cpLotApplication;

	@Inject
	private SBLClient sblClient;

	private QueryChannelService queryChannel;

	@Inject
	private InternalProductApplication internalProductApplication;

	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(
					QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}

	public InvokeResult getCPSBLTemplate(Long id) {
		return InvokeResult.success(CPSBLTemplateAssembler.toDTO(application
				.getCPSBLTemplate(id)));
	}

	public InvokeResult creatCPSBLTemplate(CPSBLTemplateDTO cPSBLTemplateDTO) {
		application.creatCPSBLTemplate(CPSBLTemplateAssembler
				.toEntity(cPSBLTemplateDTO));
		return InvokeResult.success();
	}

	public InvokeResult updateCPSBLTemplate(CPSBLTemplateDTO cPSBLTemplateDTO) {
		application.updateCPSBLTemplate(CPSBLTemplateAssembler
				.toEntity(cPSBLTemplateDTO));
		return InvokeResult.success();
	}

	public InvokeResult removeCPSBLTemplate(Long id) {
		application.removeCPSBLTemplate(application.getCPSBLTemplate(id));
		return InvokeResult.success();
	}

	public InvokeResult removeCPSBLTemplates(Long[] ids) {
		Set<CPSBLTemplate> cPSBLTemplates = new HashSet<CPSBLTemplate>();
		for (Long id : ids) {
			cPSBLTemplates.add(application.getCPSBLTemplate(id));
		}
		application.removeCPSBLTemplates(cPSBLTemplates);
		return InvokeResult.success();
	}

	public List<CPSBLTemplateDTO> findAllCPSBLTemplate() {
		return CPSBLTemplateAssembler
				.toDTOs(application.findAllCPSBLTemplate());
	}

	public Page<CPSBLTemplateDTO> pageQueryCPSBLTemplate(
			CPSBLTemplateDTO queryVo, int currentPage, int pageSize) {
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select _cPSBLTemplate from CPSBLTemplate _cPSBLTemplate   where 1=1 ");
		if (queryVo.getCreateTimestamp() != null) {
			jpql.append(" and _cPSBLTemplate.createTimestamp between ? and ? ");
			conditionVals.add(queryVo.getCreateTimestamp());
			conditionVals.add(queryVo.getCreateTimestampEnd());
		}
		if (queryVo.getLastModifyTimestamp() != null) {
			jpql.append(" and _cPSBLTemplate.lastModifyTimestamp between ? and ? ");
			conditionVals.add(queryVo.getLastModifyTimestamp());
			conditionVals.add(queryVo.getLastModifyTimestampEnd());
		}
		if (queryVo.getCreateEmployNo() != null
				&& !"".equals(queryVo.getCreateEmployNo())) {
			jpql.append(" and _cPSBLTemplate.createEmployNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCreateEmployNo()));
		}
		if (queryVo.getLastModifyEmployNo() != null
				&& !"".equals(queryVo.getLastModifyEmployNo())) {
			jpql.append(" and _cPSBLTemplate.lastModifyEmployNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getLastModifyEmployNo()));
		}
		if (queryVo.getParentIntegrationIds() != null
				&& !"".equals(queryVo.getParentIntegrationIds())) {
			jpql.append(" and _cPSBLTemplate.parentIntegrationIds like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getParentIntegrationIds()));
		}
		if (queryVo.getRule() != null) {
			jpql.append(" and _cPSBLTemplate.rule=?");
			conditionVals.add(queryVo.getRule());
		}
		if (queryVo.getName() != null && !"".equals(queryVo.getName())) {
			jpql.append(" and _cPSBLTemplate.name like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getName()));
		}
		if (queryVo.getLowerLimit() != null) {
			jpql.append(" and _cPSBLTemplate.lowerLimit=?");
			conditionVals.add(queryVo.getLowerLimit());
		}
		if (queryVo.getUpperLimit() != null) {
			jpql.append(" and _cPSBLTemplate.upperLimit=?");
			conditionVals.add(queryVo.getUpperLimit());
		}
		if (queryVo.getSite() != null) {
			jpql.append(" and _cPSBLTemplate.site=?");
			conditionVals.add(queryVo.getSite());
		}
		if (queryVo.getNode() != null && !"".equals(queryVo.getNode())) {
			jpql.append(" and _cPSBLTemplate.node like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getNode()));
		}
		if (queryVo.getTestRange() != null
				&& !"".equals(queryVo.getTestRange())) {
			jpql.append(" and _cPSBLTemplate.range like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getTestRange()));
		}
		if (queryVo.getControlType() != null
				&& !"".equals(queryVo.getControlType())) {
			jpql.append(" and _cPSBLTemplate.controlType like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getControlType()));
		}
		Page<CPSBLTemplate> pages = getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.setPage(currentPage, pageSize).pagedList();

		return new Page<CPSBLTemplateDTO>(pages.getStart(),
				pages.getResultCount(), pageSize,
				CPSBLTemplateAssembler.toDTOs(pages.getData()));
	}

	@Override
	public InternalProductDTO findInternalProductByCPSBLTemplate(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InvokeResult bindSBLTemplates(Long internalProductId,
			List<CPSBLTemplateDTO> cpSBLTemplateDTOs) {
		InternalProduct internalProduct = internalProductApplication
				.get(internalProductId);

		// 先移除所有已经绑定的模板
		List<CPSBLTemplate> oldSBLTemplates = application
				.findByInternalProductId(internalProductId);
		application.removeAll(oldSBLTemplates);

		if (cpSBLTemplateDTOs.size() <= 0) {
			return InvokeResult.success();
		}

		// 添加新增加的模板
		List<CPSBLTemplate> newSBLTemplates = CPSBLTemplateAssembler
				.toEntities(cpSBLTemplateDTOs);
		for (CPSBLTemplate newSBLTemplate : newSBLTemplates) {
			// 绑定产品
			newSBLTemplate.setInternalProduct(internalProduct);
		}
		application.createAll(newSBLTemplates);
		this.getCPSBLTemplatesJSON(internalProduct, cpSBLTemplateDTOs);
		return InvokeResult.success();
	}

	@Override
	public List<CPSBLTemplateDTO> getCPSBLTemplatesByProduct(Long id) {
		List<CPSBLTemplateDTO> result = new ArrayList<CPSBLTemplateDTO>();
		InternalProduct internalProduct = internalProductApplication.get(id);
		List<CPSBLTemplate> cpSBLTemplates = application
				.findByInternalProductId(id);
		if (cpSBLTemplates != null) {
			if (cpSBLTemplates.get(0) != null) {
				for (CPSBLTemplate cpSBLTemplate : cpSBLTemplates) {
					cpSBLTemplate.setInternalProduct(internalProduct);
					result.add(CPSBLTemplateAssembler.toDTO(cpSBLTemplate));
				}
			} else {
				return null;
			}
		} else {
			return null;
		}
		return result;
	}

	@Override
	public JSONArray getCPSBLTemplatesByLotId(Long id) {
		InternalProduct internalProduct = this.internalProductApplication
				.get(id);
		List<CPSBLTemplate> list = application.findByInternalProductId(id);
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject;
		for (CPSBLTemplate cpSBLTemplate : list) {
			jsonObject = new JSONObject();
			jsonObject.put("TOID", internalProduct.getId());
			jsonObject.put("BIN", cpSBLTemplate.getSite());
			jsonObject.put("QUALITY", cpSBLTemplate.getQuality());
			jsonObject.put("DOWN_LIMIT", cpSBLTemplate.getLowerLimit());
			jsonObject.put("TOP_LIMIT", cpSBLTemplate.getUpperLimit());
			jsonObject.put("BinType", cpSBLTemplate.getControlType());
			jsonObject.put("StationName", cpSBLTemplate.getNode());
			jsonObject.put("HorS", hOrS.get(cpSBLTemplate.getType().name()));
			jsonObject.put("Scope", cpSBLTemplate.getTestRange());
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}

	public String getCPSBLTemplatesJSON(InternalProduct internalProduct,
			List<CPSBLTemplateDTO> list) {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject;
		for (CPSBLTemplateDTO cpSBLTemplate : list) {
			jsonObject = new JSONObject();
			jsonObject.put("TOID", internalProduct.getId());
			jsonObject.put("BIN", cpSBLTemplate.getSite());
			jsonObject.put("QUALITY", cpSBLTemplate.getQuality());
			jsonObject.put("DOWN_LIMIT", cpSBLTemplate.getLowerLimit());
			jsonObject.put("TOP_LIMIT", cpSBLTemplate.getUpperLimit());
			jsonObject.put("BinType", cpSBLTemplate.getControlType());
			jsonObject.put("StationName", cpSBLTemplate.getNode());
			jsonObject.put("HorS", hOrS.get(cpSBLTemplate.getType().name()));
			jsonObject.put("Scope", cpSBLTemplate.getTestRange());
			jsonArray.add(jsonObject);
		}
		JSONObject res = new JSONObject();
		res.put("date", jsonArray);
		return sblClient.insertCPSBL(res.toString());
	}

	@SuppressWarnings("serial")
	Map<String, String> hOrS = new HashMap<String, String>() {
		{
			put("HB", "HardBin");
			put("SB", "SoftBin");
		}
	};
}
