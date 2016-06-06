package org.seu.acetec.mes2Koala.application;

import org.seu.acetec.mes2Koala.core.domain.CPRuncardTemplate;

/**
 * Created by LCN on 2016/4/27.
 */
public interface CPRuncardTemplateApplication extends GenericMES2Application<CPRuncardTemplate> {
    public CPRuncardTemplate findByInternalProductId(Long id);
}
