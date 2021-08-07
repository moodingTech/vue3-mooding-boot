package cn.mooding.modules.quartz.controller;


import cn.mooding.common.aspect.annotation.Log;
import cn.mooding.common.model.dto.ResponseResult;
import cn.mooding.common.model.enums.BusinessType;
import cn.mooding.common.utils.generate.QueryGenerator;
import cn.mooding.modules.quartz.entity.SysJob;
import cn.mooding.modules.quartz.entity.SysJobLog;
import cn.mooding.modules.quartz.service.ISysJobLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 定时任务调度日志表 前端控制器
 * </p>
 *
 * @author BlueFire
 * @since 2021-07-26
 */
@RestController
@RequestMapping("/monitor/jobLog")
public class SysJobLogController {
    @Autowired
    private ISysJobLogService jobLogService;

    /**
     * 查询定时任务调度日志列表
     */
    @GetMapping("/list")
    public ResponseResult<IPage<SysJobLog>> list(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, SysJobLog sysJobLog) {
        QueryWrapper<SysJobLog> queryWrapper = QueryGenerator.initQueryWrapper(sysJobLog);
        Page<SysJobLog> page = new Page<SysJobLog>(pageNo, pageSize);
        IPage<SysJobLog> pageList = jobLogService.selectMapsPage(page, queryWrapper);
        return ResponseResult.okResult(pageList);
    }


    /**
     * 根据调度编号获取详细信息
     */
    @GetMapping(value = "/{configId}")
    public ResponseResult getInfo(@PathVariable Long jobLogId) {
        return ResponseResult.okResult(jobLogService.getById(jobLogId));
    }

    /**
     * 删除定时任务调度日志
     */
    @Log(title = "定时任务调度日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{jobLogIds}")
    public ResponseResult remove(@PathVariable Long[] jobLogIds) {
        return ResponseResult.okResult(jobLogService.removeByIds(Arrays.asList(jobLogIds)));
    }

    /**
     * 清空定时任务调度日志
     */
     @Log(title = "调度日志", businessType = BusinessType.CLEAN)
    @DeleteMapping("/clean")
    public ResponseResult clean()
    {
        jobLogService.cleanJobLog();
        return ResponseResult.okResult();
    }
}
