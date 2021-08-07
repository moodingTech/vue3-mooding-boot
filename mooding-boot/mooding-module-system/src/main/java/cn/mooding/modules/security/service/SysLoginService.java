package cn.mooding.modules.security.service;

import cn.mooding.common.model.constant.CacheConstant;
import cn.mooding.common.model.constant.CommonConstant;
import cn.mooding.common.model.exception.BaseException;
import cn.mooding.common.model.exception.UserException;
import cn.mooding.common.utils.MessageUtils;
import cn.mooding.common.utils.redis.RedisCache;
import cn.mooding.modules.quartz.AsyncManager;
import cn.mooding.modules.quartz.factory.AsyncFactory;
import cn.mooding.modules.security.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 登录校验方法
 *
 * @Author BlueFire
 * @Date 23/3/2021 -下午10:30
 */
@Component
public class SysLoginService {
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid) {
        String verifyKey = CacheConstant.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, CommonConstant.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
            throw new UserException("user.jcaptcha.expire", null);
        }
        if (!code.equalsIgnoreCase(captcha)) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, CommonConstant.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new UserException("user.jcaptcha.error", null);
        }
        // 用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, CommonConstant.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserException("user.password.not.match", null);
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, CommonConstant.LOGIN_FAIL, e.getMessage()));
                throw new BaseException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, CommonConstant.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();


        // 生成token
        return tokenService.createToken(loginUser);
    }


    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @return 结果
     */
    public String loginBySlider(String username, String password) {
        // 用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, CommonConstant.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserException("user.password.not.match", null);
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, CommonConstant.LOGIN_FAIL, e.getMessage()));
                throw new BaseException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, CommonConstant.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 生成token
        return tokenService.createToken(loginUser);
    }
}