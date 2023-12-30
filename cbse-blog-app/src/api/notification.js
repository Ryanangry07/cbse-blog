import request from '@/request'

export function getNotifications(userId) {
  return request({
    url: `/notifications/${userId}`,
    method: 'get'
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
