package cn.mooding.modules.system.controller;


import cn.mooding.common.aspect.annotation.Log;
import cn.mooding.common.model.dto.ResponseResult;
import cn.mooding.common.model.enums.BusinessType;
import cn.mooding.common.utils.generate.QueryGenerator;
import cn.mooding.common.utils.poi.ExcelUtil;
import cn.mooding.modules.system.entity.SysOperLog;
import cn.mooding.modules.system.service.ISysOperLogService;
import cn.mooding.modules.system.service.impl.SysUserServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 操作日志记录 前端控制器
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-26
 */
@RestController
@RequestMapping("/system/operlog")
@Api(tags = "操作日志")
public class SysOperLogController {
    private static final Logger log = LoggerFactory.getLogger(SysOperLogController.class);
    @Autowired
    private ISysOperLogService sysOperLogService;


    @GetMapping(value = "/list")
    @ApiOperation(value = "分页查询操作日志", notes = "")
    @PreAuthorize("@md.hasPermi('system:operlog:list')")
    public ResponseResult<IPage<SysOperLog>> queryPageList(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,  SysOperLog operLog) {
        QueryWrapper<SysOperLog> queryWrapper = QueryGenerator.initQueryWrapper(operLog);
        Page<SysOperLog> page = new Page<SysOperLog>(pageNo, pageSize);
        IPage<SysOperLog> pageList = sysOperLogService.page(page, queryWrapper);
        return ResponseResult.okResult(pageList);
    }


    @GetMapping("/export")
    @PreAuthorize("@md.hasPermi('system:operlog:export')")
    @ApiOperation(value = "导出操作日志", notes = "")
    @Log(title = "系统管理-操作日志-导出操作日志", businessType = BusinessType.EXPORT)
    public ResponseResult export(SysOperLog operLog) {
        QueryWrapper<SysOperLog> queryWrapper = QueryGenerator.initQueryWrapper(operLog);
        List<SysOperLog> list = sysOperLogService.list(queryWrapper);
        ExcelUtil<SysOperLog> util = new ExcelUtil<SysOperLog>(SysOperLog.class);
        return util.exportExcel(list, "操作日志");
    }


    @DeleteMapping("/{operIds}")
    @PreAuthorize("@md.hasPermi('system:operlog:remove')")
    @ApiOperation(value = "删除日志记录", notes = "")
    public ResponseResult remove(@PathVariable Long[] operIds) {
        return ResponseResult.okResult(sysOperLogService.removeByIds(Arrays.asList(operIds)));
    }


    @DeleteMapping("/clean")
    @PreAuthorize("@md.hasPermi('system:operlog:remove')")
    @ApiOperation(value = "清空操作日志", notes = "")
    @Log(title = "系统管理-操作日志-清空操作日志", businessType = BusinessType.CLEAN)
    public ResponseResult clean() {
        sysOperLogService.cleanOperLog();
        return ResponseResult.okResult();
    }
}
