import { Module } from 'vuex';
import { Session } from '@/utils/storage';
import { getInfo, logout, login } from "@/api/login";

const userInfosModule: Module<UserInfosState, RootStateTypes> = {
    namespaced: true,
    state: {
        userInfos: {},
        token: Session.get('token'),
        userName: '',
        avatar: '',
        roles: [],
        permissions: [],
    },
    mutations: {
        // 设置用户信息
        getUserInfos(state: any, data: object) {
            state.userInfos = data;
        },
        SET_TOKEN(state: any, data: object) {
            state.token = data;
        },
        SET_USERNAME(state: any, data: object) {
            state.userName = data;
        },
        SET_AVATAR(state: any, data: object) {
            state.avatar = data;
        },
        SET_ROLES(state: any, data: Array<object>) {
            state.roles = data;
        },
        SET_PERMISSIONS(state: any, data: Array<object>) {
            state.permissions = data;
        },
    },
    actions: {
        // 登录
        Login({ commit }, userInfo: any) {
            const username = userInfo.userName
            const password = userInfo.password
            const code = userInfo.code
            const uuid = userInfo.uuid
            return new Promise((resolve, reject) => {
                login(username, password, code, uuid).then(res => {
                    Session.set('token', res.data.token)
                    commit('SET_TOKEN', res.data.token)
                    resolve('')
                }).catch(error => {
                    reject(error)
                })
            })
        },
        // 设置用户信息
        async setUserInfos({ commit }, data: object) {
            if (data) {
                console.log("设置用户信息", data);
                commit('getUserInfos', data);
                Session.set('userInfo', data);
            } else {
                if (Session.get('userInfo')) commit('getUserInfos', Session.get('userInfo'));
            }
        },
        // 获取用户信息
        async GetInfo({ commit }) {
            return new Promise((resolve, reject) => {
                getInfo().then((res: any) => {

                    const user = res.data.user
                    let avatar = "";

                    if (!user.avatar || user.avatar == '') {                  
                        user.avatar = 'https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1813762643,1914315241&fm=26&gp=0.jpg';
                    }
                    // const avatar = user.avatar == '' ? user.avatar : 'https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1813762643,1914315241&fm=26&gp=0.jpg';
                    // const avatar = user.avatar == "" ? require("@/assets/image/profile.jpg") : process.env.VUE_APP_BASE_API + user.avatar;
                    if (res.data.roles && res.data.roles.length > 0) { // 验证返回的roles是否是一个非空数组
                        commit('SET_ROLES', res.data.roles)
                        commit('SET_PERMISSIONS', res.data.permissions)
                    } else {
                        commit('SET_ROLES', ['ROLE_DEFAULT'])
                    }
                    commit('SET_USERNAME', user.userName)
                    commit('SET_AVATAR', avatar)
                    commit('getUserInfos', user);
                    resolve(res)
                }).catch(error => {
                    reject(error)
                })
            })
        },
        LogOut({ commit, state }) {
            return new Promise((resolve, reject) => {
                logout().then(() => {
                    commit('SET_TOKEN', '')
                    commit('SET_ROLES', [])
                    commit('SET_PERMISSIONS', [])
                    Session.remove('token')
                    resolve('')
                }).catch(error => {
                    reject(error)
                })
            })
        },

        // 前端 登出
        FedLogOut({ commit }) {
            return new Promise(resolve => {
                commit('SET_TOKEN', '')
                Session.remove('token')
                resolve('')
            })
        }
    },
};

export default userInfosModule;
