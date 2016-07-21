package org.seu.acetec.mes2Koala.application;


import org.seu.acetec.mes2Koala.core.domain.CPTestingNode;
import org.seu.acetec.mes2Koala.core.domain.ProductionSchedule;

public interface ProductionScheduleApplication extends GenericMES2Application<ProductionSchedule> {

    public void createNewFtSchedule(Long testSysId, Long ftComposedTestNodeId);
    
    public void createNewCpSchedule(Long testSysId, CPTestingNode cpTestingNode);

    public void basicScheduling( Long id, Integer version, Long testSysId );
    
    //任务开始。testing站点进站后调用
    public void startTesting( Long id );
    
    //任务结束。testing站点成功出站后调用
    public void endTesting( Long id );
    
    //分批测试
    public void separate(Long id, String newLotNumber, Double percent, Long targetTestSysId );
    
    //更新状态
    public void updateState( Long id, String state );

    //批量排产
	public void arrangeProductionsInATestSys(Long[] productionIds, Long testSysId);

	//批量撤销排产
	public void revokeProductionSchedules(Long[] ids);

	public void reorderUp(Long id);

	public void reorderDown(Long id);
	
	public void moveToTop(Long[] ids);

	public void updateAllTestingProduction();
	
	public void updateAllCpDoneQty();
}
