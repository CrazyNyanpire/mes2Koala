package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.FTQDNApplication;
import org.seu.acetec.mes2Koala.core.domain.FTQDN;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.util.List;

@Named
@Transactional
public class FTQDNApplicationImpl extends GenericMES2ApplicationImpl<FTQDN> implements FTQDNApplication {

    @Override
    public List<FTQDN> findAllDoingByFTLotId(Long ftLotId) {
        StringBuilder jpql = new StringBuilder("select a from FTQDN a where 1 = 1 ");
        jpql.append(" and a.status < 2 ");
        if (ftLotId != null) {
            jpql.append(" and a.ftLot.id = ?");
        }
        return find(jpql.toString(), ftLotId);
    }
}
