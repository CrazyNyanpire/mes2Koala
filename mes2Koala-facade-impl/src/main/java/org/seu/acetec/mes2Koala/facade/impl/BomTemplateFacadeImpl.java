package org.seu.acetec.mes2Koala.facade.impl;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.application.BomTemplateApplication;
import org.seu.acetec.mes2Koala.application.FTInfoApplication;
import org.seu.acetec.mes2Koala.application.InternalProductApplication;
import org.seu.acetec.mes2Koala.core.domain.BomTemplate;
import org.seu.acetec.mes2Koala.core.domain.FTInfo;
import org.seu.acetec.mes2Koala.core.domain.InternalProduct;
import org.seu.acetec.mes2Koala.facade.BomTemplateFacade;
import org.seu.acetec.mes2Koala.facade.ExcelFacade;
import org.seu.acetec.mes2Koala.facade.dto.BomTemplateDTO;
import org.seu.acetec.mes2Koala.facade.dto.InternalProductDTO;
import org.seu.acetec.mes2Koala.facade.impl.assembler.BomTemplateAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.InternalProductAssembler;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Named
public class BomTemplateFacadeImpl implements BomTemplateFacade {

	@Inject
	private FTInfoApplication ftInfoApplication;

	@Inject
	private BomTemplateApplication application;

	private QueryChannelService queryChannel;

	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}

	public InvokeResult getBomTemplate(Long id) {
		return InvokeResult.success(BomTemplateAssembler.toDTO(application.get(id)));
	}

	public InvokeResult createBomTemplate(BomTemplateDTO bomTemplateDTO) {
		if (fillBomDTOWithInternalProductID(bomTemplateDTO)) {
			application.create(BomTemplateAssembler.toEntity(bomTemplateDTO));
			return InvokeResult.success();
		}
		return InvokeResult.failure("找不到对应的产品信息");
	}

	public InvokeResult updateBomTemplate(BomTemplateDTO bomTemplateDTO) {
		if (fillBomDTOWithInternalProductID(bomTemplateDTO)) {
			application.update(BomTemplateAssembler.toEntity(bomTemplateDTO));
			return InvokeResult.success();
		}
		return InvokeResult.failure("找不到对应的产品信息");
	}

	public InvokeResult removeBomTemplate(Long id) {
		application.remove(application.get(id));
		return InvokeResult.success();
	}

	public InvokeResult removeBomTemplates(Long[] ids) {
		Set<BomTemplate> bomTemplates = new HashSet<BomTemplate>();
		for (Long id : ids) {
			BomTemplate bomTemplate = application.get(id);
			bomTemplates.add(bomTemplate);
		}
		application.removeAll(bomTemplates);
		return InvokeResult.success();
	}

	public List<BomTemplateDTO> findAllBomTemplate() {
		return BomTemplateAssembler.toDTOs(application.findAll());
	}

	public Page<BomTemplateDTO> pageQueryBomTemplate(BomTemplateDTO queryVo, int currentPage, int pageSize) {
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder("select _bom from BomTemplate _bom   where 1=1 ");
		if (queryVo.getCreateTimestamp() != null) {
			jpql.append(" and _bom.createTimestamp between ? and ? ");
			conditionVals.add(queryVo.getCreateTimestamp());
			conditionVals.add(queryVo.getCreateTimestampEnd());
		}
		if (queryVo.getLastModifyTimestamp() != null) {
			jpql.append(" and _bom.lastModifyTimestamp between ? and ? ");
			conditionVals.add(queryVo.getLastModifyTimestamp());
			conditionVals.add(queryVo.getLastModifyTimestampEnd());
		}
		if (queryVo.getCreateEmployNo() != null && !"".equals(queryVo.getCreateEmployNo())) {
			jpql.append(" and _bom.createEmployNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCreateEmployNo()));
		}
		if (queryVo.getLastModifyEmployNo() != null && !"".equals(queryVo.getLastModifyEmployNo())) {
			jpql.append(" and _bom.lastModifyEmployNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getLastModifyEmployNo()));
		}
		/*
		 * if (queryVo.getLevel() != null && !"".equals(queryVo.getLevel())) {
		 * jpql.append(" and _bom.level like ?");
		 * conditionVals.add(MessageFormat.format("%{0}%", queryVo.getLevel()));
		 * }
		 */
		if (queryVo.getModelNumber() != null && !"".equals(queryVo.getModelNumber())) {
			jpql.append(" and _bom.modelNumber like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getModelNumber()));
		}
		if (queryVo.getNumber() != null && !"".equals(queryVo.getNumber())) {
			jpql.append(" and _bom.number like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getNumber()));
		}
		if (queryVo.getRevision() != null && !"".equals(queryVo.getRevision())) {
			jpql.append(" and _bom.revision like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getRevision()));
		}
		if (queryVo.getDescription() != null && !"".equals(queryVo.getDescription())) {
			jpql.append(" and _bom.description like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getDescription()));
		}
		if (queryVo.getCustomerCode() != null && !"".equals(queryVo.getCustomerCode())) {
			jpql.append(" and _bom.internalProduct.customerDirect.code like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCustomerCode()));
		}
		if (queryVo.getUm() != null && !"".equals(queryVo.getUm())) {
			jpql.append(" and _bom.um like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getUm()));
		}
		/*
		 * if (queryVo.getQuantity() != null) { jpql.append(
		 * " and _bom.quantity=?"); conditionVals.add(queryVo.getQuantity()); }
		 * if (queryVo.getTheoryQuantity() != null) { jpql.append(
		 * " and _bom.theoryQuantity=?");
		 * conditionVals.add(queryVo.getTheoryQuantity()); }
		 */
		/*
		 * if (queryVo.getItemDescription() != null &&
		 * !"".equals(queryVo.getItemDescription())) { jpql.append(
		 * " and _bom.itemDescription like ?");
		 * conditionVals.add(MessageFormat.format("%{0}%",
		 * queryVo.getItemDescription())); }
		 */
		if (queryVo.getManufacturerName() != null && !"".equals(queryVo.getManufacturerName())) {
			jpql.append(" and _bom.manufacturerName like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getManufacturerName()));
		}
		if (queryVo.getInternalProductDTO() != null ) {
			if ( queryVo.getInternalProductDTO().getTestType() != null && !"".equals(queryVo.getInternalProductDTO().getTestType())) {
				jpql.append(" and _bom.internalProduct.testType like ?");
				conditionVals.add(MessageFormat.format("%{0}%", queryVo.getInternalProductDTO().getTestType()));
			}
			if ( queryVo.getInternalProductDTO().getCustomerProductNumber() != null && !"".equals( queryVo.getInternalProductDTO().getCustomerProductNumber() )){
				jpql.append(" and _bom.internalProduct.customerProductNumber like ?" );
				conditionVals.add(MessageFormat.format("%{0}%", queryVo.getInternalProductDTO().getCustomerProductNumber()));
			}
		}
		Page<BomTemplate> pages = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.setPage(currentPage, pageSize).pagedList();

		return new Page<BomTemplateDTO>(pages.getStart(), pages.getResultCount(), pageSize,
				BomTemplateAssembler.toDTOs(pages.getData()));
	}

	/**
	 * 根据bomDTO中的ModelNumber和CustomerCode唯一定位需要绑定的内部产品id，将id填充至bomDTO中
	 *
	 * @param bomTemplateDTO
	 * @return 找到产品则填充返回true，找不到则返回false
	 */
	public boolean fillBomDTOWithInternalProductID(BomTemplateDTO bomTemplateDTO) {
		// 如果DTO内产品型号(ModelNumber)不为空，则检查是否存在
		if (bomTemplateDTO.getModelNumber() != null && !"".equals(bomTemplateDTO.getModelNumber())
				&& bomTemplateDTO.getCustomerCode() != null && !"".equals(bomTemplateDTO.getCustomerCode())) {
			String customerProductNumber = bomTemplateDTO.getModelNumber();
			String customerCode = bomTemplateDTO.getCustomerCode();
			FTInfo internalProducts = ftInfoApplication
					.findByCustomerProductNumberAndCustomerCode(customerProductNumber, customerCode);
			if (internalProducts == null)
				return false;
			else {
				if (bomTemplateDTO.getInternalProductDTO() == null) {
					InternalProductDTO internalProductDTO = new InternalProductDTO();
					internalProductDTO.setId(internalProducts.getId());
					bomTemplateDTO.setInternalProductDTO(internalProductDTO);
				} else {
					bomTemplateDTO.getInternalProductDTO().setId(internalProducts.getId());
				}
				return true;
			}
		} else {
			return false;
		}
	}

}
