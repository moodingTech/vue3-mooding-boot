import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';

// 定义静态路由
export const staticRoutes: Array<RouteRecordRaw> = [
    {
        path: '/login',
        name: 'login',
        component: () => import('@/views/login/index.vue'),
        meta: {
            title: '登陆',
            isHide: true,
        },
    },
    {
        path: '/404',
        name: 'notFound',
        component: () => import('@/views/error/404.vue'),
        meta: {
            title: '找不到此页面',
            isHide: true,
        },
    },
    {
        path: '/401',
        name: 'noPower',
        component: () => import('@/views/error/401.vue'),
        meta: {
            title: '没有权限',
            isHide: true,
        },
    },
    {
        path: '/',
        component: () => import('@/layout/index.vue'),
        redirect: '/home',
        meta: {
            isKeepAlive: true,
        },
        children: [
            {
                path: '/home',
                name: 'home',
                component: () => import('@/views/home/index.vue'),
                meta: {
                    title: '首页',
                    isLink: '',
                    isHide: false,
                    isKeepAlive: true,
                    isAffix: true,
                    isFrame: false,
                    icon: 'iconfont icon-shouye',
                }
            }, {
                path: '/personal',
                name: 'personal',
                component: () => import('@/views/personal/index.vue'),
                meta: {
                    title: '个人中心',
                    isLink: '',
                    isHide: true,
                    isKeepAlive: true,
                    isAffix: false,
                    isFrame: false,
                    icon: 'iconfont icon-gerenzhongxin',
              
                },           
            },  
            {
                path: '/job/log',
                name: 'job/log',
                component: () => import('@/views/monitor/job/log.vue'),
                meta: {
                    title: '任务日志',
                    isLink: '',
                    isHide: true,
                    isKeepAlive: true,
                    isAffix: true,
                    isFrame: false,
                    icon: 'iconfont icon-gerenzhongxin',
                },
            },          

        ]
    }
];

// 添加静态路由
const router = createRouter({
    history: createWebHistory("/"),
    routes: staticRoutes,
});
// 导出路由
export default router;
