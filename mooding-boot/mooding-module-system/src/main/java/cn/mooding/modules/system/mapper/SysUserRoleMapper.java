package cn.mooding.modules.system.mapper;

import cn.mooding.modules.system.entity.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户和角色关联表 Mapper 接口
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-21
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
    /**
     * 批量新增用户角色信息
     *
     * @param userRoleList 用户角色列表
     * @return 结果
     */
    public int batchUserRole(List<SysUserRole> userRoleList);

    /**
     * 通过用户id查询角色
     *
     * @param userId 用户id
     * @return
     */
    @Select("select role_id from t_sys_user_role where  user_id =#{userId}")
    public List<Long> getRoleIdsByUserId(@Param("userId") Long userId);

    /**
     * 通过用户ID删除用户和角色关联
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Delete("delete from t_sys_user_role where  user_id =#{userId} ")
    public int deleteUserRoleByUserId(@Param("userId") Long userId);

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @Select("select count(1) from t_sys_user_role where role_id=#{roleId}")
    public int countUserRoleByRoleId(@Param("roleId") Long roleId);
}
