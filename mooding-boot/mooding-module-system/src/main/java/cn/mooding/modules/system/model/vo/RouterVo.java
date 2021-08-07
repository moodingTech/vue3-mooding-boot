package cn.mooding.modules.system.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * 路由配置信息
 * @Author BlueFire
 * @Date 25/3/2021 -上午8:48
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class RouterVo {
/**
 * 路由对象参数说明
 *  {
 *     component:          组件地址
 *     redirect:           重定向地址，当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
 *     path:               路由地址
 *     name:               路由名字
 *     // 路由meta对象参数说明
 *     meta: {
 *         title:          菜单栏及 tagsView 栏、菜单搜索名称（国际化）
 *         isLink：        是否超链接菜单，开启外链条件，`1、isLink:true 2、链接地址不为空`
 *         isHide：        是否隐藏此路由
 *         isKeepAlive：   是否缓存组件状态
 *         isAffix：       是否固定在 tagsView 栏上
 *         isFrame：      是否内嵌窗口，，开启条件，`1、isIframe:true 2、链接地址不为空`
 *         auth：          当前路由权限标识（多个请用逗号隔开），最后转成数组格式，用于与当前用户权限进行对比，控制路由显示、隐藏
 *         icon：          菜单、tagsView 图标，阿里：加 `iconfont xxx`，fontawesome：加 `fa xxx`
 *     }
 *  }
 *
 */

    /**
     * 路由名字
     */
    private String name;

    /**
     * 路由地址
     */
    private String path;


    /**
     * 重定向地址，当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
     */
    private String redirect;

    /**
     * 组件地址
     */
    private String component;

    /**
     * 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
     */
    //    private Boolean alwaysShow;

    /**
     * 其他元素
     */
    private MetaVo meta;

    /**
     * 子路由
     */
    private List<RouterVo> children;


}
