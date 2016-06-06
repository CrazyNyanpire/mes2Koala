package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.BomTemplateApplication;
import org.seu.acetec.mes2Koala.core.domain.BomTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;

@Named
@Transactional
public class BomTemplateApplicationImpl extends GenericMES2ApplicationImpl<BomTemplate> implements BomTemplateApplication {

    @Override
    public BomTemplate findByBomUseId(Long bomUseId) {
        return findOne("select a from BomUse b right join b.bomTemplate a where b.id = ?", bomUseId);
    }
}
