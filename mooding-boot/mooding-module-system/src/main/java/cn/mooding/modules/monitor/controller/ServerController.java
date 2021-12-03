package cn.mooding.modules.monitor.controller;

import cn.mooding.common.model.dto.ResponseResult;
import cn.mooding.modules.monitor.domain.Server;
<<<<<<< HEAD
=======
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
>>>>>>> master
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务器监控
 *
 * @Author BlueFire
 * @Date 30/7/2021 -上午7:59
 */

@RestController
@RequestMapping("/monitor/server")
<<<<<<< HEAD
public class ServerController {
    @PreAuthorize("@md.hasPermi('monitor:server:list')")
    @GetMapping()
=======
@Api(tags = "系统服务器监控")
public class ServerController {
    @PreAuthorize("@md.hasPermi('monitor:server:list')")
    @GetMapping()
    @ApiOperation(value = "系统服务器监控信息", notes = "")
>>>>>>> master
    public ResponseResult getInfo() throws Exception {
        Server server = new Server();
        server.copyTo();
        return ResponseResult.okResult(server);
    }
}
