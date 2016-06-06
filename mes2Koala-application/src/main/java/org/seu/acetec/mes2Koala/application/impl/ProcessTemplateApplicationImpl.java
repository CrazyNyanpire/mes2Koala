package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.ProcessTemplateApplication;
import org.seu.acetec.mes2Koala.core.domain.ProcessTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;

@Named
@Transactional
public class ProcessTemplateApplicationImpl extends GenericMES2ApplicationImpl<ProcessTemplate> implements ProcessTemplateApplication {

    @Override
    public ProcessTemplate findByInternalProductId(Long id) {
        return findOne("select b from InternalProduct a right join a.processTemplate b where a.id = ?", id);
    }

    @Override
    public ProcessTemplate findByCustomerLotId(Long id) {
        throw new UnsupportedOperationException("Todo");
    }

}
