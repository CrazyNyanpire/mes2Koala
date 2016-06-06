package org.seu.acetec.mes2Koala.facade;

import java.util.List;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.*;

public interface FTLotOptionLogFacade {

	public InvokeResult getFTLotOptionLog(Long id);
	
	public InvokeResult creatFTLotOptionLog(FTLotOptionLogDTO fTLotOptionLog);
	
	public InvokeResult updateFTLotOptionLog(FTLotOptionLogDTO fTLotOptionLog);
	
	public InvokeResult removeFTLotOptionLog(Long id);
	
	public InvokeResult removeFTLotOptionLogs(Long[] ids);
	
	public List<FTLotOptionLogDTO> findAllFTLotOptionLog();
	
	public Page<FTLotOptionLogDTO> pageQueryFTLotOptionLog(FTLotOptionLogDTO fTLotOptionLog, int currentPage, int pageSize);
	

}

