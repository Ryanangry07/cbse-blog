package com.loloao.controller;

import com.loloao.common.Base;
import com.loloao.common.Result;
import com.loloao.entity.User;
import com.loloao.enums.ResultCode;
import com.loloao.oauth.OAuthSessionManager;
import com.loloao.service.UserService;
import com.loloao.utils.UserUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
public class AuthController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        Result result = new Result();
        loginUser(user.getAccount(), user.getPassword(), result);
        return result;
    }


    @PostMapping("/register")
    //@RequiresRoles(Base.ROLE_ADMIN)
    public Result register(@RequestBody User user) {

        Result result = new Result();

        User temp = userService.getUserByAccount(user.getAccount());
        if (null != temp) {
            result.setResultCode(ResultCode.USER_HAS_EXISTED);
            return result;
        }

        String account = user.getAccount();
        String password = user.getPassword();

        Long userId = userService.saveUser(user);

        if (userId > 0) {
            loginUser(account, password, result);
        } else {
            result.setResultCode(ResultCode.USER_Register_ERROR);
        }
        return result;
    }



    private void loginUser(String account, String password, Result result) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(account, password);

        try {
            subject.login(token);

            User currentUser = userService.getUserByAccount(account);
            System.out.println("SessionID == set user ==" + subject.getSession().getId());
            subject.getSession().setAttribute(Base.CURRENT_USER, currentUser);

            result.setResultCode(ResultCode.SUCCESS);
            result.simple().put(OAuthSessionManager.OAUTH_TOKEN, subject.getSession().getId());
        } catch (UnknownAccountException e) {
            result.setResultCode(ResultCode.USER_NOT_EXIST);
        } catch (LockedAccountException e) {
            result.setResultCode(ResultCode.USER_ACCOUNT_FORBIDDEN);
        } catch (AuthenticationException e) {
            result.setResultCode(ResultCode.USER_LOGIN_ERROR);
        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR);
        }

    }

    @RequestMapping(value = "/handleLogin")
    public Result handleLogin(HttpServletRequest request) {
        String id = request.getHeader(OAuthSessionManager.OAUTH_TOKEN);
        System.out.println("Login expired。。。:" + id);
        return Result.error(ResultCode.SESSION_TIME_OUT);
    }


    @GetMapping("/logout")
    public Result logout() {

        Result result = new Result();
        Subject subject = SecurityUtils.getSubject();
        subject.logout();

        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }


    @Value("${me.upload.path}")
    private String baseFolderPath;

    @PostMapping("/upload")
    @RequiresAuthentication
    public Result upload(HttpServletRequest request, MultipartFile image) {
        if(UserUtils.getCurrentUser() == null){
            return Result.error(ResultCode.USER_NOT_LOGGED_IN);
        }

        Result r = new Result();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        StringBuffer url = new StringBuffer();
        String filePath = sdf.format(new Date());

        File baseFolder = new File(baseFolderPath + filePath);
        if (!baseFolder.exists()) {
            baseFolder.mkdirs();
        }

        url.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath())
                .append("/")
                .append(filePath);

        String imgName = UUID.randomUUID() + "_" + image.getOriginalFilename().replaceAll(" ", "");

        try {

            File dest = new File(baseFolder, imgName);
            image.transferTo(dest);

            url.append("/").append(imgName);

            r.setResultCode(ResultCode.SUCCESS);

            r.simple().put("url", url);

        } catch (IOException e) {
            //logger.error("文件上传错误 , uri: {} , caused by: ", request.getRequestURI(), e);
            r.setResultCode(ResultCode.UPLOAD_ERROR);
        }

        return r;
    }
}
