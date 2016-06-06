package org.seu.acetec.mes2Koala.facade;

import java.util.List;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.*;

public interface FTReworkItemFacade {

	public InvokeResult getFTReworkItem(Long id);
	
	public InvokeResult creatFTReworkItem(FTReworkItemDTO fTReworkItem);
	
	public InvokeResult updateFTReworkItem(FTReworkItemDTO fTReworkItem);
	
	public InvokeResult removeFTReworkItem(Long id);
	
	public InvokeResult removeFTReworkItems(Long[] ids);
	
	public List<FTReworkItemDTO> findAllFTReworkItem();
	
	public Page<FTReworkItemDTO> pageQueryFTReworkItem(FTReworkItemDTO fTReworkItem, int currentPage, int pageSize);
	
	public FTReworkDTO findFtReWorkByFTReworkItem(Long id);


	
}

