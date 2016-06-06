package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.LabelPlanApplication;
import org.seu.acetec.mes2Koala.core.domain.LabelPlan;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;

@Named
@Transactional
public class LabelPlanApplicationImpl extends GenericMES2ApplicationImpl<LabelPlan> implements LabelPlanApplication {

    @Override
    public LabelPlan findByInternalProductId(Long id) {
        return findOne("select o from LabelPlan o right join o.internalProduct e where e.id=?", id);
    }
}
