package org.seu.acetec.mes2Koala.application;


import java.util.List;
import java.util.Set;

import org.seu.acetec.mes2Koala.core.domain.CPProcess;
import org.seu.acetec.mes2Koala.core.domain.FTLot;
import org.seu.acetec.mes2Koala.core.domain.FTLotOptionLog;
import org.seu.acetec.mes2Koala.core.domain.FTNode;
import org.seu.acetec.mes2Koala.core.domain.FTProcess;

public interface FTLotOptionLogApplication extends GenericMES2Application<FTLotOptionLog>{

    public FTLotOptionLog getFTLotOptionLog(Long id);

    public void creatFTLotOptionLog(FTLotOptionLog fTLotOptionLog);

    public void updateFTLotOptionLog(FTLotOptionLog fTLotOptionLog);

    public void removeFTLotOptionLog(FTLotOptionLog fTLotOptionLog);

    public void removeFTLotOptionLogs(Set<FTLotOptionLog> fTLotOptionLogs);

    public List<FTLotOptionLog> findAllFTLotOptionLog();

    void createSaveFTNode(FTLot ftLot, FTNode ftNode, FTNode ftNodeForLog);

    void createEndFTNode(FTLot ftLot, FTNode ftNode, FTNode ftNodeForLog);

    void createEndFailTestNode(FTLot ftLot, FTNode ftNode, FTProcess ftProcessForLog);
    
    void createStartFTNode(FTLot ftLot, FTNode ftNode, FTProcess ftProcessForLog);
    
    public void removeFTLotOptionLogByLotId(Long lotid);
}

