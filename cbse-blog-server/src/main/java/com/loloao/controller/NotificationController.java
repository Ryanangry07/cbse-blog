package com.loloao.controller;

import com.alibaba.fastjson.support.spring.annotation.FastJsonFilter;
import com.alibaba.fastjson.support.spring.annotation.FastJsonView;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.loloao.common.PageParam;
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
import java.util.Map;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Resource
    public NotificationService notificationService;


    @GetMapping()
    public Result getNotificationsByPage(PageParam pageParam) {
        if(UserUtils.getCurrentUser() == null){
            return Result.error(ResultCode.USER_NOT_LOGGED_IN);
        }

        Result result = new Result();

        if (null == pageParam.getUserId()) {
            result.setResultCode(ResultCode.PARAM_IS_BLANK);
            return result;
        }
        //
        IPage<Notification> page = notificationService.getNotificationsByPage(pageParam);
        result.setResultCode(ResultCode.SUCCESS);
        Map<String, Object> mapResult = result.simple();
        mapResult.put("notificationList" ,page.getRecords());
        mapResult.put("total" ,page.getTotal());
        return result;
    }

    @GetMapping("/{id}")
    public Result getAllNotificationsByUserId(@PathVariable("id") Long userId) {
        if(UserUtils.getCurrentUser() == null){
            return Result.error(ResultCode.USER_NOT_LOGGED_IN);
        }

        Result result = new Result();

        if (null == userId) {
            result.setResultCode(ResultCode.PARAM_IS_BLANK);
            return result;
        }
        //
        List<Notification> list = notificationService.getAllNotificationsByUserId(userId);
        result.setResultCode(ResultCode.SUCCESS);
        Map<String, Object> mapResult = result.simple();
        mapResult.put("notificationList" ,list);
        mapResult.put("total" ,list.size());
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

    @GetMapping("/unread/{id}")
    public Result markAsUnread(@PathVariable("id") Long id) {
        if(UserUtils.getCurrentUser() == null){
            return Result.error(ResultCode.USER_NOT_LOGGED_IN);
        }

        Result result = new Result();

        if (null == id) {
            result.setResultCode(ResultCode.PARAM_IS_BLANK);
            return result;
        }
        //
        notificationService.markAsUnread(id);
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

    @GetMapping("/delete/{notificationId}")
    public Result deleteById(@PathVariable("notificationId") Long notificationId) {
        if(UserUtils.getCurrentUser() == null){
            return Result.error(ResultCode.USER_NOT_LOGGED_IN);
        }

        Result result = new Result();

        if (null == notificationId) {
            result.setResultCode(ResultCode.PARAM_IS_BLANK);
            return result;
        }
        //
        notificationService.deleteById(notificationId);
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }


}
