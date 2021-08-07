import request from '@/utils/request'

// 用户登录
export function signIn(params: object) {
    return request({
        url: '/user/signIn',
        method: 'post',
        data: params,
    });
}

// 用户退出登录
export function signOut(params: object) {
    return request({
        url: '/user/signOut',
        method: 'post',
        data: params,
    });
}

// 获取用户详细信息
export function getInfo() {
    return request({
        url: '/getInfo',
        method: 'get'
    })
}

// 退出方法
export function logout() {
    return request({
        url: '/logout',
        method: 'post'
    })
}

// 登录方法
export function login(username: string, password: string, code: string, uuid: string) {
    const data = {
        username,
        password,
        captcha:code,
        uuid
    }
    return request({
        url: '/login',
        method: 'post',
        data: data
    })
}

// 获取验证码
export function getCodeImg() {
    return request({
        url: '/captchaImage',
        method: 'get'
    })
}
