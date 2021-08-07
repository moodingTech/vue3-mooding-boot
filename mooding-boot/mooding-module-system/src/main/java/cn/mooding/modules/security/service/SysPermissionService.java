package cn.mooding.modules.security.service;

import cn.mooding.modules.system.entity.SysRole;
import cn.mooding.modules.system.entity.SysUser;
import cn.mooding.modules.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户权限处理
 *
 * @Author BlueFire
 * @Date 23/3/2021 -下午7:52
 */
@Component
public class SysPermissionService {
    @Autowired
    private ISysMenuService menuService;

    /**
     * 获取菜单数据权限
     *
     * @param user 用户信息
     * @return 菜单权限信息
     */
    public Set<String> getMenuPermission(SysUser user) {
        Set<String> perms = new HashSet<String>();
        // 管理员拥有所有权限
        if (user.getRoles().stream().filter(w -> w.getRoleKey().equals("admin")).findAny().isPresent()) {
            perms.add("*:*:*");
        }
        /*if (user.getRoles().contains()) {
            perms.add("*:*:*");
        } */
        else {
            perms.addAll(menuService.selectMenuPermsByUserId(user.getUserId()));
        }
        return perms;
    }

    /**
     * 获取角色数据权限
     *
     * @param user 用户信息
     * @return 角色权限信息
     */
    public Set<String> getRolePermission(SysUser user) {
        Set<String> roles = new HashSet<String>();
        // 管理员拥有所有权限
      /*  if (user.isAdmin())
        {
            roles.add("admin");
        }
        else
        {
            roles.addAll(roleService.selectRolePermissionByUserId(user.getUserId()));
        }
        return roles;*/

        if (user.getRoles().stream().filter(w -> w.getRoleKey().equals( "admin")).findAny().isPresent()) {
            roles.add("admin");
        } else {
            roles = user.getRoles().stream().map(SysRole::getRoleKey).collect(Collectors.toSet());
        }
        return roles;
    }
}
