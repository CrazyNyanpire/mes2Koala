package org.seu.acetec.mes2Koala.application.impl;

import java.util.List;

import javax.inject.Named;

import org.seu.acetec.mes2Koala.application.TestSysApplication;
import org.seu.acetec.mes2Koala.core.domain.TestSys;
import org.springframework.transaction.annotation.Transactional;

@Named
@Transactional
public class TestSysApplicationImpl extends GenericMES2ApplicationImpl<TestSys> implements TestSysApplication {
	

	@Override
	public void createOrUpdate(List<TestSys> entities) {
		if ( null == entities )
			throw new IllegalArgumentException("参数非法，无法创建（更新）机台");
		
		for (TestSys testSys : entities) {
			TestSys temp;
			if ( "FT".equals(testSys.getTestType()) )
				temp = getTestSysByProberOrHandlerNumber( testSys.getProberOrHandlerNumber() );
			else if ( "CP".equals(testSys.getTestType()) )
				temp = getTestSysByTesterNumber(testSys.getTesterNumber());
			else
				continue;
			if ( temp == null )
				create(testSys);
			else{
				updateEntity( testSys, temp );
				update(temp);
			}
		}
		
	}

	private TestSys getTestSysByProberOrHandlerNumber(String proberOrHandlerNumber) {
		return findOne("select _t from TestSys _t where _t.proberOrHandlerNumber = ?", proberOrHandlerNumber);
	}

	private TestSys getTestSysByTesterNumber(String testerNumber) {
		return findOne("select _t from TestSys _t where _t.testerNumber = ?", testerNumber);
	}

	private void updateEntity(TestSys sourceEntity, TestSys targetEntity) {
		if ( sourceEntity == null || targetEntity == null )
			throw new IllegalArgumentException("参数非法，无法更新实体");
		targetEntity.setEmsPlatformId(sourceEntity.getEmsPlatformId());
		targetEntity.setTesterNumber(sourceEntity.getTesterNumber());
		targetEntity.setProberOrHandlerNumber(sourceEntity.getProberOrHandlerNumber());
		targetEntity.setState(sourceEntity.getState());
		targetEntity.setPlatformNumber(null);	//setter自有逻辑
	}

	public TestSys getTestSysByEmsId(Long emsId) {
        return findOne("select _t from TestSys _t where _t.emsId = ?", emsId);
	}

	@Override
	public List<TestSys> findAllTestSysByTestType(String testType) {
		return find("select _t from TestSys _t where _t.testType like ?", testType );
		
	}
}
