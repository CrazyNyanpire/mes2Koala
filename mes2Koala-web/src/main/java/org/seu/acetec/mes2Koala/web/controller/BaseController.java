package org.seu.acetec.mes2Koala.web.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.openkoala.security.shiro.CurrentUser;

public class BaseController {

	private static String createEmployNo = "createEmployNo";

	private static String createTimestamp = "createTimestamp";

	private static String lastModifyEmployNo = "lastModifyEmployNo";

	private static String lastModifyTimestamp = "lastModifyTimestamp";

	protected <T> T createBase(T object) {
		try {
			BeanUtils.setProperty(object, createEmployNo,
					CurrentUser.getUserAccount() + "|"
							+ CurrentUser.getPrincipal().getName());
			BeanUtils.setProperty(object, createTimestamp, new Date());
			BeanUtils.setProperty(object, lastModifyEmployNo,
					CurrentUser.getUserAccount() + "|"
							+ CurrentUser.getPrincipal().getName());
			BeanUtils.setProperty(object, lastModifyTimestamp, new Date());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}

	protected <T> T lastModifyBase(T object) {
		try {
			BeanUtils.setProperty(object, lastModifyEmployNo,
					CurrentUser.getUserAccount() + "|"
							+ CurrentUser.getPrincipal().getName());
			BeanUtils.setProperty(object, lastModifyTimestamp, new Date());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}
}
