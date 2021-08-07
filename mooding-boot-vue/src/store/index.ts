import {InjectionKey} from 'vue';
import {createStore, useStore as baseUseStore, Store} from 'vuex';
import themeConfig from './module/themeConfig';
import userInfos from './module/userInfos';
import permissionRoute from './module/permissionRoute';


export const key: InjectionKey<Store<RootStateTypes>> = Symbol();
export const store = createStore<RootStateTypes>({
    modules: {
        themeConfig,
        userInfos,
        permissionRoute
    },
});

export function useStore() {
    return baseUseStore(key);
}

