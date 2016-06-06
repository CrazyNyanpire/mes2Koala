package org.seu.acetec.mes2Koala.application;


import org.seu.acetec.mes2Koala.core.domain.FTProductionSchedule;

public interface FTProductionScheduleApplication extends GenericMES2Application<FTProductionSchedule> {
	public void deleteProductionScheduleByNodeId(Long nodeId);
}
