package cn.mooding.modules.system.controller;

import cn.mooding.common.aspect.annotation.Log;
import cn.mooding.common.model.constant.CommonConstant;
import cn.mooding.common.model.dto.ResponseResult;
import cn.mooding.common.model.enums.BusinessType;
import cn.mooding.common.utils.ServletUtils;
import cn.mooding.modules.security.LoginBody;
import cn.mooding.modules.security.LoginUser;
import cn.mooding.modules.security.service.SysLoginService;
import cn.mooding.modules.security.service.SysPermissionService;
import cn.mooding.modules.security.service.TokenService;
import cn.mooding.modules.system.entity.SysMenu;
import cn.mooding.modules.system.entity.SysUser;
import cn.mooding.modules.system.service.ISysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 登录验证
 *
 * @Author BlueFire
 * @Date 24/3/2021 -上午6:52
 */
@RestController
@Api(tags = "系统登录验证")
public class SysLoginController {
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private ISysMenuService menuService;

    /**
     * 登录方法
     *
     * @param {username 用户名,password 密码 captcha  验证码 uuid     唯一标识}
     * @return 结果
     */
    @ApiOperation(value = "用户验证码登录", notes = "")
    @PostMapping("/login")
    public ResponseResult login(@RequestBody LoginBody loginBody) {
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCaptcha(),
                loginBody.getUuid());
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("token", token);
        return ResponseResult.okResult(result);
    }

    @ApiOperation(value = "用户滑块登录", notes = "")
    @PostMapping("/loginBySlider")
    @Log(title = "用户滑块登录", businessType = BusinessType.SELECT)
    public ResponseResult loginBySlider(@RequestBody LoginBody loginBody) {
        // 生成令牌
        String token = loginService.loginBySlider(loginBody.getUsername(), loginBody.getPassword());
        Map<String, Object> tokenInfo = new HashMap<String, Object>();
        tokenInfo.put(CommonConstant.TOKEN, token);
        return ResponseResult.okResult(tokenInfo);
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */

    @GetMapping("getInfo")
    @ApiOperation(value = "获取用户信息", notes = "")
    public ResponseResult getInfo() {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        Map<String, Object> adminInfo = new HashMap<String, Object>();
        adminInfo.put("user", user);
        adminInfo.put("roles", roles);
        adminInfo.put("permissions", permissions);
        return ResponseResult.okResult(adminInfo);
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    @ApiOperation(value = "获取路由信息", notes = "")
    public ResponseResult getRouters() {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        // 用户信息
        SysUser user = loginUser.getUser();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(user.getUserId());
        return ResponseResult.okResult(menuService.buildMenus(menus));
    }
}
