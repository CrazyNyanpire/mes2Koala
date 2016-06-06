package org.seu.acetec.mes2Koala.application;


import org.seu.acetec.mes2Koala.core.domain.SBLTemplate;

import java.util.List;

/**
 * @version 2016/3/29 YuxiangQue
 */
public interface SBLTemplateApplication extends GenericMES2Application<SBLTemplate> {

    List<SBLTemplate> findByInternalProductId(Long id);
}

