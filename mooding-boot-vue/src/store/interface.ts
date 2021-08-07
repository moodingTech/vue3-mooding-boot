// 布局配置
declare interface ThemeConfigState {
    themeConfig: {
        isDrawer: boolean;
		primary: string;
		success: string;
		info: string;
		warning: string;
		danger: string;
		topBar: string;
		menuBar: string;
		columnsMenuBar: string;
		topBarColor: string;
		menuBarColor: string;
		columnsMenuBarColor: string;
		isTopBarColorGradual: boolean;
		isMenuBarColorGradual: boolean;
		isColumnsMenuBarColorGradual: boolean;
		isMenuBarColorHighlight: boolean;
		isCollapse: boolean;
		isUniqueOpened: boolean;
		isFixedHeader: boolean;
		isFixedHeaderChange: boolean;
		isClassicSplitMenu: boolean;
		isLockScreen: boolean;
		lockScreenTime: number;
		isShowLogo: boolean;
		isShowLogoChange: boolean;
		isBreadcrumb: boolean;
		isTagsview: boolean;
		isBreadcrumbIcon: boolean;
		isTagsviewIcon: boolean;
		isCacheTagsView: boolean;
		isSortableTagsView: boolean;
		isFooter: boolean;
		isGrayscale: boolean;
		isInvert: boolean;
		isWartermark: boolean;
		wartermarkText: string;
		tagsStyle: string;
		animation: string;
		columnsAsideStyle: string;
		columnsAsideLayout: string;
		layout: string;
		isRequestRoutes: boolean;
		globalTitle: string;
		globalViceTitle: string;
		globalI18n: string;
		globalComponentSize: string;
    };
}

// 用户信息
declare interface UserInfosState {
    userInfos: object;
    token: string;
    userName: string,
    avatar: string,
    roles: Array<object>,
    permissions: Array<object>,
}

// 权限路由
declare interface PermissionRoute {
    routes: Array<string>;
    addRoutes: Array<string>;
    defaultRoutes: Array<string>;
    topbarRouters: Array<string>;
    sidebarRouters: Array<string>;
}

// 主接口(顶级类型声明)
declare interface RootStateTypes {
    themeConfig: ThemeConfigState;
    userInfos: UserInfosState;
    permissionRoute :PermissionRoute;
}
