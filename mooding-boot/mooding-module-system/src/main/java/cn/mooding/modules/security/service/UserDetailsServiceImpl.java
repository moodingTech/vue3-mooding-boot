package cn.mooding.modules.security.service;

import cn.mooding.common.model.constant.CommonConstant;
import cn.mooding.common.model.exception.BaseException;
import cn.mooding.common.utils.IpUtils;
import cn.mooding.common.utils.ServletUtils;
import cn.mooding.common.utils.string.StringUtils;
import cn.mooding.modules.security.LoginUser;
import cn.mooding.modules.system.entity.SysUser;
import cn.mooding.modules.system.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
import java.sql.Date;
import java.time.LocalDateTime;
=======
import java.time.LocalDateTime;
import java.util.Date;
>>>>>>> master

/**
 * 用户验证处理
 *
 * @Author BlueFire
 * @Date 22/3/2021 -上午8:28
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userService.selectUserByUserName(username);
        if (StringUtils.isNull(user)) {
            log.info("登录用户：{} 不存在.", username);
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        } else if (CommonConstant.USER_DEL.equals(user.getDelFlag())) {
            log.info("登录用户：{} 已被删除.", username);
            throw new BaseException("对不起，您的账号：" + username + " 已被删除");
        } else if (CommonConstant.USER_DISABLE.equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
            throw new BaseException("对不起，您的账号：" + username + " 已停用");
        }
        return createLoginUser(user);
    }

    public UserDetails createLoginUser(SysUser user) {
        // 请求的地址
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        user.setLoginIp(ip);
<<<<<<< HEAD
        user.setLoginDate(LocalDateTime.now());
=======
//        user.setLoginDate(LocalDateTime.now());
        user.setLoginDate(new Date());
>>>>>>> master
        userService.updateUser(user);
        return new LoginUser(user, permissionService.getMenuPermission(user));
    }
}
