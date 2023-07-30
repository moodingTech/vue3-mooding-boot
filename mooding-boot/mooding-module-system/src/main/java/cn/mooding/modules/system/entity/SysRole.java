package cn.mooding.modules.system.entity;

import cn.mooding.common.aspect.annotation.Excel;
import cn.mooding.common.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色信息表
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sys_role")
@ApiModel(value = "SysRole对象", description = "角色信息表")
public class SysRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色ID")
    @TableId(value = "role_id", type = IdType.AUTO)
    @Excel(name = "角色序号", cellType = Excel.ColumnType.NUMERIC)
    private Long roleId;

    @ApiModelProperty(value = "角色名称")
    @Excel(name = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色权限字符串")
    @Excel(name = "角色权限字符串")
    private String roleKey;

    @ApiModelProperty(value = "显示顺序")
    @Excel(name = "显示顺序")
    private Integer roleSort;

    @ApiModelProperty(value = "数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）")
    @Excel(name = "数据范围", readConverterExp = "1=所有数据权限,2=自定义数据权限,3=本部门数据权限,4=本部门及以下数据权限")
    private String dataScope;

    @ApiModelProperty(value = "角色状态（0正常 1停用）")
    @Excel(name = "角色状态", readConverterExp = "0=正常,1=停用")
    private String status;

    @ApiModelProperty(value = "备注")
    @Excel(name = "备注")
    private String remark;

    /** 用户是否存在此角色标识 默认不存在 */
   /* @TableField(exist = false)
    private boolean flag = false;*/

    /**
     * 菜单组
     */
    @TableField(exist = false)
    private Long[] menuIds;

    /**
     * 部门组（数据权限）
     */
    @TableField(exist = false)
    private Long[] deptIds;

    /* *//** 菜单树选择项是否关联显示（ 0：父子不互相关联显示 1：父子互相关联显示） *//*
    @TableField(exist = false)
    private transient  boolean menuCheckStrictly;

    *//** 部门树选择项是否关联显示（0：父子不互相关联显示 1：父子互相关联显示 ） *//*
    @TableField(exist = false)
    private transient  boolean deptCheckStrictly;*/

}
