package cn.mooding.modules.system.model.vo;

import lombok.Data;

/**
 * 路由显示信息
 *
 * @Author BlueFire
 * @Date 25/3/2021 -上午8:49
 */

public class MetaVo {
/**s
 *  路由meta对象参数说明
 *  meta: {
 *      title:          菜单栏及 tagsView 栏、菜单搜索名称
 *      isLink：        是否超链接菜单，开启外链条件，`1、isLink:true 2、链接地址不为空`
 *      isHide：        是否隐藏此路由
 *      isKeepAlive：   是否缓存组件状态
 *      isAffix：       是否固定在 tagsView 栏上
 *      isFrame：      是否内嵌窗口，，开启条件，`1、isFrame:true 2、链接地址不为空`
 *      auth：          当前路由权限标识（多个请用逗号隔开），最后转成数组格式，用于与当前用户权限进行对比，控制路由显示、隐藏
 *      icon：          菜单、tagsView 图标，阿里：加 `iconfont xxx`，fontawesome：加 `fa xxx`
 *  }
 */

    /**
     * 菜单栏及 tagsView 栏、菜单搜索名称
     * 设置该路由在侧边栏和面包屑中展示的名字
     */
    private String title;
    /**
     * 是否超链接菜单，开启外链条件，`1、isLink:true 2、链接地址不为空`
     */
    private String isLink;
    /**
     * 是否隐藏此路由
     */
    private boolean isHide;

    /**
     * 是否缓存组件状态
     */
    private boolean isKeepAlive;
    /**
     * 是否固定在 tagsView 栏上
     */
    private boolean isAffix;
    /**
     * 是否内嵌窗口，，开启条件，`1、isIframe:true 2、链接地址不为空`
     */
    private boolean isFrame;
    /**
     * 当前路由权限标识（多个请用逗号隔开），最后转成数组格式，用于与当前用户权限进行对比，控制路由显示、隐藏
     */
    private String auth;
    /**
     * 菜单、tagsView 图标，阿里：加 `iconfont xxx`，fontawesome：加 `fa xxx`
     * 设置该路由的图标，对应路径src/icons/svg
     */
    private String icon;

    public MetaVo() {
        super();
    }

    public MetaVo(String title, String isLink, boolean isHide, boolean isKeepAlive, boolean isAffix, boolean isFrame, String auth, String icon) {
        this.title = title;
        this.isLink = isLink;
        this.isHide = isHide;
        this.isKeepAlive = isKeepAlive;
        this.isAffix = isAffix;
        this.isFrame = isFrame;
        this.auth = auth;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsLink() {
        return isLink;
    }

    public void setIsLink(String isLink) {
        this.isLink = isLink;
    }

    public boolean getIsHide() {
        return isHide;
    }

    public void setIsHide(boolean isHide) {
        this.isHide = isHide;
    }

    public boolean getIsKeepAlive() {
        return isKeepAlive;
    }

    public void setIsKeepAlive(boolean keepAlive) {
        this.isKeepAlive = keepAlive;
    }

    public boolean getIsAffix() {
        return isAffix;
    }

    public void setIsAffix(boolean affix) {
        this.isAffix = affix;
    }

    public boolean getIsFrame() {
        return isFrame;
    }

    public void setIsFrame(boolean frame) {
        this.isFrame = frame;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
