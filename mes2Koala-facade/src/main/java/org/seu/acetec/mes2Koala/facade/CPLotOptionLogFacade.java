package org.seu.acetec.mes2Koala.facade;

import java.util.List;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.*;

public interface CPLotOptionLogFacade {

	public InvokeResult getCPLotOptionLog(Long id);
	
	public InvokeResult creatCPLotOptionLog(CPLotOptionLogDTO cPLotOptionLog);
	
	public InvokeResult updateCPLotOptionLog(CPLotOptionLogDTO cPLotOptionLog);
	
	public InvokeResult removeCPLotOptionLog(Long id);
	
	public InvokeResult removeCPLotOptionLogs(Long[] ids);
	
	public List<CPLotOptionLogDTO> findAllCPLotOptionLog();
	
	public Page<CPLotOptionLogDTO> pageQueryCPLotOptionLog(CPLotOptionLogDTO cPLotOptionLog, int currentPage, int pageSize);
	
	public CPLotDTO findCpLotByCPLotOptionLog(Long id);


	
}

