package org.seu.acetec.mes2Koala.facade;

import org.openkoala.koala.commons.InvokeResult;

public interface EmployeeInfoSync {

	public void sync();

	public void createUser();

	public void grantUser();
	
	public InvokeResult syncDepartment();
	
	public void syncJob();
	
	public void syncPosition();
}
