package org.seu.acetec.mes2Koala.application.impl;

import org.dayatang.utils.Page;
import org.seu.acetec.mes2Koala.application.FTNodeApplication;
import org.seu.acetec.mes2Koala.core.domain.FTFinishNode;
import org.seu.acetec.mes2Koala.core.domain.FTNode;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@Transactional
public class FTNodeApplicationImpl extends GenericMES2ApplicationImpl<FTNode> implements FTNodeApplication {

    @Override
    public FTFinishNode findFTFinishNodeByFTLotId(Long ftLotId) {
        return null;
    }

    @Override
    public List<FTNode> findFTCompostedTestNodesDescByProcessId(Long processId) {
        return find("select n from FTComposedTestNode n where n.ftProcess.id=? order by n.turn desc", processId);
    }

    @Override
    public List<FTNode> findFTCompostedTestNodesAscByFTLotId(Long ftLotId) {
        return find("select n from FTComposedTestNode n where n.ftProcess.ftLot.id=? order by n.turn asc", ftLotId);
    }

    @Override
    public List<FTNode> findFTCompostedTestNodesDescByFTLotId(Long ftLotId) {
        return find("select n from FTComposedTestNode n where n.ftProcess.ftLot.id=? order by n.turn desc", ftLotId);
    }

    @Override
    public List<FTNode> findUnreachedFTNodeByFTLotId(Long ftLotId) {
        return find("select n from FTNode n where n.ftProcess.ftLot.id=? and n.state<3", ftLotId);
    }

    /**
     * 找准备进站的站点
     *
     * @param ftLotId
     * @return
     */
    @Override
    public List<FTNode> findToStartFTNodeByFTLotId(Long ftLotId) {
        return find("select n from FTNode n where n.ftProcess.ftLot.id=? and n.state=1", ftLotId);
    }

    @Override
    public List<FTNode> findStartedFTNodeByFTLotId(Long ftLotId) {
        return find("select n from FTNode n where n.ftProcess.ftLot.id=? and n.state=2", ftLotId);
    }

    @Override
    public List<FTNode> findEndedFTNodeByFTLotId(Long ftLotId) {
        return find("select n from FTNode n where n.ftProcess.ftLot.id=?", ftLotId);
    }

    @Override
    public Page<FTNode> pageQueryFTNodesByFTProcessId(Long ftProcessId, int currentPage, int pageSize) {
        List<Object> conditionVals = new ArrayList<Object>();
        StringBuilder jpql = new StringBuilder("select _process.ftNodes from FTProcess _process where _process.id=?");
        conditionVals.add(ftProcessId);
        Page<FTNode> page = getQueryChannelService()
                .createJpqlQuery(jpql.toString())
                .setParameters(conditionVals)
                .setPage(currentPage, pageSize)
                .pagedList();
        return page;
    }

}
