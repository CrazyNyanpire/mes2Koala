package org.seu.acetec.mes2Koala.application;

import org.seu.acetec.mes2Koala.core.domain.FTRuncardTemplate;

/**
 * Created by LCN on 2016/3/10.
 * Modify by harlow on 2016/6/12
 */
public interface FTRuncardTemplateApplication extends GenericMES2Application<FTRuncardTemplate> {
    public FTRuncardTemplate findByInternalProductId(Long id);

    public void deleteByInternalProductId(Long id);

    public Boolean isRuncardSigned(Long id);
    
    public String isRuncardSignedMsg(Long id);
}
