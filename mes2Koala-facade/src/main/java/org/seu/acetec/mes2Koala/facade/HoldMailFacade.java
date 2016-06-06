package org.seu.acetec.mes2Koala.facade;

import org.openkoala.koala.commons.InvokeResult;

public interface HoldMailFacade {
	public InvokeResult findHoldUsers();

	public InvokeResult findHoldTDE();

	public InvokeResult findHoldQA();

	public InvokeResult findHoldPP();

	public InvokeResult findHoldCS();

	public InvokeResult findHoldEE();
}
