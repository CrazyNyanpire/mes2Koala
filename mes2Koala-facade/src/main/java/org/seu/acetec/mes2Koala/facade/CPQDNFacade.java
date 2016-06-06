package org.seu.acetec.mes2Koala.facade;

import java.util.List;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.*;

public interface CPQDNFacade {

	public InvokeResult getCPQDN(Long id);
	
	public InvokeResult creatCPQDN(CPQDNDTO cPQDN);
	
	public InvokeResult updateCPQDN(CPQDNDTO cPQDN);
	
	public InvokeResult removeCPQDN(Long id);
	
	public InvokeResult removeCPQDNs(Long[] ids);
	
	public List<CPQDNDTO> findAllCPQDN();
	
	public Page<CPQDNDTO> pageQueryCPQDN(CPQDNDTO cPQDN, int currentPage, int pageSize);

	public List<CPQDNDTO> findAllDoingQDNByLotId(Long id);

	public CPQDNDTO findCPQDN(Long id);

	public InvokeResult disposeCPQDN(CPQDNDTO cpQDNDTO);
	

}

