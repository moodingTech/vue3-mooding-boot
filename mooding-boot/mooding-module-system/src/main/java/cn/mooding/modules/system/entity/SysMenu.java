package cn.mooding.modules.system.entity;

import cn.mooding.common.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单权限表
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sys_menu")
@ApiModel(value = "SysMenu对象", description = "菜单权限表")
public class SysMenu extends BaseEntity {

    @ApiModelProperty(value = "菜单ID")
    @TableId(value = "menu_id", type = IdType.AUTO)
    private Long menuId;

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "父菜单ID")
    private Long parentId;

    @ApiModelProperty(value = "显示顺序")
    private Integer orderNum;

    @ApiModelProperty(value = "路由地址")
    private String path;

    @ApiModelProperty(value = "组件路径")
    private String component;

    @ApiModelProperty(value = "是否为外链（0是 1否）")
    private String isFrame;

    @ApiModelProperty(value = "是否超链接菜单，开启外链条件(1 isLink:true 2 链接地址不为空)")
    private String isLink;

    @ApiModelProperty(value = "菜单类型（M目录 C菜单 F按钮）")
    private String menuType;

    @ApiModelProperty(value = "显示状态（0显示 1隐藏）")
    private String isHide;

    @ApiModelProperty(value = "是否缓存组件状态（0是 1否）")
    private String isKeepAlive;

    @ApiModelProperty(value = "是否固定在 tagsView 栏上（0是 1否）")
    private String isAffix;

    @ApiModelProperty(value = "权限标识")
    private String perms;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "菜单状态（0正常 1停用）")
    private String status;

    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 子菜单
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "子菜单")
    private List<SysMenu> children = new ArrayList<SysMenu>();

}
