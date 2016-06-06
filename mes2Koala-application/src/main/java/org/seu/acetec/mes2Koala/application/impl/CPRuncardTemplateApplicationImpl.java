package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.CPRuncardTemplateApplication;
import org.seu.acetec.mes2Koala.core.domain.CPRuncardTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;

/**
 * Created by LCN on 2016/4/27.
 */
@Named
@Transactional
public class CPRuncardTemplateApplicationImpl extends GenericMES2ApplicationImpl<CPRuncardTemplate> implements CPRuncardTemplateApplication {
    @Override
    public CPRuncardTemplate findByInternalProductId(Long id) {
        return findOne("select a from CPRuncardTemplate a right join a.internalProduct b where b.id = ?", id);
    }
}
