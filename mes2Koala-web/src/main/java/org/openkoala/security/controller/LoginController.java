package org.openkoala.security.controller;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.openkoala.koala.commons.InvokeResult;
import org.openkoala.security.core.domain.EncryptService;
import org.openkoala.security.facade.SecurityAccessFacade;
import org.openkoala.security.facade.SecurityConfigFacade;
import org.openkoala.security.facade.command.ChangeUserPasswordCommand;
import org.openkoala.security.facade.command.LoginCommand;
import org.openkoala.security.facade.dto.UserDTO;
import org.openkoala.security.shiro.CurrentUser;
import org.openkoala.security.core.domain.User;
import org.seu.acetec.mes2Koala.facade.oa.OAClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 * 登陆用户控制器。
 *
 * @author lucas
 */
@Controller
@RequestMapping
@SuppressWarnings("unused")
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    @Inject
    protected EncryptService passwordEncryptService;
    @Inject
    private OAClient oaClient;
    @Inject
    private SecurityAccessFacade securityAccessFacade;

    @Inject
    private SecurityConfigFacade securityConfigFacade;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        if (CurrentUser.getSubject().isAuthenticated()) {
            return "redirect:index.koala";
        }
        return "login";
    }

    /**
     * 如果登陆了 就不用再次到登陆页面，直接就可以进入。
     * 用户登陆
     * @param request
     * @param command
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/loginOriginal", method = RequestMethod.POST)
    public InvokeResult loginOriginal(HttpServletRequest request, LoginCommand command) {
        InvokeResult invokeResult = doCaptcha(request);// 处理验证码
        if (!invokeResult.isSuccess()) {
            return invokeResult;
        } else {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                    command.getUsername(),
                    command.getPassword(),
                    command.getRememberMe());
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
    }

    /**
     * 如果登陆了 就不用再次到登陆页面，直接就可以进入。 用户登陆
     *
     * @param request
     * @param command
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public InvokeResult login(HttpServletRequest request, LoginCommand command) {
        InvokeResult invokeResult = doCaptcha(request);// 处理验证码
        if (!invokeResult.isSuccess()) {
            return invokeResult;
        } else {
            if (this.checkRightFromOA(command.getUsername(),
                    command.getPassword())) {
                UserDTO result = securityAccessFacade
                        .getUserByUserAccount(command.getUsername());
                String inputPassword = passwordEncryptService.encryptPassword(
                        User.INIT_PASSWORD,
                        result.getSalt() + command.getUsername());
                if (result.getUserPassword().equals(inputPassword)) {
                    ChangeUserPasswordCommand cupCommand = new ChangeUserPasswordCommand();
                    cupCommand.setUserAccount(command.getUsername());
                    cupCommand.setUserPassword(command.getPassword());
                    cupCommand.setOldUserPassword(User.INIT_PASSWORD);
                    securityConfigFacade.changeUserPassword(cupCommand);
                }
            }
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                    command.getUsername(), command.getPassword(),
                    command.getRememberMe());
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
    }

    private boolean checkRightFromOA(String username, String password) {
        String res = oaClient.login(username, password, "1");
        // TODO: oaClient如果没连上，jsonObject返回的不是JSON对象，这里转换就会失败，需要检查oaClient状态
        try {
            JSONObject jsonObject = JSONObject.fromObject(res);
            if ("success".equals(jsonObject.get("result").toString())) {
                return true;
            }
        } catch (JSONException ex) {
            // TODO
        }
        return false;
    }

    /**
     * 处理验证码
     *
     * @param request
     * @return
     */
    private InvokeResult doCaptcha(HttpServletRequest request) {
        String shiroLoginFailure = (String) request.getAttribute("shiroLoginFailure");
        if (!StringUtils.isBlank(shiroLoginFailure)) {
            return InvokeResult.failure("验证码错误。");
        }
        return InvokeResult.success();
    }
}
