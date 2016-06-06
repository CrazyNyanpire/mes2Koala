package org.seu.acetec.mes2Koala.application;


import org.seu.acetec.mes2Koala.core.domain.LabelPlan;

public interface LabelPlanApplication extends GenericMES2Application<LabelPlan> {

    LabelPlan findByInternalProductId(Long id);
}

