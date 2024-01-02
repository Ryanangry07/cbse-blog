package com.loloao.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.loloao.common.Base;
import com.loloao.common.Result;
import com.loloao.entity.User;
import com.loloao.enums.ResultCode;
import com.loloao.oauth.OAuthRealm;
import com.loloao.oauth.OAuthSessionManager;
import com.loloao.service.EmailService;
import com.loloao.service.UserService;
import com.loloao.utils.PasswordHelper;
import com.loloao.utils.UserUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@RestController
public class AuthController {

    @Resource
    private UserService userService;

    @Resource
    private EmailService emailService;

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
    public Result upload(HttpServletRequest request, @RequestParam("uploadAvatar") MultipartFile image) {
        /*if(UserUtils.getCurrentUser() == null){
            return Result.error(ResultCode.USER_NOT_LOGGED_IN);
        }*/

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



    private String generateEmailContentAndSend(String email) throws IOException {
        // Replace 'path/to/your/file.html' with the actual path to your HTML file
        ClassPathResource resource = new ClassPathResource("static/email.html");
        String htmlString = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);

        // Generate a random verification code (you can customize the length as needed)
        String verificationCode = generateRandomCode(6);

        // Fill the verification code into the HTML string
        String filledHtmlString = htmlString.replace("{verificationCode}", verificationCode);

        // Send the filled HTML string via email or any other method
        // Replace the following line with your email sending logic
        //sendEmail("recipient@example.com", "Verification Code Email", filledHtmlString);
        boolean isSucceed = emailService.sendHtmlMail(email, "LOLOBLOG - Forget Password", filledHtmlString);
        if(!isSucceed){
            return null;
        }
        return verificationCode;
    }

    private String generateRandomCode(int length) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < length; i++) {
            code.append(random.nextInt(10));
        }

        return code.toString();
    }

    //mailService.sendHtmlMail("xxx@qq.com","主题：你好html邮件","<h1>内容：第一封html邮件</h1>");
    //mailService.sendSimpleMail("xxx@qq.com","主题：你好普通邮件","内容：第一封邮件");

    @PostMapping("/email")
    //@RequiresRoles(Base.ROLE_ADMIN)
    public Result sendEmail(@RequestBody User user)  {

        // check email empty
        // check user exists or not
        Result result = new Result();
        String email = user.getEmail();
        User reqUser = getUserByEmail(email);
        if(reqUser == null){
            result.setResultCode(ResultCode.USER_NOT_EXIST);
            return result;
        }
        // check email valid
        // generate verification code, and send email
        String verificationCode;
        try {
            verificationCode = generateEmailContentAndSend(email);
            if(verificationCode == null){
                result.setResultCode(ResultCode.PARAM_IS_INVALID);
            }else {
                result.simple().put("code", verificationCode);
                result.setResultCode(ResultCode.SUCCESS);
            }
        }catch (IOException e){
            result.setResultCode(ResultCode.PARAM_IS_INVALID);
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Resource
    public OAuthRealm oAuthRealm;

    @PostMapping("/reset")
    public Result resetPassword(@RequestBody User user){
        Result result = new Result();
        String email = user.getEmail();
        User reqUser = getUserByEmail(email);
        if(reqUser == null){
            result.setResultCode(ResultCode.USER_NOT_EXIST);
            return result;
        }
        reqUser.setPassword(user.getPassword());
        System.out.println("resetPassword ==> old password: " + reqUser.getPassword());
        PasswordHelper.encryptPassword(reqUser);
        System.out.println("resetPassword ==> new password: " + reqUser.getPassword());
        userService.resetPassword(reqUser);

        /*Cache<Object, AuthenticationInfo> cache = oAuthRealm.getAuthenticationCache();
        if(cache != null){
            cache.remove(reqUser.getAccount());
        }*/
        return Result.success();
    }

    private User getUserByEmail(String email){
        if(email == null){
            return null;
        }
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getEmail, email);
        User reqUser = userService.getOne(wrapper);
        return reqUser;
    }
}
