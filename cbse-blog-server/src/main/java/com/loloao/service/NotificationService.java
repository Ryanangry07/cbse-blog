package com.loloao.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.loloao.common.PageParam;
import com.loloao.entity.Notification;
import com.loloao.entity.User;

import java.util.List;

public interface NotificationService extends IService<Notification> {
    Page<Notification> getNotificationsByPage(PageParam pageParam);

    List<Notification> getAllNotificationsByUserId(Long userId);

    Integer loadUnreadCounts(Long userId);

    void addNotification(Notification notification);

    void markAsRead(Long id);

    void markAsUnread(Long id);

    void addNotificationAndUpdateUnreadCounts(User notifyUser, Notification notification);

    void deleteById(Long notificationId);
}
