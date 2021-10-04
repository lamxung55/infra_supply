/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.authen.controller;

import com.mine.authen.bean.OamUser;
import com.mine.authen.bean.OamUserRole;
import com.mine.authen.service.UserManageServiceImpl;
import com.mine.authen.util.PasswordEncoder;
import com.mine.authen.util.SessionUtil;
import com.mine.exception.AppException;
import com.mine.exception.SysException;
import com.mine.util.*;
import org.apache.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;

@ManagedBean
//@Scope("view")
@ViewScoped
public class LoginController implements Serializable {
    private static final long serialVersionUID = -1334064777337405401L;
    private static Logger logger = Logger.getLogger(LoginController.class
            .getSimpleName());

    private String username;
    private String password;

    private String oldPassword;
    private String newPassword;

    private String captchaCode;


//	@PostConstruct
//	public void onStart(){
//		captcha.setReloadEnabled(true);
//	}

    public void login() throws AppException, GeneralSecurityException,
            IOException {

        if (!username.isEmpty() && !password.isEmpty()) {
            UserManageServiceImpl userManageService = new UserManageServiceImpl();
            try {
                List<OamUser> users = userManageService
                        .findByUserName(username);
                if (users.size() > 0) {
                    OamUser user = users.get(0);
                    String passEncryptInput = PasswordEncoder.get_SHA_512_SecurePassword(password, username);
                    if (user.getPassword().equals(passEncryptInput)) {
                        SessionUtil.getCurrentSession().invalidate();

                        SessionUtil.getCurrentSession().setAttribute(
                                Constants.HTTP_SESSION_ATTRIBUTE_USERNAME,
                                user.getUsername());
                        SessionUtil.getCurrentSession().setAttribute(
                                Constants.HTTP_SESSION_LOGGED, Boolean.TRUE);
                        SessionUtil.getCurrentSession().setAttribute(
                                Constants.HTTP_SESSION_USER_LOGGED,
                                Boolean.TRUE);
                        SessionUtil.getCurrentSession().setAttribute(
                                Constants.HTTP_SESSION_ATTRIBUTE_USERID,
                                user.getUserId());

                        List<OamUserRole> roles = userManageService
                                .findRoleByUserId(user.getUserId());
                        String pageRole = "";
                        String actionRole = "";
                        for (OamUserRole role : roles) {
                            pageRole = pageRole + ";" + role.getPageUrl();
                            actionRole = actionRole + ";" + role.getActionId();
                        }
                        SessionUtil.getCurrentSession().setAttribute(
                                Constants.HTTP_SESSION_ATTRIBUTE_PAGE_ROLE,
                                pageRole);
                        SessionUtil.getCurrentSession().setAttribute(
                                Constants.HTTP_SESSION_ATTRIBUTE_ACTION_ROLE,
                                actionRole);
                        logger.info(new Date() + ": Username: "
                                + user.getUsername() + " Đăng nhập thành công!");
                        new CheckRoles().redirectPage(Constants.HOME_PAGE);
                    } else {
                        MessageUtil
                                .setErrorMessage("Tài khoản hoặc mật khẩu không đúng !");
                    }
                } else {
                    MessageUtil
                            .setErrorMessage("Tài khoản hoặc mật khẩu không đúng!");
                }
            } catch (SysException s) {
                logger.error(s.getMessage(), s);
            } catch (AppException a) {
                logger.error(a.getMessage(), a);
            }
            captchaCode = "";
        }
    }

    public void savePassword() throws GeneralSecurityException,
            UnsupportedEncodingException {
        Long userId = (Long) SessionUtil.getCurrentSession().getAttribute(
                Constants.HTTP_SESSION_ATTRIBUTE_USERID);
        if (userId != null) {
            UserManageServiceImpl userManageService = new UserManageServiceImpl();
            try {
                //Check strong
                if (!isStrong(newPassword)) {
                    MessageUtil
                            .setErrorMessage("Mật khẩu không đủ mạnh!");
                    return;
                }
                OamUser user = userManageService.findById(userId);
                String savedEncrypt = PasswordEncoder.get_SHA_512_SecurePassword(user.getPassword(), user.getUsername());
                String oldEncrypt = PasswordEncoder.get_SHA_512_SecurePassword(oldPassword, user.getUsername());
                if (!savedEncrypt.equals(oldEncrypt)) {
                    MessageUtil
                            .setErrorMessage("Mật khẩu cũ không đúng!");
                    return;
                } else {
                    String newEncrypt = PasswordEncoder.get_SHA_512_SecurePassword(newPassword, user.getUsername());
                    user.setPassword(newEncrypt);
                    user.setLastModifiedDate(new Date());

                    userManageService.update(user);

                    MessageUtil
                            .setInfoMessage("Cáº­p nháº­t thÃ nh cÃ´ng máº­t kháº©u !");
                }
            } catch (Exception e) {
                logger.error(e);
            }
        }
    }

    private boolean isStrong(String password) {
        return password.matches("^(?=.*[A-Z].*[A-Z])(?=.*[!@#$&*])(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z].*[a-z])");

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }


    public String getCaptchaCode() {
        return captchaCode;
    }

    public void setCaptchaCode(String captchaCode) {
        this.captchaCode = captchaCode;
    }
}
