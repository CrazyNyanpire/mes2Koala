package org.seu.acetec.mes2Koala.application;


import java.util.List;

import org.seu.acetec.mes2Koala.core.domain.FTLot;

public interface FTLotApplication extends GenericMES2Application<FTLot> {

    FTLot findByFTQDNId(Long id);

    FTLot findByProcessId(Long id);

    void createCheckedFTLot(FTLot ftLot);

    List<FTLot> findByIds(Long[] ids);
    
    FTLot findByProductionId(Long id);
}

