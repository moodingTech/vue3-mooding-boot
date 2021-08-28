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
 * 岗位信息表
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sys_post")
@ApiModel(value = "SysPost对象", description = "岗位信息表")
public class SysPost implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "岗位ID")
    @TableId(value = "post_id", type = IdType.AUTO)
    @Excel(name = "岗位序号", cellType = Excel.ColumnType.NUMERIC)
    private Long postId;

    @ApiModelProperty(value = "岗位编码")
    @Excel(name = "岗位编码")
    private String postCode;

    @ApiModelProperty(value = "岗位名称")
    @Excel(name = "岗位名称")
    private String postName;

    @ApiModelProperty(value = "显示顺序")
    @Excel(name = "显示顺序")
    private Integer postSort;

    @ApiModelProperty(value = "状态（0正常 1停用）")
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    @ApiModelProperty(value = "删除标志（0代表存在 2代表删除）")
    private String delFlag;
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
