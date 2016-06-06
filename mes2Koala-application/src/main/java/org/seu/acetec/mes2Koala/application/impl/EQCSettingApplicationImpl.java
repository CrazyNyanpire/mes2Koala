package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.EQCSettingApplication;
import org.seu.acetec.mes2Koala.core.domain.EQCSetting;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.util.List;

@Named
@Transactional
public class EQCSettingApplicationImpl extends GenericMES2ApplicationImpl<EQCSetting> implements EQCSettingApplication {

    @Override
    public List<EQCSetting> findByInternalProductId(Long id) {
        return find("select o from EQCSetting o right join o.internalProduct e where e.id=?", id);
    }
}
