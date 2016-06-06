package org.seu.acetec.mes2Koala.application;


import java.util.List;
import java.util.Set;

import  org.seu.acetec.mes2Koala.core.domain.CPWafer;
import org.seu.acetec.mes2Koala.core.enums.CPWaferCheck;
import org.seu.acetec.mes2Koala.core.enums.CPWaferState;

public interface CPWaferApplication extends GenericMES2Application<CPWafer> {

	public CPWafer getCPWafer(Long id);
	
	public void creatCPWafer(CPWafer cPWafer);
	
	public void updateCPWafer(CPWafer cPWafer);
	
	public void removeCPWafer(CPWafer cPWafer);
	
	public void removeCPWafers(Set<CPWafer> cPWafers);
	
	public List<CPWafer> findAllCPWafer();
	
	public void changeStatus(Long waferId ,CPWaferState passed);
	
	public void saveCheck(String waferIds , CPWaferCheck check);
	
}

