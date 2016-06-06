package org.seu.acetec.mes2Koala.application;


import java.util.List;

import org.seu.acetec.mes2Koala.core.domain.TestSys;

public interface TestSysApplication extends GenericMES2Application<TestSys> {

	void createOrUpdate(List<TestSys> entities);
	
	List<TestSys> findAllTestSysByTestType( String testType );

}

