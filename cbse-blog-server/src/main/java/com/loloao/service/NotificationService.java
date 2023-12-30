package com.loloao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.loloao.entity.Notification;
import com.loloao.entity.User;

import java.util.List;

public interface NotificationService extends IService<Notification> {
    List<Notification> getNotificationsByUserId(Long userId);

    Integer loadUnreadCounts(Long userId);

    void addNotification(Notification notification);

    void markAsRead(Long id);

    void addNotificationAndUpdateUnreadCounts(User notifyUser, Notification notification);
}
