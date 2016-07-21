package org.seu.acetec.mes2Koala.web.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import javax.inject.Inject;

import org.apache.commons.beanutils.BeanUtils;
import org.openkoala.security.facade.SecurityAccessFacade;
import org.openkoala.security.facade.dto.UserDTO;
import org.openkoala.security.shiro.CurrentUser;

public class BaseController {

	private static String createEmployNo = "createEmployNo";

	private static String createTimestamp = "createTimestamp";

	private static String lastModifyEmployNo = "lastModifyEmployNo";

	private static String lastModifyTimestamp = "lastModifyTimestamp";

	private static String username = "username";

	@Inject
	SecurityAccessFacade securityAccessFacade;

	protected <T> T createBase(T object) {

		String modifyUser = null;

		try {
			modifyUser = BeanUtils.getProperty(object, username);
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {

			if (modifyUser != null && !"".equalsIgnoreCase(modifyUser)) {
				UserDTO userDTO = this.securityAccessFacade
						.getUserByUserAccount(modifyUser);
				BeanUtils.setProperty(object, createEmployNo,
						userDTO.getUserAccount() + "|" + userDTO.getName());
				BeanUtils.setProperty(object, lastModifyEmployNo,
						userDTO.getUserAccount() + "|" + userDTO.getName());
			} else {
				BeanUtils.setProperty(object, createEmployNo,
						CurrentUser.getUserAccount() + "|"
								+ CurrentUser.getPrincipal().getName());
				BeanUtils.setProperty(object, lastModifyEmployNo,
						CurrentUser.getUserAccount() + "|"
								+ CurrentUser.getPrincipal().getName());
			}
			BeanUtils.setProperty(object, createTimestamp, new Date());
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
		String modifyUser = null;

		try {
			modifyUser = BeanUtils.getProperty(object, username);
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			if (modifyUser != null && !"".equalsIgnoreCase(modifyUser)) {
				UserDTO userDTO = this.securityAccessFacade
						.getUserByUserAccount(modifyUser);
				BeanUtils.setProperty(object, lastModifyEmployNo,
						userDTO.getUserAccount() + "|" + userDTO.getName());
			} else {
				BeanUtils.setProperty(object, lastModifyEmployNo,
						CurrentUser.getUserAccount() + "|"
								+ CurrentUser.getPrincipal().getName());
			}
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
