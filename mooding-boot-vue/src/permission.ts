import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import router from './router'
import { Session } from "@/utils/storage"
import { NextLoading } from "@/utils/loading"
import { store } from "@/store"
import { RouteRecordRaw } from 'vue-router';


const whiteList: string[] = ['/login', '/captchaImage'] // 不重定向白名单
router.beforeEach((to:any, from :any, next:any) => {
    NProgress.configure({ showSpinner: false });
    if (to.meta.title) NProgress.start();
    const token = Session.get('token');
    // console.log("路由前置钩子", token)
    if (token) {
        if (to.path === '/login') {
            next('/home');
            NProgress.done();
        } else {
            if (store.state.userInfos.roles.length === 0) {
                // 判断当前用户是否已拉取完user_info信息
                store.dispatch('userInfos/GetInfo').then((res :any) => {
                    // 拉取user_info
                    const roles = res.roles
                    store.dispatch('permissionRoute/GenerateRoutes', { roles }).then((accessRoutes :any) => {
                        formatTwoStageRoutes(formatFlatteningRoutes(accessRoutes)).forEach((routeTmp: RouteRecordRaw) => {
                            const routeName: any = routeTmp.name;
                            if (!router.hasRoute(routeName)) router.addRoute(routeTmp);
                        });
                        // 根据roles权限生成可访问的路由表
                        // accessRoutes.forEach((route: any) => {
                        //     // console.log('添加路由', route)
                        //     router.addRoute(route) // 动态添加可访问路由表
                        // })
                        // console.log('动态路由', router)
                        next({ ...to, replace: true }) // hack方法 确保addRoutes已完成
                    })
                })
                    .catch((err:any) => {
                        store.dispatch('userInfos/FedLogOut').then(() => {
                            // Message.error(err)
                            console.log(err)
                            next({ path: '/' })
                        })
                    })
            } else {
                next()
                // 没有动态改变权限的需求可直接next() 删除下方权限判断 ↓
                // if (hasPermission(store.getters.roles, to.meta.roles)) {
                //   next()
                // } else {
                //   next({ path: '/401', replace: true, query: { noGoBack: true }})
                // }
                // 可删 ↑
            }
        }

    } else {
        if (whiteList.indexOf(to.path) !== -1) {
            next()
        } else {
            // 否则全部重定向到登录页
            next({
                path: '/login',
                query: {
                    redirect: to.path
                }
            })
        }
        NProgress.done();
    }
    // console.log('路由目标路径',to.path)
})


// 路由加载后
router.afterEach(() => {
    NProgress.done();
    NextLoading.done();
});



/**
 * 路由多级嵌套数组处理成一维数组
 * @param arr 传入路由菜单数据数组
 * @returns 返回处理后的一维路由菜单数组
 */
export function formatFlatteningRoutes(arr: any) {
    if (arr.length <= 0) return false;
    for (let i = 0; i < arr.length; i++) {
        // 特殊处理
        arr[i].path=arr[i].name
		// console.log('特殊处理',arr[i].path);
        if (arr[i].children) {
            arr = arr.slice(0, i + 1).concat(arr[i].children, arr.slice(i + 1));
        }
    }
    return arr;
}


/**
 * 一维数组处理成多级嵌套数组（只保留二级：也就是二级以上全部处理成只有二级，keep-alive 支持二级缓存）
 * @description isKeepAlive 处理 `name` 值，进行缓存。顶级关闭，全部不缓存
 * @link 参考：https://v3.cn.vuejs.org/api/built-in-components.html#keep-alive
 * @param arr 处理后的一维路由菜单数组
 * @returns 返回将一维数组重新处理成 `定义动态路由（dynamicRoutes）` 的格式
 */
 export function formatTwoStageRoutes(arr: any) {
	if (arr.length <= 0) return false;
	const newArr: any = [];
    newArr.push({ component: ()=>import('@/layout/index.vue'), name: '/', path: '/', redirect: '/home', meta: {isKeepAlive: true,}, children: [] });
	const cacheList: Array<string> = [];
	arr.forEach((v: any) => {
		if (v.path === '/') {
			newArr.push({ component: v.component, name: v.name, path: v.path, redirect: v.redirect, meta: v.meta, children: [] });
		} else {
			newArr[0].children.push({ ...v });
			// 存 name 值，keep-alive 中 include 使用，实现路由的缓存
			// 路径：/@/layout/routerView/parent.vue
			if (newArr[0].meta.isKeepAlive && v.meta.isKeepAlive) {
				cacheList.push(v.name);
				// store.dispatch('keepAliveNames/setCacheKeepAlive', cacheList);
			}
		}
	});
	return newArr;
}