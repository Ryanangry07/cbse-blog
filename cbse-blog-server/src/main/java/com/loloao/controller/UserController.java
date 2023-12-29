package com.loloao.controller;


import com.alibaba.fastjson.support.spring.annotation.FastJsonFilter;
import com.alibaba.fastjson.support.spring.annotation.FastJsonView;
import com.loloao.common.Base;
import com.loloao.enums.ResultCode;
import com.loloao.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;

import com.loloao.common.Result;
import com.loloao.entity.User;
import com.loloao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2023-12-09 19:28:26
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    @RequiresRoles(Base.ROLE_ADMIN)
    public Result listUsers() {
        List<User> users = userService.findAll();

        return Result.success(users);
    }

    @GetMapping("/{id}")
    @RequiresRoles(Base.ROLE_ADMIN)
    public Result getUserById(@PathVariable("id") Long id) {

        Result result = new Result();

        if (null == id) {
            result.setResultCode(ResultCode.PARAM_IS_BLANK);
            return result;
        }

        User user = userService.getUserById(id);

        result.setResultCode(ResultCode.SUCCESS);
        result.setData(user);
        return result;
    }

    @CrossOrigin
    @GetMapping("/currentUser")
    @FastJsonView(
            include = {@FastJsonFilter(clazz = User.class, props = {"id", "account", "nickname", "avatar"})})
    public Result getCurrentUser(HttpServletRequest request) {

        Result result = new Result();

        User currentUser = UserUtils.getCurrentUser();
        if(currentUser == null){
            return Result.error(ResultCode.USER_NOT_LOGGED_IN);
        }

        result.setResultCode(ResultCode.SUCCESS);
        result.setData(currentUser);
        return result;
    }

    @PostMapping("/create")
    @RequiresRoles(Base.ROLE_ADMIN)
    public Result saveUser(@Validated @RequestBody User user) {

        Long userId = userService.saveUser(user);

        Result result = Result.success();
        result.simple().put("userId", userId);
        return result;
    }

    @PostMapping("/update")
    @RequiresRoles(Base.ROLE_ADMIN)
    public Result updateUser(@Validated @RequestBody User user) {
        Result result = new Result();
        System.out.println("updateUser ==> " + user.toString());
        if (null == user.getId() || userService.updateUser(user) == 0) {
            result.setResultCode(ResultCode.USER_NOT_EXIST);
            return result;
        }

        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    @GetMapping("/delete/{id}")
    @RequiresRoles(Base.ROLE_ADMIN)
    public Result deleteUserById(@PathVariable("id") Long id) {
        Result result = new Result();

        if (null == id) {
            result.setResultCode(ResultCode.PARAM_IS_BLANK);
            return result;
        }

        userService.deleteUserById(id);

        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }


}

