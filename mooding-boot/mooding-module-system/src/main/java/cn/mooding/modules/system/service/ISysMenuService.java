package cn.mooding.modules.system.service;

import cn.mooding.modules.system.entity.SysMenu;
import cn.mooding.modules.system.model.vo.RouterVo;
import cn.mooding.modules.system.model.vo.TreeSelect;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-21
 */
public interface ISysMenuService extends IService<SysMenu> {
    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    public Set<String> selectMenuPermsByUserId(Long userId);
    /**
     * 根据用户ID查询菜单树信息
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    public List<SysMenu> selectMenuTreeByUserId(Long userId);

    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    public List<RouterVo> buildMenus(List<SysMenu> menus);
    /**
     * 根据用户查询系统菜单列表
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    public List<SysMenu> selectMenuList(Long userId);
    //查询系统菜单列表
    public List<SysMenu> selectMenuList(SysMenu menu, Long userId);

    //根据角色ID查询菜单树信息
    public List<Integer> selectMenuListByRoleId(Long roleId);

    //构建前端所需要下拉树结构
    public List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus);

    //构建前端所需要树结构
    public List<SysMenu> buildMenuTree(List<SysMenu> menus);

    //校验菜单名称是否唯一
    public String checkMenuNameUnique(SysMenu menu);
    //是否存在菜单子节点
    public boolean hasChildByMenuId(Long menuId);
    //查询菜单是否存在角色
    public boolean checkMenuExistRole(Long menuId);

}
