package org.seu.acetec.mes2Koala.application.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Named;

import org.seu.acetec.mes2Koala.application.*;
import org.seu.acetec.mes2Koala.application.utils.Mes2EntityOperator;
import org.seu.acetec.mes2Koala.core.domain.*;
import org.seu.acetec.mes2Koala.core.enums.CustomerLotState;
import org.seu.acetec.mes2Koala.core.enums.TestTypeForWms;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;

import net.sf.json.JSONObject;

@Named
@Transactional
public class CustomerFTLotApplicationImpl extends
		GenericMES2ApplicationImpl<CustomerFTLot> implements
		CustomerFTLotApplication {

	@Inject
	private FTInfoApplication ftInfoApplication;

	@Inject
	private FTLotApplication ftLotApplication;

	@Inject
	private FTProcessApplication ftProcessApplication;

	@Inject
	private IncrementNumberApplication incrementNumberApplication;
	@Inject
	private WMSClientApplication wmsClientApplication;

	@Inject
	private FTRuncardTemplateApplication ftRuncardTemplateApplication;

	@Inject
	private FTRuncardApplication ftRuncardApplication;

	// 批量下单是合批的数量上下限
	static final Long INTEGRATE_MIN_QUANTITY = 10000L;
	static final Long INTEGRATE_MAX_QUANTITY = 50000L;

	/**
	 * 产品信号对应的最大数量，用于合批
	 */
	private Map<String, Long> productOf1201_1302 = new HashMap<String, Long>() {
		{
			put("VC1613", 35000L);
			put("VC1614", 35000L);
			put("VC1616", 35000L);
			put("VC1618", 35000L);
			put("VC1623", 35000L);
			put("VC1673", 35000L);
			put("VC3278", 35000L);
			put("VC3584", 35000L);
			put("VC5268Q", 35000L);
			put("VC5276", 35000L);
			put("VC5276Q", 35000L);
			put("VC5278", 35000L);
			put("VC5278-21", 35000L);
			put("VC5278Q", 35000L);
			put("VC5282", 35000L);
			put("VC5318", 45000L);
			put("VC5341", 45000L);
			put("VC5341-21", 45000L);
			put("VC5342", 45000L);
			put("VC5342-21", 45000L);
			put("VC5343", 45000L);
			put("VC5345", 45000L);
			put("VC5348", 45000L);
			put("VC5439", 45000L);
			put("VC7582", 35000L);
			put("VC7584", 35000L);
			put("VC7584-21", 35000L);
			put("VC7584-51", 35000L);
			put("VC7590", 35000L);
			put("VC7590-21", 35000L);
			put("VC7590-31", 35000L);
			put("VC7590-51", 35000L);
			put("VC7592", 35000L);
			put("VC7592-11", 35000L);
			put("VC7593", 35000L);
			put("VC7593-21", 35000L);
			put("VC7594", 35000L);
			put("VC7594-31", 35000L);
			put("VC7810", 35000L);
			put("VC7902", 35000L);
			put("VC7903", 35000L);
			put("VC7905", 35000L);
			put("VC7909", 35000L);
		}
	};
	/**
     *
     */
	private Map<String, Long> productOfOthers = new HashMap<String, Long>() {
		{
			put("LXK6256", 50000L);
			put("MS5561", 50000L);
		}
	};
	/**
	 * 可用于合批的产品对象
	 */
	private Map<String, Long> productIntegrable = new HashMap<String, Long>() {
		{
			put("VC5345", 45000L);
			put("VC5348", 45000L);
			put("VC5341", 45000L);
			put("VC5341-21", 45000L);
			put("VC5342", 45000L);
			put("VC5342-21", 45000L);
			put("VC5343", 45000L);
			put("VC5439", 45000L);
			put("LXK6256", 50000L);
		}
	};
	/**
     *
     */
	private Map<String, String> materialType = new HashMap<String, String>() {
		{
			put("01", "量产");
			put("02", "工程");
			put("03", "RA");
			put("04", "RMA");
			put("05", "QS");
			put("06", "调运");
			put("07", "M");
			put("08", "R");
			put("09", "E1");
			put("10", "E2");
			put("11", "E3");
			put("12", "E4");
			put("13", "E5");
			put("14", "E6");
			put("15", "E7");
			put("16", "E8");
			put("17", "E9");
		}
	};

	@Override
	public void create(CustomerFTLot customerFTLot) {
		if (!checkOnCreateOrUpdate(customerFTLot)) {
			throw new RuntimeException("客户产品型号或客户编号有误，找不到对应的产品");
		}
		super.create(customerFTLot);
	}

	@Override
	public void update(CustomerFTLot customerFTLot) {
		if (!checkOnCreateOrUpdate(customerFTLot)) {
			throw new RuntimeException("客户产品型号或客户编号有误，找不到对应的产品");
		}
		super.update(customerFTLot);
	}

	@Override
	public CustomerFTLot findByWmsId(String wmsId) {
		return findOne("select _c from CustomerFTLot _c where _c.wmsId = ?",
				wmsId);
	}

	@Override
	public List<CustomerFTLot> findByParentSeparationId(Long parentSeparationId) {
		return find(
				"select _c from CustomerFTLot _c where _c.parentSeparationId = ?",
				parentSeparationId);
	}

	@Override
	public CustomerFTLot findByParentIntegrationId(String parentIntegrationId) {
		return findOne(
				"select _c from CustomerFTLot _c where _c.parentIntegrationId = ?",
				parentIntegrationId);
	}

	/**
	 * 根据customerFTLot的productNumber和CustomerNumber唯一定位需要绑定的内部产品id，
	 * 将id填充至customerFTLotDTO中
	 *
	 * @param customerFTLot
	 *            待检查
	 * @return 找到产品则填充返回true，找不到则返回false
	 * @version 2016/1/9 Howard
	 * @version 2016/3/27 YuxiangQue
	 */
	private boolean checkOnCreateOrUpdate(CustomerFTLot customerFTLot) {
		// 如果实体内品型号(来料型号)不为空，则检查是否存在
		if (Strings.isNullOrEmpty(customerFTLot.getCustomerProductNumber()))
			return false;
		if (Strings.isNullOrEmpty(customerFTLot.getCustomerNumber()))
			return false;
		if (Strings.isNullOrEmpty(customerFTLot.getProductVersion()))
			return false;
		FTInfo ftInfo = ftInfoApplication
				.findByCustomerProductNumberAndCustomerNumber(
						customerFTLot.getCustomerProductNumber(),
						customerFTLot.getCustomerNumber(),
						customerFTLot.getProductVersion());
		if (ftInfo == null)
			return false;
		customerFTLot.setFtInfo(ftInfo);
		return true;
	}

	/**
	 * 客批分批：将一批分为多批，原批次数量减少，新批次数量参照separationQties，并与母批关联
	 *
	 * @param customerFTLotId
	 *            母批id
	 * @param separateQuantities
	 *            子批数量List
	 * @return 成功返回成功标记，失败返回错误信息
	 * @version 2016/1/15 Howad
	 * @version 2016/3/27 YuxiangQue
	 */
	@Override
	public void separateMany(Long customerFTLotId, List<Long> separateQuantities) {

		// 获取母批实体
		CustomerFTLot parentLot = get(customerFTLotId);
		if (parentLot.getState() == CustomerLotState.Ordered) {
			throw new RuntimeException("该批次已下单");
		}

		// 分过批不能再分
		// if (parentLot.getParentSeparationId() != null) {
		// throw new RuntimeException("该批次已是分批批次");
		// }

		// 遍历分批数组，每一批都从相同的母批中分出
		List<CustomerFTLot> childLots = new ArrayList<CustomerFTLot>();
		for (Long separationQty : separateQuantities) {
			CustomerFTLot childLot = separateOne(parentLot, separationQty);
			if (childLot != null)
				childLots.add(childLot);
		}

		// 修改母批信息
		markCustomerFTLotInvisible(parentLot);
		parentLot.setIncomingQuantity(0L);

		// 持久化操作
		createAll(childLots);
		update(parentLot);
	}

	/**
	 * 分批还原：首先判断是否来自于同一母批，若来自于同一母批则恢复母批数量删除子批
	 *
	 * @param customerFTLotIds
	 *            子批ids
	 * @return 返回成功或错误信息
	 * @version 2016/1/17 Howard
	 */
	@Override
	public void undoSeparate(Long[] customerFTLotIds) {

		CustomerFTLot first = get(customerFTLotIds[0]);
		if (first.getState() == CustomerLotState.Ordered) {
			throw new RuntimeException("所选批次中有已下单的批次，还原失败");
		}

		// 建立子批集合以及数量总量变量
		Long parentLotId = first.getParentSeparationId(); // 获取第一子批的母批id
		Long totalQuantity = first.getIncomingQuantity();
		Set<CustomerFTLot> separatedFTLots = new HashSet<CustomerFTLot>();
		separatedFTLots.add(first);

		// 检查余下的批次是否从属于同一母批
		for (int index = 1; index < customerFTLotIds.length; ++index) {
			CustomerFTLot next = get(customerFTLotIds[index]);
			if (next.getState() == CustomerLotState.Ordered) { // 有已下单的批次
				throw new RuntimeException("所选批次中有已下单的批次，还原失败");
			}
			if (!Objects.equals(next.getParentSeparationId(), parentLotId)) { // 若不属于同一个母批，则返回错误
				throw new RuntimeException("所选批次不同属于一个母批");
			}
			// 否则加入set集合中并计算数量总量
			totalQuantity += next.getIncomingQuantity();
			separatedFTLots.add(next);
		}

		// 获取母批实体，恢复数量，重新保存
		CustomerFTLot parentLot = get(parentLotId);
		parentLot.setIncomingQuantity(parentLot.getIncomingQuantity()
				+ totalQuantity);
		markCustomerFTLotVisible(parentLot);
		update(parentLot);

		// 删除子批
		removeAll(separatedFTLots);
	}

	/**
	 * 手动合批：当产品型号、客户ppo、版本型号一致时可合批，其余字段“/”相连
	 *
	 * @param customerFTLotIds
	 *            逗号相隔的source lots' customerFTLotId
	 * @return 调用结果：成功时成功标记，失败时包含错误信息
	 * @version 2016/1/15 Howard
	 * @version 2016/3/27 YuxiangQue
	 */
	@Override
	public void integrateManual(Long[] customerFTLotIds) {

		// 获取第一个id对应的产品，由于id是customerLot的id，因此需要现货区customerLot再获取CustomerFTLot
		CustomerFTLot firstLot = get(customerFTLotIds[0]);
		if (firstLot.getState() == CustomerLotState.Ordered) {
			throw new RuntimeException("所选批次中有已下单的批次，合批失败");
		}

		// 合批
		CustomerFTLot integrateLot = instancePrototype(firstLot); // 深克隆得到目标lot
		markCustomerFTLotInvisible(firstLot);
		integrateLot.setParentIntegrationIds(Joiner.on(",").join(
				customerFTLotIds)); // 设置其来源批的ids

		// 初始化合批来源（较小批）
		Set<CustomerFTLot> parentLots = new HashSet<CustomerFTLot>();
		parentLots.add(firstLot); // 加入第一个sourceLot

		// 循环遍历ids，得到相应的实体
		for (int index = 1; index < customerFTLotIds.length; ++index) {
			CustomerFTLot tempFTLot = get(customerFTLotIds[index]);
			if (tempFTLot.getState() == CustomerLotState.Ordered) {
				throw new RuntimeException("所选批次中有已下单的批次，合批失败");
			}
			// 尝试与合批结果实体相合，产品型号、客户ppo、版本型号不一致时合批失败
			String[] errorMessage = new String[1];
			if (!integrateTwoOnIntegrateManual(integrateLot, tempFTLot,
					errorMessage)) {
				throw new RuntimeException(errorMessage[0]);
			}
			parentLots.add(tempFTLot);
		}

		// 持久化操作
		create(integrateLot);
		updateAll(parentLots);
	}

	/**
	 * 客批分批：将一批分为两批，原批次数量减少，新批次数量参照separationQty，并与母批关联
	 *
	 * @param parentLot
	 *            母批实体
	 * @param separateQuantity
	 *            分批数量
	 * @return 按照分批数量分出的子批
	 * @version 2016/1/15 Howard
	 * @version 2015/3/27 YuxiangQue
	 */
	private CustomerFTLot separateOne(CustomerFTLot parentLot,
			Long separateQuantity) {
		// 若子批数量大于母批数量或为复数则返回失败
		if (parentLot.getIncomingQuantity() == null
				|| separateQuantity > parentLot.getIncomingQuantity()
				|| separateQuantity < 0)
			return null;
		CustomerFTLot childLot = instancePrototype(parentLot); // 深克隆新建分批
		childLot.setIncomingQuantity(separateQuantity); // 设置子批数量
		childLot.setParentSeparationId(parentLot.getId()); // 设置子批中母批的id
		parentLot.setIncomingQuantity(parentLot.getIncomingQuantity()
				- separateQuantity); // 修改母批数量
		return childLot;
	}

	/**
	 * 获取内部产品批号
	 *
	 * @param customerFTLotId
	 *            需要下单的客户批号
	 * @return 成功时返回批号，失败时返回错误信息
	 * @version 2016/1/16 Howard
	 */
	@Override
	public String getExpectedLotNumber(Long customerFTLotId) {
		CustomerFTLot customerFTLot = get(customerFTLotId); // 获取将要下单的客批
		return incrementNumberApplication.peekFTLotNumber(customerFTLot);
	}

	/**
	 * 获取RC编号
	 *
	 * @param customerFTLotId
	 *            需要下单的客户批号
	 * @return 成功是返回批号，失败时返回错误信息
	 * @author Howard
	 * @version v1.0
	 * @lastModifyDate 2016.1.23
	 */
	@Override
	public String getExpectedRCNumber(Long customerFTLotId) {
		// 获取将要下单的客批
		CustomerFTLot customerFTLot = get(customerFTLotId);
		return incrementNumberApplication.peekRCNumber(customerFTLot);
	}

	private FTRuncard createFTRuncard(FTRuncardTemplate ftRuncardTemplate) {
		FTRuncard ftRuncard = new FTRuncard();
		ftRuncard.setIQC(ftRuncardTemplate.getIQC());
		ftRuncard.setBaking(ftRuncardTemplate.getBaking());
		ftRuncard.setSiteTest(ftRuncardTemplate.getSiteTest());
		ftRuncard.setFVI(ftRuncardTemplate.getFVI());
		ftRuncard.setFQC(ftRuncardTemplate.getFQC());
		ftRuncard.setPacking(ftRuncardTemplate.getPacking());
		ftRuncard.setOQC(ftRuncardTemplate.getOQC());
		SpecialFormTemplate specialFormTemplateSource = ftRuncardTemplate
				.getSpecialFormTemplate();
		SpecialFormTemplate specialFormTemplaceTarget = new SpecialFormTemplate();
		specialFormTemplaceTarget.setFirstSheet(specialFormTemplateSource
				.getFirstSheet());
		specialFormTemplaceTarget.setFirstSheetStatus(specialFormTemplateSource
				.getFirstSheetStatus());
		specialFormTemplaceTarget.setSummarySheet(specialFormTemplateSource
				.getSummarySheet());
		specialFormTemplaceTarget
				.setSummarySheetStatus(specialFormTemplateSource
						.getSummarySheetStatus());
		specialFormTemplaceTarget.setRecordSheet(specialFormTemplateSource
				.getRecordSheet());
		specialFormTemplaceTarget
				.setRecordSheetStatus(specialFormTemplateSource
						.getRecordSheetStatus());
		specialFormTemplaceTarget.setReelcodeSheet(specialFormTemplateSource
				.getReelcodeSheet());
		specialFormTemplaceTarget
				.setReelcodeSheetStatus(specialFormTemplateSource
						.getReelcodeSheetStatus());
		specialFormTemplaceTarget
				.setMachineMaterialRecordSheet(specialFormTemplateSource
						.getMachineMaterialRecordSheet());
		specialFormTemplaceTarget
				.setMachineMaterialRecordSheetStatus(specialFormTemplateSource
						.getMachineMaterialRecordSheetStatus());
		specialFormTemplaceTarget.setCheckSheet(specialFormTemplateSource
				.getCheckSheet());
		specialFormTemplaceTarget.setCheckSheetStatus(specialFormTemplateSource
				.getCheckSheetStatus());

		ftRuncard.setSpecialFormTemplate(specialFormTemplaceTarget);
		ftRuncard.setKeyProductionAuthorization(ftRuncardTemplate
				.getKeyProductionAuthorization());
		ftRuncard.setAssistProductionAuthorization(ftRuncardTemplate
				.getAssistProductionAuthorization());
		ftRuncard.setKeyQuantityAuthorization(ftRuncardTemplate
				.getKeyQuantityAuthorization());
		ftRuncard.setAssistQuantityAuthorization(ftRuncardTemplate
				.getAssistQuantityAuthorization());
		ftRuncard.setKeyTDEAuthorization(ftRuncardTemplate
				.getKeyTDEAuthorization());
		ftRuncard.setAssistTDEAuthorization(ftRuncard
				.getAssistTDEAuthorization());

		return ftRuncard;

	}

	/**
	 * 手动下单
	 *
	 * @param customerFTLotId
	 * @param ftLot
	 */
	@Override
	public void order(Long customerFTLotId, FTLot ftLot,Long ftInfoId) {
		CustomerFTLot customerLot = get(customerFTLotId);

		Boolean success = ftRuncardTemplateApplication
				.isRuncardSigned(customerLot.getFtInfo().getId());
		if (!success) {
			throw new RuntimeException("runcard没有签核");
		}

		// TODO: 检查ftLot
		ftLot.setCustomerFTLot(customerLot);
		// add by lcn 2016.5.16
		FTInfo ftInfo = null;
		if(ftInfoId != null){
			ftInfo = this.ftInfoApplication.get(ftInfoId);
		}else{
			ftInfo = customerLot.getFtInfo();
		}
		FTRuncardTemplate ftRuncardTemplate = ftRuncardTemplateApplication
				.findByInternalProductId(ftInfo.getId());
		FTRuncard ftRuncard = createFTRuncard(ftRuncardTemplate);

		ftLotApplication.createCheckedFTLot(ftLot); // 创建FTLot，检查前端回传的编号

		ftRuncard.setFtLot(ftLot);
		ftRuncard.save();

		ftProcessApplication.createFTProcess(ftLot.getId()); // 创建Process
		customerLot.setState(CustomerLotState.Ordered);
		customerLot.setShipmentNumber(ftLot.getShipmentProductNumber());// 修改出货型号
		customerLot.setFtInfo(ftInfo);
		update(customerLot);
		// 下单成功，调用WMS接口，生成领料信息
		FtTestLot ftTestLot = ConverttoWMSFTLot(customerLot, ftLot);
		ftLot.setWmsTestId(ftTestLot.getID());
		JSONObject obj = JSONObject.fromObject(ftTestLot);
		String lotjson = "[" + obj.toString() + "]";
		// wmsClientApplication.orderLots(TestTypeForWms.getStringValue(TestTypeForWms.FT),
		// lotjson);
	}

	private FtTestLot ConverttoWMSFTLot(CustomerFTLot customerLot, FTLot ftLot) {
		FtTestLot testLot = new FtTestLot();
		testLot.setID(UUID.randomUUID().toString());
		testLot.setACETEC_LOT(ftLot.getInternalLotNumber());
		testLot.setCUS_CODE(customerLot.getCustomerNumber());
		testLot.setCUS_LOT(customerLot.getCustomerLotNumber());
		testLot.setCUS_PPO(customerLot.getCustomerPPO());
		testLot.setIN_ID(customerLot.getWmsId());
		testLot.setIN_PARTNUM(customerLot.getCustomerProductNumber());
		testLot.setOUT_PARTNUM(ftLot.getShipmentProductNumber());
		testLot.setQUANTITY(ftLot.getQty());
		String parentids = ftLot.getParentIntegrationIds();
		if (parentids != null && parentids.length() > 0) {
			List<T_RUNCARDMERGER> mergerList = new ArrayList<T_RUNCARDMERGER>();
			List<String> customerLotsId = Splitter.on(",").trimResults()
					.splitToList(parentids);
			for (String str : customerLotsId) {
				T_RUNCARDMERGER merger = new T_RUNCARDMERGER();
				merger.setID(UUID.randomUUID().toString());
				merger.setIN_ID(str);
				merger.setTEST_ID(testLot.getID());
				mergerList.add(merger);
			}

			testLot.setMERGER(mergerList);
		}
		return testLot;
	}

	/**
	 * 批量下单：循环遍历尝试自动合批后下单
	 *
	 * @param customerFTLotIds
	 *            需要批量下单的批次ids
	 * @param messages
	 *            记录下单信息
	 * @return 返回批量下单结果
	 * @version 2016/1/17 Howad
	 * @version 2016/3/27 YuxiangQue
	 */
	@Override
	public List<Long> orderInBatches(Long[] customerFTLotIds,
			Map<String, Integer> messages) {

		// 过滤订单，未下单或未知状态的，有绑定的process才可下单
		List<CustomerFTLot> customerFTLots = new ArrayList<CustomerFTLot>();
		for (Long id : customerFTLotIds) {
			CustomerFTLot customerFTLot = get(id);
			if (customerFTLot.getState() == CustomerLotState.Ordered) {
				continue;
			}
			if (customerFTLot.getFtInfo().getProcessTemplate() == null) {
				continue;
			}
			if (Strings.isNullOrEmpty(customerFTLot.getFtInfo()
					.getProcessTemplate().getContent())) {
				continue;
			}
			customerFTLots.add(customerFTLot);
		}

		Set<CustomerFTLot> toCreate = new HashSet<CustomerFTLot>(); // 需要新建的批次
		Set<CustomerFTLot> toUpdate = new HashSet<CustomerFTLot>(); // 已被合批的批次
		Set<CustomerFTLot> singleLots = new HashSet<CustomerFTLot>(); // 需要单独下单的批次

		// 循环遍历，每一批都去尽可能多的寻找可合批的批次
		int integrateCount = 0;
		for (int index1 = 0; index1 < customerFTLots.size(); ++index1) {
			CustomerFTLot customerFTLot = customerFTLots.get(index1);

			// 如果该实体已经包含在某个列表当中，则跳过
			if (toUpdate.contains(customerFTLot)
					|| singleLots.contains(customerFTLot))
				continue;

			if (!productIntegrable.containsKey(customerFTLot
					.getCustomerProductNumber())
					|| customerFTLot.getIncomingQuantity() < INTEGRATE_MIN_QUANTITY
					|| customerFTLot.getIncomingQuantity() > INTEGRATE_MAX_QUANTITY) {
				singleLots.add(customerFTLot);
				continue;
			}

			// 建立记录客批号的变量，若最终只有该批次自身，则不新建批次、单独下单
			// 建立目标批次，做好合批的准备。
			CustomerFTLot integrateCustomerFTLot = instancePrototype(customerFTLot);

			// 设置目标批次的合批来源ids
			integrateCustomerFTLot.setParentIntegrationIds(String
					.valueOf(customerFTLot.getId()));
			for (int index2 = index1 + 1; index2 < customerFTLots.size(); ++index2) {
				CustomerFTLot nextIntegrateCustomerFTLot = customerFTLots
						.get(index2);
				// 如果该实体已经包含在某个列表当中，则跳过
				if (toUpdate.contains(nextIntegrateCustomerFTLot)
						|| singleLots.contains(nextIntegrateCustomerFTLot))
					continue;

				// 根据target批次的型号选择调用不同的合批规则
				// 新的合批规则
				String[] errorMessage = new String[1];
				if (productIntegrable.containsKey(integrateCustomerFTLot
						.getCustomerProductNumber())
						&& integrateTwoOnOrderInBatch(integrateCustomerFTLot,
								nextIntegrateCustomerFTLot, errorMessage)) {
					++integrateCount;
					toUpdate.add(nextIntegrateCustomerFTLot);
					// 每成功合批一组，更新parentIntegrationIds
					String integrationIds = Joiner.on(",").join(
							integrateCustomerFTLot.getParentIntegrationIds(),
							nextIntegrateCustomerFTLot.getId());
					integrateCustomerFTLot
							.setParentIntegrationIds(integrationIds);
				} else {
					singleLots.add(nextIntegrateCustomerFTLot);
				}
			}

			// 当最终目标批次的parentIntegrationIds不包含“，”即没有合批动作时，原批次单独下单
			if (integrateCustomerFTLot.getParentIntegrationIds().contains(",")) {
				markCustomerFTLotInvisible(customerFTLot);
				toUpdate.add(customerFTLot);
				toCreate.add(integrateCustomerFTLot);
			} else {
				singleLots.add(customerFTLot);
			}
		}

		createAll(toCreate);

		// 下单逻辑
		List<Long> ftLotIds = new ArrayList<Long>(); // 记录成功下单的Id
		placeOrders(singleLots, ftLotIds);
		placeOrders(toCreate, ftLotIds);

		// 更新下单状态
		updateAll(toUpdate);
		updateAll(toCreate);
		updateAll(singleLots);

		// 记录下单结果
		messages.put("select", customerFTLotIds.length); // 选择的个数
		messages.put("orders", singleLots.size() + toCreate.size()); // 下单的个数
		messages.put("combine", integrateCount); // 合批的个数

		// 返回成功下单的批次id
		return ftLotIds;
	}

	/**
	 * 合批还原，针对一个合批生成的批次进行合批还原
	 *
	 * @param customerFTLotId
	 *            合批生成的批次
	 * @return 返回成功或错误信息
	 * @version 2016/1/17 Howad
	 */
	@Override
	public void undoIntegration(Long customerFTLotId) {
		// 获取母批实体
		CustomerFTLot parentCustomerFTLot = get(customerFTLotId);
		if (parentCustomerFTLot.getState() == CustomerLotState.Ordered) {
			throw new RuntimeException("该批次已下单，还原失败");
		}

		try {
			// 获取母批对应的子批ids，获取不到则返回错误
			String ids = parentCustomerFTLot.getParentIntegrationIds();
			Long[] idArrs = parseIds(ids); // 解析id

			// 初始化lots set
			Set<CustomerFTLot> lots = new HashSet<CustomerFTLot>();
			// 遍历id，获取对应实体并修改其logic值，放入set中
			for (Long idTemp : idArrs) {
				CustomerFTLot customerFTLotTemp = get(idTemp);
				markCustomerFTLotVisible(customerFTLotTemp);
				lots.add(customerFTLotTemp);
			}
			// 恢复子批，删除母批
			updateAll(lots);
			remove(parentCustomerFTLot);
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw new RuntimeException("找不到该批次的合批来源");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new RuntimeException("解析id值出错，还原失败");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("还原过程出错，还原失败");
		}
	}

	/**
	 * 根据用“，”分隔的ids字符串解析出id数组
	 *
	 * @param ids
	 *            包含id的字符串
	 * @return id数组
	 * @author Howard
	 */
	private Long[] parseIds(String ids) {
		String[] value = ids.split(",");
		Long[] idArrs = new Long[value.length];
		for (int i = 0; i < value.length; i++) {
			idArrs[i] = Long.parseLong(value[i]);
		}
		return idArrs;
	}

	/**
	 * 根据一个目标批次建立一个新的批次，包括深克隆、初始化基本信息等
	 *
	 * @param original
	 *            参考批次
	 * @return 新建立的批次
	 * @since 2016.03.30 Howard
	 */
	private CustomerFTLot instancePrototype(CustomerFTLot original) {
		/*
		 * CustomerFTLot customerFTLot = SerializationUtils.clone(original);
		 * resetFTCustomerLot(customerFTLot); return customerFTLot;
		 */

		CustomerFTLot customerFTLot = (CustomerFTLot) Mes2EntityOperator
				.instancePrototype(original);
		customerFTLot.setParentSeparationId(null);
		customerFTLot.setParentIntegrationIds(null);
		return customerFTLot;
	}

	// /**
	// * 将某个客批下单
	// *
	// * @param customerFTLot 需要下单的客批
	// * @return 下单结果
	// * @author Howard
	// */
	// private InvokeResult placeOrder(CustomerFTLot customerFTLot) {
	// //获取internalLotNumber
	// InvokeResult lotNumberResult =
	// getExpectedLotNumber(customerFTLot.getId());
	// if (lotNumberResult.isHasErrors())
	// return lotNumberResult;
	// //获取RCNumber
	// InvokeResult rcNumberResult = getExpectedRCNumber(customerFTLot.getId());
	// if (rcNumberResult.isHasErrors())
	// return rcNumberResult;
	//
	// //根据客批创建内部批次DTO
	// FTLotDTO ftLotDTO = new FTLotDTO();
	// //设置两个编号
	// ftLotDTO.setInternalLotNumber((String) lotNumberResult.getData());
	// ftLotDTO.setRcNumber((String) rcNumberResult.getData());
	// //出货类型等同于来料类型
	// ftLotDTO.setShipmentProductNumber(customerFTLot.getCustomerProductNumber());
	// ftLotDTO.setCustomerLotDTO(CustomerFTLotAssembler.toDTO(customerFTLot));
	// ftLotDTO.setMaterialType(customerFTLot.getMaterialType());
	// ftLotDTO.setQty(customerFTLot.getIncomingQuantity());
	//
	// //调用internalLot下单（包括持久化操作）
	// try {
	// // result = internalLotFacade.creatInternalLot(internalLotDTO);
	// //return result.isHasErrors() ? result :
	// InvokeResult.success(internalLotDTO.getId());
	// lotNumberResult = ftLotFacade.creatFTLot(ftLotDTO);
	// //如果下单成功则改变客批状态
	// if (lotNumberResult.isSuccess()) {
	// customerFTLot.setState(1);
	// return lotNumberResult;
	// }
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// return InvokeResult.failure("下单操作失败。" + e.getMessage());
	// }
	//
	// return InvokeResult.failure("下单操作失败。");
	//
	// }

	/**
	 * 将某个客批下单
	 *
	 * @param customerFTLot
	 *            需要下单的客批
	 * @return 下单结果
	 * @version Howard
	 * @version LCN
	 * @version 2016/3/22 YuxiangQue
	 * @version Eva 20160414 下单成功之后需返回批次信息至WMS以便生成领料信息
	 */

	private Long placeOrder(CustomerFTLot customerFTLot) {

		String ftLotNumber = incrementNumberApplication
				.nextFTLotNumber(customerFTLot); // 获取internalLotNumber
		String ftRcNumber = incrementNumberApplication
				.nextRCNumber(customerFTLot); // 获取RCNumber

		// 根据客批创建内部批次DTO
		FTLot ftLot = new FTLot();
		// 设置两个编号
		ftLot.setInternalLotNumber(ftLotNumber);
		ftLot.setRcNumber(ftRcNumber);
		// 出货类型等同于来料类型
		ftLot.setShipmentProductNumber(customerFTLot.getCustomerProductNumber());
		ftLot.setCustomerFTLot(customerFTLot);
		// TODO:
		// ftLot.setMaterialType(customerFTLot.getMaterialType());
		ftLot.setQty(customerFTLot.getIncomingQuantity());

		// 调用internalLot下单（包括持久化操作）
		// result = internalLotFacade.creatInternalLot(internalLotDTO);
		// return result.isHasErrors() ? result :
		// InvokeResult.success(internalLotDTO.getId());
		ftLotApplication.create(ftLot);

		// add by lcn 2016.5.16
		Boolean success = ftRuncardTemplateApplication.isRuncardSigned(ftLot
				.getFtInfo().getId());
		if (!success) {
			throw new RuntimeException("runcard没有签核");
		}

		// add by lcn 2016.5.16
		FTInfo ftInfo = ftLot.getFtInfo();
		FTRuncardTemplate ftRuncardTemplate = ftRuncardTemplateApplication
				.findByInternalProductId(ftInfo.getId());
		FTRuncard ftRuncard = createFTRuncard(ftRuncardTemplate);
		ftRuncard.setFtLot(ftLot);
		ftRuncard.save();

		// 创建Process
		ftProcessApplication.createFTProcess(ftLot.getId());
		// 如果下单成功则改变客批状态
		customerFTLot.setState(CustomerLotState.Ordered);

		// 下单成功，调用WMS接口，生成领料信息
		FtTestLot ftTestLot = ConverttoWMSFTLot(customerFTLot, ftLot);
		ftLot.setWmsTestId(ftTestLot.getID());
		JSONObject obj = JSONObject.fromObject(ftTestLot);
		String lotjson = "[" + obj.toString() + "]";
		// wmsClientApplication.orderLots(TestTypeForWms.getStringValue(TestTypeForWms.FT),
		// lotjson);

		return ftLot.getId();
	}

	/**
	 * 将一批customerftlots下单
	 *
	 * @param customerFTLots
	 *            需要下单的批次
	 * @return 批量下单结果
	 * @throws RuntimeException
	 *             当下单失败时终止下单过程
	 * @author Howard
	 */
	private void placeOrders(Collection<CustomerFTLot> customerFTLots,
			Collection<Long> ftLotIds) {
		int count = 0;
		// 循环调用单批下单
		try {
			for (CustomerFTLot customerFTLot : customerFTLots) {
				ftLotIds.add(placeOrder(customerFTLot));
				count++;
			}
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			throw new RuntimeException("批量下单过程意外中断，已成功下单" + count + "个批次，"
					+ ex.getMessage());
		}
	}

	// /**
	// * 对1201、1302客户的里两个批次进行合批，合批规则见具体注释
	// *
	// * @param targetLot 目标批次
	// * @param sourceLot 将要被合批的批次
	// * @return 成功则返回成功标记，否则返回错误信息
	// * @throws Exception 多数情况下抛出的应为nullPointerException
	// * @author Howard
	// * @version v1.1
	// * @lastModifyDate 2016.01.16
	// */
	// private void combineTwo1201_1302Lot(CustomerFTLot targetLot,
	// CustomerFTLot sourceLot) throws Exception {
	// //初始化sum值以及上限
	// Long sum = targetLot.getIncomingQuantity();
	// Long maxSum =
	// productOf1201_1302.get(targetLot.getCustomerProductNumber());
	//
	// //获取封装批号前缀
	// String packageNumber = targetLot.getPackageNumber();
	// if (packageNumber.contains("-")) {
	// packageNumber = packageNumber.substring(0,
	// packageNumber.lastIndexOf('-'));
	// }
	//
	// //是否在1201、1302set内，不在返回错误
	// if
	// (!productOf1201_1302.containsKey(sourceLot.getCustomerProductNumber()))
	// return InvokeResult.failure("所选批次不可合批！");
	// //是否与基准产品型号相同，不同返回错误
	// if
	// (!targetLot.getCustomerProductNumber().equals(sourceLot.getCustomerProductNumber()))
	// return InvokeResult.failure("所选批次不属于同一个产品型号！");
	// //是否与基准产品版本型号相同，不同返回错误
	// if (!targetLot.getProductVersion().equals(sourceLot.getProductVersion()))
	// return InvokeResult.failure("产品版本型号不同，合批失败");
	// //是否与基准PPO相同
	// if (!targetLot.getCustomerPPO().equals(sourceLot.getCustomerPPO()))
	// return InvokeResult.failure("所属客户PPO不同!");
	// //是否与基准客户批号相同
	// if
	// (!targetLot.getCustomerLotNumber().equals(sourceLot.getCustomerLotNumber()))
	// return InvokeResult.failure("所属客户批号不同！");
	// //是否与基准封装批号前缀相同，不同返回错误
	// if (sourceLot.getPackageNumber().contains("-")) {
	// if (!packageNumber.equals(sourceLot.getPackageNumber().substring(0,
	// sourceLot.getPackageNumber().lastIndexOf('-')))) {
	// return InvokeResult.failure("封装批号不符合要求，不可合批！");
	// }
	// } else if (!packageNumber.equals(sourceLot.getPackageNumber())) {
	// return InvokeResult.failure("封装批号不符合要求，不可合批！");
	// }
	// //计算sum，若超出map中指定的数量上限，则返回错误
	// sum += sourceLot.getIncomingQuantity();
	// if (sum > maxSum)
	// return InvokeResult.failure("数量超过限制，合批失败！");
	// targetLot.setIncomingQuantity(sum);
	// //检查到料形式
	// if (!targetLot.getIncomingStyle().equals(sourceLot.getIncomingStyle()))
	// return InvokeResult.failure("到料形式不同，合批失败");
	// //检查物料类型
	// if (!targetLot.getMaterialType().equals(sourceLot.getMaterialType()))
	// return InvokeResult.failure("物料类型不同，合批失败");
	// //检查保税类型
	// if (!targetLot.getTaxType().equals(sourceLot.getTaxType()))
	// return InvokeResult.failure("保税类型不同，合批失败");
	// //检查wireBond
	// if (!targetLot.getWireBond().equals(sourceLot.getWireBond()))
	// return InvokeResult.failure("wire bond 不同， 合批失败");
	// //检查waferLot
	// if (!targetLot.getWaferLot().equals(sourceLot.getWaferLot()))
	// return InvokeResult.failure("wafer lot 不同，合批失败");
	// //检查mfgpn
	// if (!targetLot.getMFGPN().equals(sourceLot.getMFGPN()))
	// return InvokeResult.failure("MFG PN不同，合批失败");
	// /* //设置到料形式
	// if ( !
	// targetLot.getIncomingStyle().contains(sourceLot.getIncomingStyle()) )
	// targetLot.setIncomingStyle(targetLot.getIncomingStyle() + '/' +
	// sourceLot.getIncomingStyle());
	// //设置物料类型
	// if ( ! targetLot.getMaterialType().contains(sourceLot.getMaterialType()))
	// targetLot.setMaterialType(targetLot.getMaterialType() + '/' +
	// sourceLot.getMaterialType());
	// //设置保税类型
	// if ( ! targetLot.getTaxType().contains(sourceLot.getTaxType()))
	// targetLot.setTaxType(targetLot.getTaxType() + '/' +
	// sourceLot.getTaxType());
	// //设置wireBond
	// if ( ! targetLot.getWireBond().contains(sourceLot.getWireBond()) )
	// targetLot.setWireBond(targetLot.getWireBond() + '/' +
	// sourceLot.getWireBond());
	// //设置waferLot
	// if ( ! targetLot.getWaferLot().contains(sourceLot.getWaferLot()))
	// targetLot.setWaferLot(targetLot.getWaferLot() + '/' +
	// sourceLot.getWaferLot());
	// //设置mfgpn
	// if ( ! targetLot.getMFGPN().contains(sourceLot.getMFGPN()))
	// targetLot.setMFGPN(targetLot.getMFGPN() + '/' + sourceLot.getMFGPN());*/
	// //设置dateCode
	// if (!targetLot.getDateCode().contains(sourceLot.getDateCode()))
	// targetLot.setDateCode(targetLot.getDateCode() + '/' +
	// sourceLot.getDateCode());
	//
	// //将该实体flag设置为1，表示已被合批
	// markCustomerFTLotInvisible(sourceLot);
	//
	// return InvokeResult.success();
	// }

	// /**
	// * 对除1201、1302客户的产品以外的其他产品进行两个批次合批
	// *
	// * @param targetLot 目标批次
	// * @param sourceLot 将要被合批的批次
	// * @return 成功则返回成功标记，否则返回错误信息
	// * @throws Exception 多数情况下抛出的应为nullPointerException
	// * @author Howard
	// * @version v1.1
	// * @lastModifyDate 2016.01.16
	// */
	// private InvokeResult combineTwoOtherLot(CustomerFTLot targetLot,
	// CustomerFTLot sourceLot) throws Exception {
	// //初始化客户批号（有较为复杂的应用）
	// String customerLotNumber = targetLot.getCustomerLotNumber();
	// //初始化sum值及其上下限
	// Long sum = targetLot.getIncomingQuantity();
	// Long minSum = (long) 10000;
	// Long maxSum = productOfOthers.get(targetLot.getCustomerProductNumber());
	//
	// //是否在其他可合批产品set内，不在返回错误
	// if (!productOfOthers.containsKey(sourceLot.getCustomerProductNumber()))
	// return InvokeResult.failure("所选批次不可合批！");
	// //是否与基准产品型号相同，不同返回错误
	// if
	// (!targetLot.getCustomerProductNumber().equals(sourceLot.getCustomerProductNumber()))
	// return InvokeResult.failure("所选批次不属于同一个产品型号！");
	// //是否与基准产品版本型号相同，不同返回错误
	// if (!targetLot.getProductVersion().equals(sourceLot.getProductVersion()))
	// return InvokeResult.failure("产品版本型号不同，合批失败");
	// //客批是否超过两个（包括字符'/'并且再次需要再次增加则超过两个）
	// if (!customerLotNumber.contains(sourceLot.getCustomerLotNumber())) {
	// if (customerLotNumber.contains("/"))
	// return InvokeResult.failure("所选批次超过两个客户批号！");
	// customerLotNumber += ("/" + sourceLot.getCustomerLotNumber());
	// targetLot.setCustomerLotNumber(customerLotNumber);
	// }
	// //查看sum是否在规定范围内
	// if (sourceLot.getIncomingQuantity() < minSum)
	// return InvokeResult.failure("所选批次数量小于10K，合批失败！");
	// sum += sourceLot.getIncomingQuantity();
	// if (sum > maxSum)
	// return InvokeResult.failure("数量超过限制，合批失败！");
	// targetLot.setIncomingQuantity(sum);
	// //检查到料形式
	// if (!targetLot.getIncomingStyle().equals(sourceLot.getIncomingStyle()))
	// return InvokeResult.failure("到料形式不同，合批失败");
	// //检查物料类型
	// if (!targetLot.getMaterialType().equals(sourceLot.getMaterialType()))
	// return InvokeResult.failure("物料类型不同，合批失败");
	// //检查保税类型
	// if (!targetLot.getTaxType().equals(sourceLot.getTaxType()))
	// return InvokeResult.failure("保税类型不同，合批失败");
	// //检查wireBond
	// if (!targetLot.getWireBond().equals(sourceLot.getWireBond()))
	// return InvokeResult.failure("wire bond 不同， 合批失败");
	// //检查waferLot
	// if (!targetLot.getWaferLot().equals(sourceLot.getWaferLot()))
	// return InvokeResult.failure("wafer lot 不同，合批失败");
	// //检查mfgpn
	// if (!targetLot.getMFGPN().equals(sourceLot.getMFGPN()))
	// return InvokeResult.failure("MFG PN不同，合批失败");
	// /* //设置到料形式
	// if ( !
	// targetLot.getIncomingStyle().contains(sourceLot.getIncomingStyle()) )
	// targetLot.setIncomingStyle(targetLot.getIncomingStyle() + '/' +
	// sourceLot.getIncomingStyle());
	// //设置物料类型
	// if ( ! targetLot.getMaterialType().contains(sourceLot.getMaterialType()))
	// targetLot.setMaterialType(targetLot.getMaterialType() + '/' +
	// sourceLot.getMaterialType());
	// //设置保税类型
	// if ( ! targetLot.getTaxType().contains(sourceLot.getTaxType()))
	// targetLot.setTaxType(targetLot.getTaxType() + '/' +
	// sourceLot.getTaxType());
	// //设置wireBond
	// if ( ! targetLot.getWireBond().contains(sourceLot.getWireBond()) )
	// targetLot.setWireBond(targetLot.getWireBond() + '/' +
	// sourceLot.getWireBond());
	// //设置waferLot
	// if ( ! targetLot.getWaferLot().contains(sourceLot.getWaferLot()))
	// targetLot.setWaferLot(targetLot.getWaferLot() + '/' +
	// sourceLot.getWaferLot());
	// //设置mfgpn
	// if ( ! targetLot.getMFGPN().contains(sourceLot.getMFGPN()))
	// targetLot.setMFGPN(targetLot.getMFGPN() + '/' + sourceLot.getMFGPN());
	// */
	// //设置客户ppo
	// if (!targetLot.getCustomerPPO().contains(sourceLot.getCustomerPPO()))
	// targetLot.setCustomerPPO(targetLot.getCustomerPPO()
	// + '/' + sourceLot.getCustomerPPO());
	// //设置封装批号
	// if (!targetLot.getPackageNumber().contains(sourceLot.getPackageNumber()))
	// targetLot.setPackageNumber(targetLot.getPackageNumber() + '/' +
	// sourceLot.getPackageNumber());
	// //设置dateCode
	// if (!targetLot.getDateCode().contains(sourceLot.getDateCode()))
	// targetLot.setDateCode(targetLot.getDateCode() + '/' +
	// sourceLot.getDateCode());
	//
	// //标记该实体
	// markCustomerFTLotInvisible(sourceLot);
	//
	// return InvokeResult.success();
	// }

	/**
	 * 将来源批次合并到目标批次当中：产品型号、客户ppo、版本型号不同不可合批，其余如果不同用‘/’相连
	 *
	 * @param targetLot
	 *            目标批次
	 * @param sourceLot
	 *            来源批次
	 * @return 调用结果
	 * @version 2016/01/16 Howard
	 */
	private boolean integrateTwoOnIntegrateManual(CustomerFTLot targetLot,
			CustomerFTLot sourceLot, String[] errorMessage) {
		// 尝试与合批结果实体相合，产品型号、客户ppo、版本型号不一致时合批失败
		if ((!targetLot.getCustomerNumber().equals(
				sourceLot.getCustomerNumber()))
				|| (!targetLot.getCustomerProductNumber().equals(
						sourceLot.getCustomerProductNumber()))
				|| (!targetLot.getCustomerPPO().equals(
						sourceLot.getCustomerPPO()))
				|| (!targetLot.getProductVersion().equals(
						sourceLot.getProductVersion()))) {
			errorMessage[0] = "产品型号、客户ppo、版本型号不同，不可合批";
			return false;
		}
		// 来料通过WMS录入故字段值不会为null，此处不检查String字段是否为null
		// 如果客批不同则追加，如果后者为空则contains结果应当为true
		if (!targetLot.getCustomerLotNumber().contains(
				sourceLot.getCustomerLotNumber()))
			targetLot.setCustomerLotNumber(targetLot.getCustomerLotNumber()
					+ '/' + sourceLot.getCustomerLotNumber());
		// 设置封装型号
		if (!targetLot.getPackageNumber()
				.contains(sourceLot.getPackageNumber()))
			targetLot.setPackageNumber(targetLot.getPackageNumber() + '/'
					+ sourceLot.getPackageNumber());
		// 设置数量
		targetLot.setIncomingQuantity(targetLot.getIncomingQuantity()
				+ sourceLot.getIncomingQuantity());
		// 设置到料形式
		if (!targetLot.getIncomingStyle()
				.contains(sourceLot.getIncomingStyle()))
			targetLot.setIncomingStyle(targetLot.getIncomingStyle() + '/'
					+ sourceLot.getIncomingStyle());
		// 设置dateCode
		if (!targetLot.getDateCode().contains(sourceLot.getDateCode()))
			targetLot.setDateCode(targetLot.getDateCode() + '/'
					+ sourceLot.getDateCode());
		// 设置物料类型
		if (!targetLot.getMaterialType().contains(sourceLot.getMaterialType()))
			targetLot.setMaterialType(targetLot.getMaterialType() + '/'
					+ sourceLot.getMaterialType());
		// 设置保税类型
		if (!targetLot.getTaxType().contains(sourceLot.getTaxType()))
			targetLot.setTaxType(targetLot.getTaxType() + '/'
					+ sourceLot.getTaxType());
		// 设置wireBond
		if (!targetLot.getWireBond().contains(sourceLot.getWireBond()))
			targetLot.setWireBond(targetLot.getWireBond() + '/'
					+ sourceLot.getWireBond());
		// 设置waferLot
		if (!targetLot.getWaferLot().contains(sourceLot.getWaferLot()))
			targetLot.setWaferLot(targetLot.getWaferLot() + '/'
					+ sourceLot.getWaferLot());
		// 设置mfgpn
		if (!targetLot.getMFGPN().contains(sourceLot.getMFGPN()))
			targetLot.setMFGPN(targetLot.getMFGPN() + '/'
					+ sourceLot.getMFGPN());

		// 将sourceLot标记为logic 1
		markCustomerFTLotInvisible(sourceLot);
		return true;
	}

	/**
	 * 新的合批规则
	 *
	 * @param targetLot
	 *            目标批次
	 * @param sourceLot
	 *            将要被合批的批次
	 * @return 成功则返回成功标记，否则返回错误信息
	 * @throws Exception
	 *             多数情况下抛出的应为nullPointerException
	 * @version 2016/1/16 Howard
	 * @verison 2016/3/27 YuxiangQue
	 */
	private boolean integrateTwoOnOrderInBatch(CustomerFTLot targetLot,
			CustomerFTLot sourceLot, String[] errorMessage) {
		// 初始化客户批号（有较为复杂的应用）
		String customerLotNumber = targetLot.getCustomerLotNumber();

		// 初始化sum值及其上下限
		Long sum = targetLot.getIncomingQuantity();

		// 是否在可合批产品set内，不在返回错误
		if (!productIntegrable
				.containsKey(sourceLot.getCustomerProductNumber())) {
			errorMessage[0] = "所选批次不可合批";
			return false;
		}
		// 是否与基准产品型号相同，不同返回错误
		if (!Objects.equals(targetLot.getCustomerProductNumber(),
				sourceLot.getCustomerProductNumber())) {
			errorMessage[0] = "所选批次不属于同一个产品型号";
			return false;
		}
		// 是否与基准产品版本型号相同，不同返回错误
		if (!Objects.equals(targetLot.getProductVersion(),
				sourceLot.getProductVersion())) {
			errorMessage[0] = "产品版本型号不同，合批失败";
			return false;
		}
		// 客批是否超过两个（包括字符'/'并且再次需要再次增加则超过两个）
		if (!customerLotNumber.contains(sourceLot.getCustomerLotNumber())) {
			if (customerLotNumber.contains("/")) {
				errorMessage[0] = "所选批次超过两个客户批号";
				return false;
			}
			customerLotNumber += ("/" + sourceLot.getCustomerLotNumber());
			targetLot.setCustomerLotNumber(customerLotNumber);
		}

		// 查看sum是否在规定范围内
		if (sourceLot.getIncomingQuantity() < INTEGRATE_MIN_QUANTITY) {
			errorMessage[0] = "所选批次数量小于10K，合批失败";
			return false;
		}
		sum += sourceLot.getIncomingQuantity();
		if (sum > INTEGRATE_MAX_QUANTITY) {
			errorMessage[0] = "数量超过50k限制，合批失败";
			return false;
		}
		targetLot.setIncomingQuantity(sum);
		// 检查到料形式
		if (!Objects.equals(targetLot.getIncomingStyle(),
				sourceLot.getIncomingStyle())) {
			errorMessage[0] = "到料形式不同，合批失败";
			return false;
		}
		// 检查物料类型
		if (!Objects.equals(targetLot.getMaterialType(),
				sourceLot.getMaterialType())) {
			errorMessage[0] = "物料类型不同，合批失败";
			return false;
		}
		// 检查保税类型
		if (!Objects.equals(targetLot.getTaxType(), sourceLot.getTaxType())) {
			errorMessage[0] = "保税类型不同，合批失败";
			return false;
		}
		// 检查wireBond
		if (!Objects.equals(targetLot.getWireBond(), sourceLot.getWireBond())) {
			errorMessage[0] = "wire bond 不同，合批失败";
			return false;
		}
		// 检查waferLot
		if (!Objects.equals(targetLot.getWaferLot(), sourceLot.getWaferLot())) {
			errorMessage[0] = "wafer lot 不同，合批失败";
			return false;
		}
		// 检查mfgpn
		if (!Objects.equals(targetLot.getMFGPN(), sourceLot.getMFGPN())) {
			errorMessage[0] = "MFG PN不同，合批失败";
			return false;
		}

		// 设置客户ppo
		if (!targetLot.getCustomerPPO().contains(sourceLot.getCustomerPPO()))
			targetLot.setCustomerPPO(targetLot.getCustomerPPO() + '/'
					+ sourceLot.getCustomerPPO());
		// 设置封装批号
		if (!targetLot.getPackageNumber()
				.contains(sourceLot.getPackageNumber()))
			targetLot.setPackageNumber(targetLot.getPackageNumber() + '/'
					+ sourceLot.getPackageNumber());

		// 设置dateCode
		if (!targetLot.getDateCode().contains(sourceLot.getDateCode()))
			targetLot.setDateCode(targetLot.getDateCode() + '/'
					+ sourceLot.getDateCode());

		// 标记该实体
		markCustomerFTLotInvisible(sourceLot);

		return true;
	}

	/**
	 * 由于本类中存在克隆已存在实体的做法，本函数用于初始化MES2AbstractEntity基本信息
	 *
	 * @param mes2AbstractEntity
	 * @author Howard
	 */
	private void resetMes2AbstractEntity(MES2AbstractEntity mes2AbstractEntity) {
		mes2AbstractEntity.setId(null);
		mes2AbstractEntity.setVersion(0);
		mes2AbstractEntity.setLogic(null);
		mes2AbstractEntity.setCreateEmployNo(null);
		mes2AbstractEntity.setCreateTimestamp(null);
		mes2AbstractEntity.setLastModifyEmployNo(null);
		mes2AbstractEntity.setLastModifyTimestamp(null);
	}

	/**
	 * 重载：由于本类中存在克隆已存在实体的做法，本函数用于初始化CustomerLot基本信息，包括合批来源、分批来源
	 *
	 * @param customerFTLot
	 * @author Howard
	 */
	private CustomerFTLot resetFTCustomerLot(CustomerFTLot customerFTLot) {
		customerFTLot.setId(null);
		customerFTLot.setVersion(0);
		customerFTLot.setLogic(null);
		customerFTLot.setCreateEmployNo(null);
		customerFTLot.setCreateTimestamp(null);
		customerFTLot.setLastModifyEmployNo(null);
		customerFTLot.setLastModifyTimestamp(null);
		customerFTLot.setParentIntegrationIds(null);
		customerFTLot.setParentSeparationId(null);
		return customerFTLot;
	}

	/**
	 * 标记CustomerFTLot
	 *
	 * @param customerFTLot
	 * @author Howard
	 */
	private void markCustomerFTLotInvisible(CustomerFTLot customerFTLot) {
		customerFTLot.setLogic(1);
	}

	/**
	 * 恢复标记CustomerFTLot
	 *
	 * @param customerFTLot
	 * @author Howard
	 */
	private void markCustomerFTLotVisible(CustomerFTLot customerFTLot) {
		customerFTLot.setLogic(null);
	}

	@Override
	public void rollbackWMSTestInfo(String linetype, String testId, Long testQTY) {
		wmsClientApplication.deleteOrderLot(linetype, testId, testQTY);
	}

	/**
	 * 删除下单
	 *
	 * @param ftLot
	 */
	public void deleteOrder(FTLot ftLot) {
		CustomerFTLot customerLot = ftLot.getCustomerFTLot();
		// 删除runcard
		this.ftRuncardApplication.deleteRuncardByLotId(ftLot.getId());
		ftProcessApplication.deleteFTProcess(ftLot.getId()); // 创建Process
		customerLot.setState(CustomerLotState.Unordered);
		// customerLot.setShipmentNumber(ftLot.getShipmentProductNumber());//修改出货型号
		update(customerLot);
	}
}
