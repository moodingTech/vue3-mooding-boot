package cn.mooding.modules.security.handle;

import cn.mooding.common.model.dto.ResponseResult;
import cn.mooding.common.model.enums.HttpCodeEnum;
import cn.mooding.common.utils.ServletUtils;
import cn.mooding.common.utils.string.StringUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * 认证失败处理类 返回未授权
 *
 * @Author BlueFire
 * @Date 22/3/2021 -上午7:02
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException {
        int code = HttpCodeEnum.UNAUTHORIZED.getCode();
        String msg = StringUtils.format("请求访问：{}，认证失败，无法访问系统资源", request.getRequestURI());
        ServletUtils.renderString(response, JSON.toJSONString(ResponseResult.errorResult(code, msg)));
    }
}
