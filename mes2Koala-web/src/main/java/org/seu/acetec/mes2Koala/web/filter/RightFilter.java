package org.seu.acetec.mes2Koala.web.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.codehaus.jackson.map.ObjectMapper;
import org.openkoala.framework.i18n.I18NManager;
import org.openkoala.koala.commons.InvokeResult;
import org.openkoala.security.controller.LoginController;
import org.openkoala.security.facade.SecurityAccessFacade;
import org.openkoala.security.facade.dto.MenuResourceDTO;
import org.openkoala.security.facade.dto.PermissionDTO;
import org.openkoala.security.facade.dto.RoleDTO;
import org.openkoala.security.facade.dto.UrlAccessResourceDTO;
import org.openkoala.security.facade.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;


/**
 * 权限过滤
 * 
 * @author harlow
 * @date 2016-04-27 下午5:37:38
 */
public class RightFilter extends OncePerRequestFilter {

	public static final String MSG_SUCCESS = "success";

	public static final String MSG_FAIL = "fail";

	public static final String MSG_NO_RIGHT = "noRight";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	@Inject
	private SecurityAccessFacade securityAccessFacade;
	// 不过滤的uri
	public static String[] NOT_FILTER = new String[] { "login.koala",
			"index.koala", "/auth/", "/log/", "/employee/", "/job/",
			"/organization/", "/post/","/SystemDictionary/" };

	private Map<String, List<RoleDTO>> roleListMap = new HashMap<String, List<RoleDTO>>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// 请求的uri
		String uri = request.getRequestURI();

		// uri中包含background时才进行过滤
		if (uri.indexOf(".koala") != -1 && this.checkNotFilter(uri)) {
			// 执行过滤
			// 从session中获取登录者实体
			ObjectMapper mapper = new ObjectMapper();
			String userName = request.getParameter("userName");
			String password = request.getParameter("password");
			if (userName == null) {
				String data = request.getParameter("data");
				if (data != null) {
					Map<String, Object> maps = mapper
							.readValue(data, Map.class);
					userName = maps.get("userName").toString();
					password = maps.get("password").toString();
				}
			}
			if (userName != null && password != null) {
				String identifier = request.getRequestURL().toString();
				InvokeResult passwordCheck = this.passwordCheck(userName, password);
				if(passwordCheck.isHasErrors()){
					// 设置request和response的字符集，防止乱码
					request.setCharacterEncoding("UTF-8");
					response.setCharacterEncoding("UTF-8");
					response.setContentType("application/json;charset=UTF-8");
					PrintWriter out = response.getWriter();
					JSONObject jsonObject = JSONObject.fromObject(passwordCheck);
					out.print(jsonObject.toString());
					return ;
				}
			}
		}
		// 如果uri中不包含background，则继续
		filterChain.doFilter(request, response);
	}

	private boolean checkNotFilter(String uri) {
		for (String s : NOT_FILTER) {
			if (uri.indexOf(s) != -1 && uri.length() > 1) {
				// 如果uri中包含不过滤的uri，则不进行过滤
				return false;
			}
		}
		return true;
	}

	private InvokeResult passwordCheck(String useraccount, String password) {
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
        		useraccount,
        		password);
        try {
            SecurityUtils.getSubject().login(usernamePasswordToken);
            return InvokeResult.success();
        } catch (UnknownAccountException e) {
            LOGGER.error(e.getMessage(), e);
            return InvokeResult.failure("账号或者密码不存在。");
        } catch (LockedAccountException e) {
            LOGGER.error(e.getMessage(), e);
            return InvokeResult.failure("该用户已禁用，请联系管理员。");
        } catch (AuthenticationException e) {
            LOGGER.error(e.getMessage(), e);
            return InvokeResult.failure("账号或者密码不正确。");
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return InvokeResult.failure("登录失败。");
        }
	}

	private boolean rightCheck(String useraccount, String identifier) {
		UserDTO userDTO = securityAccessFacade.getUserByUserAccount(useraccount);
		UserDTO userRoleDTO = (UserDTO)securityAccessFacade.findInfoOfUser(userDTO.getId()).getData();
			for (PermissionDTO permissionDTO : userRoleDTO.getPermissions()) {
				// WaferProcess/getFutureHoldReason/\\d*.koala
				Pattern pattern = Pattern.compile(permissionDTO.getUrl());
				Matcher matcher = pattern.matcher(identifier);
				if (matcher.find()) {
					return true;
				}
			}
		return false;
	}
}