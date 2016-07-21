package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.FTRuncardApplication;
import org.seu.acetec.mes2Koala.core.domain.AcetecAuthorization;
import org.seu.acetec.mes2Koala.core.domain.FTRuncard;
import org.seu.acetec.mes2Koala.core.domain.SpecialFormTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;

/**
 * Created by harlow on 2016/5/30.
 */
@Named
@Transactional
public class FTRuncardApplicationImpl extends
        GenericMES2ApplicationImpl<FTRuncard> implements FTRuncardApplication {

    @Override
    public boolean deleteRuncardByLotId(Long lotId) {
        FTRuncard runcard = this.findOne(
                "select a from FTRuncard a where a.ftLot.id = ?", lotId);
        if (runcard == null) {
            return true;
        }
        runcard.setSpecialFormTemplate(null);
        runcard.setKeyQuantityAuthorization(null);
        runcard.setAssistQuantityAuthorization(null);
        runcard.setKeyProductionAuthorization(null);
        runcard.setAssistProductionAuthorization(null);
        runcard.setKeyTDEAuthorization(null);
        runcard.setAssistTDEAuthorization(null);
        this.remove(runcard);
        return true;
    }
}
