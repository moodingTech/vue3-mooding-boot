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
 * 操作日志记录
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sys_oper_log")
@ApiModel(value="SysOperLog对象", description="操作日志记录")
public class SysOperLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "日志主键")
    @TableId(value = "oper_id", type = IdType.AUTO)
    private Long operId;

    @ApiModelProperty(value = "模块标题")
    private String title;

    @ApiModelProperty(value = "业务类型（0其它 1新增 2修改 3删除）")
    private Integer businessType;

    @ApiModelProperty(value = "方法名称")
    private String method;

    @ApiModelProperty(value = "请求方式")
    private String requestMethod;

    @ApiModelProperty(value = "操作类别（0其它 1后台用户 2手机端用户）")
    private Integer operatorType;

    @ApiModelProperty(value = "操作人员")
    private String operName;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "请求URL")
    private String operUrl;

    @ApiModelProperty(value = "主机地址")
    private String operIp;

    @ApiModelProperty(value = "操作地点")
    private String operLocation;

    @ApiModelProperty(value = "请求参数")
    private String operParam;

    @ApiModelProperty(value = "返回参数")
    private String jsonResult;

    @ApiModelProperty(value = "操作状态（0正常 1异常）")
    private Integer status;

    @ApiModelProperty(value = "错误消息")
    private String errorMsg;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime operTime;


    public static final String OPER_ID = "oper_id";

    public static final String TITLE = "title";

    public static final String BUSINESS_TYPE = "business_type";

    public static final String METHOD = "method";

    public static final String REQUEST_METHOD = "request_method";

    public static final String OPERATOR_TYPE = "operator_type";

    public static final String OPER_NAME = "oper_name";

    public static final String DEPT_NAME = "dept_name";

    public static final String OPER_URL = "oper_url";

    public static final String OPER_IP = "oper_ip";

    public static final String OPER_LOCATION = "oper_location";

    public static final String OPER_PARAM = "oper_param";

    public static final String JSON_RESULT = "json_result";

    public static final String STATUS = "status";

    public static final String ERROR_MSG = "error_msg";

    public static final String OPER_TIME = "oper_time";

}
