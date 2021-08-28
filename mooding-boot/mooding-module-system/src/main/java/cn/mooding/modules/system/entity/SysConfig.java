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
 * 参数配置表
 * </p>
 *
 * @author BlueFire
 * @since 2021-07-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sys_config")
@ApiModel(value = "SysConfig对象", description = "参数配置表")
public class SysConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "参数主键")
    @TableId(value = "config_id", type = IdType.AUTO)
    @Excel(name = "参数主键", cellType = Excel.ColumnType.NUMERIC)
    private Integer configId;

    @ApiModelProperty(value = "参数名称")
    @Excel(name = "参数名称")
    private String configName;

    @ApiModelProperty(value = "参数键名")
    @Excel(name = "参数键名")
    private String configKey;

    @ApiModelProperty(value = "参数键值")
    @Excel(name = "参数键值")
    private String configValue;

    @ApiModelProperty(value = "系统内置（Y是 N否）")
    @Excel(name = "系统内置", readConverterExp = "Y=是,N=否")
    private String configType;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "备注")
    @Excel(name = "备注")
    private String remark;
}
