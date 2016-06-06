package org.seu.acetec.mes2Koala.application.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.seu.acetec.mes2Koala.application.CPTestingNodeApplication;
import org.seu.acetec.mes2Koala.application.FTCompostedTestApplication;
import org.seu.acetec.mes2Koala.application.ProductionScheduleApplication;
import org.seu.acetec.mes2Koala.application.TestSysApplication;
import org.seu.acetec.mes2Koala.application.utils.Mes2EntityOperator;
import org.seu.acetec.mes2Koala.core.domain.CPLot;
import org.seu.acetec.mes2Koala.core.domain.CPProductionSchedule;
import org.seu.acetec.mes2Koala.core.domain.CPTestingNode;
import org.seu.acetec.mes2Koala.core.domain.CustomerCPLot;
import org.seu.acetec.mes2Koala.core.domain.CustomerFTLot;
import org.seu.acetec.mes2Koala.core.domain.FTComposedTestNode;
import org.seu.acetec.mes2Koala.core.domain.FTLot;
import org.seu.acetec.mes2Koala.core.domain.FTProductionSchedule;
import org.seu.acetec.mes2Koala.core.domain.ProductionSchedule;
import org.seu.acetec.mes2Koala.core.domain.TestSys;
import org.seu.acetec.mes2Koala.core.enums.TransferStorageState;
import org.seu.acetec.mes2Koala.infra.EmsFetcher;
import org.seu.acetec.mes2Koala.infra.MyDateUtils;
import org.springframework.transaction.annotation.Transactional;

@Named
@Transactional
public class ProductionScheduleApplicationImpl extends GenericMES2ApplicationImpl<ProductionSchedule>
		implements ProductionScheduleApplication {

	@Inject
	private TestSysApplication testSysApplication;
	@Inject
	private FTCompostedTestApplication ftCompostedTestApplication;
	@Inject
	private CPTestingNodeApplication cpTestingNodeApplication;

	@Override
	public void createNewFtSchedule(Long testSysId, Long ftComposedTestNodeId) {

		// 根据id取出TestSys FTComposedTestNode CustomerFTLot实体
		TestSys testSys = testSysId == null ? null : testSysApplication.get(testSysId);
		FTComposedTestNode ftComposedTestNode;
		FTLot ftLot;
		CustomerFTLot customerFTLot;
		try {

			ftComposedTestNode = ftCompostedTestApplication.get(ftComposedTestNodeId);
			ftLot = ftComposedTestNode.getFtProcess().getFtLot();
			customerFTLot = ftLot.getCustomerFTLot();
			/*
			 * //下单时testSysId为空，批量排产时才有此值 if ( testSysId != null ) testSys =
			 * testSysApplication.get(testSysId);
			 */
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("创建排产任务过程中实体取用错误");
		}

		// 新建ProductionSchedule实体
		FTProductionSchedule ftProductionSchedule;
		try {
			ftProductionSchedule = new FTProductionSchedule();
			// 关联实体
			// productionSchedule.setTestSys(testSys == null ? null : testSys );
			ftProductionSchedule.setFtComposedTestNode(ftComposedTestNode);
			ftProductionSchedule.setFtLot(ftLot);
			// ftComposedTestNode.setProductionSchedule(productionSchedule);

			ftProductionSchedule.setState(TaskState.NOT_TEST_YET.getState());
			ftProductionSchedule.setAmount(customerFTLot.getIncomingQuantity());
			ftProductionSchedule.setDoneQty(0L);
			ftProductionSchedule.setNote("");
			ftProductionSchedule.setLotNumber(ftLot.getInternalLotNumber());
			ftProductionSchedule.setpPO(customerFTLot.getCustomerPPO());
			ftProductionSchedule.setCustomerProductNumber(customerFTLot.getCustomerProductNumber());
			ftProductionSchedule.setPackageNumber(customerFTLot.getPackageNumber());
			ftProductionSchedule.setCustomerLotNumber(customerFTLot.getCustomerLotNumber());
			/*
			 * // 时间相关，初步设定简单的时间值，之后参照需求细则再做修改
			 * productionSchedule.setPlanedStartTimestamp(new Date());
			 * productionSchedule.setPlannedTimeTakes(null);//私有逻辑
			 * productionSchedule.setActualTimeTakes(null);//私有逻辑(DO NOT MODIFY
			 * THIS!)
			 */
			create(ftProductionSchedule);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("创建排产任务过程中设置属性或关联实体时错误");
		}
	}

	@Override
	public void createNewCpSchedule(Long testSysId, Long cpTestingNodeId) {

		// 根据id取出TestSys CPLot CustomerCPLot实体
		TestSys testSys = testSysId == null ? null : testSysApplication.get(testSysId);
		CPTestingNode cpTestingNode = null;
		CPLot cpLot;
		CustomerCPLot customerCPLot;
		try {
			cpTestingNode = cpTestingNodeApplication.get(cpTestingNodeId);
			cpLot = cpTestingNode.getCpProcess().getCpLot();
			customerCPLot = cpLot.getCustomerCPLot();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("创建排产任务过程中实体取用错误");
		}

		// 新建ProductionSchedule实体
		CPProductionSchedule cpProductionSchedule;
		try {
			cpProductionSchedule = new CPProductionSchedule();
			// 关联实体
			cpProductionSchedule.setCpTestingNode(cpTestingNode);
			cpProductionSchedule.setCpLot(cpLot);

			cpProductionSchedule.setState(TaskState.NOT_TEST_YET.getState());
			cpProductionSchedule.setAmount(customerCPLot.getIncomingQuantity());
			cpProductionSchedule.setDoneQty(0L);
			cpProductionSchedule.setNote("");
			cpProductionSchedule.setLotNumber(cpLot.getInternalLotNumber());
			cpProductionSchedule.setpPO(customerCPLot.getCustomerPPO());
			cpProductionSchedule.setCustomerProductNumber(customerCPLot.getCustomerProductNumber());
			cpProductionSchedule.setPackageNumber(customerCPLot.getPackingLot());
			cpProductionSchedule.setCustomerLotNumber(customerCPLot.getCustomerLotNumber());

			create(cpProductionSchedule);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("创建排产任务过程中设置属性或关联实体时错误");
		}
	}

	@Override
	public void basicScheduling(Long id, Integer version, Long testSysId ) {
		if (null == id || null == testSysId)
			throw new IllegalArgumentException("任务id或机台id为空，排产失败");
		if (null == version)
			throw new IllegalArgumentException("记录version为空，排产失败");

		ProductionSchedule productionSchedule = get(id);

		/****** 更新源机台的时间安排 **********/
		removeTargetAndUpdateTaskList(productionSchedule);

		/********* 将排产任务安排至目标机台 ********/
		// 更新相应信息
		TestSys targetTestSys = testSysApplication.get(testSysId);
		Long order = getTheLastOrder(targetTestSys.getProductions());
		Date lastPlanedEndTime = getTheLastPlanedEndTime(targetTestSys.getProductions());
		// 设置相应信息
		productionSchedule.setVersion(version);
		productionSchedule.setScheduleOrder(++order);
		productionSchedule.setTestSys(targetTestSys);
		productionSchedule.setPlanedStartTimestamp(lastPlanedEndTime);
		// 持久化操作
		update(productionSchedule);
	}

	@Override
	public void startTesting(Long id) {
		if (null == id)
			throw new IllegalArgumentException("id为空，无法开始测试");

		ProductionSchedule productionSchedule = get(id);
//		productionSchedule.setActualStartTimestamp(new Date());
		productionSchedule.setState(TaskState.TESTING.getState());
		// 同时更新计划开始时间
		// productionSchedule.setPlanedStartTimestamp(productionSchedule.getActualStartTimestamp());

		// 取出任务对应的机台所有的任务
		List<ProductionSchedule> tasks;
		try {
			tasks = productionSchedule.getTestSys().getProductions();
		} catch (NullPointerException e) {
			throw new RuntimeException("找不到机台。请检查是否已排产");
		}
		ProductionSchedule preTask = getPreviousTask(productionSchedule);
		if (preTask != null && (preTask.getState().equals(TaskState.NOT_TEST_YET.getState())
				|| preTask.getState().equals(TaskState.TESTING.getState())))
			throw new RuntimeException("该排产任务之前还有待测试或正在测试状态的任务");
		else
			updatePlanedTimeFromTargetIndex(tasks, tasks.indexOf(productionSchedule) + 1); // 已包含持久化操作

		// update(productionSchedule);
	}

	@Override
	public void endTesting(Long id) {
		if (null == id)
			throw new IllegalArgumentException("id为空，无法结束测试");

		ProductionSchedule productionSchedule = get(id);
		productionSchedule.setActualEndTimestamp(new Date());
		productionSchedule.setState(TaskState.TEST_DONE.getState());
		productionSchedule.setSubState(null);

		refreshPlanedTime(productionSchedule);
	}

	@Override
	public void separate(Long id, String newLotNumber, Double percent, Long targetTestSysId) {
		// 取出源批次
		ProductionSchedule sourceProduction = get(id);

		// 深克隆
		ProductionSchedule targetProduction = (ProductionSchedule) Mes2EntityOperator
				.instancePrototype(sourceProduction);

		// 设置批号、计算并设置数量、设置对应的TestSys及其id
		Long targetAmount = Math.round(sourceProduction.getAmount() * percent / 100);
		targetProduction.setLotNumber(newLotNumber);
		targetProduction.setAmount(targetAmount);
		// sourceLot.setAmount(sourceLot.getAmount() - targetAmount); //源批次数量不变
		TestSys testSys = testSysApplication.get(targetTestSysId);

		// 设置次序
		Long order = getTheLastOrder(testSys.getProductions());
		targetProduction.setTestSys(testSys);
		targetProduction.setScheduleOrder(++order);
		// 设置预计开始时间
		Date date = getTheLastPlanedEndTime(testSys.getProductions());
		targetProduction.setPlanedStartTimestamp(date);

		/*
		 * if (targetProduction instanceof FTProductionSchedule)
		 * ((FTProductionSchedule)
		 * targetProduction).setFtComposedTestNode(null); // TODO // 绑定目标复合节点
		 * else if (targetProduction instanceof CPProductionSchedule)
		 * ((CPProductionSchedule) targetProduction).setCpTestingNode(null);
		 * else throw new RuntimeException("批次类型错误");
		 */

		targetProduction.setPlannedTimeTakes(null); // 更正时间

		targetProduction.setState(TaskState.NOT_TEST_YET.getState());

		// 持久化
		// updateSeparatedLots(sourceLot, targetLot); //源批次无需更新
		create(targetProduction);

	}

	@Override
	public void updateState(Long id, String state) {
		// TODO Auto-generated method stub
		ProductionSchedule productionSchedule = get(id);
		productionSchedule.setState(state);
		update(productionSchedule);
	}

	@Override
	public void arrangeProductionsInATestSys(Long[] productionIds, Long testSysId) {
		TestSys testSys = testSysApplication.get(testSysId);
		List<ProductionSchedule> tasks = testSys.getProductions();
		Long order = getTheLastOrder(tasks);

		for (Long id : productionIds) {
			ProductionSchedule temp = get(id);
			temp.setTestSys(testSys);
			temp.setScheduleOrder(++order);

			// 如果有上一条记录，则取上一条记录的planedEndTimestamp，否则取新的日期
			temp.setPlanedStartTimestamp(
					tasks.size() > 0 ? tasks.get(tasks.size() - 1).getPlanedEndTimestamp() : new Date());
			temp.setPlannedTimeTakes(null);// 私有逻辑
			temp.setActualTimeTakes(null);// 私有逻辑(DO NOT MODIFY THIS!)

			update(temp);
			tasks.add(temp);
		}
		testSys.setProductions(tasks);
		testSysApplication.update(testSys);
	}

	@Override
	public void revokeProductionSchedules(Long[] ids) {
		for (Long id : ids) {
			ProductionSchedule temp = get(id);
			removeTargetAndUpdateTaskList(temp);
			temp.setTestSys(null);
			temp.setScheduleOrder(null);
			temp.setPlanedStartTimestamp(null);
			temp.setActualStartTimestamp(null);
			temp.setState(TaskState.NOT_TEST_YET.getState());
			temp.setSubState(null);
			update(temp);
		}
	}

	/**
	 * 获取当前任务列表最后一项任务的结束时间。无任务则返回当前时间
	 *
	 * @param tasks
	 * @return
	 */
	private Date getTheLastPlanedEndTime(List<ProductionSchedule> tasks) {
		if (tasks.size() != 0) {
			return tasks.get(tasks.size() - 1).getPlanedEndTimestamp();
		} else {
			return new Date();
		}
	}

	private Long getTheLastOrder(List<ProductionSchedule> tasks) {
		Long order = 0L;
		if (tasks.size() != 0) {
			order = tasks.get(tasks.size() - 1).getScheduleOrder();
		}
		return order;
	}

	/**
	 * 找到任务列表中最后一个某种状态的批次
	 *
	 * @param list
	 *            任务列表
	 * @param taskState
	 *            状态，枚举类型
	 * @return 返回索引值。否则返回-1
	 */
	private int getIndexOfLastStateTask(List<ProductionSchedule> list, TaskState taskState) {
		if (null == list)
			throw new IllegalArgumentException("无法获取列表的最后一个" + taskState.getState() + "的批次：参数错误");

		int index = list.size() - 1;
		for (; index >= 0; --index) {
			if (list.get(index).getState().equals(taskState.getState()))
				return index;
		}
		return -1;
	}

	@Override
	public void reorderUp(Long id) {
		ProductionSchedule productionSchedule = get(id);
		List<ProductionSchedule> productionSchedules;
		try {
			productionSchedules = productionSchedule.getTestSys().getProductions();
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw new RuntimeException("无法获取对应机台的排产任务列表");
		}
		int index = productionSchedules.indexOf(productionSchedule);
		if (index == -1) {
			throw new RuntimeException("该排产任务不在当前机台的任务列表内");
		} else if (index == 0) {
			throw new RuntimeException("已为当前机台任务列表的第一项");
		} else {
			// 交换当前测试任务与前一个测试任务的顺序
			adjustOrder(productionSchedules, index, index - 1);
		}
	}

	@Override
	public void reorderDown(Long id) {
		ProductionSchedule productionSchedule = get(id);
		List<ProductionSchedule> productionSchedules;
		try {
			productionSchedules = productionSchedule.getTestSys().getProductions();
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw new RuntimeException("无法获取对应机台的排产任务列表");
		}
		int index = productionSchedules.indexOf(productionSchedule);
		if (index == -1) {
			throw new RuntimeException("该排产任务不在当前机台的任务列表内");
		} else if (index == productionSchedules.size() - 1) {
			throw new RuntimeException("已为当前机台任务列表的最后一项");
		} else {
			// 交换当前测试任务与前一个测试任务的顺序
			adjustOrder(productionSchedules, index, index + 1);
		}

	}

	@Override
	public void moveToTop(Long[] ids) {
		for (int i = ids.length - 1; i >= 0; --i) {
			Long id = ids[i];
			ProductionSchedule productionSchedule = get(id);
			List<ProductionSchedule> productionSchedules;
			try {
				productionSchedules = productionSchedule.getTestSys().getProductions();
			} catch (NullPointerException e) {
				e.printStackTrace();
				throw new RuntimeException("无法获取对应机台的排产任务列表");
			}
			int index = productionSchedules.indexOf(productionSchedule);
			if (index == -1) {
				throw new RuntimeException("该排产任务不在当前机台的任务列表内");
			} else if (index == 0) {
				throw new RuntimeException("已为当前机台任务列表的第一项");
			} else {
				int indexOfLastTestingTask = getIndexOfLastStateTask(productionSchedules, TaskState.TESTING);
				if (index > indexOfLastTestingTask) {
					adjustOrder(productionSchedules, index, indexOfLastTestingTask + 1);
				}
			}
		}
	}

	/**
	 * 验证该调整操作是否合法，执行调整操作。
	 *
	 * @param productionSchedules
	 *            任务列表
	 * @param sourceIndex
	 *            原索引
	 * @param targetIndex
	 *            目标索引
	 */
	private void adjustOrder(List<ProductionSchedule> productionSchedules, int sourceIndex, int targetIndex) {

		if (sourceIndex < 0 || targetIndex < 0 || sourceIndex > productionSchedules.size()
				|| targetIndex > productionSchedules.size()) {
			throw new IllegalArgumentException();
		}
		// 获取最后一个正在测试任务的索引
		int indexOfLastTestingTask = getIndexOfLastStateTask(productionSchedules, TaskState.TESTING);
		// 获取最后一个已完成任务的索引
		int indexOfLastDoneTask = getIndexOfLastStateTask(productionSchedules, TaskState.TEST_DONE);

		// 判断任务是否可移动
		if (indexOfLastTestingTask != -1) {
			if ((sourceIndex <= indexOfLastTestingTask && targetIndex >= indexOfLastTestingTask)
					|| (targetIndex <= indexOfLastTestingTask && sourceIndex >= indexOfLastTestingTask)) {
				throw new RuntimeException("不可与测试完成、正在测试的批次交换顺序");
			}
		}
		if (indexOfLastDoneTask != -1) {
			if ((sourceIndex <= indexOfLastDoneTask && targetIndex >= indexOfLastDoneTask)
					|| (targetIndex <= indexOfLastDoneTask && sourceIndex >= indexOfLastDoneTask)) {
				throw new RuntimeException("不可与测试完成、正在测试的批次交换顺序");
			}
		}

		// 执行移动
		ProductionSchedule productionSchedule = productionSchedules.get(sourceIndex);
		productionSchedules.remove(sourceIndex);
		productionSchedules.add(targetIndex, productionSchedule);

		// 更新时间
		updatePlanedTimeFromTargetIndex(productionSchedules, sourceIndex < targetIndex ? sourceIndex : targetIndex);
	}

	/**
	 * 获取当前批次所在机台的下一个批次
	 *
	 * @param productionSchedule
	 * @return
	 */
	private ProductionSchedule getNextTask(ProductionSchedule productionSchedule) {
		if (productionSchedule == null)
			throw new IllegalArgumentException();

		List<ProductionSchedule> productionSchedules = productionSchedule.getTestSys().getProductions();
		int index = productionSchedules.indexOf(productionSchedule);
		if (index + 1 < productionSchedules.size())
			return productionSchedules.get(index + 1);
		else
			return null;
	}

	/**
	 * 获取当前批次所在机台的上一个批次
	 *
	 * @param productionSchedule
	 * @return 若存在，则返回实体对象，否则返回null
	 */
	private ProductionSchedule getPreviousTask(ProductionSchedule productionSchedule) {
		if (productionSchedule == null)
			throw new IllegalArgumentException();

		List<ProductionSchedule> productionSchedules = productionSchedule.getTestSys().getProductions();
		int index = productionSchedules.indexOf(productionSchedule);
		if (index - 1 >= 0)
			return productionSchedules.get(index - 1);
		else
			return null;
	}

	/**
	 * 从里表中移除指定的任务并更新余下的任务时间安排
	 *
	 * @param productionSchedule
	 */
	private void removeTargetAndUpdateTaskList(ProductionSchedule productionSchedule) {
		if (productionSchedule == null)
			throw new IllegalArgumentException();

		List<ProductionSchedule> sourceProductionSchedules = productionSchedule.getTestSys().getProductions();
		int sourceIndex = sourceProductionSchedules.indexOf(productionSchedule);
		sourceProductionSchedules.remove(sourceIndex);
		updatePlanedTimeFromTargetIndex(sourceProductionSchedules, sourceIndex);
	}

	/**
	 * 更新指定测试任务所在机台的后续任务的计划时间
	 * @param changedProduction
	 */
	private void refreshPlanedTime( ProductionSchedule changedProduction ) {
		if ( changedProduction == null ) return;

		try{
			List<ProductionSchedule> sourceProductionSchedules = changedProduction.getTestSys().getProductions();
			int sourceIndex = sourceProductionSchedules.indexOf(changedProduction);
			updatePlanedTimeFromTargetIndex(sourceProductionSchedules, sourceIndex + 1);
		} catch ( NullPointerException e ) {
			//没有拿到所在机台的任务列表
		}
	}
	
	/**
	 * 重载：对指定列表中的所有排产任务update
	 * {@link org.seu.acetec.mes2Koala.application.impl.ProductionScheduleApplicationImpl.refreshPlanedTime(ProductionSchedule) }
	 * @param changedProductions
	 */
	private void refreshPlanedTime( List<ProductionSchedule> changedProductions ) {
		if ( changedProductions == null ) return;

		for ( ProductionSchedule productionSchedule : changedProductions ) {
			refreshPlanedTime(productionSchedule);
		}
	}

	/**
	 * 从指定索引处更新后续所有的排产任务的时间
	 *
	 * @param productionSchedules
	 *            任务列表
	 * @param targetIndex
	 *            指定索引处
	 */
	private void updatePlanedTimeFromTargetIndex(List<ProductionSchedule> productionSchedules, int targetIndex) {
		if (productionSchedules == null || targetIndex < 0 || targetIndex > productionSchedules.size())
			throw new IllegalArgumentException();

		for (int i = targetIndex; i < productionSchedules.size(); ++i) {
			ProductionSchedule temp = productionSchedules.get(i);
			if (i == 0) {
				temp.setPlanedStartTimestamp(new Date());
			} else {
				temp.setPlanedStartTimestamp(productionSchedules.get(i - 1).getPlanedEndTimestamp());
			}
		}
		updateAll(productionSchedules);
	}

	@Override
	public void updateAllTestingProduction() {
		// 获取所有状态为正在测试的批次
		List<ProductionSchedule> productionSchedules = find("select o from ProductionSchedule o where o.state=?",
				TaskState.TESTING.getState());
		for (ProductionSchedule p : productionSchedules) {
			Long platformId = null;
			try {
				platformId = p.getTestSys().getEmsPlatformId();
			} catch (NullPointerException e) {
				System.err.println("测试任务" + p.getId() + "获取测试平台ID失败");
				continue;
			}
			//更新相关信息
			updateTestingProduction(p, platformId);
		}
		// 持久化
		updateAll(productionSchedules);

	}

	private void updateTestingProduction(ProductionSchedule productionSchedule, Long platformId) {

		/* 从EMS获取机台状态 */
		String subState = EmsFetcher.getPlatformState(platformId);
		productionSchedule.setSubState(subState);

		/* 从EMS获取setup时间 */
		if ( productionSchedule.getActualStartTimestamp() == null ) {
			Date actualStartTime = EmsFetcher.getSetupTime(platformId, productionSchedule.getLotNumber());
			productionSchedule.setActualStartTimestamp(actualStartTime);
			//更新所在机台的后续排产任务的计划时间（可能会引起持久化异常）
			refreshPlanedTime(productionSchedule);
		}

		/* 尝试针对该批次从EMS获取已完成数量 */
		Long doneQty = null;
		if ( productionSchedule instanceof FTProductionSchedule ) {
			doneQty = EmsFetcher.getFtDoneQty(platformId);
		} else if ( productionSchedule instanceof CPProductionSchedule ) {
			doneQty = EmsFetcher.getCpDoneQty(platformId);
		} else {
			doneQty = null;
		}
		if (doneQty == null && productionSchedule.getActualStartTimestamp() != null ) {
			// 若无法获取则根据时间和UPH计算
			// 直接获取对应节点的uphReality，不再检查
			int uphReality = 0;
			if ( productionSchedule instanceof FTProductionSchedule ) {
				uphReality = ((FTProductionSchedule) productionSchedule).getFtComposedTestNode().getTestProgram().getUphReality();
			} else if ( productionSchedule instanceof CPProductionSchedule ) {
				uphReality = ((CPProductionSchedule) productionSchedule).getCpTestingNode().getTestProgram().getUphReality();
			} else {
				uphReality = 0;
			}
			Double hourDiff = MyDateUtils.hourDateDiff(productionSchedule.getActualStartTimestamp(), new Date());
			doneQty = (long) (uphReality * hourDiff);
		}
		productionSchedule.setDoneQty(doneQty);
	}

	@Override
	public void updateAllCpDoneQty() {
		// TODO Auto-generated method stub

	}

	public enum TaskState {
		NOT_TEST_YET("0"), TEST_DONE("1"), TESTING("2"), WAIT_FOR_ENGINEERING("3"), WAIT_FOR_NOTIFY("4");

		private String state;

		private TaskState(String state) {
			this.state = state;
		}

		public String getState() {
			return state;
		}
	}

}
