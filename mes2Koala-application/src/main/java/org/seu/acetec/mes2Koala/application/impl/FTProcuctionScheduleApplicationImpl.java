package org.seu.acetec.mes2Koala.application.impl;


import java.util.List;

import org.seu.acetec.mes2Koala.application.FTProductionScheduleApplication;
import org.seu.acetec.mes2Koala.core.domain.FTProductionSchedule;
import org.seu.acetec.mes2Koala.core.domain.InternalLot;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;

/**
 * Created by harlow on 2016/5/30.
 */
@Named
@Transactional
public class FTProcuctionScheduleApplicationImpl extends
		GenericMES2ApplicationImpl<FTProductionSchedule> implements
		FTProductionScheduleApplication {

	@Override
	public void deleteProductionScheduleByNodeId(Long nodeId) {
		List<FTProductionSchedule> productionScheduleList = this.find(
				"select a from FTProductionSchedule a where a.ftComposedTestNode.id = ?", nodeId);
		this.removeAll(productionScheduleList);
	}
}
