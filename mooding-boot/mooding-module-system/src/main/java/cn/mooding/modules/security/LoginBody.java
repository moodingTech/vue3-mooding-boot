package cn.mooding.modules.security;

import lombok.Data;

/**
 * 用户登录对象
 * @Author BlueFire
 * @Date 22/3/2021 -上午7:21
 */
@Data
public class LoginBody {
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 验证码
     */
    private String captcha;

    /**
     * 唯一标识
     */
    private String uuid = "";
}
