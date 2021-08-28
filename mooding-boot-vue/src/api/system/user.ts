import request from '@/utils/request';
import {praseStrEmpty} from '@/utils';

// 查询用户列表
export function listUser(query: Array<object>) {
    return request({
        url: '/system/user/list',
        method: 'get',
        params: query
    })
}

// 用户状态修改
export function changeUserStatus(userId: number, status: string) {
    const data = {
        userId,
        status
    }
    return request({
        url: '/system/user/changeStatus',
        method: 'put',
        data: data
    })
}

// 查询用户详细
export function getUser(userId: number) {
    return request({
        url: '/system/user/getById/' + praseStrEmpty(userId),
        method: 'get'
    })
}

// 删除用户
export function delUser(userId: number) {
    return request({
        url: '/system/user/' + userId,
        method: 'delete'
    })
}

// 导出用户
export function exportUser(query: Array<object>) {
    return request({
        url: '/system/user/export',
        method: 'get',
        params: query
    })
}

// 新增用户
export function addUser(data: any) {
    return request({
        url: '/system/user/add',
        method: 'put',
        data: data
    })
}

// 修改用户
export function updateUser(data:any) {
    return request({
        url: '/system/user/edit',
        method: 'put',
        data: data
    })
}

// 下载用户导入模板
export function importTemplate() {
    return request({
      url: '/system/user/importTemplate',
      method: 'get'
    })
  }