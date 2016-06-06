package org.seu.acetec.mes2Koala.application;


import java.util.List;
import java.util.Set;

import org.seu.acetec.mes2Koala.core.domain.CPProcess;
import  org.seu.acetec.mes2Koala.core.domain.CPQDN;

public interface CPQDNApplication extends GenericMES2Application<CPQDN>{

	public CPQDN getCPQDN(Long id);
	
	public void creatCPQDN(CPQDN cPQDN);
	
	public void updateCPQDN(CPQDN cPQDN);
	
	public void removeCPQDN(CPQDN cPQDN);
	
	public void removeCPQDNs(Set<CPQDN> cPQDNs);
	
	public List<CPQDN> findAllCPQDN();

	public List<CPQDN> findAllDoingByCPLotId(Long id);

	public CPQDN findByCPLotId(Long cpLotId);
	
}

