package cn.mooding.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色和部门关联表
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sys_role_dept")
@ApiModel(value="SysRoleDept对象", description="角色和部门关联表")
public class SysRoleDept implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    @ApiModelProperty(value = "部门ID")
    private Long deptId;


    public static final String ROLE_ID = "role_id";

    public static final String DEPT_ID = "dept_id";

}
