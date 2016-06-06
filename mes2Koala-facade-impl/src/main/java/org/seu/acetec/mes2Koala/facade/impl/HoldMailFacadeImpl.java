package org.seu.acetec.mes2Koala.facade.impl;

import javax.inject.Inject;

import org.openkoala.koala.commons.InvokeResult;
import org.openkoala.organisation.facade.EmployeeFacade;
import org.seu.acetec.mes2Koala.facade.HoldMailFacade;

public class HoldMailFacadeImpl implements HoldMailFacade {

	private String holdUsers;

	private String holdTDE;

	private String holdQA;

	private String holdPP;

	private String holdCS;

	private String holdEE;

	@Inject
	private EmployeeFacade employeeFacade;

	public String getHoldUsers() {
		return holdUsers;
	}

	public void setHoldUsers(String holdUsers) {
		this.holdUsers = holdUsers;
	}

	public String getHoldTDE() {
		return holdTDE;
	}

	public void setHoldTDE(String holdTDE) {
		this.holdTDE = holdTDE;
	}

	public String getHoldQA() {
		return holdQA;
	}

	public void setHoldQA(String holdQA) {
		this.holdQA = holdQA;
	}

	public String getHoldPP() {
		return holdPP;
	}

	public void setHoldPP(String holdPP) {
		this.holdPP = holdPP;
	}

	public String getHoldCS() {
		return holdCS;
	}

	public void setHoldCS(String holdCS) {
		this.holdCS = holdCS;
	}

	public String getHoldEE() {
		return holdEE;
	}

	public void setHoldEE(String holdEE) {
		this.holdEE = holdEE;
	}

	@Override
	public InvokeResult findHoldUsers() {
		return InvokeResult.success(employeeFacade.findEmployeeByAccount(this
				.getHoldUsers()));
	}

	@Override
	public InvokeResult findHoldTDE() {
		return InvokeResult.success(employeeFacade.findEmployeeByAccount(this
				.getHoldTDE()));
	}

	@Override
	public InvokeResult findHoldQA() {
		return InvokeResult.success(employeeFacade.findEmployeeByAccount(this
				.getHoldQA()));
	}

	@Override
	public InvokeResult findHoldPP() {
		return InvokeResult.success(employeeFacade.findEmployeeByAccount(this
				.getHoldPP()));
	}

	@Override
	public InvokeResult findHoldCS() {
		return InvokeResult.success(employeeFacade.findEmployeeByAccount(this
				.getHoldCS()));
	}

	@Override
	public InvokeResult findHoldEE() {
		return InvokeResult.success(employeeFacade.findEmployeeByAccount(this
				.getHoldEE()));
	}

}
