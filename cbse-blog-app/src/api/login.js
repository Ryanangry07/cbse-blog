import request from '@/request'

export function login(account, password) {
  const data = {
    account,
    password
  }
  return request({
    url: '/login',
    method: 'post',
    data
  })
}

export function logout() {
  return request({
    url: '/logout',
    method: 'get'
  })
}

export function getUserInfo() {
  return request({
    url: '/users/currentUser',
    method: 'get'
  })
}

export function getUserProfile(userId) {
  return request({
    url: `/users/${userId}`,
    method: 'get'
  })
}

export function saveUserProfile(user) {
  const data = {
    id: user.id,
    account: user.account,
    nickname: user.nickname,
    avatar: user.avatar, // Set the default avatar URL if available
    email: user.email,
    mobilePhoneNumber: user.mobilePhoneNumber,
    aboutMe: user.aboutMe,
    aboutMeVisible: user.aboutMeVisible
  }
  return request({
    url: '/users/update',
    method: 'post',
    data
  })
}

export function register(account, nickname, password) {
  const data = {
    account,
    nickname,
    password
  }
  return request({
    url: '/register',
    method: 'post',
    data
  })
}
