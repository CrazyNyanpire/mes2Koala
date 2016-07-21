package org.seu.acetec.mes2Koala.application;

import net.sf.json.JSONArray;

import org.seu.acetec.mes2Koala.application.bean.SaveBaseBean;
import org.seu.acetec.mes2Koala.core.domain.CPLot;
import org.seu.acetec.mes2Koala.core.domain.CPNode;

/**
 * @author yuxiangque
 * @version 2016/3/28
 */
public interface CPLotNodeOperationApplication {

    /**
     * 入站
     *
     * @param processId
     */
    void startCPNode(Long processId,SaveBaseBean sbb);

    /**
     * 保存
     *
     * @param processId
     * @param cpNode
     */
    void saveCPNode(Long processId, CPNode cpNode);

    /**
     * 出站
     *
     * @param processId
     */
    void endCPNode(Long processId,SaveBaseBean sbb);

    /**
     * 跳站
     *
     * @param processId
     */
    void jumpCPNode(Long processId,CPNode cpNode,String targetNode);

    /**
     * 分批
     *
     * @param cpLotId            待分批的CPLotId
     * @param separateQuantities 分批数量
     */
    void separateCPLot(Long cpLotId, Long[] separateQuantities,SaveBaseBean sbb);

    /**
     * 合批
     *
     * @param cpLotIds 待合批的CPLotId
     */
    void integrateCPLots(Long[] cpLotIds,SaveBaseBean sbb);

	void endFail(Long processId,CPNode cpNode);
	
	public void endCPNodeIncoming(Long processId, JSONArray wafers,SaveBaseBean sbb);

	void separateCPLotCheck(CPLot cplot);

}
