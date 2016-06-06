package org.seu.acetec.mes2Koala.application;


import org.seu.acetec.mes2Koala.core.domain.CPProductionSchedule;

public interface CPProductionScheduleApplication extends GenericMES2Application<CPProductionSchedule> {
	public void deleteProductionScheduleByNodeId(Long nodeId);
}
