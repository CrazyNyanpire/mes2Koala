package org.seu.acetec.mes2Koala.application.impl;

import com.google.common.base.Joiner;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.seu.acetec.mes2Koala.application.CPLotApplication;
import org.seu.acetec.mes2Koala.application.CPLotNodeOperationApplication;
import org.seu.acetec.mes2Koala.application.CPLotOptionLogApplication;
import org.seu.acetec.mes2Koala.application.CPNodeApplication;
import org.seu.acetec.mes2Koala.application.CPProcessApplication;
import org.seu.acetec.mes2Koala.application.CPQDNApplication;
import org.seu.acetec.mes2Koala.application.CPSBLTemplateApplication;
import org.seu.acetec.mes2Koala.application.CPWaferApplication;
import org.seu.acetec.mes2Koala.application.ProductionScheduleApplication;
import org.seu.acetec.mes2Koala.application.TskInfoApplication;
import org.seu.acetec.mes2Koala.application.WMSClientApplication;
import org.seu.acetec.mes2Koala.application.bean.SaveBaseBean;
import org.seu.acetec.mes2Koala.core.common.BeanUtils;
import org.seu.acetec.mes2Koala.core.domain.CPLot;
import org.seu.acetec.mes2Koala.core.domain.CPLotOptionLog;
import org.seu.acetec.mes2Koala.core.domain.CPNode;
import org.seu.acetec.mes2Koala.core.domain.CPProcess;
import org.seu.acetec.mes2Koala.core.domain.CPProductionSchedule;
import org.seu.acetec.mes2Koala.core.domain.CPQDN;
import org.seu.acetec.mes2Koala.core.domain.CPSBLTemplate;
import org.seu.acetec.mes2Koala.core.domain.CPStorage;
import org.seu.acetec.mes2Koala.core.domain.CPStorageWafer;
import org.seu.acetec.mes2Koala.core.domain.CPTestingNode;
import org.seu.acetec.mes2Koala.core.domain.CPWaferCheckLog;
import org.seu.acetec.mes2Koala.core.domain.CustomerCPLot;
import org.seu.acetec.mes2Koala.core.domain.CPWafer;
import org.seu.acetec.mes2Koala.core.domain.TskInfo;
import org.seu.acetec.mes2Koala.core.enums.CPNodeState;
import org.seu.acetec.mes2Koala.core.enums.CPWaferCheck;
import org.seu.acetec.mes2Koala.core.enums.CPWaferState;
import org.seu.acetec.mes2Koala.core.enums.StorageType;
import org.seu.acetec.mes2Koala.core.enums.TestTypeForWms;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityNotFoundException;
import javax.swing.JOptionPane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

/**
 * @author yuxiangque
 * @version 2016/3/28
 */
@Named
@Transactional
public class CPLotNodeOperationApplicationImpl implements
		CPLotNodeOperationApplication {

	@Inject
	CPLotApplication cpLotApplication;

	@Inject
	CPProcessApplication cpProcessApplication;

	@Inject
	CPQDNApplication cpQDNApplication;

	@Inject
	CPNodeApplication cpNodeApplication;

	@Inject
	CPLotOptionLogApplication cpLotOptionLogApplication;

	@Inject
	private CPWaferApplication cpWaferApplication;

	@Inject
	WMSClientApplication wmsClientApplication;

	@Inject
	private ProductionScheduleApplication productionScheduleApplication;

	@Inject
	private TskInfoApplication tskInfoApplication;

	@Inject
	private CPSBLTemplateApplication cpSBLTemplateApplication;

	private Timer timer = new Timer();

	private CPLot timerCplot = null;

	@Override
	public void startCPNode(Long processId, SaveBaseBean sbb) {
		CPProcess process = cpProcessApplication.get(processId);
		CPLot cpLot = process.getCpLot();
		timerCplot = cpLot;
		// 更新最后修改时间及修改人
		BeanUtils.copyProperties(sbb, cpLot);
		BeanUtils.copyProperties(sbb, process);
		if (Objects.equals(cpLot.getHoldState(), CPLot.HOLD_STATE_HOLD)) {
			throw new RuntimeException("进站失败：开HOLD");
		}
		List<CPNode> cpNodes = process.getCpNodes();
		Collections.sort(cpNodes);
		// 找到等待进站的节点
		for (CPNode cpNode : cpNodes) {
			switch (cpNode.getState()) {
			case ENDED: // 已经出站的跳过
				break;
			case TO_START: // 到这个站没进站
				process.setNowNode(cpNode);
				cpNode.setState(CPNodeState.STARTED); // 更新状态为已经进站
				String nodeName = cpNode.getName();
				// nodeName = nodeName.startsWith("Test-") ?
				// nodeName.substring(5) : nodeName; // 测试节点特殊考虑
				String nodeStateDescription = "正在" + nodeName;

				//
				updateExtraOnStartCPNode(cpLot, cpNode, cpNodes);

				// 事务性
				cpLot.setCurrentState(nodeStateDescription);

				cpLotApplication.update(cpLot);
				BeanUtils.copyProperties(sbb, cpNode);
				cpNodeApplication.update(cpNode);
				this.cpProcessApplication.update(process);
				// 操作记录
				cpLotOptionLogApplication.createStartCPNode(cpLot, cpNode);
				return;
			default:
				break; // 其他状态不合法
			}
		}
		throw new RuntimeException("进站失败：非法状态");
	}

	private void updateExtraOnStartCPNode(CPLot cpLot, CPNode cpNode,
			List<CPNode> cpNodes) {
		if (cpNode instanceof CPTestingNode
				|| cpNode.getName().indexOf("CP") > -1) {
			CPTestingNode cpTestingNode = CPTestingNode.get(
					CPTestingNode.class, cpNode.getId());
			List<CPProductionSchedule> cpProductionSchedules = cpTestingNode
					.getCpProductionSchedules();
			if (cpProductionSchedules == null
					|| cpProductionSchedules.size() < 1) {
				throw new RuntimeException("没有排产不能进入测试站点！");
			}
			for (CPProductionSchedule cpProductionSchedule : cpProductionSchedules) {
				productionScheduleApplication.startTesting(cpProductionSchedule
						.getId());
			}
		}
	}

	@Override
	public void saveCPNode(Long processId, CPNode cpNode) {
		throw new UnsupportedOperationException("Todo");
	}

	@Override
	public void endCPNode(Long processId, SaveBaseBean sbb) {
		this.endCPNode(processId, true, null, sbb);
	}

	@Override
	public void endCPNodeIncoming(Long processId, JSONArray wafers,
			SaveBaseBean sbb) {
		this.endCPNode(processId, true, wafers, sbb);
	}

	public void endCPNode(Long processId, boolean checkWafer, JSONArray wafers,
			SaveBaseBean sbb) {
		CPProcess process = cpProcessApplication.get(processId); // 找到流程
		CPLot cpLot = process.getCpLot();
		BeanUtils.copyProperties(sbb, cpLot);
		BeanUtils.copyProperties(sbb, process);
		if (Objects.equals(cpLot.getHoldState(), CPLot.HOLD_STATE_HOLD)) {
			throw new RuntimeException("出站失败：开HOLD");
		}

		// 3. 出站更新信息
		List<CPNode> cpNodes = process.getCpNodes();
		Collections.sort(cpNodes);

		// 获取future Hold 节点名称
		String futureHoldNodes = "";
		if (cpLot.getIsFuture() != null && cpLot.getIsFuture()) {
			futureHoldNodes = cpLot.getFutureFlow();
		}
		for (int index = 0; index < cpNodes.size(); index++) {
			// CPNode cpNode = cpNodes.get(index);
			CPNode cpNode = cpLot.getCpProcess().getCpNodes().get(index);
			switch (cpNode.getState()) {
			case ENDED: // 已经出站的跳过
				break;
			case STARTED: // 进站了没出站

				// 良率卡控
				// if (!checkYield(cpNode)) {
				// throw new RuntimeException("出站失败：良率卡控");
				// }

				cpNode.setState(CPNodeState.ENDED); // 更新状态为已经出站
				// 操作日志
				cpLotOptionLogApplication.createEndCPNode(cpLot, cpNode);
				// 更新当前站点的下一个站点为等待入站
				if (index != cpNodes.size() - 1) {
					CPNode nextCPNode = cpNodes.get(index + 1);
					nextCPNode.setState(CPNodeState.TO_START); // 等待入站
					String nodeName2 = nextCPNode.getName();
					String nodeStateDescription = "待"
							+ (nodeName2.startsWith("Test-") ? nodeName2
									.substring(5) : nodeName2);
					cpLot.setCurrentState(nodeStateDescription);

				} else {
					String nodeStateDescription = "已经完成";
					cpLot.setCurrentState(nodeStateDescription);
				}
				List<CPWafer> cpWafers = cpWaferApplication.find(
						"select o from CPWafer o where o.cpLot.id = ?",
						cpLot.getId());
				// 检查所有的wafer都通过本站,Incoming除外
				if (checkWafer
						&& !cpNode.getName().equalsIgnoreCase("Incoming")) {
					this.checkCpWaferAllPassed(cpWafers, cpNode.getName());
				}

				boolean updateWaferInfo = false;
				if (wafers != null) {
					updateWaferInfo = true;
				}
				// 变更wafer的状态：未通过，未抽检
				for (CPWafer cpWafer : cpWafers) {
					if (updateWaferInfo) {
						String waferCode = this.getWaferInfo(wafers, cpWafer
								.getCpCustomerWafer().getWaferIndex());
						if (waferCode != null) {
							cpWafer.setInternalWaferCode(waferCode);
						}
					}
					this.saveCheckLog(cpWafer, cpNode);
					cpWafer.setState(CPWaferState.UNPASS);
					cpWafer.setCpWaferCheck(CPWaferCheck.UNCHECKED);
					BeanUtils.copyProperties(sbb, cpWafer);
					cpWafer.save();
				}
				// 如果当下一站点是future hold站点设置lot状态hold
				if (cpNodes.size() - 1 > index) {
					CPNode nextCPNode = cpNodes.get(index + 1);
					if (futureHoldNodes.indexOf(nextCPNode.getName()) > -1) {
						cpLot.setCurrentState("Hold");
						cpLot.setHoldState(CPLot.HOLD_STATE_HOLD);
					}
					BeanUtils.copyProperties(sbb, nextCPNode);
					process.setNowNode(nextCPNode);
				}
				// cpLotOptionLogApplication.createEndCPNode(cpLot, cpNode);
				updateExtraOnEndCPNode(cpLot, cpNode, cpNodes);
				cpLotApplication.update(cpLot);
				cpProcessApplication.update(process);// 级联

				// Eva 2016-4-19 Packing出站完成之后，WMS添加入库申请
				// harlow 2016-5-30 OQC出站完成之后，WMS添加入库申请
				if (cpNode.getName().toUpperCase().equals("OQC")) {
					CPStorage cpstorage = ConvertToCPStorage(cpLot, cpWafers);
					JSONObject obj = JSONObject.fromObject(cpstorage);
					String lotjson = "[" + obj.toString() + "]";
					wmsClientApplication.mfgInWMS(
							TestTypeForWms.getStringValue(TestTypeForWms.CP),
							StorageType.getValue(StorageType.FINISH_STORAGE),
							lotjson);
				}
				// Hongyu 2016/07/18 3360平台出站tskInfo和SBl卡控
				if (cpNode.getName().startsWith("CP")) {
					CPTestingNode cpTestingNode = CPTestingNode.get(
							CPTestingNode.class, cpNode.getId());
					if (cpTestingNode.getTestProgram().getTestSys()
							.contains("3360")) {
						List<TskInfo> tskInfoList = tskInfoApplication
								.find("select o from TskInfo o where o.lotNum like ? and o.testSite like ? order by o.waferId",
										cpLot.getInternalLotNumber(),
										cpNode.getName());
						List<CPSBLTemplate> cpSBLTemplates = cpSBLTemplateApplication
								.findByInternalProductId(cpLot
										.getCustomerCPLot().getCpInfo().getId());
						for (int p = 0; p < cpSBLTemplates.size(); p++) {
							if (cpSBLTemplates.get(0) == null) {
								throw new RuntimeException("请先维护SBL信息");
							}
							if ("by wafer".equals(cpSBLTemplates.get(p)
									.getTestRange())) {
								for (int q = 0; q < tskInfoList.size(); q++) {
									String yield = tskInfoList
											.get(q)
											.getYield()
											.substring(
													0,
													tskInfoList.get(q)
															.getYield()
															.indexOf("%"));
									double yieldDouble = Double
											.parseDouble(yield);
									if (cpSBLTemplates.get(p).getLowerLimit() > yieldDouble) {
										throw new RuntimeException("出站失败：wafer"
												+ tskInfoList.get(q)
														.getWaferId() + "良率过低");
									}
								}
							} else if ("by lot".equals(cpSBLTemplates.get(p)
									.getTestRange())) {
								int sumTotalDice = 0;
								int sumPassDice = 0;
								for (int q = 0; q < tskInfoList.size(); q++) {
									sumTotalDice += tskInfoList.get(q)
											.getTotalDice();
									sumPassDice += tskInfoList.get(q)
											.getPassDice();
								}
								if (cpSBLTemplates.get(p).getLowerLimit() > (double) sumPassDice
										/ sumTotalDice) {
									throw new RuntimeException(
											"出站失败：by lot卡控良率过低");
								}
							}
						}
					}
				}
				return;

			case UNREACHED:
				break;
			default: // 其他状态不合法
				throw new RuntimeException("出站失败：非法状态");
			}
		}
	}

	private void saveCheckLog(CPWafer cpWafer, CPNode cpNode) {
		if (cpWafer.getCpWaferCheck() != null
				&& cpWafer.getCpWaferCheck().ordinal() == CPWaferCheck.CHECKED
						.ordinal()) {
			CPWaferCheckLog cpWaferCheckLog = new CPWaferCheckLog();
			cpWaferCheckLog.setCpWafer(cpWafer);
			cpWaferCheckLog.setCpLot(cpWafer.getCpLot());
			cpWaferCheckLog.setNode(cpNode.getName());
			cpWaferCheckLog.setCpNode(cpNode);
			cpWaferCheckLog.setCreateTimestamp(new Date());
			cpWaferCheckLog.setLastModifyTimestamp(cpWaferCheckLog
					.getCreateTimestamp());
			cpWaferCheckLog.save();
		}
	}

	private void updateExtraOnEndCPNode(CPLot cpLot, CPNode cpNode,
			List<CPNode> cpNodes) {
		CPTestingNode cpTestingNode;
		try {
			cpTestingNode = CPTestingNode.get(CPTestingNode.class,
					cpNode.getId());
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			cpTestingNode = null;
		}
		if (cpNode instanceof CPTestingNode || cpTestingNode != null) {
			List<CPProductionSchedule> cpProductionSchedules = cpTestingNode
					.getCpProductionSchedules();
			for (CPProductionSchedule cpProductionSchedule : cpProductionSchedules) {
				productionScheduleApplication.endTesting(cpProductionSchedule
						.getId());
			}
		}
	}

	@SuppressWarnings("unchecked")
	private String getWaferInfo(JSONArray wafers, String index) {
		for (JSONObject jsonObject : (List<JSONObject>) JSONArray.toCollection(
				wafers, JSONObject.class)) {
			if (Integer.valueOf(jsonObject.getString("index").trim()) == Integer
					.valueOf(index.trim())) {
				return jsonObject.getString("waferCode");
			}
		}
		return null;
	}

	private String futureHoldNodes(Long lotId) {
		// 检查是否被Future Hold
		List<CPQDN> qdnList = cpQDNApplication
				.find("select o from CPQDN o where o.status <> 2 and "
						+ "o.isFuture = true and o.cpLot.id = " + lotId);
		String futureHoldNode = "";
		for (CPQDN cpQDN : qdnList) {
			futureHoldNode += cpQDN.getFlowNow();
		}
		return futureHoldNode;
	}

	/**
	 * 检查所有的wafer都通过本站
	 * 
	 * @param cpWafers
	 * @throws RuntimeException
	 */
	private void checkCpWaferAllPassed(List<CPWafer> cpWafers, String node)
			throws RuntimeException {
		int checkIndex = 0;
		for (CPWafer cpWafer : cpWafers) {
			if (CPWaferState.getIntValue(CPWaferState.UNPASS) == CPWaferState
					.getIntValue(cpWafer.getState())) {
				throw new RuntimeException("出站失败：wafer"
						+ cpWafer.getInternalWaferCode() + "没有通过本站");
			}
			if (cpWafer.getCpWaferCheck().ordinal() == CPWaferCheck.CHECKED
					.ordinal()) {
				checkIndex++;
			}
		}
		if ("IQC".equalsIgnoreCase(node) || "FQC".equalsIgnoreCase(node)) {
			if (cpWafers.size() <= 5 && checkIndex != cpWafers.size()) {
				throw new RuntimeException("批次小于等于5片必须全检！");
			} else if (cpWafers.size() > 5 && checkIndex < 5) {
				throw new RuntimeException("批次大于5片必须至少抽检5片！");
			}
		}
	}

	@Override
	public void jumpCPNode(Long processId, CPNode cpNodeBase, String targetNode) {
		CPProcess process = cpProcessApplication.get(processId); // 找到流程
		CPLot cpLot = process.getCpLot();
		if (Objects.equals(cpLot.getHoldState(), CPLot.HOLD_STATE_HOLD)) {
			throw new RuntimeException("跳站失败：开HOLD");
		}
		String futureHoldNode = this.futureHoldNodes(cpLot.getId());
		// 跳站更新信息
		List<CPNode> cpNodes = process.getCpNodes();
		Collections.sort(cpNodes);
		int targetNodeTurn = 0;
		for (int i = 0; i < cpNodes.size(); i++) {
			if (targetNode.equals(cpNodes.get(i).getName())) {
				targetNodeTurn = cpNodes.get(i).getTurn();
			}
		}
		for (int index = 0; index < cpNodes.size(); index++) {
			CPNode cpNode = cpNodes.get(index);
			int turn = cpNodes.get(index).getTurn();
			switch (cpNode.getState()) {
			case ENDED: // 已经出站的跳过
				break;
			case TO_START: // 到这个站没进站
				if (turn > targetNodeTurn) {
					throw new RuntimeException("跳站失败：请选择后续站点进行跳站");
				} else if (turn == targetNodeTurn) {
					throw new RuntimeException("跳站失败：目标站点可直接进站");
				}
				if (!"".equals(futureHoldNode)) {
					for (int current = index; current < cpNodes.size(); current++) {
						if (futureHoldNode.contains(cpNodes.get(current)
								.getName())
								&& targetNodeTurn >= cpNodes.get(current)
										.getTurn()) {
							throw new RuntimeException("跳站失败：至目标站点间有预HOLD站点！");
						}
					}
				}
				updateExtraOnEndCPNode(cpLot, cpNode, cpNodes);
				cpNode.setState(CPNodeState.UNREACHED); // 更新状态未来到这个站
				CPNode targetCPNode = cpNodes.get(index
						+ (targetNodeTurn - turn));
				targetCPNode.setState(CPNodeState.TO_START); // 更新状态为已经来到这个站未进站
				// 更新目标站点的下一个站点为等待入站
				String nodeName2 = targetCPNode.getName();
				String nodeStateDescription = "待" + nodeName2;
				cpLot.setCurrentState(nodeStateDescription);
				// 操作记录
				CPLotOptionLog cpLotOptionLog = new CPLotOptionLog();
				BeanUtils.copyProperties(cpNodeBase, cpLotOptionLog);
				cpLotOptionLog.setCpLot(cpLot);
				cpLotOptionLog.setOptType("跳站");
				cpLotOptionLog.setRemark("跳站");
				cpLotOptionLog.setFlownow(cpNode.getName());
				cpLotOptionLogApplication.creatCPLotOptionLog(cpLotOptionLog);
				cpLotApplication.update(cpLot);
				cpNodeApplication.update(cpNode);
				cpNodeApplication.update(targetCPNode);
				cpProcessApplication.update(process);// 级联
				return;
			case STARTED: // 未出站
				throw new RuntimeException("跳站失败：请先出站");
			default: // 其他状态不合法
				break;
			}
		}
	}

	@Override
	public void separateCPLot(Long cpLotId, Long[] separateQuantities,
			SaveBaseBean sbb) {
		CPLot parent = cpLotApplication.get(cpLotId);
		Set<CPLot> children = new HashSet<CPLot>();
		Long quantity = parent.getQuantity();
		for (Long separateQuantity : separateQuantities) {
			if (separateQuantity < 0) {
				throw new RuntimeException("分批数量错误");
			}
			quantity -= separateQuantity;
		}
		if (quantity != 0) {
			throw new RuntimeException("分批数量错误");
		}
		for (Long separateQuantity : separateQuantities) {
			CPLot cpLot = new CPLot();
			// TODO 分批逻辑
			cpLot.setQuantity(separateQuantity);
			children.add(cpLot);
		}
		parent.setLogic(1);
		parent.setQuantity(0L);
		cpLotApplication.createAll(children);
	}

	@Override
	public void integrateCPLots(Long[] cpLotIds, SaveBaseBean sbb) {
		if (cpLotIds.length == 0)
			return;

		// 只有由同一个批次分批的批次才能合批
		Set<Long> distinctCPLotIds = new HashSet<>();
		Set<CPLot> cpLots = new HashSet<CPLot>();
		CPLot temp = cpLotApplication.get(cpLotIds[0]);
		Long parentSeparationId = temp.getParentSeparationId();
		cpLots.add(temp);
		for (int index = 1; index < cpLotIds.length; index++) {
			temp = cpLotApplication.get(cpLotIds[index]);
			if (!Objects.equals(temp.getParentSeparationId(),
					parentSeparationId)) {
				throw new RuntimeException("只有由同一个批次分批的批次才能合批");
			}
			// TODO 合批检查
			cpLots.add(temp);
			distinctCPLotIds.add(cpLotIds[index]);
		}

		Long quantity = 0L;
		for (CPLot lot : cpLots) {
			quantity += quantity;
			temp.setLogic(1);
			temp.setQuantity(0L);
		}
		cpLotApplication.updateAll(cpLots);

		CPLot cpLot = new CPLot();
		cpLot.setQuantity(quantity);
		cpLot.setParentIntegrationIds(Joiner.on(",").join(distinctCPLotIds));
		cpLotApplication.create(cpLot);
		cpProcessApplication.createCPProcess(cpLot.getId(), cpLotIds[0]);
	}

	@Override
	public void endFail(Long processId, CPNode cpNodeBase) {
		boolean checkWafer = true;
		CPProcess process = cpProcessApplication.get(processId); // 找到流程
		CPLot cpLot = process.getCpLot();
		if (Objects.equals(cpLot.getHoldState(), CPLot.HOLD_STATE_HOLD)) {
			throw new RuntimeException("良率放行失败：开HOLD");
		}

		// 出站更新信息
		List<CPNode> cpNodes = process.getCpNodes();
		Collections.sort(cpNodes);
		// 获取future Hold 节点名称
		String futureHoldNodes = this.futureHoldNodes(cpLot.getId());
		for (int index = 0; index < cpNodes.size(); index++) {
			CPNode cpNode = cpNodes.get(index);
			switch (cpNode.getState()) {
			case ENDED: // 已经出站的跳过
				break;
			case STARTED: // 进站了没出站

				// 非Test站不得良率放行
				if (!cpNode.getName().startsWith("CP")) {
					throw new RuntimeException("出站失败：非Test站不得良率放行");
				}
				cpNode.setState(CPNodeState.ENDED); // 更新状态为已经出站
				// 更新当前站点的下一个站点为等待入站
				if (index != cpNodes.size() - 1) {
					CPNode nextCPNode = cpNodes.get(index + 1);
					nextCPNode.setState(CPNodeState.TO_START); // 等待入站
					String nodeName2 = nextCPNode.getName();
					String nodeStateDescription = "待" + nodeName2;
					cpLot.setCurrentState(nodeStateDescription);
				} else {
					String nodeStateDescription = "已经完成";
					cpLot.setCurrentState(nodeStateDescription);
				}
				List<CPWafer> cpWafers = cpWaferApplication.find(
						"select o from CPWafer o where o.cpLot.id = ?",
						cpLot.getId());
				// 检查所有的wafer都通过本站
				if (checkWafer
						&& !cpNode.getName().equalsIgnoreCase("Incoming"))
					this.checkCpWaferAllPassed(cpWafers, cpNode.getName());
				for (CPWafer cpWafer : cpWafers) {
					cpWafer.setState(CPWaferState.UNPASS);
					cpWafer.save();
				}
				// 如果当下一站点是future hold站点设置lot状态hold
				if (cpNodes.size() > index) {
					CPNode nextCPNode = cpNodes.get(index + 1);
					if (futureHoldNodes.indexOf(nextCPNode.getName()) > -1) {
						cpLot.setCurrentState("Hold");
						cpLot.setHoldState(CPLot.HOLD_STATE_HOLD);
					}
				}
				// 操作记录
				CPLotOptionLog cpLotOptionLog = new CPLotOptionLog();
				BeanUtils.copyProperties(cpNodeBase, cpLotOptionLog);
				cpLotOptionLog.setCpLot(cpLot);
				cpLotOptionLog.setOptType("出站" + cpNode.getName());
				cpLotOptionLog.setRemark("良率放行");
				cpLotOptionLog.setFlownow(cpNode.getName());
				cpLotOptionLogApplication.creatCPLotOptionLog(cpLotOptionLog);
				cpLotApplication.update(cpLot);
				cpProcessApplication.update(process); // 级联
				return;
			default: // 其他状态不合法
				throw new RuntimeException("良率放行失败：未进站测试，不能放行！");
			}
		}
	}

	@Override
	public void separateCPLotCheck(CPLot cpLot) {
		CPProcess process = cpLot.getCpProcess(); // 找到流程
		// if (Objects.equals(cpLot.getHoldState(), CPLot.HOLD_STATE_HOLD)) {
		// throw new RuntimeException("分批失败：开HOLD");
		// }

		// 出站更新信息
		List<CPNode> cpNodes = process.getCpNodes();
		Collections.sort(cpNodes);
		for (int index = 0; index < cpNodes.size(); index++) {
			CPNode cpNode = cpNodes.get(index);
			switch (cpNode.getState()) {
			case STARTED: // 进站了没出站
				break;
			// throw new RuntimeException("分批失败：进站后未出站不能分批");
			case TO_START: // 来到这个站点
				if ("shipping".equals(cpNode.getName())) {
					throw new RuntimeException("分批失败：shipping站点不能分批");
				} else {
					return;
				}
			default: // 未经过站点
				break;
			}
		}
	}

	private CPStorage ConvertToCPStorage(CPLot cplot, List<CPWafer> cpWafers) {
		CPStorage cpstorage = new CPStorage();
		CustomerCPLot customerlot = cplot.getCustomerCPLot();
		cpstorage.setID(UUID.randomUUID().toString());
		cpstorage.setACETEC_LOT(cplot.getInternalLotNumber());
		cpstorage.setCUS_CODE(customerlot.getCustomerNumber());
		cpstorage.setCUS_LOT(customerlot.getCustomerLotNumber());
		cpstorage.setCUS_PPO(customerlot.getCustomerPPO());
		cpstorage.setIN_PARTNUM(customerlot.getCustomerProductNumber());
		cpstorage.setMASK_NAME(customerlot.getMaskName());
		cpstorage.setNUMBER(cplot.getInternalLotNumber());
		cpstorage.setOUT_PARTNUM(cplot.getShipmentProductNumber());
		cpstorage.setQUANTITY(cplot.getQuantity().intValue());
		cpstorage.setTEST_ID(cplot.getWmsTestId());
		List<CPStorageWafer> cpwaferList = new ArrayList<CPStorageWafer>();
		for (CPWafer wafer : cpWafers) {
			CPStorageWafer swafer = new CPStorageWafer();
			swafer.setID(UUID.randomUUID().toString());
			swafer.setLOT_ID(cpstorage.getID());
			swafer.setWAFER_CODE(wafer.getCpCustomerWafer().getWaferCode());
			swafer.setWAFER_INDEX(wafer.getCpCustomerWafer().getWaferIndex());
			swafer.setIS_TRUE(1);
			swafer.setGOOD_DIE(0);
			swafer.setGROSS_DIE(0);
			cpwaferList.add(swafer);
		}
		cpstorage.setWAFERS(cpwaferList);
		return cpstorage;
	}
}
