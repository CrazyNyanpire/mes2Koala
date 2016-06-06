package org.seu.acetec.mes2Koala.application.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.seu.acetec.mes2Koala.application.CustomerFTLotApplication;
import org.seu.acetec.mes2Koala.application.FTLotApplication;
import org.seu.acetec.mes2Koala.application.FTLotNodeOperationApplication;
import org.seu.acetec.mes2Koala.application.FTLotOptionLogApplication;
import org.seu.acetec.mes2Koala.application.FTNodeApplication;
import org.seu.acetec.mes2Koala.application.FTProcessApplication;
import org.seu.acetec.mes2Koala.application.FTQDNApplication;
import org.seu.acetec.mes2Koala.application.ProductionScheduleApplication;
import org.seu.acetec.mes2Koala.application.ReelDiskApplication;
import org.seu.acetec.mes2Koala.core.common.BeanUtils;
import org.seu.acetec.mes2Koala.core.domain.CustomerFTLot;
import org.seu.acetec.mes2Koala.core.domain.EQC;
import org.seu.acetec.mes2Koala.core.domain.FTBakingNode;
import org.seu.acetec.mes2Koala.core.domain.FTComposedTestNode;
import org.seu.acetec.mes2Koala.core.domain.FTFinishNode;
import org.seu.acetec.mes2Koala.core.domain.FTGuTestNode;
import org.seu.acetec.mes2Koala.core.domain.FTIQCNode;
import org.seu.acetec.mes2Koala.core.domain.FTLot;
import org.seu.acetec.mes2Koala.core.domain.FTNode;
import org.seu.acetec.mes2Koala.core.domain.FTPassNode;
import org.seu.acetec.mes2Koala.core.domain.FTProcess;
import org.seu.acetec.mes2Koala.core.domain.FTProductionSchedule;
import org.seu.acetec.mes2Koala.core.domain.FTQDN;
import org.seu.acetec.mes2Koala.core.domain.FTResult;
import org.seu.acetec.mes2Koala.core.domain.FTRuncard;
import org.seu.acetec.mes2Koala.core.domain.FTTest;
import org.seu.acetec.mes2Koala.core.domain.ReelDisk;
import org.seu.acetec.mes2Koala.core.domain.SBL;
import org.seu.acetec.mes2Koala.core.enums.FTNodeState;
import org.seu.acetec.mes2Koala.core.enums.SBLQuality;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

@Named
@Transactional
public class FTLotNodeOperationApplicationImpl implements
		FTLotNodeOperationApplication {

	@Inject
	FTLotApplication ftLotApplication;

	@Inject
	CustomerFTLotApplication customerFTLotApplication;

	@Inject
	FTProcessApplication ftProcessApplication;

	@Inject
	FTLotOptionLogApplication ftLotOptionLogApplication;

	@Inject
	FTNodeApplication ftNodeApplication;

	@Inject
	ReelDiskApplication reelDiskApplication;

	@Inject
	FTQDNApplication ftQDNApplication;

	@Inject
	private ProductionScheduleApplication productionScheduleApplication;

	@Override
	public void hold(Long ftLotId) {

	}

	@Override
	public void unhold(Long ftLotId) {

	}

	@Override
	public void separate(Long ftLotId, Long[] separateQuantities) {
		FTLot current = ftLotApplication.get(ftLotId);

		// 检查分批数量合法性
		long quantity = current.getQty();
		for (Long separateQuantity : separateQuantities) {
			if (separateQuantity < 0) {
				throw new RuntimeException("非法的分批数量");
			}
			quantity -= separateQuantity;
		}
		if (quantity != 0) {
			throw new RuntimeException("非法的分批数量");
		}

		// 开始分批
		CustomerFTLot currentCustomerFTLot = current.getCustomerFTLot();
		String parentLotNumber = current.getInternalLotNumber();
		quantity = current.getQty();
		for (int index = 0; index < separateQuantities.length; index++) {
			FTLot child = new FTLot();
			BeanUtils.copyProperties(current, child, "id", "version");
			child.setRcNumber(customerFTLotApplication
					.getExpectedRCNumber(currentCustomerFTLot.getId()));
			child.setInternalLotNumber(parentLotNumber + "-" + (index + 1));
			child.setCustomerFTLot(currentCustomerFTLot);
			child.setQty(quantity - separateQuantities[index]);
			child.setParentSeparationId(current.getId());
			FTRuncard ftRuncard = new FTRuncard();
			BeanUtils.copyProperties(current.getFtRuncard(), ftRuncard, "id", "version");
			ftLotApplication.create(child);
			ftRuncard.setFtLot(child);
			ftRuncard.save();
			ftProcessApplication
					.createFTProcess(child.getId(), current.getId()); // 创建FTProcess
		}
		current.setLogic(1);
		current.setQty(0L);
		ftLotApplication.update(current);
	}

	@Override
	public void integrate(Long[] ftLotIds) {
		if (ftLotIds.length <= 0)
			return;
		List<FTLot> currentFTLots = new ArrayList<>();

		// 检查合批的合法性
		Long parentSeparationId = null;
		for (int index = 0; index < ftLotIds.length; index++) {
			FTLot currentFTLot = ftLotApplication.get(ftLotIds[index]);
			if (index == 0) {
				parentSeparationId = currentFTLot.getParentSeparationId();
				if (parentSeparationId == null)
					throw new RuntimeException("母批不同不能合批");
			} else {
				if (!Objects.equals(parentSeparationId,
						currentFTLot.getParentSeparationId()))
					throw new RuntimeException("母批不同不能合批");
			}
			currentFTLots.add(currentFTLot);
		}

		// 收集数量并更新状态
		long quantity = 0;
		for (FTLot currentFTLot : currentFTLots) {
			quantity += currentFTLot.getQty();
			currentFTLot.setLogic(1);
			currentFTLot.setQty(0L);
		}
		ftLotApplication.updateAll(currentFTLots);

		FTLot parent = ftLotApplication.get(parentSeparationId);
		CustomerFTLot parentCustomerFTLot = parent.getCustomerFTLot();
		FTLot child = new FTLot();
		BeanUtils.copyProperties(parent, child, "id", "version");
		child.setRcNumber(customerFTLotApplication
				.getExpectedRCNumber(parentCustomerFTLot.getId()));
		child.setInternalLotNumber(parent.getInternalLotNumber());
		child.setParentIntegrationIds(Joiner.on(",").join(ftLotIds));
		child.setQty(quantity);
		
		FTRuncard ftRuncard = new FTRuncard();
		BeanUtils.copyProperties(parent.getFtRuncard(), ftRuncard, "id", "version");
		ftLotApplication.create(child);
		ftRuncard.setFtLot(child);
		ftRuncard.save();
		ftProcessApplication.createFTProcess(child.getId(), ftLotIds[0]); // 根据第一个FTLot创建FTProcess
	}

	@Override
	public void end(Long processId, FTNode ftNodeForLog) {

		FTProcess process = ftProcessApplication.get(processId); // 找到流程
		FTLot ftLot = process.getFtLot();
		if (Objects.equals(ftLot.getHoldState(), FTLot.HOLD_STATE_HOLD)) {
			throw new RuntimeException("出站失败：开HOLD");
		}

		// 3. 出站更新信息
		List<FTNode> ftNodes = process.getFtNodes();
		Collections.sort(ftNodes);
		// 获取future Hold 节点名称
		String futureHoldNodes = this.futureHoldNodes(ftLot.getId());
		for (int index = 0; index < ftNodes.size(); index++) {
			FTNode ftNode = ftNodes.get(index);
			switch (ftNode.getState()) {
			case ENDED: // 已经出站的跳过
				break;
			case STARTED: // 进站了没出站
				// EQC检查，EQC没有维护数量时系统出站不会做数量卡控
				if (ftLot.getQty() != 0 && !checkEQC(ftNode, ftLot.getQty()))
					throw new RuntimeException("EQC数量错误");
				if (!checkBakingLimit(ftNode))
					throw new RuntimeException("烤箱限制时间未到");
				int results[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0 };
				// 良率卡控
				if (!checkYield(ftNode, results)) {
					String str = "";
					for (int i = 0; i < 20; i++) {
						if (results[i] == 0)
							break;
						str += results[i] + " ";
					}
					if (str.equals(""))
						throw new RuntimeException("总数<1");
					else
						throw new RuntimeException("Bin" + str + "良率未达标!");
				}

				ftNode.setState(FTNodeState.ENDED); // 更新状态为已经出站
				// 更新当前站点的下一个站点为等待入站
				if (index != ftNodes.size() - 1) {
					FTNode nextFTNode = ftNodes.get(index + 1);
					// 如果当下一站点是future hold站点设置lot状态hold
					if (futureHoldNodes.indexOf(nextFTNode.getName()) > -1) {
						nextFTNode.setState(FTNodeState.TO_START);
						ftLot.setCurrentState("Hold");
						ftLot.setHoldState(FTLot.HOLD_STATE_HOLD);
					} else {
						nextFTNode.setState(FTNodeState.TO_START); // 等待入站
						String nodeName2 = nextFTNode.getName();
						String nodeStateDescription = "待"
								+ (nodeName2.startsWith("Test-") ? nodeName2
										.substring(5) : nodeName2);
						ftLot.setCurrentState(nodeStateDescription);
					}
				} else {
					String nodeStateDescription = "已经完成";
					ftLot.setCurrentState(nodeStateDescription);
				}

				updateExtraOnEndFTNode(ftLot, ftNode, ftNodes);
				ftLotOptionLogApplication.createEndFTNode(ftLot, ftNode,
						ftNodeForLog);
				ftLotApplication.update(ftLot);
				ftProcessApplication.update(process);// 级联
				return;
			default: // 其他状态不合法
				throw new RuntimeException("出站失败：非法状态");
			}
		}
	}

	private boolean checkBakingLimit(FTNode ftNode) {
		if (ftNode instanceof FTBakingNode) {
			FTBakingNode ftBakingNode = (FTBakingNode) ftNode;
			
			String timeLimitString = ftBakingNode.getTimeLimit();
			if ( timeLimitString != null && !"".equals(timeLimitString) ){
				try {
					Date timeOut = new Date(); // 当前时间
					Date timeIn = ftBakingNode.getTimeIn();
					long milliSeconds = (long) (Double.parseDouble(timeLimitString) * 3600 * 1000);
					long diffMilliSeconds = timeOut.getTime() - timeIn.getTime();
					if (diffMilliSeconds < milliSeconds) {  // 未到达限制时间
						return false;
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException("限制时间格式错误");
				}
			}
		}
		return true;
	}

	@Override
	public void endFail(Long processId, FTProcess ftProcessForLog) {

		FTProcess process = ftProcessApplication.get(processId); // 找到流程
		FTLot ftLot = process.getFtLot();
		if (Objects.equals(ftLot.getHoldState(), FTLot.HOLD_STATE_HOLD)) {
			throw new RuntimeException("出站失败：开HOLD");
		}

		// 3. 出站更新信息
		List<FTNode> ftNodes = process.getFtNodes();
		Collections.sort(ftNodes);

		for (int index = 0; index < ftNodes.size(); index++) {
			FTNode ftNode = ftNodes.get(index);
			switch (ftNode.getState()) {
			case ENDED: // 已经出站的跳过
				break;
			case STARTED: // 进站了没出站

				// 良率卡控
				// if (!checkYield(ftNode)) {
				// return InvokeResult.failure("出站失败：良率卡控！");
				// }

				// 非Test站不得良率放行
				if (!ftNode.getName().startsWith("Test-")) {
					throw new RuntimeException("出站失败：非Test站不得良率放行");
				}

				ftNode.setState(FTNodeState.ENDED); // 更新状态为已经出站

				// 更新当前站点的下一个站点为等待入站
				if (index != ftNodes.size() - 1) {
					FTNode nextFTNode = ftNodes.get(index + 1);
					nextFTNode.setState(FTNodeState.TO_START); // 等待入站
					String nodeName2 = nextFTNode.getName();
					String nodeStateDescription = "待"
							+ (nodeName2.startsWith("Test-") ? nodeName2
									.substring(5) : nodeName2);
					ftLot.setCurrentState(nodeStateDescription);
				} else {
					String nodeStateDescription = "已经完成";
					ftLot.setCurrentState(nodeStateDescription);
				}

				// 良率放行不更新字段，已由保存和出站时更新
				// updateExtraOnEndFTNode(ftLot, ftNode, ftNodes);

				ftLotOptionLogApplication.createEndFailTestNode(ftLot, ftNode,
						ftProcessForLog);
				ftLotApplication.update(ftLot);
				ftProcessApplication.update(process); // 级联
				return;
			default: // 其他状态不合法
				throw new RuntimeException("出站失败：非法状态");
			}
		}
	}

	@Override
	public void start(Long processId, FTProcess ftProcess) {

		FTLot ftLot = ftLotApplication.findByProcessId(processId);
		if (Objects.equals(ftLot.getHoldState(), FTLot.HOLD_STATE_HOLD)) {
			throw new RuntimeException("进站失败：开HOLD");
		}
		// 找到等待进站的节点
		List<FTNode> toStart = ftNodeApplication
				.findToStartFTNodeByFTLotId(ftLot.getId());
		if (toStart.size() != 1) {
			throw new RuntimeException("进站失败：未找到待进站站点");
		}
		FTNode toStartFTNode = toStart.get(0);

		toStartFTNode.setState(FTNodeState.STARTED); // 更新状态为已经进站
		String nodeName = toStartFTNode.getName();
		nodeName = nodeName.startsWith("Test-") ? nodeName.substring(5)
				: nodeName; // 测试节点特殊考虑
		String nodeStateDescription = "正在" + nodeName;

		// 进入packing时将ReelDisk状态改为中转库
		updateExtraOnStartFTNode(ftLot.getId(), toStartFTNode);

		// TODO: 事务性
		ftLot.setCurrentState(nodeStateDescription);
		ftLotApplication.update(ftLot);
		ftNodeApplication.update(toStartFTNode);

		// 操作记录
		ftLotOptionLogApplication.createStartFTNode(ftLot, toStartFTNode,
				ftProcess);
	}

	/**
	 * 出站时更新小样信息
	 *
	 * @param ftLot
	 * @param ftNode
	 */
	private void updateExtraOnEndFTNode(FTLot ftLot, FTNode ftNode,
			List<FTNode> ftNodes) {

		Set<Class<?>> nodeThatNeedsOther = new HashSet<>();
		nodeThatNeedsOther.add(FTIQCNode.class);
		nodeThatNeedsOther.add(FTBakingNode.class);
		nodeThatNeedsOther.add(FTGuTestNode.class);

		if (nodeThatNeedsOther.contains(ftNode.getClass())) { // Testing站点出站保前品质有other
			// 统计所有pass品的bin数目和
			int other = 0;
			List<SBL> sbls = ftNode.getSbls();
			FTResult ftResult = ftNode.getResult();
			int[] ftResultBins = FTResult.getBins(ftResult);
			for (SBL sbl : sbls) {
				// bin下标
				String binType = sbl.getType();
				binType = binType.startsWith("Bin") ? binType.substring(3)
						: binType;
				int binValue = ftResultBins[Integer.parseInt(binType) - 1];
				if (binValue == -1)
					continue;
				if (Objects.equals(SBLQuality.PASS, sbl.getQuality())) {
					other += binValue; // 统计pass
				}
			}
			ftLot.setOther(other);

		} else if (ftNode instanceof FTComposedTestNode) { // Testing出站后品质有pass,
															// fail, lat

			List<SBL> sbls = ftNode.getSbls();
			FTResult finalYield = ftNode.getResult();

			// 如果Testing站点数据保存但是未生成ReelCode，数量将就根据品质到FinalYield中捞取

			// 如果Testing站点数据未保存，则只有other品质有数量，就是当前批次的总数量

			// pass:final yield测试过之后的pass数量
			// fail：final yield测试过程中的Fail品数量
			// final yield中LAT的数量
			int pass = 0;
			int fail = 0;
			int lat = Integer.parseInt(finalYield.getLat());
			int[] finalYieldBins = FTResult.getBins(finalYield);
			for (SBL sbl : sbls) {
				// bin下标
				String binType = sbl.getType();
				binType = binType.startsWith("Bin") ? binType.substring(3)
						: binType;
				int binIndex = Integer.parseInt(binType) - 1;

				int binValue = finalYieldBins[binIndex];
				if (binValue == -1)
					continue;
				if (Objects.equals(SBLQuality.PASS, sbl.getQuality())) {
					pass += binValue; // 统计pass
				} else if (Objects.equals(SBLQuality.FAIL, sbl.getQuality())) {
					fail += binValue; // 统计fail
				}
			}
			ftLot.setPass(pass);
			ftLot.setFail(fail);
			ftLot.setLat(lat);

			// 结束排产任务
			List<FTProductionSchedule> ftProductionSchedules = ((FTComposedTestNode) ftNode)
					.getFtProductionSchedules();
			for (FTProductionSchedule ftProductionSchedule : ftProductionSchedules) {
				productionScheduleApplication.endTesting(ftProductionSchedule
						.getId());
			}
		}
	}

	/**
	 * 每个测试站点出站时系统做卡控，如果良率不达标系统自动HOLD住，finish站点不做卡控 良率卡控，目前只要卡控finalYield
	 * 判断某个测试节点是否通过良率卡控
	 *
	 * @param ftNode
	 *            当前站点
	 * @return 是否通过
	 */

	private boolean checkYield(FTNode ftNode, int[] strs) {

		// 只有测试节点才需要卡控
		String nodeName = ftNode.getName();
		if (Strings.isNullOrEmpty(nodeName) || !nodeName.startsWith("Test-")) {
			return true;
		}

		if (!(ftNode instanceof FTComposedTestNode)) {
			return true;
		}
		FTComposedTestNode ftComposedTestNode = (FTComposedTestNode) ftNode;

		// 不作良率卡控
		List<SBL> sbls = ftComposedTestNode.getSbls();
		if (sbls.size() == 0)
			return true;

		FTResult finalYield = ftComposedTestNode.getResult();

		// 获取所有bins结果
		int[] bins = FTResult.getBins(finalYield);

		// 获取总量
		int total = Integer.parseInt(finalYield.getResultSum());
		if (total < 1) {
			return false;
		}

		int index = 0;
		for (SBL sbl : sbls) {
			// 节点名称要对应正确
			String binType = sbl.getType();
			binType = binType.startsWith("Bin") ? binType.substring(3)
					: binType;
			int binIndex = Integer.parseInt(binType) - 1;

			if (bins[binIndex] == -1) {
				// Unexpected
				continue;
			}

			// 计算bin的Yield
			double binYield = bins[binIndex] * 1.0 / total * 100;
			double lowerLimit = sbl.getLowerLimit();
			double upperLimit = sbl.getUpperLimit();

			// 判断良率
			if (binYield < lowerLimit || binYield > upperLimit) {
				strs[index] = binIndex + 1;
				index++;
			}
		}
		if (strs[0] != 0)
			return false;
		return true;
	}

	/**
	 * 每个测试站点出站时系统做卡控，如果良率不达标系统自动HOLD住，finish站点不做卡控 良率卡控，目前只要卡控EQC
	 * 判断某个测试节点是否通过eqc检查
	 *
	 * @param ftNode
	 *            当前站点
	 * @return 是否通过
	 */

	private boolean checkEQC(FTNode ftNode, Long qty) {
		String nodeName = ftNode.getName();
		if (Strings.isNullOrEmpty(nodeName) || !nodeName.startsWith("Test-")) {
			return true;
		}
		if (!(ftNode instanceof FTComposedTestNode)) {
			return true;
		}
		FTComposedTestNode ftComposedTestNode = (FTComposedTestNode) ftNode;
		
		List<EQC> eqcs = ftComposedTestNode.getEqcs();
		for (EQC eqc : eqcs) {
			if (eqc.getLowerLimit() <= qty && eqc.getUpperLimit() >= qty) {
				List<FTTest> tests = ftComposedTestNode.getTests();
				for (FTTest fttest : tests) {
					if (fttest.getName().startsWith("EQC")) {
						String resultSum = fttest.getResult().getResultSum();
						int sum = Integer.parseInt(resultSum);
						//如果总数为0，则不做卡控
						if ( sum == 0 )
							return true;
						if (sum != eqc.getQty())
							return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * @param ftNode
	 */
	private void updateExtraOnStartFTNode(Long ftLotId, FTNode ftNode) {

		// 进入Packing站点更新数据
		if ((ftNode instanceof FTPassNode)
				&& ftNode.getName().startsWith("Packing")) {
			List<ReelDisk> reelDisks = reelDiskApplication
					.findReelDisksByFTLotId(ftLotId);
			for (ReelDisk reelDisk : reelDisks) {
				if (reelDisk != null)
					reelDisk.setStatus("在库");
			}
			reelDiskApplication.updateAll(reelDisks);
			// 进入Finish更新站点数据
		} else if ((ftNode instanceof FTFinishNode)
				&& ftNode.getName().startsWith("Finish")) {
			/*
			 * FTFinishNode ftFinishNode = (FTFinishNode) ftNode; int[]
			 * finishBins = FTResult.emptyBins(); List<FTNode>
			 * ftComposedTestNodes =
			 * ftNodeApplication.findFTCompostedTestNodesAscByFTLotId(ftLotId);
			 * for (FTNode ftComposedTestNode : ftComposedTestNodes) { int[]
			 * binValues1 = FTResult.getBins(ftComposedTestNode.getResult()); //
			 * FinalYield of every node for (int binIndex = 0; binIndex < 20;
			 * binIndex++) { int binValue = binValues1[binIndex]; if (binValue
			 * != -1) { finishBins[binIndex] = finishBins[binIndex] == -1 ? 0 :
			 * finishBins[binIndex]; finishBins[binIndex] += binValue; } } }
			 * FTResult.setBins(ftFinishNode.getResult(), finishBins);
			 */
		} else if (ftNode instanceof FTComposedTestNode) {
			List<FTProductionSchedule> ftProductionSchedules = ((FTComposedTestNode) ftNode)
					.getFtProductionSchedules();
			for (FTProductionSchedule ftProductionSchedule : ftProductionSchedules) {
				productionScheduleApplication.startTesting(ftProductionSchedule
						.getId());
			}
		}
	}

	private String futureHoldNodes(Long lotId) {
		// 检查是否被Future Hold
		List<FTQDN> qdnList = ftQDNApplication
				.find("select o from FTQDN o where o.status <> 2 and "
						+ "o.isFuture = true and o.ftLot.id = " + lotId);
		String futureHoldNode = "";
		for (FTQDN ftQDN : qdnList) {
			futureHoldNode += ftQDN.getFlowNow();
		}
		return futureHoldNode;
	}

}