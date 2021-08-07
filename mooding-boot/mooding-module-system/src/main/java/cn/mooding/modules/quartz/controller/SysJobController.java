package cn.mooding.modules.quartz.controller;


import cn.mooding.common.aspect.annotation.Log;
import cn.mooding.common.model.dto.ResponseResult;
import cn.mooding.common.model.enums.BusinessType;
import cn.mooding.common.model.enums.HttpCodeEnum;
import cn.mooding.common.model.exception.TaskException;
import cn.mooding.common.utils.generate.QueryGenerator;
import cn.mooding.modules.quartz.entity.SysJob;
import cn.mooding.modules.quartz.service.ISysJobService;
import cn.mooding.modules.quartz.utils.CronUtils;
import cn.mooding.modules.security.utils.SecurityUtils;
import cn.mooding.modules.system.entity.SysConfig;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 定时任务调度表 前端控制器
 * </p>
 *
 * @author BlueFire
 * @since 2021-07-26
 */
@RestController
@RequestMapping("/monitor/job")
public class SysJobController {
    @Autowired
    private ISysJobService jobService;

    /**
     * 查询定时任务列表
     *
     * @param pageNo   页码
     * @param pageSize 每页大小
     * @param sysJob   查询信息
     * @return
     */
    @GetMapping("/list")
    public ResponseResult<IPage<SysJob>> list(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                              @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, SysJob sysJob) {
        QueryWrapper<SysJob> queryWrapper = QueryGenerator.initQueryWrapper(sysJob);
        Page<SysJob> page = new Page<SysJob>(pageNo, pageSize);
        IPage<SysJob> pageList = jobService.selectMapsPage(page, queryWrapper);
        return ResponseResult.okResult(pageList);
    }

    /**
     * 导出定时任务列表
     */
//    @Log(title = "定时任务", businessType = BusinessType.EXPORT)
//    @GetMapping("/export")
//    public AjaxResult export(SysJob sysJob) {
//        List<SysJob> list = jobService.selectJobList(sysJob);
//        ExcelUtil<SysJob> util = new ExcelUtil<SysJob>(SysJob.class);
//        return util.exportExcel(list, "定时任务");
//    }

    /**
     * 获取定时任务详细信息
     */
    @GetMapping(value = "/{jobId}")
    public ResponseResult getInfo(@PathVariable("jobId") Long jobId) {
        return ResponseResult.okResult(jobService.getById(jobId));
    }

    /**
     * 新增定时任务
     */
    @Log(title = "定时任务", businessType = BusinessType.INSERT)
    @PostMapping
    public ResponseResult add(@RequestBody SysJob sysJob) throws SchedulerException, TaskException {
        if (!CronUtils.isValid(sysJob.getCronExpression())) {
            return ResponseResult.errorResult(HttpCodeEnum.PARAM_ERR, "cron表达式不正确");
        }
        return ResponseResult.okResult(jobService.insertJob(sysJob));
    }

    /**
     * 修改定时任务
     */
    @Log(title = "定时任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResponseResult edit(@RequestBody SysJob sysJob) throws SchedulerException, TaskException {
        if (!CronUtils.isValid(sysJob.getCronExpression())) {
            return ResponseResult.errorResult(HttpCodeEnum.PARAM_ERR, "cron表达式不正确");
        }
        return ResponseResult.okResult(jobService.updateJob(sysJob));
    }

    /**
     * 定时任务状态修改
     */
    @Log(title = "定时任务", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody SysJob job) throws SchedulerException {
        SysJob newJob = jobService.getById(job.getJobId());
        newJob.setStatus(job.getStatus());
        return ResponseResult.okResult(jobService.changeStatus(newJob));
    }

    /**
     * 定时任务立即执行一次
     */
    @Log(title = "定时任务", businessType = BusinessType.UPDATE)
    @PutMapping("/run")
    public ResponseResult run(@RequestBody SysJob job) throws SchedulerException {
        jobService.run(job);
        return ResponseResult.okResult();
    }

    /**
     * 删除定时任务
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:remove')")
    @Log(title = "定时任务", businessType = BusinessType.DELETE)
    @DeleteMapping("/{jobIds}")
    public ResponseResult remove(@PathVariable Long[] jobIds) throws SchedulerException, TaskException {
        jobService.deleteJobByIds(jobIds);
        return ResponseResult.okResult();
    }
}
