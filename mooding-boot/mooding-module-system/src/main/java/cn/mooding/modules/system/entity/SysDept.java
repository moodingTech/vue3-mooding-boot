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
 * 部门表
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sys_dept")
@ApiModel(value = "SysDept对象", description = "部门表")
public class SysDept extends BaseEntity {

    private static final long serialVersionUID = 6330778161962293354L;

    @ApiModelProperty(value = "部门id")
    @TableId(value = "dept_id", type = IdType.AUTO)
    private Long deptId;

    @ApiModelProperty(value = "父部门id")
    private Long parentId;

    @ApiModelProperty(value = "祖级列表")
    private String ancestors;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "显示顺序")
    private Integer orderNum;

    @ApiModelProperty(value = "负责人")
    private String leader;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "部门状态（0正常 1停用）")
    private String status;

    @ApiModelProperty(value = "删除标志（0代表存在 2代表删除）")
    private String delFlag;

    /**
     * 子菜单
     */
    @TableField(exist = false)
    private List<SysDept> children = new ArrayList<SysDept>();


}
