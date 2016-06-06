package org.seu.acetec.mes2Koala.application;


import org.seu.acetec.mes2Koala.core.domain.FTQDN;

import java.util.List;

public interface FTQDNApplication extends GenericMES2Application<FTQDN> {

    List<FTQDN> findAllDoingByFTLotId(Long ftLotId);
}

