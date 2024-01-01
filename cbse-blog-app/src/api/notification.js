import request from '@/request'

export function getAllNotifications(userId) {
  return request({
    url: `/notifications/${userId}`,
    method: 'get'
  })
}

export function getNotifications(userId,pageNum , pageSize) {
  return request({
    url: '/notifications',
    method: 'get',
    params: {
      userId: userId,
      pageSize: pageSize,
      pageNum: pageNum
    }
  })
}


export function loadUnreadCounts(userId) {
  return request({
    url: `/notifications/unreadCounts/${userId}`,
    method: 'get'
  })
}

export function markAsRead(notificationId) {
  return request({
    url: `/notifications/read/${notificationId}`,
    method: 'get'
  })
}

export function markAsUnread(notificationId) {
  return request({
    url: `/notifications/unread/${notificationId}`,
    method: 'get'
  })
}

export function deleteById(notificationId) {
  return request({
    url: `/notifications/delete/${notificationId}`,
    method: 'get'
  })
}

export function markPageAsRead(page) {
  return request({
    url: '/notifications/read',
    method: 'post',
    data: page
  })
}

export function markPageAsUnread(page) {
  return request({
    url: '/notifications/unread',
    method: 'post',
    data: page
  })
}

export function deletePage(page) {
  return request({
    url: '/notifications/delete',
    method: 'post',
    data: page
  })
}
