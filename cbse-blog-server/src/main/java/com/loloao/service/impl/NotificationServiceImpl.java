package com.loloao.service.impl;

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
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
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
    @Transactional
    public void deleteById(Long notificationId) {
        Notification notification = notificationMapper.selectById(notificationId);
        // if read, do nothing
        // if unread, update user's unreadCounts
        if(!notification.getReadStatus()){
            userUnreadCountsIncrement(notificationId, -1);
        }
        notificationMapper.deleteById(notificationId);
    }

    @Override
    @Transactional
    public void addNotification(Notification notification) {
        notificationMapper.insert(notification);
    }

    @Override
    @Transactional
    public void markAsRead(Long id) {
        //update notification read_status to true
        updateNotificationReadStatus(id, true);

        //update user read_counts - 1
        userUnreadCountsIncrement(id, -1);

    }

    @Override
    @Transactional
    public void markAsUnread(Long id) {
        //update notification read_status to false (unread)
        updateNotificationReadStatus(id, false);

        //update user read_counts + 1 (one more notification unread)
        userUnreadCountsIncrement(id, 1);
    }

    @Override
    @Transactional
    public void markPageAsRead(List<Long> page) {
        //update notification's read_status in page to true
        int decrement = updatePageReadStatus(page, true);

        // update user unreadCounts
        for (long notificationId: page){
            // ture: update successfully
            if(userUnreadCountsIncrement(notificationId, 0 - decrement)){
                break;
            }
        }

    }

    @Override
    @Transactional
    public void markPageAsUnread(List<Long> page) {
        //update notification's read_status in page to true
        int increment = updatePageReadStatus(page, false);

        // update user unreadCounts
        for (long notificationId: page){
            // ture: update successfully
            if(userUnreadCountsIncrement(notificationId, increment)){
                break;
            }
        }
    }

    @Override
    @Transactional
    public void deletePage(List<Long> page) {
        // if delete 'unread', update user unread_counts
        for (long notificationId: page){
            Notification notification = notificationMapper.selectById(notificationId);
            if(!notification.getReadStatus()){
                userUnreadCountsIncrement(notificationId, -1);
            }
        }
        //update/delete，返回值是：更新或删除的行数
        notificationMapper.deleteBatchIds(page);



    }

    // update user's unreadCounts
    private boolean userUnreadCountsIncrement(long notificationId, int increment){
        Notification notification = notificationMapper.selectById(notificationId);
        User user = userMapper.selectById(notification.getUid());
        if(user == null) {
            return false;
        }
        UpdateWrapper<User> userWrapper = new UpdateWrapper<>();
        userWrapper.eq("id", user.getId());
        int newCounts = user.getUnreadCounts() + increment;
        userWrapper.set("unread_counts", (newCounts < 0) ? 0 : newCounts);
        userMapper.update(null, userWrapper);
        return true;
    }

    private int updateNotificationReadStatus(long notificationId, boolean read){
        UpdateWrapper<Notification> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", notificationId);
        wrapper.set("read_status", read);
        return notificationMapper.update(null, wrapper);
    }


    private int updatePageReadStatus(List<Long> notificationIds, boolean read){
        UpdateWrapper<Notification> wrapper = new UpdateWrapper<>();
        wrapper.in("id", notificationIds);
        wrapper.set("read_status", read);
        return notificationMapper.update(null, wrapper);
    }


}
