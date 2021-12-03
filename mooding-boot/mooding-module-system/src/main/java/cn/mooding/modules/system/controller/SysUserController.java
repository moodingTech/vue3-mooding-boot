package cn.mooding.modules.system.controller;


import cn.mooding.common.aspect.annotation.Log;
import cn.mooding.common.model.dto.ResponseResult;
import cn.mooding.common.model.enums.BusinessType;
import cn.mooding.common.model.enums.HttpCodeEnum;
import cn.mooding.common.utils.MessageUtils;
import cn.mooding.common.utils.ServletUtils;
import cn.mooding.common.utils.generate.QueryGenerator;
import cn.mooding.common.utils.poi.ExcelUtil;
import cn.mooding.common.utils.string.StringUtils;
import cn.mooding.modules.security.LoginUser;
import cn.mooding.modules.security.service.TokenService;
import cn.mooding.modules.security.utils.SecurityUtils;
import cn.mooding.modules.system.entity.SysDept;
import cn.mooding.modules.system.entity.SysRole;
import cn.mooding.modules.system.entity.SysUser;
import cn.mooding.modules.system.service.ISysDeptService;
import cn.mooding.modules.system.service.ISysRoleService;
import cn.mooding.modules.system.service.ISysUserRoleService;
import cn.mooding.modules.system.service.ISysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-21
 */
@RestController
@RequestMapping("/system/user")
@Api(tags = "系统用户")
public class SysUserController {
    private static final Logger log = LoggerFactory.getLogger(SysOperLogController.class);
    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISysRoleService roleService;
    @Autowired
    private ISysUserRoleService userRoleService;

    @Autowired
    private TokenService tokenService;

    @GetMapping(value = "/list")
    @PreAuthorize("@md.hasPermi('system:user:list')")
    @ApiOperation(value = "分页查询用户列表", notes = "")
    public ResponseResult<IPage<SysUser>> queryPageList(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, SysUser sysUser) {
        QueryWrapper<SysUser> queryWrapper = QueryGenerator.initQueryWrapper(sysUser);
        Page<SysUser> page = new Page<SysUser>(pageNo, pageSize);
        queryWrapper.lambda().notIn(SysUser::getDelFlag, "2");

        IPage<SysUser> pageList = userService.selectMapsPage(page, queryWrapper);
        log.info("查询当前页：" + pageList.getCurrent());
        log.info("查询当前页数量：" + pageList.getSize());
        log.info("查询结果数量：" + pageList.getRecords().size());
        log.info("数据总数：" + pageList.getTotal());
        return ResponseResult.okResult(pageList);
    }


    @GetMapping("/export")
    @PreAuthorize("@md.hasPermi('system:user:export')")
    @ApiOperation(value = "导出数据", notes = "")
    @Log(title = "系统管理-用户管理-导出数据", businessType = BusinessType.EXPORT)
    public ResponseResult export(SysUser sysUser) {
        QueryWrapper<SysUser> queryWrapper = QueryGenerator.initQueryWrapper(sysUser);
        List<SysUser> list = userService.list(queryWrapper);
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        return util.exportExcel(list, "用户数据");
    }


    @PostMapping("/importData")
    @PreAuthorize("@md.hasPermi('system:user:import')")
    @Log(title = "系统管理-用户管理-导入用户数据", businessType = BusinessType.IMPORT)
    public ResponseResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        List<SysUser> userList = util.importExcel(file.getInputStream());
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        String message = userService.importUser(userList, updateSupport, operName);
        return ResponseResult.okResult(message);
    }

    @GetMapping("/{name}")
    public ResponseResult getUserById(@PathVariable(value = "name", required = false) String name) {
        SysUser user = userService.selectUserByUserName(name);
        return ResponseResult.okResult(user);
    }

    /**
     * 新增用户
     */
    @PutMapping("/add")
    @PreAuthorize("@md.hasPermi('system:user:add')")
    @ApiOperation(value = "新增用户", notes = "")
    @Log(title = "系统管理-用户管理-新增用户", businessType = BusinessType.INSERT)
    public ResponseResult add(@Validated @RequestBody SysUser user) {
        if (StringUtils.isNotEmpty(user.getUserName())
                && userService.checkUserNameUnique(user)) {
            return ResponseResult.errorResult(HttpCodeEnum.USER_NAME_EXIST.getCode(), "新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        } else if (StringUtils.isNotEmpty(user.getPhonenumber())
                && userService.checkPhoneUnique(user)) {
            return ResponseResult.errorResult(HttpCodeEnum.PHONE_EXIST.getCode(), "新增用户'" + user.getUserName() + "'失败，手机号码:" + user.getPhonenumber() + ",已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail())
                && userService.checkEmailUnique(user)) {
            return ResponseResult.errorResult(HttpCodeEnum.EMAIL_EXIST.getCode(), "新增用户'" + user.getUserName() + "'失败，邮箱账号:" + user.getEmail() + ",已存在");
        }
        userService.addSysUser(user);
<<<<<<< HEAD
        return ResponseResult.okResult(MessageUtils.message("user.login.success", null));
=======
        return ResponseResult.okResult(MessageUtils.message("user.login.success", new Object[]{}));
>>>>>>> master
    }

    /**
     * 根据用户编号获取详细信息
     */

    @GetMapping(value = {"/getById", "/getById/{userId}"})
    @PreAuthorize("@md.hasPermi('system:user:query')")
    @ApiOperation(value = "根据ID查询用户", notes = "")
    public ResponseResult getInfo(@PathVariable(value = "userId", required = false) Long userId) {
        Map<String, Object> userInfo = new HashMap<String, Object>();
        // 角色处理
        List<SysRole> roles = null;
        if (SecurityUtils.isAdmin()) {
<<<<<<< HEAD
            roles = roleService.list();
=======
            roles = roleService.selectRoleAll();
>>>>>>> master
        } else {
            roles = SecurityUtils.getLoginUser().getUser().getRoles();//登陆具有的角色才能查询出来
        }
        // 部门处理
//        Set<Long> loginUserDeptIds = deptService.getLoginUserDeptIds();
//        List<SysDept> sysDepts = deptService.listByIds(loginUserDeptIds);
        userInfo.put("roles", roles);
        userInfo.put("posts", "");
        if (StringUtils.isNotNull(userId)) {
            userInfo.put("userInfo", userService.getById(userId));
            userInfo.put("roleIds", userRoleService.getRoleIdsByUserId(userId));
        }
        return ResponseResult.okResult(userInfo);
    }

    /**
     * 修改用户
     */

    @PutMapping("/edit")
    @PreAuthorize("@md.hasPermi('system:user:edit')")
    @ApiOperation(value = "修改用户", notes = "")
    @Log(title = "系统管理-用户管理-修改用户", businessType = BusinessType.UPDATE)
    public ResponseResult edit(@Validated @RequestBody SysUser user) {
//        userService.checkUserAllowed(user);
        //判断是不是管理员，管理员不能被修改。

        if (StringUtils.isNotEmpty(user.getPhonenumber())
                && userService.checkPhoneUnique(user)) {
            return ResponseResult.errorResult(HttpCodeEnum.PHONE_EXIST.getCode(), "修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail())
                && userService.checkEmailUnique(user)) {
            return ResponseResult.errorResult(HttpCodeEnum.EMAIL_EXIST.getCode(), "修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        return ResponseResult.okResult(userService.updateUser(user));
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{userIds}")
    @PreAuthorize("@md.hasPermi('system:user:remove')")
    @ApiOperation(value = "删除用户", notes = "")
    @Log(title = "系统管理-用户管理-删除用户", businessType = BusinessType.DELETE)
    public ResponseResult remove(@PathVariable Long[] userIds) {
        return ResponseResult.okResult(userService.deleteUserByIds(userIds));
    }
<<<<<<< HEAD
=======

    /**
     * 重置密码
     */
    @PreAuthorize("@md.hasPermi('system:user:resetPwd')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    public ResponseResult resetPwd(@RequestBody SysUser user)
    {
//        userService.checkUserAllowed(user);
//        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        return ResponseResult.okResult(userService.resetPwd(user));
    }

    /**
     * 状态修改
     */
    @PreAuthorize("@md.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody SysUser user)
    {
//        userService.checkUserAllowed(user);
        return ResponseResult.okResult(userService.updateUserStatus(user));
    }
>>>>>>> master
}
