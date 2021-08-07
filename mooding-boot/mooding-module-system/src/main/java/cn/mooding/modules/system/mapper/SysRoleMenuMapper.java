package cn.mooding.modules.system.mapper;

import cn.mooding.modules.system.entity.SysRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 角色和菜单关联表 Mapper 接口
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-21
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
    /**
     * 批量新增角色菜单信息
     *
     * @param roleMenuList 角色菜单列表
     * @return 结果
     */
    public int batchRoleMenu(List<SysRoleMenu> roleMenuList);

    /**
     * 通过角色ID删除角色和菜单关联
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @Delete("delete from t_sys_role_menu  where role_id=#{roleId}")
    public int deleteRoleMenuByRoleId(@Param("roleId") Long roleId);

    /**
     * 通过菜单id查询具有的角色
     * @param menuId
     * @return 结果
     */
    @Select("select * from t_sys_role_menu  where menu_id=#{menuId}")
    public List<Long> seleectRoleCountByMenuId(@Param("menuId") Long menuId);

}
