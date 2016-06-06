package org.seu.acetec.mes2Koala.application;

import org.seu.acetec.mes2Koala.core.domain.FTNode;
import org.seu.acetec.mes2Koala.core.domain.FTProcess;

/**
 * @author yuxiangque
 * @version 2016/3/29
 */
public interface FTLotNodeOperationApplication {

    void hold(Long ftLotId);

    void unhold(Long ftLotId);

    void separate(Long ftLotId, Long[] separateQuantities);

    /**
     * 合批，只有来自同一批的分批后的批次才能合批
     *
     * @param ftLotIds
     */
    void integrate(Long[] ftLotIds);
 
    void end(Long processId, FTNode ftNode);

    void endFail(Long processId, FTProcess ftProcessForLog);

    void start(Long processId, FTProcess ftProcess);
}
