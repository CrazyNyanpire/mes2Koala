package org.seu.acetec.mes2Koala.facade;

import java.util.List;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.*;

public interface FTQDNFacade {

	public InvokeResult getFTQDN(Long id);
	
	public InvokeResult creatFTQDN(FTQDNDTO fTQDN);
	
	public InvokeResult updateFTQDN(FTQDNDTO fTQDN);
	
	public InvokeResult removeFTQDN(Long id);
	
	public InvokeResult removeFTQDNs(Long[] ids);
	
	public List<FTQDNDTO> findAllFTQDN();
	
	public Page<FTQDNDTO> pageQueryFTQDN(FTQDNDTO fTQDN, int currentPage, int pageSize);
	
	public FTLotDTO findFtLotByFTQDN(Long id);

	public InvokeResult disposeFTQDN(FTQDNDTO fTQDN);
	
	public List<FTQDNDTO> findAllDoingFTQDNByLotId(Long lotId);
	
}

