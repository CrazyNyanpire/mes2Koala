package org.seu.acetec.mes2Koala.facade.ganttvo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.seu.acetec.mes2Koala.core.domain.FTLot;
import org.seu.acetec.mes2Koala.core.domain.ProductionSchedule;
import org.seu.acetec.mes2Koala.core.domain.TestSys;
import org.seu.acetec.mes2Koala.core.common.Mes2DateUtils;

/**
 * @deprecated 甘特图弃用，该类弃用
 * @author Administrator
 *
 */
public class TaskVoAssembler {
	
	final private static int assumeTimeIncreIfPlanedTimeIsNull = 5;

	static public TaskVo toVo( ProductionSchedule productionSchedule ){
		if ( productionSchedule == null )
			return null;
		
		TaskVo taskVo = new TaskVo();
		taskVo.setName(productionSchedule.getLotNumber());
		taskVo.setId(productionSchedule.getId().toString());
		taskVo.setAmount(productionSchedule.getAmount());
		taskVo.setDoneQty(productionSchedule.getDoneQty());
		taskVo.setNote(productionSchedule.getNote());
		taskVo.setState(productionSchedule.getState());

		TestSys testSys = productionSchedule.getTestSys();
		taskVo.setTestSysId( testSys.getId().toString());
//		taskVo.setTestSysName( testSys.getNumber());

//		taskVo.setFtLotId(productionSchedule.getFtLot().getId().toString());
		taskVo.setVersion(productionSchedule.getVersion());
		if ( productionSchedule.getPlanedStartTimestamp() == null ){
			taskVo.setFrom(Mes2DateUtils.formaterDate((new Date()), Mes2DateUtils.DefFormatGanttDateString));
		} else {
			taskVo.setFrom(Mes2DateUtils.formaterDate(productionSchedule.getPlanedStartTimestamp(), Mes2DateUtils.DefFormatGanttDateString));
		}

		if ( productionSchedule.getPlanedEndTimestamp() == null ){
			taskVo.setTo(Mes2DateUtils.formaterDate(Mes2DateUtils.addHours((new Date()), assumeTimeIncreIfPlanedTimeIsNull), Mes2DateUtils.DefFormatGanttDateString));
		} else {
			taskVo.setTo(Mes2DateUtils.formaterDate(productionSchedule.getPlanedEndTimestamp(), Mes2DateUtils.DefFormatGanttDateString));
		}
		
		if ( productionSchedule.getActualStartTimestamp() == null ){
			taskVo.setActualFrom(Mes2DateUtils.formaterDate((new Date()), Mes2DateUtils.DefFormatGanttDateString));
		} else {
			taskVo.setActualFrom(Mes2DateUtils.formaterDate(productionSchedule.getActualStartTimestamp(), Mes2DateUtils.DefFormatGanttDateString));
		}

		if ( productionSchedule.getActualEndTimestamp() == null ){
			taskVo.setActualTo(Mes2DateUtils.formaterDate(Mes2DateUtils.addHours((new Date()), assumeTimeIncreIfPlanedTimeIsNull), Mes2DateUtils.DefFormatGanttDateString));
		} else {
			taskVo.setActualTo(Mes2DateUtils.formaterDate(productionSchedule.getActualEndTimestamp(), Mes2DateUtils.DefFormatGanttDateString));
		}
		
		return taskVo;
	}
	
	static public List<TaskVo> toVos ( Collection<ProductionSchedule> productionSchedules ){
		if ( productionSchedules == null )
			return null;
		List<TaskVo> taskVos = new ArrayList<TaskVo>();
		for (ProductionSchedule productionSchedule : productionSchedules) {
			taskVos.add(toVo(productionSchedule));
		}
		return taskVos;
	}
	
	static public ProductionSchedule toEntity( TaskVo taskVo ){
		if ( taskVo == null )
			return null;
		ProductionSchedule productionSchedule = new ProductionSchedule();
		productionSchedule.setId(Long.parseLong(taskVo.getId()));
		productionSchedule.setLotNumber(taskVo.getName());
		productionSchedule.setAmount(taskVo.getAmount());
		productionSchedule.setDoneQty(taskVo.getDoneQty());
		productionSchedule.setNote(taskVo.getNote());
		productionSchedule.setState(taskVo.getState());
		productionSchedule.setVersion(taskVo.getVersion());
		
		//设置实体中与之关联的testSys
		TestSys testSys = new TestSys();
		if ( taskVo.getTestSysId() != null && !"".equals(taskVo.getTestSysId())){
			testSys.setId(Long.parseLong(taskVo.getTestSysId()));
		} else {
			//如果前端传回的vo中不存在该字段则设置为1（待排产）
			testSys.setId(1L);
		}
		productionSchedule.setTestSys(testSys);
		
		//设置实体中与之关联的internalLot
		FTLot ftLot = new FTLot();
		ftLot.setId(Long.parseLong(taskVo.getFtLotId()));
//		productionSchedule.setFtLot(ftLot);

		
		if ( "1".equals(taskVo.getTestSysId()) ){
			productionSchedule.setPlanedStartTimestamp(null);
			productionSchedule.setPlanedEndTimestamp(null);
			productionSchedule.setActualStartTimestamp(null);
			productionSchedule.setActualEndTimestamp(null);
		} else {
			productionSchedule.setPlanedStartTimestamp(Mes2DateUtils.str2Date(taskVo.getFrom().replace("Z", " UTC"), Mes2DateUtils.DefFormatGanttDateString));
			productionSchedule.setPlanedEndTimestamp(Mes2DateUtils.str2Date(taskVo.getTo().replace("Z", " UTC"), Mes2DateUtils.DefFormatGanttDateString));
			productionSchedule.setActualStartTimestamp(Mes2DateUtils.str2Date(taskVo.getActualFrom(), Mes2DateUtils.DefFormatExtString));
			productionSchedule.setActualEndTimestamp(Mes2DateUtils.str2Date(taskVo.getActualTo(), Mes2DateUtils.DefFormatGanttDateString));
		}
		return productionSchedule;
	}
	
	static public List<ProductionSchedule> toEntities( Collection<TaskVo> taskVos ){
		if ( taskVos == null )
			return null;
		List<ProductionSchedule> productionSchedules = new ArrayList<ProductionSchedule>();
		for (TaskVo taskVo : taskVos) {
			productionSchedules.add(toEntity(taskVo));
		}
		return productionSchedules;
	}

}
