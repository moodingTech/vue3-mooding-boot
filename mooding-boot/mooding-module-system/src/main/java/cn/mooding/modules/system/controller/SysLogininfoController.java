package cn.mooding.modules.system.controller;


import cn.mooding.common.aspect.annotation.Log;
import cn.mooding.common.model.dto.ResponseResult;
import cn.mooding.common.model.enums.BusinessType;
import cn.mooding.common.utils.generate.QueryGenerator;
import cn.mooding.common.utils.poi.ExcelUtil;
import cn.mooding.modules.system.entity.SysLogininfo;
import cn.mooding.modules.system.entity.SysOperLog;
import cn.mooding.modules.system.service.ISysLogininfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 系统访问记录 前端控制器
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-26
 */
@RestController
@RequestMapping("/system/logininfo")
@Api(tags = "系统访问日志")
public class SysLogininfoController {
    @Autowired
    private ISysLogininfoService logininfoService;


    @GetMapping("/list")
    @PreAuthorize("@md.hasPermi('system:logininfo:list')")
    @ApiOperation(value = "分页查询登录日志", notes = "")
    public ResponseResult<IPage<SysLogininfo>> list(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, SysLogininfo logininfo) {
        QueryWrapper<SysLogininfo> queryWrapper = QueryGenerator.initQueryWrapper(logininfo);
        Page<SysLogininfo> page = new Page<SysLogininfo>(pageNo, pageSize);
        IPage<SysLogininfo> pageList = logininfoService.page(page, queryWrapper);
        return ResponseResult.okResult(pageList);
    }


    @GetMapping("/export")
    @PreAuthorize("@md.hasPermi('system:logininfo:export')")
    @ApiOperation(value = "导出登录日志", notes = "")
    @Log(title = "系统管理-系统访问日志-导出登录日志", businessType = BusinessType.EXPORT)
    public ResponseResult export(SysLogininfo logininfo) {
        QueryWrapper<SysLogininfo> queryWrapper = QueryGenerator.initQueryWrapper(logininfo);
        List<SysLogininfo> list = logininfoService.list(queryWrapper);
        ExcelUtil<SysLogininfo> util = new ExcelUtil<SysLogininfo>(SysLogininfo.class);
        return util.exportExcel(list, "登录日志");
    }


    @DeleteMapping("/{infoIds}")
    @PreAuthorize("@md.hasPermi('system:logininfo:remove')")
    @ApiOperation(value = "删除登录日志", notes = "")
    @Log(title = "系统管理-系统访问日志-删除登录日志", businessType = BusinessType.DELETE)
    public ResponseResult remove(@PathVariable Long[] infoIds) {
        return ResponseResult.okResult(logininfoService.removeByIds(Arrays.asList(infoIds)));
    }


    @DeleteMapping("/clean")
    @PreAuthorize("@md.hasPermi('system:logininfo:remove')")
    @ApiOperation(value = "清空登录日志", notes = "")
    @Log(title = "系统管理-系统访问日志-清空登录日志", businessType = BusinessType.CLEAN)
    public ResponseResult clean() {
        logininfoService.cleanLogininfor();
        return ResponseResult.okResult();
    }

}
