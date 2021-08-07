package cn.mooding.modules.system.controller;


import cn.mooding.common.aspect.annotation.Log;
import cn.mooding.common.model.constant.CommonConstant;
import cn.mooding.common.model.constant.Constants;
import cn.mooding.common.model.dto.ResponseResult;
import cn.mooding.common.model.enums.BusinessType;
import cn.mooding.common.model.enums.HttpCodeEnum;
import cn.mooding.common.utils.ServletUtils;
import cn.mooding.common.utils.string.StringUtils;
import cn.mooding.modules.security.LoginUser;
import cn.mooding.modules.security.service.TokenService;
import cn.mooding.modules.security.utils.SecurityUtils;
import cn.mooding.modules.system.entity.SysMenu;
import cn.mooding.modules.system.service.ISysMenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单权限表 前端控制器
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-21
 */
@RestController
@RequestMapping("/system/menu")
public class SysMenuController {
    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private TokenService tokenService;

    /**
     * 加载对应角色菜单列表树
     */
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    @ApiOperation(value = "加载对应角色菜单列表树", notes = "")
    public ResponseResult roleMenuTreeselect(@PathVariable("roleId") Long roleId) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        List<SysMenu> menus = menuService.selectMenuList(loginUser.getUser().getUserId());
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("checkedKeys", menuService.selectMenuListByRoleId(roleId));
        data.put("menus", menuService.buildMenuTreeSelect(menus));
        return ResponseResult.okResult(data);
    }

    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/treeselect")
    @ApiOperation(value = "获取菜单下拉树列表", notes = "")
    public ResponseResult treeselect() {
        Long userId = SecurityUtils.getLoginUser().getUser().getUserId();
        List<SysMenu> menus = menuService.selectMenuList(userId);
        return ResponseResult.okResult(menuService.buildMenuTreeSelect(menus));
    }

    /**
     * 获取菜单列表
     */
    @GetMapping("/list")
    @PreAuthorize("@md.hasPermi('system:menu:list')")
    @ApiOperation(value = "获取菜单列表", notes = "")
    public ResponseResult list(SysMenu menu) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long userId = loginUser.getUser().getUserId();
        List<SysMenu> menus = menuService.selectMenuList(menu, userId);
        return ResponseResult.okResult(menus);
    }

    /**
     * 根据菜单编号获取详细信息
     */
    @GetMapping(value = "/{menuId}")
    @PreAuthorize("@md.hasPermi('system:menu:query')")
    @ApiOperation(value = "根据菜单编号获取详细信息", notes = "")
    public ResponseResult getInfo(@PathVariable Long menuId) {
        return ResponseResult.okResult(menuService.getById(menuId));
    }

    /**
     * 新增菜单
     */

    @PostMapping
    @PreAuthorize("@md.hasPermi('system:menu:add')")
    @ApiOperation(value = "新增菜单", notes = "")
    @Log(title = "系统管理-菜单管理-新增", businessType = BusinessType.INSERT)
    public ResponseResult add(@Validated @RequestBody SysMenu menu) {
        if (CommonConstant.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
            return ResponseResult.errorResult(HttpCodeEnum.DATA_EXIST, "新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        } else if (CommonConstant.YES_FRAME.equals(menu.getIsFrame())
                && !StringUtils.startsWithAny(menu.getPath(), Constants.HTTP, Constants.HTTPS)) {
            return ResponseResult.errorResult(HttpCodeEnum.PARAM_ERR, "新增菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        }
        menu.setCreateBy(SecurityUtils.getUsername());
        return menuService.save(menu) ? ResponseResult.okResult(menuService.save(menu)) : ResponseResult.errorResult(HttpCodeEnum.SERVER_ERROR);
    }

    /**
     * 修改菜单
     */
    @PutMapping
    @PreAuthorize("@md.hasPermi('system:menu:edit')")
    @ApiOperation(value = "修改菜单", notes = "")
    @Log(title = "系统管理-菜单管理-修改菜单", businessType = BusinessType.UPDATE)
    public ResponseResult edit(@Validated @RequestBody SysMenu menu) {
        if (CommonConstant.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
            return ResponseResult.errorResult(HttpCodeEnum.DATA_EXIST, "修改菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        } else if (CommonConstant.YES_FRAME.equals(menu.getIsFrame())
                && !StringUtils.startsWithAny(menu.getPath(), Constants.HTTP, Constants.HTTPS)) {
            return ResponseResult.errorResult(HttpCodeEnum.PARAM_ERR, "修改菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        } else if (menu.getMenuId().equals(menu.getParentId())) {
            return ResponseResult.errorResult(HttpCodeEnum.PARAM_ERR, "修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        return menuService.updateById(menu) ? ResponseResult.okResult(menuService.updateById(menu)) : ResponseResult.errorResult(HttpCodeEnum.SERVER_ERROR);
    }

    /**
     * 删除菜单
     */

    @DeleteMapping("/{menuId}")
    @PreAuthorize("@md.hasPermi('system:menu:remove')")
    @ApiOperation(value = "删除菜单", notes = "")
    @Log(title = "系统管理-菜单管理-删除菜单", businessType = BusinessType.DELETE)
    public ResponseResult remove(@PathVariable("menuId") Long menuId) {
        if (menuService.hasChildByMenuId(menuId)) {
            return ResponseResult.errorResult(HttpCodeEnum.PARAM_INVALID, "存在子菜单,不允许删除");
        }
        if (menuService.checkMenuExistRole(menuId)) {
            return ResponseResult.errorResult(HttpCodeEnum.PARAM_INVALID, "菜单已分配,不允许删除");
        }
        return menuService.removeById(menuId) ? ResponseResult.okResult("删除成功") : ResponseResult.errorResult(HttpCodeEnum.SERVER_ERROR);
    }
}
