package cn.mooding.modules.monitor.controller;

import cn.mooding.common.model.dto.ResponseResult;
import cn.mooding.modules.monitor.domain.Server;
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
public class ServerController {
    @PreAuthorize("@md.hasPermi('monitor:server:list')")
    @GetMapping()
    public ResponseResult getInfo() throws Exception {
        Server server = new Server();
        server.copyTo();
        return ResponseResult.okResult(server);
    }
}
