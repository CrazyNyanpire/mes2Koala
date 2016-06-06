package org.seu.acetec.mes2Koala.application;


import org.seu.acetec.mes2Koala.core.domain.ProcessTemplate;

public interface ProcessTemplateApplication extends GenericMES2Application<ProcessTemplate> {

    ProcessTemplate findByInternalProductId(Long id);

    ProcessTemplate findByCustomerLotId(Long id);
}

