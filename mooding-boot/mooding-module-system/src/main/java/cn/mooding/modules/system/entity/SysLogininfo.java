package cn.mooding.modules.system.entity;

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
@ApiModel(value="SysLogininfo对象", description="系统访问记录")
public class SysLogininfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "访问ID")
    @TableId(value = "info_id", type = IdType.AUTO)
    private Long infoId;

    @ApiModelProperty(value = "用户账号")
    private String userName;

    @ApiModelProperty(value = "登录IP地址")
    private String ipaddr;

    @ApiModelProperty(value = "登录地点")
    private String loginLocation;

    @ApiModelProperty(value = "浏览器类型")
    private String browser;

    @ApiModelProperty(value = "操作系统")
    private String os;

    @ApiModelProperty(value = "登录状态（0成功 1失败）")
    private String status;

    @ApiModelProperty(value = "提示消息")
    private String msg;

    @ApiModelProperty(value = "访问时间")
    private LocalDateTime loginTime;


    public static final String INFO_ID = "info_id";

    public static final String USER_NAME = "user_name";

    public static final String IPADDR = "ipaddr";

    public static final String LOGIN_LOCATION = "login_location";

    public static final String BROWSER = "browser";

    public static final String OS = "os";

    public static final String STATUS = "status";

    public static final String MSG = "msg";

    public static final String LOGIN_TIME = "login_time";

}
