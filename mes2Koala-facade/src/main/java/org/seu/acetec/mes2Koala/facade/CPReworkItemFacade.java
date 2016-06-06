package org.seu.acetec.mes2Koala.facade;

import java.util.List;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.*;

public interface CPReworkItemFacade {

	public InvokeResult getCPReworkItem(Long id);
	
	public InvokeResult creatCPReworkItem(CPReworkItemDTO cpReworkItem);
	
	public InvokeResult updateCPReworkItem(CPReworkItemDTO cpReworkItem);
	
	public InvokeResult removeCPReworkItem(Long id);
	
	public InvokeResult removeCPReworkItems(Long[] ids);
	
	public List<CPReworkItemDTO> findAllCPReworkItem();
	
	public Page<CPReworkItemDTO> pageQueryCPReworkItem(CPReworkItemDTO cpReworkItem, int currentPage, int pageSize);
	
	public CPReworkDTO findCpReWorkByCPReworkItem(Long id);


	
}

