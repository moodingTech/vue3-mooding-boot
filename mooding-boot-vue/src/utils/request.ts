import axios from 'axios';
import {ElMessage, ElMessageBox} from 'element-plus';
import { Session } from '@/utils/storage';
import router from '@/router/index';

// 配置新建一个 axios 实例
const service = axios.create({
    baseURL: '/api',
    timeout: 50000,
    headers: {'Content-Type': 'application/json'},
});

// 添加请求拦截器
service.interceptors.request.use(
    (config) => {

        const token = Session.get('token')
        // console.log("请求拦截器", token)
        // 在发送请求之前做些什么 token
        if (token) {
            config.headers.common['Authorization'] = token;
        }
        return config;
    },
    (error) => {
        // 对请求错误做些什么
        console.log("请求拦截器错误", error)
        return Promise.reject(error);
    }
);

// 添加响应拦截器
service.interceptors.response.use(
    (response) => {
        // 对响应数据做点什么
        const res = response.data;
        // console.log('添加响应拦截器', res, res.code && res.code !== 200)
        if (res.code && res.code !== 200) {
            if (res.code === 401) {
                Session.clear(); // 清除浏览器全部临时缓存
                router.push('/login'); // 去登录页面
                // resetRoute(); // 删除/重置路由
                ElMessageBox.alert('你已被登出，请重新登录', '提示', {})
                    .then(() => {
                    })
                    .catch(() => {
                    });
            }
            if (res.code != 401) {
                // console.log('dgl', res.message)
                ElMessage.error(res.code + '  ' + res.message)
            }
            return Promise.reject(service.interceptors.response);
        } else {
            // console.log('添加响应拦截器数据正常', response.data)
            return response.data;
        }
    },
    (error) => {
        console.log("响应拦截器异常", error)
        // 对响应错误做点什么
        if (error.message.indexOf('timeout') != -1) {
            ElMessage.error('网络超时');
        } else if (error.message == 'Network Error') {
            ElMessage.error('网络连接错误');
        } else {
            if (error.response.data) ElMessage.error(error.response.statusText);
            else ElMessage.error('接口路径找不到');
        }
        return Promise.reject(error);
    }
);

export default service;
