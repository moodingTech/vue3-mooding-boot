package cn.mooding.modules.system.controller;


import cn.mooding.common.aspect.annotation.Log;
import cn.mooding.common.model.dto.ResponseResult;
import cn.mooding.common.model.enums.BusinessType;
import cn.mooding.common.model.enums.HttpCodeEnum;
import cn.mooding.common.utils.ServletUtils;
import cn.mooding.common.utils.generate.QueryGenerator;
import cn.mooding.common.utils.poi.ExcelUtil;
import cn.mooding.common.utils.string.StringUtils;
import cn.mooding.modules.security.LoginUser;
import cn.mooding.modules.security.service.SysPermissionService;
import cn.mooding.modules.security.service.TokenService;
import cn.mooding.modules.security.utils.SecurityUtils;
import cn.mooding.modules.system.entity.SysRole;
import cn.mooding.modules.system.entity.SysUser;
import cn.mooding.modules.system.service.ISysRoleService;
import cn.mooding.modules.system.service.ISysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
<<<<<<< HEAD
=======
import io.swagger.annotations.Api;
>>>>>>> master
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 角色信息表 前端控制器
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-21
 */
@RestController
@RequestMapping("/system/role")
<<<<<<< HEAD
=======
@Api(tags = "系统角色")
>>>>>>> master
public class SysRoleController {

    private static final Logger log = LoggerFactory.getLogger(SysRoleController.class);
    @Autowired
    private ISysRoleService roleService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private SysPermissionService permissionService;
    @Autowired
    private ISysUserService userService;

    @GetMapping("/list")
    @PreAuthorize("@md.hasPermi('system:role:list')")
    @ApiOperation(value = "分页查询角色信息", notes = "")
    public ResponseResult<IPage<SysRole>> queryPageList(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, SysRole sysRole) {
        QueryWrapper<SysRole> queryWrapper = QueryGenerator.initQueryWrapper(sysRole);
        Page<SysRole> page = new Page<SysRole>(pageNo, pageSize);
        queryWrapper.lambda().notIn(SysRole::getDelFlag, "2");
        IPage<SysRole> pageList = roleService.selectMapsPage(page, queryWrapper);
        return ResponseResult.okResult(pageList);
    }


    @GetMapping("/export")
    @PreAuthorize("@md.hasPermi('system:role:export')")
    @ApiOperation(value = "导出数据", notes = "")
    @Log(title = "系统管理-角色管理-导出数据", businessType = BusinessType.EXPORT)
    public ResponseResult export(SysRole sysRole) {
        QueryWrapper<SysRole> queryWrapper = QueryGenerator.initQueryWrapper(sysRole);
        List<SysRole> list = roleService.list(queryWrapper);

        ExcelUtil<SysRole> util = new ExcelUtil<SysRole>(SysRole.class);
        return util.exportExcel(list, "角色数据");
    }

    /**
     * 根据角色编号获取详细信息
     */
    @GetMapping(value = "/{roleId}")
    @PreAuthorize("@md.hasPermi('system:role:query')")
    @ApiOperation(value = "根据ID查询角色", notes = "")
    public ResponseResult getInfo(@PathVariable Long roleId) {
        return ResponseResult.okResult(roleService.getById(roleId));
    }

    /**
     * 新增角色
     */
    @PostMapping
    @PreAuthorize("@md.hasPermi('system:role:add')")
    @ApiOperation(value = "新增角色", notes = "")
    @Log(title = "系统管理-角色管理-新增角色", businessType = BusinessType.INSERT)
    public ResponseResult add(@Validated @RequestBody SysRole role) {
        int checkNo = roleService.checkRoleUnique(role);
        if (checkNo == 1) {
            return ResponseResult.errorResult(HttpCodeEnum.EMAIL_EXIST, "新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
        } else if (checkNo == 2) {
            return ResponseResult.errorResult(HttpCodeEnum.EMAIL_EXIST, "新增角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        return ResponseResult.okResult(roleService.insertRole(role));
    }

    /**
     * 修改保存角色
     */


    @PutMapping
    @PreAuthorize("@md.hasPermi('system:role:edit')")
    @ApiOperation(value = "修改保存角色", notes = "")
    @Log(title = "系统管理-角色管理-修改角色", businessType = BusinessType.UPDATE)
    public ResponseResult edit(@Validated @RequestBody SysRole role) {
//        roleService.checkRoleAllowed(role);
        int checkNo = roleService.checkRoleUnique(role);
        if (checkNo == 1) {
            return ResponseResult.errorResult(HttpCodeEnum.EMAIL_EXIST, "修改角色'" + role.getRoleName() + "'失败，角色名称已存在");
        } else if (checkNo == 1) {
            return ResponseResult.errorResult(HttpCodeEnum.EMAIL_EXIST, "修改角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }

        if (roleService.updateRole(role) > 0) {
            // 更新缓存用户权限
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            if (StringUtils.isNotNull(loginUser.getUser()) && !SecurityUtils.isAdmin()) {
                loginUser.setPermissions(permissionService.getMenuPermission(loginUser.getUser()));
                loginUser.setUser(userService.selectUserByUserName(loginUser.getUser().getUserName()));
                tokenService.setLoginUser(loginUser);
            }
            return ResponseResult.okResult();
        }
        return ResponseResult.errorResult(HttpCodeEnum.SERVER_ERROR, "修改角色'" + role.getRoleName() + "'失败，请联系管理员");
    }

    /**
     * 修改保存数据权限
     */

    @PutMapping("/dataScope")
    @PreAuthorize("@md.hasPermi('system:role:edit')")
    @ApiOperation(value = "修改保存数据权限", notes = "")
    @Log(title = "系统管理-角色管理-修改角色数据权限", businessType = BusinessType.UPDATE)
    public ResponseResult dataScope(@RequestBody SysRole role) {
//        roleService.checkRoleAllowed(role);
        return ResponseResult.okResult(roleService.authDataScope(role));
    }

    /**
     * 角色状态修改
     */


    @PutMapping("/changeStatus")
    @PreAuthorize("@md.hasPermi('system:role:edit')")
    @ApiOperation(value = "角色状态修改", notes = "")
    @Log(title = "系统管理-角色管理-角色状态修改", businessType = BusinessType.UPDATE)
    public ResponseResult changeStatus(@RequestBody SysRole role) {
//        roleService.checkRoleAllowed(role);
        return ResponseResult.okResult(roleService.updateRoleStatus(role));
    }

    /**
     * 删除角色
     */
    @DeleteMapping("/{roleIds}")
    @PreAuthorize("@md.hasPermi('system:role:remove')")
    @ApiOperation(value = "删除角色", notes = "")
    @Log(title = "系统管理-角色管理-删除角色", businessType = BusinessType.DELETE)
    public ResponseResult remove(@PathVariable Long[] roleIds) {
        return ResponseResult.okResult(roleService.deleteRoleByIds(roleIds));
    }

    /**
     * 获取角色选择框列表
     */
    @GetMapping("/optionselect")
    @ApiOperation(value = "获取角色选择框列表", notes = "")
    public ResponseResult optionselect() {
        return ResponseResult.okResult(roleService.selectRoleAll());
    }
}
