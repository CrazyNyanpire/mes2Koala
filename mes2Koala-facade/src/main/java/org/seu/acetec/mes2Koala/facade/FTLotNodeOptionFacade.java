package org.seu.acetec.mes2Koala.facade;

import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.core.domain.Process;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FTLotNodeOptionFacade {


    /**
     * @param ftLotId
     * @param separateQuantities
     * @return
     * @version 2015/3/29 YuxiangQue
     */
    InvokeResult separate(Long ftLotId, Long[] separateQuantities);

    /**
     * @param ftLotId
     * @param separateQuantities
     * @return
     * @version 2015/3/29 YuxiangQue
     */
    InvokeResult integrate(Long[] ftLotIds);

    InvokeResult hold(FTQDNDTO fTQDNDTO);

    InvokeResult unhold(FTLotDTO fTLotDTO);

    InvokeResult futureHold(FTQDNDTO fTLotDTO);

    InvokeResult sampleShipping(SampleShippingDTO sampleShippingDTO);

    InvokeResult optionLog(FTLotDTO fTLotDTO);

    InvokeResult splitLot(FTLotDTO fTLotDTO);

    InvokeResult mergeLot(FTLotDTO fTLotDTO);

    InvokeResult getFTResultByLotId(Long id, String labelType);

    /**
     * 切换等待入站（1）状态变为已入站（2）
     * 入站，会顺序查找整个Process上的所有站点，
     * 跳过已经出站的站点，找到第一个没进站的站点，进站，对于其他情况作为异常状态处理
     *
     * @param processId
     * @return
     * @version 2016/3/3 YuxiangQue
     */
    InvokeResult startFTNode(Long processId, FTProcessDTO ftProcessDTO);

    /**
     * 出站
     * 1. 首先检查外部的DTO中的state状态，保证不会又客户端修改状态
     * 2. 然后检查状态必须是进站而没出战
     * 3. 接着对Test站点进行良率卡控
     * 4. 最后更新出站信息，更新出站信息也是顺序查找，找到一个没出站的站点，对于其他状态作为异常处理
     *
     * @param processId
     * @param currentNode 当前所在站点
     * @return
     * @version 2016/3/6 YuxiangQue
     */
    InvokeResult endFTNode(Long processId, FTNodeDTO currentNode);

    /**
     * 良率放行
     *
     * @param processId
     * @return
     * @version 2016/3/28 LiuHaobo
     */
    InvokeResult endFailTestNode(Long processId, FTProcessDTO ftProcessDTO );


    /**
     * 更新站点信息，不做任何检查
     *
     * @param processId
     * @param updateProps
     * @return
     */
    InvokeResult saveUncheckedFTNode(Long processId, FTNodeDTO updateProps);

    /**
     * 更新站点信息
     *
     * @param currentNode
     * @param processId
     * @version 2016/3/3 YuxiangQue
     */
    InvokeResult saveFTNode(Long processId, FTNodeDTO currentNode);
    
    /**
     * 检索当前站点Sbl信息
     *
     * @param id
     * @return
     * @version 2016/3/17 HongYu
     */
    InvokeResult findCurrentSblByLotId(Long id);

    /**
     * 统计信息
     *
     * @param processId
     * @return
     */
    InvokeResult quantityStatistics(Long processId);
    
    /**
     * 检查导入的ReelCode是否合法，实际上执行了一次excel导入的功能
     * @param filename
     * @param ftProcessId
     * @return
     */
    InvokeResult checkImportedReelCode( String filename, Long ftProcessId );
}

