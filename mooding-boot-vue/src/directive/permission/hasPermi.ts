/**
* 操作权限处理
* Copyright (c) 2022 moodingtech
*/

import { store } from '@/store/index'

export default {   
        mounted(el: any, binding: any, vnode: any) {
            const { value } = binding
            const all_permission = "*:*:*";
            const permissions = store.state.userInfos && store.state.userInfos.permissions

            if (value && value instanceof Array && value.length > 0) {
                const permissionFlag = value

                const hasPermissions = permissions.some((permission: any) => {
                    return all_permission === permission || permissionFlag.includes(permission)
                })

                if (!hasPermissions) {
                    el.parentNode && el.parentNode.removeChild(el)
                }
            } else {
                throw new Error(`请设置操作权限标签值`)
            }
        }
    
}