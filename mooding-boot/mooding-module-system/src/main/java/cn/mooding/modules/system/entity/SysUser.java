package cn.mooding.modules.system.entity;

import cn.mooding.common.aspect.annotation.Excel;
import cn.mooding.common.aspect.annotation.Excels;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sys_user")
@ApiModel(value = "SysUser对象", description = "用户信息表")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableId(value = "user_id", type = IdType.AUTO)
    @Excel(name = "用户序号", cellType = Excel.ColumnType.NUMERIC, prompt = "用户编号")
    private Long userId;

    @ApiModelProperty(value = "部门ID")
    @Excel(name = "部门编号", type = Excel.Type.IMPORT)
    private Long deptId;

    @ApiModelProperty(value = "用户账号")
    @Excel(name = "用户账号")
    private String userName;

    @ApiModelProperty(value = "用户昵称")
    @Excel(name = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "用户类型（00系统用户）")
    @Excel(name = "用户类型", readConverterExp = "00=系统用户,01=未知")
    private String userType;

    @ApiModelProperty(value = "用户邮箱")
    @Excel(name = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "手机号码")
    @Excel(name = "手机号码")
    private String phonenumber;

    @ApiModelProperty(value = "用户性别（0男 1女 2未知）")
    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    private String sex;

    @ApiModelProperty(value = "头像地址")
    private String avatar;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "帐号状态（0正常 1停用）")
    @Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
    private String status;

    @ApiModelProperty(value = "删除标志（0代表存在 2代表删除）")
    private String delFlag;

    @ApiModelProperty(value = "最后登陆IP")

    @Excel(name = "最后登录IP", type = Excel.Type.EXPORT)
    private String loginIp;

    @ApiModelProperty(value = "最后登陆时间")
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Excel(name = "最后登陆时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date loginDate;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "备注")
    @Excel(name = "备注")
    private String remark;

    /**
     * 部门对象
     */
    @ApiModelProperty(value = "部门对象")
    @TableField(exist = false)
    @Excels({
            @Excel(name = "部门名称", targetAttr = "deptName", type = Excel.Type.EXPORT),
            @Excel(name = "部门负责人", targetAttr = "leader", type = Excel.Type.EXPORT)
    })
    private SysDept dept;

    /**
     * 角色对象
     */
    @ApiModelProperty(value = "角色对象")
    @TableField(exist = false)
    private List<SysRole> roles;

    /**
     * 角色组
     */
    @ApiModelProperty(value = "roleIds")
    @TableField(exist = false)
    private Long[] roleIds;

    /**
     * 岗位组
     */
    @ApiModelProperty(value = "postIds")
    @TableField(exist = false)
    private Long[] postIds;

}
