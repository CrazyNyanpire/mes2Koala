package org.seu.acetec.mes2Koala.web.controller;

import org.apache.log4j.Logger;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.EmployeeInfoSync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@Controller
@RequestMapping("/EmployeeSync")
public class EmployeeSyncController {

	private static Logger LOGGER = Logger
			.getLogger(EmployeeSyncController.class);

	@Inject
	EmployeeInfoSync employeeInfoSync;

	@ResponseBody
	@RequestMapping("/sync")
	public InvokeResult sync() {
		LOGGER.info("sync");
		employeeInfoSync.syncDepartment();
		employeeInfoSync.syncPosition();
		employeeInfoSync.sync();
		return InvokeResult.success();
	}
	
	@ResponseBody
	@RequestMapping("/employeeInfoSync")
	public InvokeResult employeeInfoSync() {
		LOGGER.info("employeeInfoSync");
		employeeInfoSync.sync();
		return InvokeResult.success();
	}

	@ResponseBody
	@RequestMapping("/addUser")
	public InvokeResult createUser() {
		LOGGER.info("createUser");
		employeeInfoSync.createUser();
		return InvokeResult.success();
	}

	@ResponseBody
	@RequestMapping("/syncDepartment")
	public InvokeResult syncDepartment() {
		LOGGER.info("employeeInfoSync");
		return employeeInfoSync.syncDepartment();
	}

	@ResponseBody
	@RequestMapping("/syncJob")
	public InvokeResult syncJob() {
		LOGGER.info("syncJob");
		employeeInfoSync.syncJob();
		return InvokeResult.success();
	}

	@ResponseBody
	@RequestMapping("/syncPosition")
	public InvokeResult syncPosition() {
		LOGGER.info("syncPosition");
		employeeInfoSync.syncPosition();
		return InvokeResult.success();
	}
}
