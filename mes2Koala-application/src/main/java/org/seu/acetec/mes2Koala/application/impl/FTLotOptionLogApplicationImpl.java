package org.seu.acetec.mes2Koala.application.impl;

import java.util.List;
import java.util.Set;

import javax.inject.Named;

import org.seu.acetec.mes2Koala.core.common.BeanUtils;
import org.seu.acetec.mes2Koala.core.domain.CPProcess;
import org.seu.acetec.mes2Koala.core.domain.FTLot;
import org.seu.acetec.mes2Koala.core.domain.FTNode;
import org.seu.acetec.mes2Koala.core.domain.FTProcess;
import org.seu.acetec.mes2Koala.core.domain.FTRuncard;
import org.springframework.transaction.annotation.Transactional;
import org.seu.acetec.mes2Koala.application.FTLotOptionLogApplication;
import org.seu.acetec.mes2Koala.core.domain.FTLotOptionLog;

@Named
@Transactional
public class FTLotOptionLogApplicationImpl extends
		GenericMES2ApplicationImpl<FTLotOptionLog> implements
		FTLotOptionLogApplication {

	public FTLotOptionLog getFTLotOptionLog(Long id) {
		return FTLotOptionLog.get(FTLotOptionLog.class, id);
	}

	public void creatFTLotOptionLog(FTLotOptionLog fTLotOptionLog) {
		fTLotOptionLog.save();
	}

	public void updateFTLotOptionLog(FTLotOptionLog fTLotOptionLog) {
		fTLotOptionLog.save();
	}

	public void removeFTLotOptionLog(FTLotOptionLog fTLotOptionLog) {
		if (fTLotOptionLog != null) {
			fTLotOptionLog.remove();
		}
	}

	public void removeFTLotOptionLogs(Set<FTLotOptionLog> fTLotOptionLogs) {
		for (FTLotOptionLog fTLotOptionLog : fTLotOptionLogs) {
			fTLotOptionLog.remove();
		}
	}

	public List<FTLotOptionLog> findAllFTLotOptionLog() {
		return FTLotOptionLog.findAll(FTLotOptionLog.class);
	}

	@Override
	public void createSaveFTNode(FTLot ftLot, FTNode ftNode, FTNode ftNodeForLog) {
		// 操作记录
		FTLotOptionLog fTLotOptionLog = new FTLotOptionLog();
		BeanUtils.copyProperties(ftNodeForLog, fTLotOptionLog); // 复制相同的属性值
		fTLotOptionLog.setId(null);
		fTLotOptionLog.setFtLot(ftLot);
		fTLotOptionLog.setOptType("保存" + ftNode.getName());
		creatFTLotOptionLog(fTLotOptionLog);
	}

	@Override
	public void createEndFTNode(FTLot ftLot, FTNode ftNode, FTNode ftNodeForLog) {
		// 操作记录
		FTLotOptionLog fTLotOptionLog = new FTLotOptionLog();
		BeanUtils.copyProperties(ftNodeForLog, fTLotOptionLog); // 复制相同的属性值
		fTLotOptionLog.setId(null);
		fTLotOptionLog.setFtLot(ftLot);
		fTLotOptionLog.setOptType("出站" + ftNode.getName());
		creatFTLotOptionLog(fTLotOptionLog);
	}

	@Override
	public void createEndFailTestNode(FTLot ftLot, FTNode ftNode,
			FTProcess ftProcessForLog) {
		FTLotOptionLog fTLotOptionLog = new FTLotOptionLog();
		BeanUtils.copyProperties(ftProcessForLog, fTLotOptionLog); // 复制相同的属性值
		fTLotOptionLog.setId(null);
		fTLotOptionLog.setFtLot(ftLot);
		fTLotOptionLog.setOptType("良率放行" + ftNode.getName());
		creatFTLotOptionLog(fTLotOptionLog);
	}

	@Override
	public void createStartFTNode(FTLot ftLot, FTNode ftNode,
			FTProcess ftProcessForLog) {
		FTLotOptionLog fTLotOptionLog = new FTLotOptionLog();
		BeanUtils.copyProperties(ftProcessForLog, fTLotOptionLog); // 复制相同的属性值
		fTLotOptionLog.setId(null);
		fTLotOptionLog.setFtLot(ftLot);
		fTLotOptionLog.setOptType("入站" + ftNode.getName());
		creatFTLotOptionLog(fTLotOptionLog);
	}

	@Override
	public void removeFTLotOptionLogByLotId(Long lotId) {
		List<FTLotOptionLog> list = this.find(
				"select a from FTLotOptionLog a where a.ftLot.id = ?", lotId);
		this.removeAll(list);
	}
}
