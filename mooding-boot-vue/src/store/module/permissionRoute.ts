import {Module} from 'vuex'
import {staticRoutes} from '@/router'
import {getRouters} from "@/api/menu"

const Layout = () => import('@/layout/index.vue')

const permissionRouteModule: Module<PermissionRoute, RootStateTypes> = {
    namespaced: true,
    state: {
        routes: [],
        addRoutes: [],// keepAliveNames
        defaultRoutes: [],
        topbarRouters: [],//tagsViewRoutesList
        sidebarRouters: []
    },
    mutations: {
        SET_ROUTES: (state: any, routes) => {
            state.addRoutes = routes
            state.routes = staticRoutes.concat(routes)
        },
        SET_DEFAULT_ROUTES: (state: any, routes) => {
            state.defaultRoutes = staticRoutes.concat(routes)
        },
        SET_TOPBAR_ROUTES: (state: any, routes) => {
            // 顶部导航菜单默认添加统计报表栏指向首页
            const index = [{
                path: 'index',
                meta: {title: '统计报表', icon: 'dashboard'}
            }]
            state.topbarRouters = routes.concat(index);
        },
        SET_SIDEBAR_ROUTERS: (state: any, routes) => {
            state.sidebarRouters = routes
        },
    },
    actions: {
        // 生成路由
        async GenerateRoutes({commit}) {
            return new Promise(resolve => {
                // 向后端请求路由数据
                getRouters().then((res: any) => {       
                    // console.log("向后端请求路由数据1",JSON.stringify(res.data));             
                    const sdata = JSON.parse(JSON.stringify(res.data))
                    const rdata = JSON.parse(JSON.stringify(res.data))
<<<<<<< HEAD
                    // console.log("向后端请求路由数据2",rdata);
=======
                    console.log("向后端请求路由数据2",rdata);
>>>>>>> master
                    const sidebarRoutes = filterAsyncRouter(sdata)
                    const rewriteRoutes = filterAsyncRouter(rdata, false, true)
                    
                    // rewriteRoutes.push({path: '/:pathMatch(.*)*', redirect: '/404', hidden: true})                   
                    commit('SET_ROUTES', rewriteRoutes)
                    commit('SET_SIDEBAR_ROUTERS', staticRoutes[3].children.concat(sidebarRoutes))
                    commit('SET_DEFAULT_ROUTES', staticRoutes[3].children.concat(sidebarRoutes))
                    // commit('SET_TOPBAR_ROUTES', staticRoutes[3].children.concat(sidebarRoutes))
                    commit('SET_TOPBAR_ROUTES', formatTwoStageRoutes(formatFlatteningRoutes(staticRoutes[3].children.concat(sidebarRoutes)))[0].children)
                    resolve(rewriteRoutes)
                })
            })
        }
    },
};

// 遍历后台传来的路由字符串，转换为组件对象
function filterAsyncRouter(asyncRouterMap: any, lastRouter = false, type = false) {
    return asyncRouterMap.filter((route: any) => {
        if (type && route.children) {
            route.children = filterChildren(route.children)
        }
        if (route.component) {
            // Layout ParentView 组件特殊处理
            if (route.component === 'Layout') {
                route.component = Layout
            } else {
                // route.component = loadView(route.component)
                // console.log("组件特殊处理",dynamicImport(dynamicViewsModules, route.component as string));
                route.component = dynamicImport(dynamicViewsModules, route.component as string)

            }
        }
        if (route.children != null && route.children && route.children.length) {
            route.children = filterAsyncRouter(route.children, route, type)
        } else {
            delete route['children']
            delete route['redirect']
        }
        return true
    })
}

function filterChildren(childrenMap: any, lastRouter = false) {
    var children: any = [];
    childrenMap.forEach((el: any, index: number) => {
        if (el.children && el.children.length) {
            el.children.forEach((c: any) => {
                c.path = el.path + '/' + c.path
                if (c.children && c.children.length) {
                    children = children.concat(filterChildren(c.children, c))
                    return
                }
                children.push(c)
            })
            return
        }
        if (lastRouter && lastRouter.path) {
            el.path = lastRouter.path + '/' + el.path
        }
        children = children.concat(el)
    })
    return children
}

// export const loadView = (view: string) => { // 路由懒加载
//     // return (resolve: any) => require([`@/views/${view}`], resolve)
//     return () => import('@/views/system/user/svgIcon.vue')
//     var path = '@/views/' + view
//     // return (resolve: any) => require([path], resolve)
//     return () => import('@/views/' + view)
// }

// 获取目录下的 .vue 全部文件，参考 vite：import.meta.glob
const dynamicViewsModules = import.meta.glob('../../views/**/*.{vue,tsx}');

// 后端控制路由，后端路由 component 转换函数
export function dynamicImport(dynamicViewsModules: Record<string, () => Promise<{ [key: string]: any }>>, component: string) {
    const keys = Object.keys(dynamicViewsModules);
    const matchKeys = keys.filter((key) => {
        const k = key.replace('../../views', '');
        return k.startsWith(`${component}`) || k.startsWith(`/${component}`);
    });
    if (matchKeys?.length === 1) {
        const matchKey = matchKeys[0];
        return dynamicViewsModules[matchKey];
    }
    if (matchKeys?.length > 1) {
        console.warn('Do not create files that do not end with. Vue');
        return false;
    }
}


/**
 * 缓存多级嵌套数组处理后的一维数组
 * @description 用于 tagsView、菜单搜索中：未过滤隐藏的(isHide)
 */
 export function setCacheTagsViewRoutes(authsRoutes: any) {
	// 添加到 vuex setTagsViewRoutes 中
	// store.dispatch('tagsViewRoutes/setTagsViewRoutes', formatTwoStageRoutes(formatFlatteningRoutes(authsRoutes))[0].children);
}

/**
 * 路由多级嵌套数组处理成一维数组
 * @param arr 传入路由菜单数据数组
 * @returns 返回处理后的一维路由菜单数组
 */
 export function formatFlatteningRoutes(arr: any) {
	if (arr.length <= 0) return false;
	for (let i = 0; i < arr.length; i++) {
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
export default permissionRouteModule;
