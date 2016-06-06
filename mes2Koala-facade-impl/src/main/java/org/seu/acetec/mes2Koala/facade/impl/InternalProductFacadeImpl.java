package org.seu.acetec.mes2Koala.facade.impl;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.application.InternalProductApplication;
import org.seu.acetec.mes2Koala.core.common.SerialNumberUtils;
import org.seu.acetec.mes2Koala.core.domain.Customer;
import org.seu.acetec.mes2Koala.core.domain.InternalProduct;
import org.seu.acetec.mes2Koala.facade.InternalProductFacade;
import org.seu.acetec.mes2Koala.facade.dto.InternalProductDTO;
import org.seu.acetec.mes2Koala.facade.dto.ProcessTemplateDTO;
import org.seu.acetec.mes2Koala.facade.impl.assembler.CustomerAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.InternalProductAssembler;

import javax.inject.Inject;
import javax.inject.Named;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Named
public class InternalProductFacadeImpl implements InternalProductFacade {

	@Inject
	private InternalProductApplication application;

	private QueryChannelService queryChannel;

	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(
					QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}

	public InvokeResult getInternalProduct(Long id) {
		return InvokeResult.success(InternalProductAssembler.toDTO(application
				.get(id)));
	}

	public InvokeResult createInternalProduct(
			InternalProductDTO internalProductDTO) {
		application.create(InternalProductAssembler
				.toEntity(internalProductDTO));
		return InvokeResult.success();
	}

	public InvokeResult updateInternalProduct(
			InternalProductDTO internalProductDTO) {
		application.update(InternalProductAssembler
				.toEntity(internalProductDTO));
		return InvokeResult.success();
	}

	public InvokeResult removeInternalProduct(Long id) {
		application.remove(application.get(id));
		return InvokeResult.success();
	}

	public InvokeResult removeInternalProducts(Long[] ids) {
		Set<InternalProduct> internalProducts = new HashSet<InternalProduct>();
		for (Long id : ids) {
			internalProducts.add(application.get(id));
		}
		application.removeAll(internalProducts);
		return InvokeResult.success();
	}

	public List<InternalProductDTO> findAllInternalProduct() {
		return InternalProductAssembler.toDTOs(application.findAll());
	}

	public List<InternalProductDTO> findAllInternalProduct(String type) {
		return InternalProductAssembler
				.toDTOs(application
						.find("select _internalProduct from InternalProduct _internalProduct where _internalProduct.testType = '"
								+ type + "'"));
	}

	public Page<InternalProductDTO> pageQueryInternalProduct(
			InternalProductDTO queryVo, int currentPage, int pageSize) {
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select _internalProduct from InternalProduct _internalProduct   where 1=1 ");
		if (queryVo.getCustomerProductNumber() != null
				&& !"".equals(queryVo.getCustomerProductNumber())) {
			jpql.append(" and _internalProduct.customerProductNumber like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCustomerProductNumber()));
		}
		if (queryVo.getCustomerProductRevision() != null
				&& !"".equals(queryVo.getCustomerProductRevision())) {
			jpql.append(" and _internalProduct.customerProductRevision like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCustomerProductRevision()));
		}
		if (queryVo.getInternalProductNumber() != null
				&& !"".equals(queryVo.getInternalProductNumber())) {
			jpql.append(" and _internalProduct.internalProductNumber like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getInternalProductNumber()));
		}
		if (queryVo.getInternalProductRevision() != null
				&& !"".equals(queryVo.getInternalProductRevision())) {
			jpql.append(" and _internalProduct.internalProductRevision like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getInternalProductRevision()));
		}
		if (queryVo.getPackageType() != null
				&& !"".equals(queryVo.getPackageType())) {
			jpql.append(" and _internalProduct.packageType like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getPackageType()));
		}
		if (queryVo.getShipmentProductNumber() != null
				&& !"".equals(queryVo.getShipmentProductNumber())) {
			jpql.append(" and _internalProduct.shipmentProductNumber like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getShipmentProductNumber()));
		}
		Page<InternalProduct> pages = getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.setPage(currentPage, pageSize).pagedList();

		return new Page<InternalProductDTO>(pages.getStart(),
				pages.getResultCount(), pageSize,
				InternalProductAssembler.toDTOs(pages.getData()));
	}

	@Override
	public InvokeResult bindProcess(InternalProductDTO internalProductDTO) {
		InternalProduct i = application.get(internalProductDTO.getId());
		Long pid = internalProductDTO.getProcessId();
		InternalProductDTO iDTO = InternalProductAssembler.toDTO(i);
		ProcessTemplateDTO pDTO = new ProcessTemplateDTO();
		pDTO.setId(pid);
		iDTO.setProcessTemplateDTO(pDTO);
		application.update(InternalProductAssembler.toEntity(iDTO));

		return InvokeResult.success();
	}

	@Override
	public List<InternalProductDTO> findAllInternalProductByCus(String customerNumber, String lineType) {
		String jpql = "select _internalProduct from InternalProduct _internalProduct  left join _internalProduct.customerDirect a where a.number=? and _internalProduct.testType=?";
		List<InternalProduct> products = getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[]{customerNumber,lineType}).list();
		return InternalProductAssembler.toDTOs(products);
	}

	@Override
	public InvokeResult queryProductByVersion(String cuscode, String version) {
		String jpql = "select _internalProduct from InternalProduct _internalProduct  left join _internalProduct.customerDirect a where a.number=? and _internalProduct.customerProductRevision=?";
		List<InternalProduct> products = getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[]{cuscode,version}).list();
		return InvokeResult.success(InternalProductAssembler.toDTOs(products));
	}

	/**
	 * 生成Pid
	 * pid=客户产品型号+两位序列号
	 */
	@Override
	public InvokeResult createPID(InternalProductDTO internalProductDTO) {
		String jpql = "select a.internalProductNumber from InternalProduct a where a.customerProductNumber=? ORDER BY a.internalProductNumber DESC";
		Object internalProductNumber = getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[]{internalProductDTO.getCustomerProductNumber()}).singleResult();
		if(internalProductNumber == null){
			return InvokeResult.success(internalProductDTO.getCustomerProductNumber() + "01");
		}else{
			String oldPID = internalProductNumber.toString();
			String oldNo = oldPID.substring(oldPID.length()-2);
			String newNo = internalProductDTO.getCustomerProductNumber().concat(SerialNumberUtils.getNumberStrNext(2, Integer.valueOf(oldNo)));
			return InvokeResult.success(newNo);
		}
	}
}
