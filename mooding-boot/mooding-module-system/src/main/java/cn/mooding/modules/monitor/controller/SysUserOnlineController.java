package cn.mooding.modules.monitor.controller;

import cn.mooding.common.aspect.annotation.Log;
import cn.mooding.common.model.constant.Constants;
import cn.mooding.common.model.dto.ResponseResult;
import cn.mooding.common.model.enums.BusinessType;
import cn.mooding.common.utils.redis.RedisCache;
import cn.mooding.common.utils.string.StringUtils;
import cn.mooding.modules.monitor.domain.SysUserOnline;
import cn.mooding.modules.monitor.service.ISysUserOnlineService;
import cn.mooding.modules.security.LoginUser;
<<<<<<< HEAD
=======
import io.swagger.annotations.Api;
>>>>>>> master
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 在线用户监控
 *
 * @Author BlueFire
 * @Date 30/7/2021 -上午8:03
 */

@RestController
@RequestMapping("/monitor/online")
<<<<<<< HEAD
=======
@Api(tags = "系统在线用户监控")
>>>>>>> master
public class SysUserOnlineController {

    @Autowired
    private ISysUserOnlineService userOnlineService;

    @Autowired
    private RedisCache redisCache;

    @GetMapping("/list")
    @PreAuthorize("@md.hasPermi('monitor:online:list')")
    public ResponseResult list(String ipaddr, String userName) {
        Collection<String> keys = redisCache.keys(Constants.LOGIN_TOKEN_KEY + "*");
        List<SysUserOnline> userOnlineList = new ArrayList<SysUserOnline>();
        for (String key : keys) {
            LoginUser user = redisCache.getCacheObject(key);
            if (StringUtils.isNotEmpty(ipaddr) && StringUtils.isNotEmpty(userName)) {
                if (StringUtils.equals(ipaddr, user.getIpaddr()) && StringUtils.equals(userName, user.getUsername())) {
                    userOnlineList.add(userOnlineService.selectOnlineByInfo(ipaddr, userName, user));
                }
            } else if (StringUtils.isNotEmpty(ipaddr)) {
                if (StringUtils.equals(ipaddr, user.getIpaddr())) {
                    userOnlineList.add(userOnlineService.selectOnlineByIpaddr(ipaddr, user));
                }
            } else if (StringUtils.isNotEmpty(userName) && StringUtils.isNotNull(user.getUser())) {
                if (StringUtils.equals(userName, user.getUsername())) {
                    userOnlineList.add(userOnlineService.selectOnlineByUserName(userName, user));
                }
            } else {
                userOnlineList.add(userOnlineService.loginUserToUserOnline(user));
            }
        }
        Collections.reverse(userOnlineList);
        userOnlineList.removeAll(Collections.singleton(null));
        return ResponseResult.okResult(userOnlineList);
    }

    /**
     * 强退用户
     */
    @PreAuthorize("@md.hasPermi('monitor:online:forceLogout')")
    @Log(title = "系统监控-在线用户-强退用户", businessType = BusinessType.FORCE)
    @DeleteMapping("/{tokenId}")
    public ResponseResult forceLogout(@PathVariable String tokenId) {
        redisCache.deleteObject(Constants.LOGIN_TOKEN_KEY + tokenId);
        return ResponseResult.okResult();
    }
}
