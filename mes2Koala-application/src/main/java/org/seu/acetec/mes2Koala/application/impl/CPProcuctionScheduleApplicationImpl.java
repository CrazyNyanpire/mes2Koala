package org.seu.acetec.mes2Koala.application.impl;

import java.util.List;

import org.seu.acetec.mes2Koala.application.CPProductionScheduleApplication;
import org.seu.acetec.mes2Koala.core.domain.CPProductionSchedule;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;

/**
 * Created by harlow on 2016/5/30.
 */
@Named
@Transactional
public class CPProcuctionScheduleApplicationImpl extends
		GenericMES2ApplicationImpl<CPProductionSchedule> implements
		CPProductionScheduleApplication {

	@Override
	public void deleteProductionScheduleByNodeId(Long nodeId) {
		List<CPProductionSchedule> productionScheduleList = this
				.find("select a from CPProductionSchedule a where a.cpTestingNode.id = ?",
						nodeId);
		this.removeAll(productionScheduleList);
	}
}
