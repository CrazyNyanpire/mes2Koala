package org.seu.acetec.mes2Koala.facade;

import java.util.List;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.*;

public interface FT_StatisticsFacade {

	public InvokeResult getFT_Statistics(Long id);
	
	public InvokeResult creatFT_Statistics(FT_StatisticsDTO fT_Statistics);
	
	public InvokeResult updateFT_Statistics(FT_StatisticsDTO fT_Statistics);
	
	public InvokeResult removeFT_Statistics(Long id);
	
	public InvokeResult removeFT_Statisticss(Long[] ids);
	
	public List<FT_StatisticsDTO> findAllFT_Statistics();
	
	public Page<FT_StatisticsDTO> pageQueryFT_Statistics(FT_StatisticsDTO fT_Statistics, int currentPage, int pageSize);
	

}

