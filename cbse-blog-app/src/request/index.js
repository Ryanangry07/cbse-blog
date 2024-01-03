import axios from 'axios'
import {Message} from 'element-ui'
import store from '@/store'
import {getToken} from '@/request/token'

const service = axios.create({
  baseURL: process.env.BASE_API,
  timeout: 10000
})

//request拦截器
service.interceptors.request.use(config => {

  if (store.state.token) {
    config.headers['Oauth-Token'] = getToken()
  }
  return config
}, error => {

  Promise.reject(error)
})

// respone拦截器
service.interceptors.response.use(
  response => {

    //全局统一处理 Session超时
    if (response.headers['session_time_out'] == 'timeout') {
      store.dispatch('fedLogOut')
    }


    const res = response.data;

    //0 为成功状态
    if (res.code !== 0) {

      //90001 Session超时
      if (res.code === 90001) {
        return Promise.reject('error');
      }

      //20001 用户未登录
      if (res.code === 20001) {
        console.info("User not login yet。。")

        Message({
          type: 'warning',
          showClose: true,
          message: 'Login failed or expired，please login again'
        })

        return Promise.reject('error');
      }

      //70001 权限认证错误
      if (res.code === 70001) {
        console.info("No access permission")
        Message({
          type: 'warning',
          showClose: true,
          message: 'No access permission'
        })
        return Promise.reject('error');
      }

      return Promise.reject(res.msg);
    } else {
      return response.data;
    }
  },
  error => {
    Message({
      type: 'warning',
      showClose: true,
      message: 'Connection timeout, please try again later'
    })
    return Promise.reject('error')
  })

export default service
