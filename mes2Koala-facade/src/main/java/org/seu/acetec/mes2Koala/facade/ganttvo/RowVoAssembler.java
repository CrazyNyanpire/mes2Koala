package org.seu.acetec.mes2Koala.facade.ganttvo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.seu.acetec.mes2Koala.core.domain.TestSys;


public class RowVoAssembler implements Serializable {
	
	static public RowVo toVo( TestSys testSys ){
		if ( testSys == null )
			return null;
		RowVo rowVo = new RowVo();
		rowVo.setId(testSys.getId().toString());
		rowVo.setVersion(testSys.getVersion());
//		rowVo.setName(testSys.getNumber());
//		rowVo.setMateEquipment(testSys.getMateEquipment());
		rowVo.setTasks(TaskVoAssembler.toVos(testSys.getProductions()));
		return rowVo;
	}
	
	static public List<RowVo> toVos( Collection<TestSys> testSyses ){
		if ( testSyses == null )
			return null;
		List<RowVo> rowVos = new ArrayList<RowVo>();
		for (TestSys testSys : testSyses) {
			rowVos.add(toVo(testSys));
		}
		return rowVos;
	}
	
	static public TestSys toEntity( RowVo rowVo ){
		if ( rowVo == null )
			return null;
		TestSys testSys = new TestSys();
		testSys.setId(Long.parseLong(rowVo.getId()));
		testSys.setVersion(rowVo.getVersion());
//		testSys.setNumber(rowVo.getName());
//		testSys.setState(rowVo.getState());
//		testSys.setMateEquipment(rowVo.getMateEquipment());
		//将rowVo中的taskVo中的testSysId字段设置为rowVo的id
		refreshTaskVo(rowVo);
		testSys.setProductions(TaskVoAssembler.toEntities(rowVo.getTasks()));
		return testSys;
	}
	
	static public List<TestSys> toEntities( Collection<RowVo> rowVos ){
		if ( rowVos == null )
			return null;
		List<TestSys> testSys = new ArrayList<TestSys>();
		for (RowVo rowVo : rowVos) {
			testSys.add(RowVoAssembler.toEntity(rowVo));
		}
		return testSys;
	}
	
	private static void refreshTaskVo( RowVo rowVo ){
		List<TaskVo> taskVos = rowVo.getTasks();
		for (TaskVo taskVo : taskVos) {
			taskVo.setTestSysId(rowVo.getId());
		}
	}

}
