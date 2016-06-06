package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.CPLotApplication;
import org.seu.acetec.mes2Koala.application.CPProductionScheduleApplication;
import org.seu.acetec.mes2Koala.application.IncrementNumberApplication;
import org.seu.acetec.mes2Koala.core.domain.CPLot;
import org.seu.acetec.mes2Koala.core.domain.CPProductionSchedule;
import org.seu.acetec.mes2Koala.core.domain.CustomerCPLot;
import org.seu.acetec.mes2Koala.core.domain.MES2AbstractEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Objects;

/**
 * @author 阙宇翔
 * @version 2016/3/28 YuangQue
 */

@Named
@Transactional
public class CPLotApplicationImpl extends GenericMES2ApplicationImpl<CPLot>
		implements CPLotApplication {
	
	@Inject
	CPProductionScheduleApplication cpProductionScheduleApplication;

	@Inject
	IncrementNumberApplication incrementNumberApplication;

	@Override
	public void createCheckedCPLot(CPLot cpLot) {
		this.createCheckedCPLot(cpLot, true, true);
	}

	@Override
	public void createCheckedCPLot(CPLot cpLot, boolean checkLotNo,
			boolean checkRCNumber) {
		if (checkLotNo)
			checkedCPLOtNumber(cpLot);
		if (checkRCNumber)
			checkRCNumber(cpLot);
		create(cpLot);
	}

	private void checkedCPLOtNumber(CPLot cpLot) {
		if (null == cpLot.getInternalLotNumber())
			throw new RuntimeException("艾科批号为空，不可下单");

		CustomerCPLot customerCPLot = cpLot.getCustomerCPLot();
		String peek = incrementNumberApplication.peekCPLotNumber(customerCPLot);
		if (!Objects.equals(peek, cpLot.getInternalLotNumber())) {
			throw new RuntimeException("艾科批号错误");
		}
		incrementNumberApplication.nextCPLotNumber(customerCPLot);
	}

	private void checkRCNumber(CPLot cpLot) {
		if (null == cpLot.getRcNumber())
			throw new RuntimeException("RC编号为空，不可下单");
		CustomerCPLot customerCPLot = cpLot.getCustomerCPLot();
		String peek = incrementNumberApplication.peekRCNumber(customerCPLot);
		if (!Objects.equals(peek, cpLot.getRcNumber())) {
			throw new RuntimeException("RC编号错误");
		}
		incrementNumberApplication.nextRCNumber(customerCPLot);
	}

	@Override
	public CPLot findByProductionId(Long id) {
		CPProductionSchedule cpProductionSchedule = cpProductionScheduleApplication.get(id);
		CPLot result = MES2AbstractEntity.load(CPLot.class, cpProductionSchedule.getCpTestingNode().getCpProcess().getCpLot().getId());
		return result;
		//return cpProductionSchedule.getCpTestingNode().getCpProcess().getCpLot();
		
	}
}
