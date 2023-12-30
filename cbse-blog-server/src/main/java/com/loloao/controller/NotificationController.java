package com.loloao.controller;

import com.alibaba.fastjson.support.spring.annotation.FastJsonFilter;
import com.alibaba.fastjson.support.spring.annotation.FastJsonView;
import com.loloao.common.Result;
import com.loloao.entity.Article;
import com.loloao.entity.ArticleBody;
import com.loloao.entity.Notification;
import com.loloao.enums.ResultCode;
import com.loloao.service.NotificationService;
import com.loloao.utils.UserUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Resource
    public NotificationService notificationService;


    @GetMapping("/{userId}")
    public Result getNotificationsByUserId(@PathVariable("userId") Long userId) {
        if(UserUtils.getCurrentUser() == null){
            return Result.error(ResultCode.USER_NOT_LOGGED_IN);
        }

        Result result = new Result();

        if (null == userId) {
            result.setResultCode(ResultCode.PARAM_IS_BLANK);
            return result;
        }
        //
        List<Notification> list = notificationService.getNotificationsByUserId(userId);
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(list);
        return result;
    }

    @GetMapping("/read/{id}")
    public Result markAsRead(@PathVariable("id") Long id) {
        if(UserUtils.getCurrentUser() == null){
            return Result.error(ResultCode.USER_NOT_LOGGED_IN);
        }

        Result result = new Result();

        if (null == id) {
            result.setResultCode(ResultCode.PARAM_IS_BLANK);
            return result;
        }
        //
        notificationService.markAsRead(id);
        //List<Notification> list = notificationService.getNotificationsByUserId(userId);
        result.setResultCode(ResultCode.SUCCESS);
        //result.setData(list);
        return result;
    }

    @GetMapping("/unreadCounts/{userId}")
    public Result loadUnreadCounts(@PathVariable("userId") Long userId) {
        if(UserUtils.getCurrentUser() == null){
            return Result.error(ResultCode.USER_NOT_LOGGED_IN);
        }

        Result result = new Result();

        if (null == userId) {
            result.setResultCode(ResultCode.PARAM_IS_BLANK);
            return result;
        }
        //
        int unreadCounts = notificationService.loadUnreadCounts(userId);
        result.setResultCode(ResultCode.SUCCESS);
        result.simple().put("unreadCounts", unreadCounts);
        return result;
    }


}
