package org.seu.acetec.mes2Koala.facade;

import java.util.Date;
import java.util.List;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.core.domain.FTLot;
import org.seu.acetec.mes2Koala.facade.dto.ProductionScheduleDTO;

public interface ProductionScheduleFacade {

	public InvokeResult getProductionSchedule(Long id);
	
	public InvokeResult creatProductionSchedule(ProductionScheduleDTO productionSchedule);
	
	public InvokeResult updateProductionSchedule(ProductionScheduleDTO productionSchedule);
	
	public InvokeResult removeProductionSchedule(Long id);
	
	public InvokeResult removeProductionSchedules(Long[] ids);
	
	public List<ProductionScheduleDTO> findAllProductionSchedule();
	
	public Page<ProductionScheduleDTO> pageQueryProductionSchedule(ProductionScheduleDTO productionSchedule, int currentPage, int pageSize, String sortname, String sortorder);

	public InvokeResult updateState(Long id, String state, String note);

	public InvokeResult separate(Long id, String newLotNumber, Double percent, Long targetTestSysId);
	
	public InvokeResult basicScheduling( Long id, Integer version, Long testSysId );

	public InvokeResult arrangeProductionsInATestSys(Long[] productionIds, Long testSysId);

	public InvokeResult revokeProductionSchedules(Long[] ids);

	public Page<ProductionScheduleDTO> pageQueryProductionsWaitToBeScheduled(
			ProductionScheduleDTO productionScheduleDTO, int page, int pagesize);

	public InvokeResult reorderUp(Long id);

	public InvokeResult reorderDown(Long id);
	
	public InvokeResult moveToTop(Long[] ids);

	public InvokeResult updateAllTestingProduction();

	public InvokeResult startTesting(Long id);

	public InvokeResult endTesting(Long id);

}

