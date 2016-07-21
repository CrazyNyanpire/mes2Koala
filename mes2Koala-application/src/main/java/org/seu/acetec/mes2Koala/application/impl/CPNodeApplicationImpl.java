package org.seu.acetec.mes2Koala.application.impl;

import org.dayatang.utils.Page;
import org.seu.acetec.mes2Koala.application.CPLotApplication;
import org.seu.acetec.mes2Koala.application.CPNodeApplication;
import org.seu.acetec.mes2Koala.application.CPProcessApplication;
import org.seu.acetec.mes2Koala.core.common.BeanUtils;
import org.seu.acetec.mes2Koala.core.domain.CPLot;
import org.seu.acetec.mes2Koala.core.domain.CPNode;
import org.seu.acetec.mes2Koala.core.domain.CPProcess;
import org.seu.acetec.mes2Koala.core.enums.CPNodeState;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yuxiangque
 * @version 2016/3/28
 */
@Named
@Transactional
public class CPNodeApplicationImpl extends GenericMES2ApplicationImpl<CPNode>
		implements CPNodeApplication {
	@Inject
	CPLotApplication cpLotApplication;

	@Inject
	CPProcessApplication cpProcessApplication;

	@Override
	public Page<CPNode> pageQueryCPNodesByCPProcessId(Long cpProcessId,
			int currentPage, int pageSize) {

		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select _process.cpNodes from CPProcess _process where _process.id=?");
		conditionVals.add(cpProcessId);
		Page<CPNode> pages = getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.setPage(currentPage, pageSize).pagedList();
		return pages;
	}

	public String createTransferStorage(String lotNo) {
		List<Object> params = new ArrayList<Object>();
		params.add(lotNo);
		// 获取Process
		CPLot cpLot = this.cpLotApplication.findOne(
				"select o from CPLot o where o.internalLotNumber = ? "
						+ "and (o.logic is null or o.logic <> 1)", params);
		if (cpLot == null) {
			return "查询批次信息失败-" + lotNo;
		}
		CPProcess cpProcess = cpLot.getCpProcess();
		List<CPNode> nodeList = cpProcess.getCpNodes();
		CPNode lastNode = new CPNode();
		lastNode.setTurn(0);
		// 获取最后一个节点
		for (CPNode cpNode : nodeList) {
			if (cpNode.getTurn() > lastNode.getTurn()) {
				BeanUtils.copyProperties(cpNode, lastNode);
			}
		}
		// 增加一个TransferStorage Node
		lastNode.setName("TransferStorage");
		lastNode.setState(CPNodeState.STARTED);
		lastNode.setTurn(lastNode.getTurn() + 1);
		lastNode.setId(null);
		cpProcess.setIsTransferStorage(true);
		cpProcess.setLastModifyTimestamp(new Date());
		cpProcessApplication.update(cpProcess);
		this.create(lastNode);
		return null;
		// cpNodeApplication.create(lastNode);
	}

	@Override
	public List<CPNode> findEndedCPNodeByCPLotId(Long cpLotId) {
		return find("select n from CPNode n where n.cpProcess.cpLot.id=?", cpLotId);
	}

	@Override
	public List<CPNode> findUnreachedCPNodeByCPLotId(Long cpLotId) {
		return find("select n from CPNode n where n.cpProcess.cpLot.id=? and n.state<3", cpLotId);

	}

	@Override
	public List<CPNode> findToStartCPNodeByCPLotId(Long cpLotId) {
		return find("select n from CPNode n where n.cpProcess.cpLot.id=? and n.state=1", cpLotId);
	}

	@Override
	public List<CPNode> findStartedCPNodeByCPLotId(Long cpLotId) {
		return find("select n from CPNode n where n.cpProcess.cpLot.id=? and n.state=2", cpLotId);
	}
}
