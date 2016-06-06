package org.seu.acetec.mes2Koala.facade.quartz.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.seu.acetec.mes2Koala.facade.EmployeeInfoSync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;


@Named
@Transactional
public class EmployeeBasicInfoTask {
	private static Logger LOGGER = Logger
			.getLogger(EmployeeBasicInfoTask.class);

	@Inject
	EmployeeInfoSync employeeInfoSync;

	//@Scheduled(cron = "0 0 2 ? * WED")
	//@Scheduled(cron = "30 * * * * ?")
	public void employeeInfoSync() {
		LOGGER.info("employeeInfoSync");
		employeeInfoSync.sync();
	}

	//@Scheduled(cron = "0 * * * * ?")
	// 每个星期三1点同步员工信息
	public void createUser() {
		LOGGER.info("createUser");
		employeeInfoSync.createUser();
	}
	
	//@Scheduled(cron = "10 * * * * ?")
	public void syncDepartment() {
		LOGGER.info("employeeInfoSync");
		employeeInfoSync.syncDepartment();
	}
	
	//@Scheduled(cron = "30 * * * * ?")
	public void syncJob() {
		LOGGER.info("syncJob");
		employeeInfoSync.syncJob();
	}
	
	//@Scheduled(cron = "50 * * * * ?")
	public void syncPosition() {
		LOGGER.info("syncPosition");
		employeeInfoSync.syncPosition();
	}
}
