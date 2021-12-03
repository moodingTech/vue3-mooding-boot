package cn.mooding.modules.system.controller;

import cn.mooding.common.aspect.annotation.Log;
import cn.mooding.common.model.constant.CommonConstant;
import cn.mooding.common.model.dto.ResponseResult;
import cn.mooding.common.model.enums.BusinessType;
import cn.mooding.common.model.enums.HttpCodeEnum;
import cn.mooding.common.utils.ServletUtils;
import cn.mooding.common.utils.UploadGiteeImgBed;
import cn.mooding.common.utils.file.FileUploadUtils;
import cn.mooding.common.utils.string.StringUtils;
import cn.mooding.modules.security.LoginUser;
import cn.mooding.modules.security.service.TokenService;
import cn.mooding.modules.security.utils.SecurityUtils;
import cn.mooding.modules.system.entity.SysUser;
import cn.mooding.modules.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 个人信息 前端控制器
 *
 * @Author BlueFire
 * @Date 8/9/2021 -下午8:25
 */
@RestController
@RequestMapping("/system/user/profile")
@Api(tags = "个人信息")
public class SysProfileController {
    @Autowired
    private ISysUserService userService;

    @Autowired
    private TokenService tokenService;


    /**
     * 个人信息
     */
    @GetMapping
    public ResponseResult profile() {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        Map<String, Object> resultData = new HashMap<String, Object>();
        resultData.put("userInfo", user);
        resultData.put("roleGroup", userService.selectUserRoleGroup(loginUser.getUser().getUserId()));
        resultData.put("postGroup", userService.selectUserPostGroup(loginUser.getUser().getUserId()));
        return ResponseResult.okResult();
    }

    /**
     * 修改用户
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResponseResult updateProfile(@RequestBody SysUser user) {
        if (StringUtils.isNotEmpty(user.getPhonenumber())
                && CommonConstant.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return ResponseResult.errorResult(HttpCodeEnum.PARAM_ERR, "修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        if (StringUtils.isNotEmpty(user.getEmail())
                && CommonConstant.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return ResponseResult.errorResult(HttpCodeEnum.PARAM_ERR, "修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        if (userService.updateUserProfile(user) > 0) {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            // 更新缓存用户信息
            loginUser.getUser().setNickName(user.getNickName());
            loginUser.getUser().setPhonenumber(user.getPhonenumber());
            loginUser.getUser().setEmail(user.getEmail());
            loginUser.getUser().setSex(user.getSex());
            tokenService.setLoginUser(loginUser);
            return ResponseResult.okResult();
        }
        return ResponseResult.errorResult(HttpCodeEnum.SERVER_ERROR, "修改个人信息异常，请联系管理员");
    }

    /**
     * 重置密码
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping("/updatePwd")
    @ApiOperation(value = "重置密码", notes = "")
    public ResponseResult updatePwd(String oldPassword, String newPassword) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String userName = loginUser.getUsername();
        String password = loginUser.getPassword();
        if (!SecurityUtils.matchesPassword(oldPassword, password)) {
            return ResponseResult.errorResult(HttpCodeEnum.PARAM_ERR, "修改密码失败，旧密码错误");
        }
        if (SecurityUtils.matchesPassword(newPassword, password)) {
            return ResponseResult.errorResult(HttpCodeEnum.PARAM_ERR, "新密码不能与旧密码相同");
        }
        if (userService.resetUserPwd(loginUser.getUser().getUserId(), SecurityUtils.encryptPassword(newPassword)) > 0) {
            // 更新缓存用户密码
            loginUser.getUser().setPassword(SecurityUtils.encryptPassword(newPassword));
            tokenService.setLoginUser(loginUser);
            return ResponseResult.okResult();
        }
        return ResponseResult.errorResult(HttpCodeEnum.SERVER_ERROR, "修改密码异常，请联系管理员");
    }

    /**
     * 头像上传
     */
    @Log(title = "用户头像", businessType = BusinessType.UPDATE)
    @PostMapping("/avatar")
    @ApiOperation(value = "头像上传", notes = "")
    public ResponseResult avatar(@RequestParam("avatarfile") MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String avatar = UploadGiteeImgBed.uploadFileToGitee(file);
            if (userService.updateUserAvatar(loginUser.getUser().getUserId(), avatar)) {
                Map<String, Object> resultData = new HashMap<String, Object>();
                resultData.put("imgUrl", avatar);
                // 更新缓存用户头像
                loginUser.getUser().setAvatar(avatar);
                tokenService.setLoginUser(loginUser);
                return ResponseResult.okResult(resultData);
            }
        }
        return ResponseResult.errorResult(HttpCodeEnum.SERVER_ERROR, "上传图片异常，请联系管理员");
    }
}
