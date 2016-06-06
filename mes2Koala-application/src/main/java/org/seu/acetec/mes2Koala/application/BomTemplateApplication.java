package org.seu.acetec.mes2Koala.application;


import org.seu.acetec.mes2Koala.core.domain.BomTemplate;

public interface BomTemplateApplication extends GenericMES2Application<BomTemplate> {

    BomTemplate findByBomUseId(Long bomUseId);

}

