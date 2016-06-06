package org.seu.acetec.mes2Koala.application;


import org.seu.acetec.mes2Koala.core.domain.EQCSetting;

import java.util.List;

public interface EQCSettingApplication extends GenericMES2Application<EQCSetting> {

    List<EQCSetting> findByInternalProductId(Long id);
}