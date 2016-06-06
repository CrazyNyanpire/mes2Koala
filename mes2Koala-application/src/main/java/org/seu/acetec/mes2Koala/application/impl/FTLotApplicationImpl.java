package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.FTCompostedTestApplication;
import org.seu.acetec.mes2Koala.application.FTLotApplication;
import org.seu.acetec.mes2Koala.application.FTProductionScheduleApplication;
import org.seu.acetec.mes2Koala.application.IncrementNumberApplication;
import org.seu.acetec.mes2Koala.core.domain.CustomerFTLot;
import org.seu.acetec.mes2Koala.core.domain.FTComposedTestNode;
import org.seu.acetec.mes2Koala.core.domain.FTLot;
import org.seu.acetec.mes2Koala.core.domain.FTProductionSchedule;
import org.seu.acetec.mes2Koala.core.domain.MES2AbstractEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Named
@Transactional
public class FTLotApplicationImpl extends GenericMES2ApplicationImpl<FTLot>
		implements FTLotApplication {
	
	@Inject
	private FTProductionScheduleApplication ftProductionScheduleApplication;

	@Inject
	private IncrementNumberApplication incrementNumberApplication;

	@Override
	public FTLot findByFTQDNId(Long id) {
		throw new UnsupportedOperationException("Todo");
	}

    public FTLot findByProcessId(Long id) {
        return findOne("select o from FTLot o where o.ftProcess.id = ?", id);
    }

	/**
	 * @param ftLot
	 */
	@Override
	public void createCheckedFTLot(FTLot ftLot) {
		checkFTLotNumber(ftLot);
		checkRCNumber(ftLot);
		create(ftLot);
	}

	private void checkFTLotNumber(FTLot ftLot) {
		if (null == ftLot.getInternalLotNumber())
			throw new RuntimeException("艾科批号为空，不可下单");

		CustomerFTLot customerFTLot = ftLot.getCustomerFTLot();
		
		//原本需要检查，现将检查取消，直接存入前端传入的艾科批号
		/*
		String peek = incrementNumberApplication.peekFTLotNumber(customerFTLot);
		if (!Objects.equals(peek, ftLot.getInternalLotNumber())
				&& !ftLot.getInternalLotNumber().contains("-R")) {
			throw new RuntimeException("艾科批号错误");
		}
		*/
		incrementNumberApplication.nextFTLotNumber(customerFTLot);
	}

	private void checkRCNumber(FTLot ftLot) {
		if (null == ftLot.getRcNumber())
			throw new RuntimeException("RC编号为空，不可下单");
		CustomerFTLot customerFTLot = ftLot.getCustomerFTLot();
		String peek = incrementNumberApplication.peekRCNumber(customerFTLot);
		if (!Objects.equals(peek, ftLot.getRcNumber())) {
			throw new RuntimeException("RC编号错误");
		}
		incrementNumberApplication.nextRCNumber(customerFTLot);
	}

	@Override
	public List<FTLot> findByIds(Long[] ids) {
		if (ids == null || ids.length <= 0)
			throw new IllegalArgumentException();

		List<FTLot> result = new ArrayList<>();
		try {
			for (Long id : ids) {
				result.add(get(id));
			}
			return result;
		} catch (Exception e) {
			throw new RuntimeException("获取FTLot列表时错误");
		}
	}

	@Override
	public FTLot findByProductionId(Long id) {
		FTProductionSchedule ftProductionSchedule = ftProductionScheduleApplication.get(id);
		FTLot result = MES2AbstractEntity.load(FTLot.class, ftProductionSchedule.getFtComposedTestNode().getFtProcess().getFtLot().getId());
		return result;
//		return ftProductionSchedule.getFtComposedTestNode().getFtProcess().getFtLot();
	}
}




















