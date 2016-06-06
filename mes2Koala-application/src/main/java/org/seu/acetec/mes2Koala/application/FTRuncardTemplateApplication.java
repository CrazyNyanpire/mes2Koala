package org.seu.acetec.mes2Koala.application;

import org.seu.acetec.mes2Koala.core.domain.FTRuncardTemplate;

/**
 * Created by LCN on 2016/3/10.
 */
public interface FTRuncardTemplateApplication extends GenericMES2Application<FTRuncardTemplate> {
    public FTRuncardTemplate findByInternalProductId(Long id);

    public void deleteByInternalProductId(Long id);

    public Boolean isRuncardSigned(Long id);
}
