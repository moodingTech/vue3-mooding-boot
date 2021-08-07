package cn.mooding.modules.system.mapper;

import cn.mooding.modules.system.entity.SysRoleDept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 角色和部门关联表 Mapper 接口
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-21
 */
public interface SysRoleDeptMapper extends BaseMapper<SysRoleDept> {
    /**
     * 通过角色ID查询角色和部门关联
     *
     * @param roleId 角色ID
     * @return
     */
    @Select("select dept_id from t_sys_role_dept where role_id =#{roleId}")
    List<Long> getDeptIdsByRoleId(@Param("roleId") Long roleId);

    /**
     * 通过角色ID删除角色和部门关联
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @Delete("delete from t_sys_role_dept where role_id=#{roleId}")
    public int deleteRoleDeptByRoleId(@Param("roleId") Long roleId);

    /**
     * 批量新增角色部门信息
     *
     * @param roleDeptList 角色部门列表
     * @return 结果
     */
    public int batchRoleDept(List<SysRoleDept> roleDeptList);
}
