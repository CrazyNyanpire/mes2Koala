package org.seu.acetec.mes2Koala.application;


import org.dayatang.utils.Page;
import org.seu.acetec.mes2Koala.core.domain.FTFinishNode;
import org.seu.acetec.mes2Koala.core.domain.FTNode;

import java.util.List;

public interface FTNodeApplication extends GenericMES2Application<FTNode> {

    FTFinishNode findFTFinishNodeByFTLotId(Long ftLotId);

    /**
     * 从最后一个站点开始，提取所有节点中的测试站点
     *
     * @param processId
     * @return
     */
    List<FTNode> findFTCompostedTestNodesDescByProcessId(Long processId);

    List<FTNode> findFTCompostedTestNodesAscByFTLotId(Long ftLotId);

    List<FTNode> findFTCompostedTestNodesDescByFTLotId(Long ftLotId);

    List<FTNode> findUnreachedFTNodeByFTLotId(Long ftLotId);


    List<FTNode> findToStartFTNodeByFTLotId(Long ftLotId);

    /**
     * 找已经进站还没出站的站点
     *
     * @param ftLotId
     * @return
     */
    List<FTNode> findStartedFTNodeByFTLotId(Long ftLotId);

    List<FTNode> findEndedFTNodeByFTLotId(Long ftLotId);

    Page<FTNode> pageQueryFTNodesByFTProcessId(Long ftProcessId, int currentPage, int pageSize);

}

