package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.SBLTemplateApplication;
import org.seu.acetec.mes2Koala.core.domain.SBLTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.util.List;

@Named
@Transactional
public class SBLTemplateApplicationImpl extends GenericMES2ApplicationImpl<SBLTemplate> implements SBLTemplateApplication {

    @Override
    public List<SBLTemplate> findByInternalProductId(Long productId) {
        return find("select o from SBLTemplate o right join o.internalProduct e where e.id = ?", productId);
    }
}
