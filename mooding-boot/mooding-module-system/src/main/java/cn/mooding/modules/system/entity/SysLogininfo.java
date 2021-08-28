package cn.mooding.modules.system.entity;

import cn.mooding.common.aspect.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统访问记录
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sys_logininfo")
@ApiModel(value = "SysLogininfo对象", description = "系统访问记录")
public class SysLogininfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "访问ID")
    @TableId(value = "info_id", type = IdType.AUTO)
    @Excel(name = "序号", cellType = Excel.ColumnType.NUMERIC)
    private Long infoId;

    @ApiModelProperty(value = "用户账号")
    @Excel(name = "用户账号")
    private String userName;

    @ApiModelProperty(value = "登录IP地址")
    @Excel(name = "登录地址")
    private String ipaddr;

    @ApiModelProperty(value = "登录地点")
    @Excel(name = "登录地点")
    private String loginLocation;

    @ApiModelProperty(value = "浏览器类型")
    @Excel(name = "浏览器类型")
    private String browser;

    @ApiModelProperty(value = "操作系统")
    @Excel(name = "操作系统")
    private String os;

    @ApiModelProperty(value = "登录状态（0成功 1失败）")
    @Excel(name = "登录状态", readConverterExp = "0=成功,1=失败")
    private String status;

    @ApiModelProperty(value = "提示消息")
    @Excel(name = "提示消息")
    private String msg;

    @ApiModelProperty(value = "访问时间")
    @Excel(name = "访问时间", width = 30, localDateTimeFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime loginTime;


}
