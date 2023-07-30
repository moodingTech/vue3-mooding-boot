package cn.mooding.modules.system.entity;

import cn.mooding.common.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 字典类型表
 * </p>
 *
 * @author BlueFire
 * @since 2021-05-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sys_dict_type")
@ApiModel(value = "SysDictType对象", description = "字典类型表")
public class SysDictType extends BaseEntity {

    private static final long serialVersionUID = -2524839542283202975L;

    @ApiModelProperty(value = "字典主键")
    @TableId(value = "dict_id", type = IdType.AUTO)
    private Long dictId;

    @ApiModelProperty(value = "字典名称")
    private String dictName;

    @ApiModelProperty(value = "字典类型")
    private String dictType;

    @ApiModelProperty(value = "状态（0正常 1停用）")
    private String status;

    @ApiModelProperty(value = "备注")
    private String remark;
}
