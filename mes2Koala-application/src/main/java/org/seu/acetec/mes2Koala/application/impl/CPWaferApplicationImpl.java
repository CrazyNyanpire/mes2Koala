package org.seu.acetec.mes2Koala.application.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;
import org.seu.acetec.mes2Koala.application.CPWaferApplication;
import org.seu.acetec.mes2Koala.application.GenericMES2Application;
import org.seu.acetec.mes2Koala.core.domain.CPLot;
import org.seu.acetec.mes2Koala.core.domain.CPWafer;
import org.seu.acetec.mes2Koala.core.domain.CPWaferCheckLog;
import org.seu.acetec.mes2Koala.core.enums.CPWaferCheck;
import org.seu.acetec.mes2Koala.core.enums.CPWaferState;

@Named
@Transactional
public class CPWaferApplicationImpl extends GenericMES2ApplicationImpl<CPWafer>
		implements CPWaferApplication {

	public CPWafer getCPWafer(Long id) {
		return CPWafer.get(CPWafer.class, id);
	}

	public void creatCPWafer(CPWafer cPWafer) {
		cPWafer.save();
	}

	public void updateCPWafer(CPWafer cPWafer) {
		cPWafer.save();
	}

	public void removeCPWafer(CPWafer cPWafer) {
		if (cPWafer != null) {
			cPWafer.remove();
		}
	}

	public void removeCPWafers(Set<CPWafer> cPWafers) {
		for (CPWafer cPWafer : cPWafers) {
			cPWafer.remove();
		}
	}

	public List<CPWafer> findAllCPWafer() {
		return CPWafer.findAll(CPWafer.class);
	}

	@Override
	public void changeStatus(Long waferId, CPWaferState passed) {
		CPWafer cpWafer = CPWafer.get(CPWafer.class, waferId);
		cpWafer.setState(passed);
		cpWafer.save();
	}

	@Override
	public void saveCheck(String waferIds, CPWaferCheck cpWaferCheck) {
		Long cpLotId = null;
		for (String id : waferIds.split(",")) {
			CPWafer cpWafer = CPWafer.get(CPWafer.class, Long.valueOf(id));
			cpLotId = cpWafer.getCpLot().getId();
			cpWafer.setCpWaferCheck(cpWaferCheck);
			cpWafer.save();
			CPWaferCheckLog cpWaferCheckLog = new CPWaferCheckLog();
			cpWaferCheckLog.setCpWafer(cpWafer);
			cpWaferCheckLog.setCpLot(cpWafer.getCpLot());
			cpWaferCheckLog.setNode(cpWafer.getCpLot().getCurrentState());
			cpWaferCheckLog.setCreateTimestamp(new Date());
			cpWaferCheckLog.setLastModifyTimestamp(cpWaferCheckLog
					.getCreateTimestamp());
			cpWaferCheckLog.save();
			
		}
		if(cpLotId != null){
			List<CPWafer> cpWafers = this.find("select e from CPWafer e where e.cpLot.id = " + cpLotId);
			for(CPWafer cpWafer : cpWafers){
				cpWafer.setState(CPWaferState.PASSED);
				cpWafer.save();
			}
		}

	}
}
