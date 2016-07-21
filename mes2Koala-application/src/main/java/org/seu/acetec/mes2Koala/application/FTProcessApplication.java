package org.seu.acetec.mes2Koala.application;


import java.util.List;

import org.seu.acetec.mes2Koala.core.domain.FTNode;
import org.seu.acetec.mes2Koala.core.domain.FTProcess;

public interface FTProcessApplication extends GenericMES2Application<FTProcess> {

    /**
     * @param ftLotId
     * @return
     */
    FTProcess findFTProcessByFTLotId(Long ftLotId);


    /**
     * 兼容分批时从母批当前阶段开始生产process
     *
     * @param ftLotId
     * @param parentFTLotId 父批的Id
     * @return
     * @version 2016/3/4 YuxiangQue
     */
    void createFTProcess(Long ftLotId, Long parentFTLotId);

    /**
     * 下单成功后创建测试站点
     *
     * @param ftLotId
     * @return
     * @version 2016/3/6 YuxiangQue
     */
    void createFTProcess(Long ftLotId);
    
    public void createFTProcess(Long ftLotId, Long parentFTLotId , int index);
    
    /**
     * 删除测试process
     *
     * @param ftLotId
     * @return harlow
     * @version 2016/5/30 
     */
    void deleteFTProcess(Long ftLotId);
    
    public FTNode findToStartNode(List<FTNode> ftNodes) ;
    
}

