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
 * 操作日志记录
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sys_oper_log")
@ApiModel(value = "SysOperLog对象", description = "操作日志记录")
public class SysOperLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "日志主键")
    @TableId(value = "oper_id", type = IdType.AUTO)
    @Excel(name = "操作序号", cellType = Excel.ColumnType.NUMERIC)
    private Long operId;

    @ApiModelProperty(value = "模块标题")
    @Excel(name = "操作模块")
    private String title;

    @ApiModelProperty(value = "业务类型（0其它 1新增 2修改 3删除）")
    @Excel(name = "业务类型", readConverterExp = "0=其它,1=新增,2=修改,3=删除,4=授权,5=导出,6=导入,7=强退,8=生成代码,9=清空数据")
    private Integer businessType;

    @ApiModelProperty(value = "方法名称")
    @Excel(name = "请求方法")
    private String method;

    @ApiModelProperty(value = "请求方式")
    @Excel(name = "请求方式")
    private String requestMethod;

    @ApiModelProperty(value = "操作类别（0其它 1后台用户 2手机端用户）")
    @Excel(name = "操作类别", readConverterExp = "0=其它,1=后台用户,2=手机端用户")
    private Integer operatorType;

    @ApiModelProperty(value = "操作人员")
    @Excel(name = "操作人员")
    private String operName;

    @ApiModelProperty(value = "部门名称")
    @Excel(name = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "请求URL")
    @Excel(name = "请求URL")
    private String operUrl;

    @ApiModelProperty(value = "主机地址")
    @Excel(name = "主机地址")
    private String operIp;

    @ApiModelProperty(value = "操作地点")
    @Excel(name = "操作地点")
    private String operLocation;

    @ApiModelProperty(value = "请求参数")
    @Excel(name = "请求参数")
    private String operParam;

    @ApiModelProperty(value = "返回参数")
    @Excel(name = "返回参数")
    private String jsonResult;

    @ApiModelProperty(value = "操作状态（0正常 1异常）")
    @Excel(name = "状态", readConverterExp = "0=正常,1=异常")
    private Integer status;

    @ApiModelProperty(value = "错误消息")
    @Excel(name = "错误消息")
    private String errorMsg;

    @ApiModelProperty(value = "操作时间")
    @Excel(name = "操作时间", width = 30, localDateTimeFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime operTime;
}
