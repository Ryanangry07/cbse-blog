package com.loloao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loloao.common.PageParam;
import com.loloao.entity.Notification;
import com.loloao.entity.User;
import com.loloao.mapper.NotificationMapper;
import com.loloao.mapper.UserMapper;
import com.loloao.service.NotificationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("notificationService")
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification> implements NotificationService {

    @Resource
    public NotificationMapper notificationMapper;

    @Resource
    public UserMapper userMapper;

    @Override
    public Page<Notification> getNotificationsByPage(PageParam pageParam) {
        // get param
        String keyword = (String) pageParam.getData().get("keyword");
        Page<Notification> page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());

        QueryWrapper<Notification> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", pageParam.getUserId());
        wrapper.orderByDesc("create_date");
        // query condition
        if(StringUtils.isNotBlank(keyword)){
            wrapper.like("CONCAT(title, content)", keyword);
        }
        return notificationMapper.selectPage(page, wrapper);
    }

    @Override
    public List<Notification> getAllNotificationsByUserId(Long userId) {

        QueryWrapper<Notification> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", userId);
        wrapper.orderByDesc("create_date");
        // query condition
        /*if(StringUtils.isNotBlank(keyword)){
            wrapper.like("CONCAT(title, content)", keyword);
        }*/
        return notificationMapper.selectList(wrapper);
    }

    @Override
    public Integer loadUnreadCounts(Long userId) {
        User user = userMapper.selectById(userId);
        return user.getUnreadCounts();
    }



    @Override
    public void addNotificationAndUpdateUnreadCounts(User notifyUser, Notification notification){
        // fill in basic default information
        notification.setReadStatus(false);
        notification.setCreateDate(new Date());
        notification.setUid(notifyUser.getId());
        this.addNotification(notification);

        //update user unreadCounts + 1
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", notifyUser.getId());
        wrapper.set("unread_counts", notifyUser.getUnreadCounts() + 1);
        userMapper.update(null, wrapper);

    }

    @Override
    public void deleteById(Long notificationId) {
        notificationMapper.deleteById(notificationId);
    }

    @Override
    public void addNotification(Notification notification) {
        notificationMapper.insert(notification);
    }

    @Override
    public void markAsRead(Long id) {

        Notification notification = notificationMapper.selectById(id);

        //update notification read_status to true
        UpdateWrapper<Notification> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id);
        wrapper.set("read_status", true);
        notificationMapper.update(null, wrapper);

        //update user read_counts - 1
        User user = userMapper.selectById(notification.getUid());
        UpdateWrapper<User> userWrapper = new UpdateWrapper<>();
        userWrapper.eq("id", user.getId());
        int newCounts = user.getUnreadCounts() - 1;
        userWrapper.set("unread_counts", (newCounts < 0) ? 0 : newCounts);
        userMapper.update(null, userWrapper);

    }

    @Override
    public void markAsUnread(Long id) {

        Notification notification = notificationMapper.selectById(id);

        //update notification read_status to false (unread)
        UpdateWrapper<Notification> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id);
        wrapper.set("read_status", false);
        notificationMapper.update(null, wrapper);

        //update user read_counts + 1 (one more notification unread)
        User user = userMapper.selectById(notification.getUid());
        UpdateWrapper<User> userWrapper = new UpdateWrapper<>();
        userWrapper.eq("id", user.getId());
        int newCounts = user.getUnreadCounts() + 1;
        userWrapper.set("unread_counts", (newCounts < 0) ? 0 : newCounts);
        userMapper.update(null, userWrapper);

    }


}
