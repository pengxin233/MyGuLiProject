import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/eduService/user/loginIn',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/eduService/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/eduService/user/logout',
    method: 'post'
  })
}
