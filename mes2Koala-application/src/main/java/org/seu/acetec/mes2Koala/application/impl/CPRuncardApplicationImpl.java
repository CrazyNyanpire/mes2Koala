package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.CPRuncardApplication;
import org.seu.acetec.mes2Koala.core.domain.CPRuncard;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;

/**
 * Created by LCN on 2016/4/27.
 */
@Named
@Transactional
public class CPRuncardApplicationImpl extends GenericMES2ApplicationImpl<CPRuncard> implements CPRuncardApplication {
    @Override
    public CPRuncard findByCPLotId(Long id) {
        return findOne("select a from CPRuncard a where a.cpLot.id = ?", id);
    }
}
