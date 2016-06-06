package org.seu.acetec.mes2Koala.application;


import java.util.List;
import java.util.Set;

import org.seu.acetec.mes2Koala.core.domain.CPLot;
import  org.seu.acetec.mes2Koala.core.domain.CPLotOptionLog;
import org.seu.acetec.mes2Koala.core.domain.CPNode;

public interface CPLotOptionLogApplication extends GenericMES2Application<CPLotOptionLog>{

	public CPLotOptionLog getCPLotOptionLog(Long id);
	
	public void creatCPLotOptionLog(CPLotOptionLog cPLotOptionLog);
	
	public void updateCPLotOptionLog(CPLotOptionLog cPLotOptionLog);
	
	public void removeCPLotOptionLog(CPLotOptionLog cPLotOptionLog);
	
	public void removeCPLotOptionLogs(Set<CPLotOptionLog> cPLotOptionLogs);
	
	public List<CPLotOptionLog> findAllCPLotOptionLog();

	public void createStartCPNode(CPLot cpLot, CPNode cpNode);

	void createEndCPNode(CPLot cpLot, CPNode cpNode);

	void createCPNode(CPLot cpLot, CPNode cpNode, String optType, String remark);

	public void removeCPLotOptionLogByLotId(Long id);
	
}

