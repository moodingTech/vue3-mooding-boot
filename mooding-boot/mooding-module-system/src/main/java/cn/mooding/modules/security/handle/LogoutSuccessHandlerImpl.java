package cn.mooding.modules.security.handle;

import cn.mooding.common.model.constant.Constants;
import cn.mooding.common.model.dto.ResponseResult;
import cn.mooding.common.model.enums.HttpCodeEnum;
import cn.mooding.common.utils.ServletUtils;
import cn.mooding.common.utils.string.StringUtils;
import cn.mooding.modules.quartz.AsyncManager;
import cn.mooding.modules.quartz.factory.AsyncFactory;
import cn.mooding.modules.security.LoginUser;
import cn.mooding.modules.security.service.TokenService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义退出处理类 返回成功
 *
 * @Author BlueFire
 * @Date 22/3/2021 -上午7:13
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Autowired
    private TokenService tokenService;

    /**
     * 退出处理
     *
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser)) {
            String userName = loginUser.getUsername();
            // 删除用户缓存记录
            tokenService.delLoginUser(loginUser.getToken());
            // 记录用户退出日志
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, Constants.LOGOUT, "退出成功"));
        }
        ServletUtils.renderString(response, JSON.toJSONString(ResponseResult.okResult(HttpCodeEnum.SUCCESS.getCode(), "退出成功")));
    }
}

