package org.seu.acetec.mes2Koala.facade.impl;

import com.google.common.base.Joiner;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.application.*;
import org.seu.acetec.mes2Koala.core.domain.*;
import org.seu.acetec.mes2Koala.core.enums.CustomerLotState;
import org.seu.acetec.mes2Koala.core.enums.TestTypeForWms;
import org.seu.acetec.mes2Koala.facade.CustomerFTLotFacade;
import org.seu.acetec.mes2Koala.facade.InternalProductFacade;
import org.seu.acetec.mes2Koala.facade.dto.CustomerFTLotDTO;
import org.seu.acetec.mes2Koala.facade.dto.FTLotDTO;
import org.seu.acetec.mes2Koala.facade.dto.InternalProductDTO;
import org.seu.acetec.mes2Koala.facade.impl.assembler.CustomerFTLotAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.FTInfoAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.FTLotAssembler;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.inject.Inject;
import javax.inject.Named;

import java.text.MessageFormat;
import java.util.*;

@Named
public class CustomerFTLotFacadeImpl implements CustomerFTLotFacade {

	@Inject
	FTProcessApplication FTProcessApplication;

	@Inject
	CustomerFTLotApplication customerFTLotApplication;

	@Inject
	FTLotOptionLogApplication ftLotOptionLogApplication;

	@Inject
	FTLotApplication ftLotApplication;

	@Inject
	InternalProductFacade internalProductFacade;

	QueryChannelService queryChannel;

	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(
					QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}

	@Override
	public CustomerFTLotDTO findCustomerFTLotByWmsID(String id) {
		CustomerFTLot customerFTLot = customerFTLotApplication.findByWmsId(id);
		return CustomerFTLotAssembler.toDTO(customerFTLot);
	}

	public InvokeResult getCustomerFTLot(Long id) {
		return InvokeResult.success(CustomerFTLotAssembler
				.toDTO(customerFTLotApplication.get(id)));
	}

	public InvokeResult removeCustomerFTLots(Long[] ids) {
		Set<CustomerFTLot> customerFTLots = new HashSet<CustomerFTLot>();
		for (Long id : ids) {
			customerFTLots.add(customerFTLotApplication.get(id));
		}
		customerFTLotApplication.removeAll(customerFTLots);
		return InvokeResult.success();
	}

	public Page<CustomerFTLotDTO> pageQueryCustomerFTLot(
			CustomerFTLotDTO queryVo, int currentPage, int pageSize,
			String sortname, String sortorder) {
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select _customerFTLot from CustomerFTLot _customerFTLot   where _customerFTLot.logic=null ");
		if (queryVo.getPackageNumber() != null
				&& !"".equals(queryVo.getPackageNumber())) {
			jpql.append(" and _customerFTLot.packageNumber like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getPackageNumber()));
		}
		if (queryVo.getProductVersion() != null
				&& !"".equals(queryVo.getProductVersion())) {
			jpql.append(" and _customerFTLot.productVersion like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getProductVersion()));
		}
		if (queryVo.getCustomerProductNumber() != null
				&& !"".equals(queryVo.getCustomerProductNumber())) {
			jpql.append(" and _customerFTLot.customerProductNumber like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCustomerProductNumber()));
		}
		if (queryVo.getDeliveryType() != null
				&& !"".equals(queryVo.getDeliveryType())) {
			jpql.append(" and _customerFTLot.incomingStyle like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getDeliveryType()));
		}
		if (queryVo.getDateCode() != null && !"".equals(queryVo.getDateCode())) {
			jpql.append(" and _customerFTLot.dateCode like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getDateCode()));
		}
		if (queryVo.getMaterialType() != null
				&& !"".equals(queryVo.getMaterialType())) {
			jpql.append(" and _customerFTLot.materialType like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getMaterialType()));
		}
		if (queryVo.getTaxType() != null && !"".equals(queryVo.getTaxType())) {
			jpql.append(" and _customerFTLot.taxType like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getTaxType()));
		}
		if (queryVo.getWireBond() != null && !"".equals(queryVo.getWireBond())) {
			jpql.append(" and _customerFTLot.wireBond like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getWireBond()));
		}
		if (queryVo.getWaferLot() != null && !"".equals(queryVo.getWaferLot())) {
			jpql.append(" and _customerFTLot.waferLot like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getWaferLot()));
		}
		if (queryVo.getMFGPN() != null && !"".equals(queryVo.getMFGPN())) {
			jpql.append(" and _customerFTLot.MFGPN like ?");
			conditionVals
					.add(MessageFormat.format("%{0}%", queryVo.getMFGPN()));
		}
		if (queryVo.getWaferManufacturer() != null
				&& !"".equals(queryVo.getWaferManufacturer())) {
			jpql.append(" and _customerFTLot.waferManufacturer like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getWaferManufacturer()));
		}
		if (queryVo.getCustomerLotNumber() != null
				&& !"".equals(queryVo.getCustomerLotNumber())) {
			jpql.append(" and _customerFTLot.customerLotNumber like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCustomerLotNumber()));
		}
		if (queryVo.getCustomerNumber() != null
				&& !"".equals(queryVo.getCustomerNumber())) {
			jpql.append(" and _customerFTLot.customerNumber like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCustomerNumber()));
		}
		if (queryVo.getIncomingQuantity() != null
				&& !"".equals(queryVo.getIncomingQuantity())) {
			jpql.append(" and _customerFTLot.incomingQuantity = ?");
			conditionVals.add(queryVo.getIncomingQuantity());
		}
		if (sortname != null && !"".equals(sortname) && sortorder != null
				&& !"".equals(sortname)) {
			jpql.append(" order by _customerFTLot." + sortname + " "
					+ sortorder);
		} else {
			jpql.append(" order by _customerFTLot.state,_customerFTLot.incomingDate DESC ");
		}
		Page<CustomerFTLot> pages = getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.setPage(currentPage, pageSize).pagedList();

		return new Page<CustomerFTLotDTO>(pages.getStart(),
				pages.getResultCount(), pageSize,
				CustomerFTLotAssembler.toDTOs(pages.getData()));
	}

	@Transactional
	@Override
	public InvokeResult order(Long customerFTLotId, FTLotDTO ftLotDTO,
			String operatorName) {
		FTLot ftLot = FTLotAssembler.toEntity(ftLotDTO);
		FTLotOptionLog ftLotOptionLog = new FTLotOptionLog();

		try {
			customerFTLotApplication.order(customerFTLotId, ftLot,ftLotDTO.getFtInfoId());
			ftLotOptionLog.setOptType("下单操作 by" + operatorName);
			ftLotOptionLog.setFtLot(ftLot);
			ftLotOptionLog.setRemark("下单成功");
			return InvokeResult.success(ftLot.getId());
		} catch (Exception ex) {
			ex.printStackTrace();
			ftLotOptionLog.setRemark("下单失败");
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly(); // 回滚所有操作
			// 删除WMS领料记录
			customerFTLotApplication.rollbackWMSTestInfo(
					TestTypeForWms.getStringValue(TestTypeForWms.FT),
					ftLot.getWmsTestId(), ftLot.getQty());
			return InvokeResult.failure(ex.getMessage());
		} finally {
			ftLotOptionLogApplication.creatFTLotOptionLog(ftLotOptionLog);
		}
	}

	@Transactional
	@Override
	public InvokeResult orderInBatches(Long[] customerFTLotIds,
			String operatorName, FTLotDTO ftLotDTO) {
		FTLotOptionLog ftLotOptionLog = new FTLotOptionLog();
		try {
			Map<String, Integer> messages = new HashMap<String, Integer>();
			List<Long> ftLotIds = customerFTLotApplication.orderInBatches(
					customerFTLotIds, messages, ftLotDTO.getFtInfoId());
			// 添加日志信息
			ftLotOptionLog.setRemark("批量下单："
					+ Joiner.on(",").join(customerFTLotIds));
			ftLotOptionLog.setOptType("批量下单 by" + operatorName);

			Map<String, Object> result = new HashMap<>();
			result.put("message", "共选择了" + messages.get("select") + "个批次，"
					+ "共合批了" + messages.get("combine") + "批次," + "成功下单了"
					+ messages.get("orders") + "个批次。");
			result.put("ids", ftLotIds);

			return InvokeResult.success(result);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			ftLotOptionLog.setRemark("失败");
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly(); // 回滚所有操作
			return InvokeResult.failure(ex.getMessage());
		} finally {
			ftLotOptionLogApplication.creatFTLotOptionLog(ftLotOptionLog);
		}
	}

	@Transactional
	@Override
	public InvokeResult undoSeparation(Long[] customerFTLotIds) {
		try {
			customerFTLotApplication.undoSeparate(customerFTLotIds);
			return InvokeResult.success();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly(); // 回滚所有操作
			return InvokeResult.failure(ex.getMessage());
		}
	}

	@Transactional
	@Override
	public InvokeResult integrateManual(Long[] customerFTLotIds,
			String operatorName) {
		FTLotOptionLog ftLotOptionLog = new FTLotOptionLog();
		try {
			// 添加日志信息

			ftLotOptionLog.setRemark("手动合批："
					+ Joiner.on(",").join(customerFTLotIds));
			ftLotOptionLog.setOptType("手动合批 by" + operatorName);

			customerFTLotApplication.integrateManual(customerFTLotIds);
			return InvokeResult.success();
		} catch (Exception ex) {
			ftLotOptionLog.setRemark("失败");
			ex.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly(); // 回滚所有操作
			return InvokeResult.failure(ex.getMessage());
		} finally {
			ftLotOptionLogApplication.creatFTLotOptionLog(ftLotOptionLog);
		}
	}

	@Transactional
	@Override
	public InvokeResult undoIntegration(Long customerFTLotId) {
		try {
			customerFTLotApplication.undoIntegration(customerFTLotId);
			return InvokeResult.success();
		} catch (Exception ex) {
			ex.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly(); // 回滚所有操作
			return InvokeResult.failure(ex.getMessage());
		}
	}

	@Transactional
	@Override
	public InvokeResult separateMany(Long parentCustomerFTLotId,
			List<Long> separationQuantities, String operatorName) {
		FTLotOptionLog ftLotOptionLog = new FTLotOptionLog();
		try {
			ftLotOptionLog.setRemark("客批分批");
			ftLotOptionLog.setOptType("客批分批 by" + operatorName);
			customerFTLotApplication.separateMany(parentCustomerFTLotId,
					separationQuantities);
			return InvokeResult.success();
		} catch (Exception ex) {
			ex.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly(); // 回滚所有操作
			return InvokeResult.failure(ex.getMessage());
		} finally {
			ftLotOptionLogApplication.creatFTLotOptionLog(ftLotOptionLog);
		}
	}

	@Transactional
	@Override
	public InvokeResult getExpectedLotNumber(Long customerFTLodId) {
		try {
			return InvokeResult.success(customerFTLotApplication
					.getExpectedLotNumber(customerFTLodId));
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			return InvokeResult.failure("更新失败" + ex.getMessage());
		}
	}

	@Transactional
	@Override
	public InvokeResult getExpectedRCNumber(Long customerFTLodId) {
		try {
			return InvokeResult.success(customerFTLotApplication
					.getExpectedRCNumber(customerFTLodId));
		} catch (RuntimeException e) {
			e.printStackTrace();
			return InvokeResult.failure("更新失败" + e.getMessage());
		}
	}

	@Transactional
	@Override
	public InvokeResult addWmsCustomerFTLots(String json) {
		try {
			Set<CustomerFTLot> customerFTLots = CustomerFTLotAssembler
					.wmsJsonToEntities(json);
			for (CustomerFTLot customerFTLot : customerFTLots) {
				if (!fillCustomerFTLotWithFTInfo(customerFTLot)) {
					return InvokeResult.failure("客户产品型号或客户编号有误，找不到对应的产品！");
				}
				customerFTLotApplication.create(customerFTLot);
			}
			// customerFTLotApplication.createAll(customerFTLots);
			return InvokeResult.success();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return InvokeResult.failure("添加失败" + e.getMessage());
		}
	}

	@Transactional
	@Override
	public InvokeResult updateWmsCustomerFTLots(String wmsJson) {
		try {
			Set<CustomerFTLot> found = new HashSet<>();
			Set<CustomerFTLot> customerFTLots = CustomerFTLotAssembler
					.wmsJsonToEntities(wmsJson);
			for (CustomerFTLot customerFTLot : customerFTLots) {
				CustomerFTLot wmsCustomerFTLot = customerFTLotApplication
						.findByWmsId(customerFTLot.getWmsId());
				if (wmsCustomerFTLot == null) {
					return InvokeResult.failure("更新失败,系统中没有此批次的信息");
				}

				wmsCustomerFTLot.setState(customerFTLot.getState());
				wmsCustomerFTLot.setCustomerLotNumber(customerFTLot
						.getCustomerLotNumber());
				wmsCustomerFTLot.setCustomerPPO(customerFTLot.getCustomerPPO());
				wmsCustomerFTLot.setProductVersion(customerFTLot
						.getProductVersion());
				wmsCustomerFTLot.setCustomerProductNumber(customerFTLot
						.getCustomerProductNumber());
				wmsCustomerFTLot.setMaterialType(customerFTLot
						.getMaterialType());
				wmsCustomerFTLot.setIncomingQuantity(customerFTLot
						.getIncomingQuantity());
				wmsCustomerFTLot.setDateCode(customerFTLot.getDateCode());
				wmsCustomerFTLot.setIncomingStyle(customerFTLot
						.getIncomingStyle());
				wmsCustomerFTLot.setIncomingDate(customerFTLot
						.getIncomingDate());

				wmsCustomerFTLot.setTaxType(customerFTLot.getTaxType());
				wmsCustomerFTLot.setWaferLot(customerFTLot.getWaferLot());
				wmsCustomerFTLot.setWireBond(customerFTLot.getWireBond());
				wmsCustomerFTLot.setWaferManufacturer(customerFTLot
						.getWaferManufacturer());
				wmsCustomerFTLot.setPackageNumber(customerFTLot
						.getPackageNumber());
				wmsCustomerFTLot.setMFGPN(customerFTLot.getMFGPN());

				found.add(wmsCustomerFTLot);
			}
			customerFTLotApplication.updateAll(found);
			return InvokeResult.success();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			return InvokeResult.failure("更新失败" + ex.getMessage());
		}
	}

	@Transactional
	@Override
	public InvokeResult deleteWmsCustomerFTLos(String wmsJson) {
		List<Long> ids = new LinkedList<>();
		Set<CustomerFTLot> customerFTLots = CustomerFTLotAssembler
				.wmsJsonToEntities(wmsJson);
		Set<CustomerFTLot> remove = new HashSet<>();
		for (CustomerFTLot customerFTLot : customerFTLots) {
			CustomerFTLot wmsCustomerFTLot = customerFTLotApplication
					.findByWmsId(customerFTLot.getWmsId());
			if (wmsCustomerFTLot == null) {
				return InvokeResult.failure("删除失败,系统中没有此批次的信息"
						+ customerFTLot.getWmsId());
			}
			if (wmsCustomerFTLot.getState() == CustomerLotState.Ordered) {
				return InvokeResult.failure("数据中包含已下单批次");
			} else {
				remove.add(wmsCustomerFTLot);
			}
		}
		try {
			customerFTLotApplication.removeAll(remove);
		} catch (Exception e) {
			return InvokeResult.failure("删除失败");
		}

		return InvokeResult.success();
	}

	@Override
	public CustomerFTLotDTO findParentIntegrationByCustomerLot(Long id) {
		throw new UnsupportedOperationException("");
	}

	@Override
	public CustomerFTLotDTO findParentSeparationByCustomerLot(Long id) {
		throw new UnsupportedOperationException("");
	}

	@Override
	public InternalProductDTO findInternalProductByCustomerLot(Long id) {
		throw new UnsupportedOperationException("");
	}

	@Override
	public CustomerFTLotDTO findCustomerFTLotByCustomerLot(Long id) {
		throw new UnsupportedOperationException("");
	}

	/**
	 * 填充FTInfo
	 *
	 * @param customerFTLot
	 * @return 找到产品则填充返回true，找不到则返回false
	 * @author Howard
	 * @version v1.0
	 * @lastModifyDate 2016.01.09
	 */
	public boolean fillCustomerFTLotWithFTInfo(CustomerFTLot customerFTLot) {
		// 如果实体内品型号(来料型号)不为空，则检查是否存在
		/**
		 * 此处 查找到的信息会被下@link中提到的方法覆盖
		 * 
		 * @{link org.seu.acetec.mes2Koala.application.impl.
		 *        CustomerFTLotApplicationImpl
		 *        .checkOnCreateOrUpdate(CustomerFTLot)
		 */
		if (customerFTLot.getCustomerNumber() != null
				&& !"".equals(customerFTLot.getCustomerNumber())
				&& customerFTLot.getCustomerProductNumber() != null
				&& !"".equals(customerFTLot.getCustomerProductNumber())
				&& customerFTLot.getProductVersion() != null
				&& !"".equals(customerFTLot.getProductVersion())) {
			List<FTInfo> result = getInProdByCusProNumAndCusNum(
					customerFTLot.getCustomerProductNumber(),
					customerFTLot.getCustomerNumber(),
					customerFTLot.getProductVersion());
			// 2016-06-06删除判断result.size() != 1
			// 增加PID之后存在多条PID来料时任意关联PID，下单后重新选择PID
			if (result == null) {
				return false;
			} else {
				customerFTLot.setFtInfo(result.get(0));
				return true;
			}
		} else {
			return false;
		}
	}

	/**
	 * 通过CustomerFTLotId获取PID List
	 *
	 * @param Long
	 * @author harlow
	 * @version v1.0
	 * @lastModifyDate 2016.06.06
	 */
	@Transactional
	public InvokeResult findPIDByCustomerFTLotId(Long id) {
		CustomerFTLot customerFTLot = this.customerFTLotApplication.get(id);
		List<FTInfo> result = null;
		if (customerFTLot.getCustomerNumber() != null
				&& !"".equals(customerFTLot.getCustomerNumber())
				&& customerFTLot.getCustomerProductNumber() != null
				&& !"".equals(customerFTLot.getCustomerProductNumber())
				&& customerFTLot.getProductVersion() != null
				&& !"".equals(customerFTLot.getProductVersion())) {
			result = getInProdByCusProNumAndCusNum(
					customerFTLot.getCustomerProductNumber(),
					customerFTLot.getCustomerNumber(),
					customerFTLot.getProductVersion());
		}
		if (result != null && result.size() > 0) {
			return InvokeResult.success(FTInfoAssembler.toDTOs(result));
		} else {
			return InvokeResult.failure("获取PID失败！");
		}
	}

	public List<FTInfo> getInProdByCusProNumAndCusNum(
			String customerProductNumber, String customerNumber,
			String customerProductRevision) {
		List<FTInfo> result = new ArrayList<FTInfo>();
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select _FTInfo from FTInfo _FTInfo   where 1=1 ");
		if (customerProductNumber != null && !"".equals(customerProductNumber)) {
			jpql.append(" and _FTInfo.customerProductNumber like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					customerProductNumber));
		}
		if (customerNumber != null && !"".equals(customerNumber)) {
			jpql.append(" and _FTInfo.customerDirect.number like ?");
			conditionVals.add(MessageFormat.format("%{0}%", customerNumber));
		}

		if (customerNumber != null && !"".equals(customerNumber)) {
			jpql.append(" and _FTInfo.customerProductRevision like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					customerProductRevision));
		}

		result = getQueryChannelService().createJpqlQuery(jpql.toString())
				.setParameters(conditionVals).list();
		return result;
	}

	@Override
	public Long getFTinfoIdByCustomerFTLotId(Long id) {
		CustomerFTLot customerFTLot = customerFTLotApplication.get(id);
		FTInfo ftInfo = customerFTLot.getFtInfo();
		return ftInfo.getId();
	}

	@Override
	public InvokeResult deleteOrder(FTLotDTO ftLotDTO) {
		FTLot ftLot = ftLotApplication.get(ftLotDTO.getId());
		if (ftLot.getParentSeparationId() != null) {
			return InvokeResult.failure("删除失败,此批次为分批批次!");
		}
		if (ftLot.getParentIntegrationIds() != null
				&& !"".equals(ftLot.getParentIntegrationIds())) {
			return InvokeResult.failure("删除失败,此批次为合批批次!");
		}
		try {
			customerFTLotApplication.deleteOrder(ftLot);
			ftLotOptionLogApplication
					.removeFTLotOptionLogByLotId(ftLot.getId());
			ftLotApplication.remove(ftLot);
			// 删除成功，调用WMS接口，回滚下单信息
			customerFTLotApplication.rollbackWMSTestInfo(TestTypeForWms
					.getStringValue(TestTypeForWms.FT), ftLot
					.getCustomerFTLot().getWmsId(), ftLot.getCustomerFTLot()
					.getIncomingQuantity());
			return InvokeResult.success("删除成功！");
		} catch (Exception ex) {
			ex.printStackTrace();
			return InvokeResult.failure("删除失败," + ex.getMessage());
		}
	}

}
