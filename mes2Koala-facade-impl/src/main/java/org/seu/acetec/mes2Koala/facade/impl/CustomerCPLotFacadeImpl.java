package org.seu.acetec.mes2Koala.facade.impl;

import com.google.common.base.Joiner;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.application.CPInfoApplication;
import org.seu.acetec.mes2Koala.application.CPLotApplication;
import org.seu.acetec.mes2Koala.application.CPLotOptionLogApplication;
import org.seu.acetec.mes2Koala.application.CustomerCPLotApplication;
import org.seu.acetec.mes2Koala.application.InternalProductApplication;
import org.seu.acetec.mes2Koala.application.bean.SaveBaseBean;
import org.seu.acetec.mes2Koala.core.domain.*;
import org.seu.acetec.mes2Koala.core.enums.CustomerLotState;
import org.seu.acetec.mes2Koala.core.enums.TestTypeForWms;
import org.seu.acetec.mes2Koala.facade.CustomerCPLotFacade;
import org.seu.acetec.mes2Koala.facade.InternalProductFacade;
import org.seu.acetec.mes2Koala.facade.dto.CPLotDTO;
import org.seu.acetec.mes2Koala.facade.dto.CustomerCPLotDTO;
import org.seu.acetec.mes2Koala.facade.dto.vo.CPCustomerLotPageVo;
import org.seu.acetec.mes2Koala.facade.impl.assembler.CPCustomerWaferAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.CPInfoAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.CPLotAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.CustomerCPLotAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.FTInfoAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.InternalProductAssembler;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.inject.Inject;
import javax.inject.Named;

import java.text.MessageFormat;
import java.util.*;

/**
 * @author 阙宇翔
 * @version 2016/2/27
 */
@Named
public class CustomerCPLotFacadeImpl implements CustomerCPLotFacade {

	@Inject
	CustomerCPLotApplication customerCPLotApplication;

	@Inject
	CPInfoApplication cpInfoApplication;

	@Inject
	CPLotApplication cpLotApplication;

	@Inject
	CPLotOptionLogApplication cpLotOptionLogApplication;

	private QueryChannelService queryChannel;

	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(
					QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}

	@Override
	public InvokeResult getCustomerCPLot(Long id) {
		CustomerCPLot customerCPLot = customerCPLotApplication.get(id);
		return InvokeResult
				.success(CustomerCPLotAssembler.toDTO(customerCPLot));
	}

	@Override
	public InvokeResult updateCustomerCPLot(CustomerCPLotDTO customerCPLotDTO) {
		customerCPLotApplication.update(CustomerCPLotAssembler
				.toEntity(customerCPLotDTO));
		return InvokeResult.success();
	}

	@Override
	public InvokeResult removeCustomerCPLot(Long id) {
		customerCPLotApplication.remove(customerCPLotApplication.get(id));
		return InvokeResult.success();
	}

	@Override
	public InvokeResult removeCustomerCPLots(Long[] ids) {
		Set<CustomerCPLot> customerCPLots = new HashSet<>();
		for (Long id : ids) {
			customerCPLots.add(customerCPLotApplication.get(id));
		}
		customerCPLotApplication.removeAll(customerCPLots);
		return InvokeResult.success();
	}

	@Override
	public List<CustomerCPLotDTO> findAllCustomerCPLot() {
		return CustomerCPLotAssembler
				.toDTOs(customerCPLotApplication.findAll());
	}

	@Override
	@Transactional
	public Page<CPCustomerLotPageVo> pageQueryCustomerCPLot(
			CustomerCPLotDTO queryVo, int currentPage, int pageSize) {
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select _customerCPLot from CustomerCPLot _customerCPLot   where _customerCPLot.logic=null ");
		if (queryVo.getCreateTimestamp() != null) {
			jpql.append(" and _customerCPLot.createTimestamp between ? and ? ");
			conditionVals.add(queryVo.getCreateTimestamp());
			conditionVals.add(queryVo.getCreateTimestampEnd());
		}
		if (queryVo.getLastModifyTimestamp() != null) {
			jpql.append(" and _customerCPLot.lastModifyTimestamp between ? and ? ");
			conditionVals.add(queryVo.getLastModifyTimestamp());
			conditionVals.add(queryVo.getLastModifyTimestampEnd());
		}
		if (queryVo.getCreateEmployNo() != null
				&& !"".equals(queryVo.getCreateEmployNo())) {
			jpql.append(" and _customerCPLot.createEmployNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCreateEmployNo()));
		}
		if (queryVo.getLastModifyEmployNo() != null
				&& !"".equals(queryVo.getLastModifyEmployNo())) {
			jpql.append(" and _customerCPLot.lastModifyEmployNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getLastModifyEmployNo()));
		}
		if (queryVo.getCustomerLotNumber() != null
				&& !"".equals(queryVo.getCustomerLotNumber())) {
			jpql.append(" and _customerCPLot.customerLotNumber like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCustomerLotNumber()));
		}
		if (queryVo.getCustomerNumber() != null
				&& !"".equals(queryVo.getCustomerNumber())) {
			jpql.append(" and _customerCPLot.customerNumber like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCustomerNumber()));
		}
		if (queryVo.getPackageNumber() != null
				&& !"".equals(queryVo.getPackageNumber())) {
			jpql.append(" and _customerCPLot.packingLot like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getPackageNumber()));
		}
		if (queryVo.getCustomerProductNumber() != null
				&& !"".equals(queryVo.getCustomerProductNumber())) {
			jpql.append(" and _customerCPLot.customerProductNumber like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCustomerProductNumber()));
		}
		if(queryVo.getSortname() != null
				&& !"".equals(queryVo.getSortname())){
			jpql.append(" order by " + queryVo.getSortname() + " " + queryVo.getSortorder());
		}else
			jpql.append(" order by _customerCPLot.state,_customerCPLot.incomingDate DESC ");
		Page<CustomerCPLot> pages = getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.setPage(currentPage, pageSize).pagedList();
		return new Page<CPCustomerLotPageVo>(pages.getStart(),
				pages.getResultCount(), pageSize,
				CustomerCPLotAssembler.toPageVos(pages.getData()));

	}

	@Override
	@Transactional
	public InvokeResult getCPVo(Long id) {
		CustomerCPLot customerCPLot = customerCPLotApplication.get(id);
		return InvokeResult.success(CustomerCPLotAssembler
				.toPageVo(customerCPLot));
	}

	@Override
	public InvokeResult createCustomerCPLot(CustomerCPLotDTO customerCPLotDTO) {
		throw new UnsupportedOperationException("");
	}

	/**
	 * 手动下单
	 *
	 * @param customerCPLotId
	 * @param cpLotDTO
	 * @return
	 * @version 2016/3/28 YuxiangQue
	 */
	@Transactional
	@Override
	public InvokeResult order(Long customerCPLotId, CPLotDTO cpLotDTO) {
		this.checkOrderLotNo(cpLotDTO.getInternalLotNumber());
		SaveBaseBean sbb = new SaveBaseBean();
		BeanUtils.copyProperties(cpLotDTO, sbb);
		CPLot cpLot = CPLotAssembler.toEntity(cpLotDTO);
		try {
			customerCPLotApplication.order(customerCPLotId, cpLot,
					cpLotDTO.getCpInfoId(),sbb);
			return InvokeResult.success(cpLot.getId());
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly(); // 回滚所有操作
			// 下单失败，删除领料信息
			customerCPLotApplication.rollbackWMSTestInfo(
					TestTypeForWms.getStringValue(TestTypeForWms.CP),
					cpLot.getWmsTestId(), cpLot.getQuantity());
			return InvokeResult.failure(ex.getMessage());
		}
	}
	
	private void checkOrderLotNo(String lotNo){
		if(lotNo == null || "".equals(lotNo)){
			throw new RuntimeException("批号不能为空!");
		}
		List<CPLot> list = cpLotApplication.find("select a from CPLot a where a.internalLotNumber = ? ",lotNo);
		if(list!=null && list.size() > 0){
			throw new RuntimeException("批次:" + lotNo + "已存在,请修改批号后再进行下单!");
		}
	}

	/**
	 * 批量下单，自动合批后下单
	 *
	 * @param customerCPLotIds
	 *            需要批量下单的批次ids
	 * @return
	 * @version 2016/3/27 YuxiangQue
	 * @version 2015/6/14 harlow 增加批量下单选择PID
	 */
	@Transactional
	@Override
	public InvokeResult batchOrder(Long[] customerCPLotIds, CPLotDTO cpLotDTO) {
		SaveBaseBean sbb = new SaveBaseBean();
		BeanUtils.copyProperties(cpLotDTO, sbb);
		CPLotOptionLog cpLotOptionLog = new CPLotOptionLog();

		try {
			Map<String, Integer> messages = new HashMap<String, Integer>();
			List<Long> ftLotIds = customerCPLotApplication.batchOrder(
					customerCPLotIds, messages,cpLotDTO.getCpInfoId(),sbb);

			// 添加日志信息
			cpLotOptionLog.setRemark("批量下单："
					+ Joiner.on(",").join(customerCPLotIds));
			cpLotOptionLog.setOptType("批量下单");

			Map<String, Object> result = new HashMap<>();
			result.put("message", "共选择了" + messages.get("select") + "个批次，"
					+ "共合批了" + messages.get("combine") + "批次," + "成功下单了"
					+ messages.get("orders") + "个批次。");
			result.put("ids", ftLotIds);

			return InvokeResult.success(result);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly(); // 回滚所有操作
			return InvokeResult.failure(ex.getMessage());
		} finally {
			cpLotOptionLogApplication.creatCPLotOptionLog(cpLotOptionLog);
		}
	}

	@Override
	public InvokeResult getExpectedLotNumber(Long customerCPLotId) {
		try {
			return InvokeResult.success(customerCPLotApplication
					.peekLotNumber(customerCPLotId));
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			return InvokeResult.failure("更新失败" + ex.getMessage());
		}
	}

	@Override
	public InvokeResult getExpectedRCNumber(Long customerCPLotId) {
		try {
			return InvokeResult.success(customerCPLotApplication
					.peekRCNumber(customerCPLotId));
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			return InvokeResult.failure(ex.getMessage());
		}
	}

	@Transactional
	@Override
	public InvokeResult addWmsCustomerCPLots(String json) {
		try {
			Set<CustomerCPLot> customerCPLots = CustomerCPLotAssembler
					.wmsJsonToEntities(json);
			for (CustomerCPLot customerCPLot : customerCPLots) {

				CPInfo cpInfo = cpInfoApplication
						.findByCustomerProductNumberAndCustomerNumber(
								customerCPLot.getCustomerProductNumber(),
								customerCPLot.getCustomerNumber());
				if (cpInfo == null) {
					return InvokeResult.failure("客户产品型号或客户编号有误，找不到对应的产品！");
				}
				customerCPLot.setCpInfo(cpInfo);
				customerCPLotApplication.create(customerCPLot);
			}
			// customerCPLotApplication.createAll(CustomerCPLotAssembler.wmsJsonToEntities(json));
			return InvokeResult.success();
		} catch (RuntimeException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly(); // 回滚所有操作
			return InvokeResult.failure(e.getMessage());
		}
	}

	@Transactional
	@Override
	public InvokeResult updateWmsCustomerCPLots(String wmsJson) {
		try {
			Set<CustomerCPLot> found = new HashSet<CustomerCPLot>();
			Set<CustomerCPLot> customerFTLots = CustomerCPLotAssembler
					.wmsJsonToEntities(wmsJson);
			for (CustomerCPLot customerCPLot : customerFTLots) {
				CustomerCPLot wmsCustomerCPLot = customerCPLotApplication
						.findByWmsId(customerCPLot.getWmsId());
				if (wmsCustomerCPLot == null) {
					return InvokeResult.failure("更新失败,系统中没有此批次的信息");
				}

				wmsCustomerCPLot.setState(customerCPLot.getState());
				wmsCustomerCPLot.setCustomerPPO(customerCPLot.getCustomerPPO());
				wmsCustomerCPLot.setCustomerLotNumber(customerCPLot
						.getCustomerLotNumber());
				wmsCustomerCPLot.setIncomingDate(customerCPLot
						.getIncomingDate());
				wmsCustomerCPLot.setIncomingQuantity(customerCPLot
						.getIncomingQuantity());
				wmsCustomerCPLot.setProductVersion(customerCPLot
						.getProductVersion());
				wmsCustomerCPLot.setMaskName(customerCPLot.getMaskName());
				wmsCustomerCPLot.setSize(customerCPLot.getSize());
				//2016/07/18 Hongyu add-start
				for (CPCustomerWafer wmscpCustomerWafer : wmsCustomerCPLot.getCpCustomerWafers()) {
					wmscpCustomerWafer.remove();
				}
				//2016/07/18 Hongyu add-end
				wmsCustomerCPLot.setCpCustomerWafers(customerCPLot
						.getCpCustomerWafers());
				for (CPCustomerWafer cpCustomerWafer : customerCPLot
						.getCpCustomerWafers()) {
					cpCustomerWafer.setCustomerCPLot(wmsCustomerCPLot);
				}

				wmsCustomerCPLot.setCustomerProductNumber(customerCPLot
						.getCustomerProductNumber());

				found.add(wmsCustomerCPLot);
			}
			customerCPLotApplication.updateAll(found);
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
	public InvokeResult deleteWmsCustomerCPLots(String wmsJson) {
		try {
			Set<CustomerCPLot> customerCPLots = CustomerCPLotAssembler
					.wmsJsonToEntities(wmsJson);
			Set<CustomerCPLot> remove = new HashSet<CustomerCPLot>();
			for (CustomerCPLot customerCPLot : customerCPLots) {
				CustomerCPLot customerFTLotByWmsId = customerCPLotApplication
						.findByWmsId(customerCPLot.getWmsId());
				if (customerFTLotByWmsId == null) {
					return InvokeResult.failure("删除失败,系统中没有此批次的信息"
							+ customerCPLot.getWmsId());
				}
				remove.add(customerFTLotByWmsId);
			}

			// 已经下单的批次不能删除
			for (CustomerCPLot removeLot : remove) {
				if (removeLot.getState() == CustomerLotState.Ordered) {
					return InvokeResult.failure("删除失败,该批次已经下单"
							+ removeLot.getWmsId());
				}
			}

			customerCPLotApplication.removeAll(remove);
			return InvokeResult.success();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly(); // 回滚所有操作
			return InvokeResult.failure(ex.getMessage());
		}
	}

	public boolean fillCustomerCPLotWithCPInfo(CustomerCPLot customerCPLot) {
		// 如果实体内品型号(来料型号)不为空，则检查是否存在
		if (customerCPLot.getCustomerNumber() != null
				&& !"".equals(customerCPLot.getCustomerNumber())
				&& customerCPLot.getCustomerProductNumber() != null
				&& !"".equals(customerCPLot.getCustomerProductNumber())) {
			List<CPInfo> result = getInProdByCusProNumAndCusNum(
					customerCPLot.getCustomerProductNumber(),
					customerCPLot.getCustomerNumber());
			if (result == null || result.size() != 1)
				return false;
			customerCPLot.setCpInfo(result.get(0));
			return true;
		} else {
			return false;
		}
	}

	public List<CPInfo> getInProdByCusProNumAndCusNum(
			String customerProductNumber, String customerNumber) {
		List<CPInfo> result = new ArrayList<CPInfo>();
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select _CPInfo from CPInfo _CPInfo   where 1=1 ");
		if (customerProductNumber != null && !"".equals(customerProductNumber)) {
			jpql.append(" and _CPInfo.customerProductNumber like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					customerProductNumber));
		}
		if (customerNumber != null && !"".equals(customerNumber)) {
			jpql.append(" and _CPInfo.customerDirect.number like ?");
			conditionVals.add(MessageFormat.format("%{0}%", customerNumber));
		}

		result = getQueryChannelService().createJpqlQuery(jpql.toString())
				.setParameters(conditionVals).list();
		return result;
	}

	@Override
	public InvokeResult getCPCustomerWafer(Long customerCPLotId) {
		try {
			return InvokeResult.success(CPCustomerWaferAssembler
					.toDTOs(customerCPLotApplication
							.getCPCustomerWafer(customerCPLotId)));
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			return InvokeResult.failure(ex.getMessage());
		}
	}

	@Override
	public InvokeResult deleteOrder(CPLotDTO cpLotDTO) {
		CPLot cpLot = cpLotApplication.get(cpLotDTO.getId());
		try {
			cpLot.setLastModifyEmployNo(cpLotDTO.getLastModifyEmployNo());
			cpLot.setLastModifyTimestamp(cpLotDTO.getLastModifyTimestamp());
			customerCPLotApplication.deleteOrder(cpLot);
			cpLotOptionLogApplication
					.removeCPLotOptionLogByLotId(cpLot.getId());
			cpLotApplication.remove(cpLot);
			// 删除成功，调用WMS接口，回滚下单信息
			customerCPLotApplication.rollbackWMSTestInfo(TestTypeForWms
					.getStringValue(TestTypeForWms.CP), cpLot
					.getCustomerCPLot().getWmsId(), cpLot.getCustomerCPLot()
					.getIncomingQuantity());
			return InvokeResult.success("删除成功！");
		} catch (Exception ex) {
			ex.printStackTrace();
			return InvokeResult.failure("删除失败！," + ex.getMessage());
		}
	}

	@Override
	public Long getCPinfoIdByCustomerCPLotId(Long id) {
		CustomerCPLot customerFTLot = customerCPLotApplication.get(id);
		CPInfo cpInfo = customerFTLot.getCpInfo();
		return cpInfo.getId();
	}

	@Transactional
	@Override
	public InvokeResult findPIDByCustomerCPLotId(Long id) {
		CustomerCPLot customerCPLot = this.customerCPLotApplication.get(id);
		List<CPInfo> result = cpInfoApplication
				.findPIDByCustomerProductNumberAndCustomerNumber(
						customerCPLot.getCustomerProductNumber(),
						customerCPLot.getCustomerNumber());
		if (result != null && result.size() > 0) {
			return InvokeResult.success(CPInfoAssembler.toDTOs(result, false));
		} else {
			return InvokeResult.failure("获取PID失败！");
		}
	}

}
