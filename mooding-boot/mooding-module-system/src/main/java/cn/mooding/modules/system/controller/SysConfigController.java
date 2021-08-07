package cn.mooding.modules.system.controller;


import cn.mooding.common.aspect.annotation.Log;
import cn.mooding.common.model.constant.CommonConstant;
import cn.mooding.common.model.dto.ResponseResult;
import cn.mooding.common.model.enums.BusinessType;
import cn.mooding.common.model.enums.HttpCodeEnum;
import cn.mooding.common.utils.generate.QueryGenerator;
import cn.mooding.common.utils.poi.ExcelUtil;
import cn.mooding.modules.security.utils.SecurityUtils;
import cn.mooding.modules.system.entity.SysConfig;
import cn.mooding.modules.system.entity.SysPost;
import cn.mooding.modules.system.service.ISysConfigService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 参数配置表 前端控制器
 * </p>
 *
 * @author BlueFire
 * @since 2021-07-25
 */
@RestController
@RequestMapping("/system/config")
@Api(tags = "参数配置")
public class SysConfigController {

    @Autowired
    private ISysConfigService configService;

    /**
     * 获取参数配置列表
     */
    @GetMapping("/list")
    @PreAuthorize("@md.hasPermi('system:config:list')")
    @ApiOperation(value = "分页查询参数配置", notes = "")
    public ResponseResult<IPage<SysConfig>> list(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, SysConfig config) {
        QueryWrapper<SysConfig> queryWrapper = QueryGenerator.initQueryWrapper(config);
        Page<SysConfig> page = new Page<SysConfig>(pageNo, pageSize);
        IPage<SysConfig> pageList = configService.selectMapsPage(page, queryWrapper);
        return ResponseResult.okResult(pageList);
    }


    @GetMapping("/export")
    @PreAuthorize("@md.hasPermi('system:config:export')")
    @ApiOperation(value = "导出参数配置", notes = "")
    @Log(title = "系统管理-参数配置-导出参数", businessType = BusinessType.EXPORT)
    public ResponseResult export(SysConfig config) {
        QueryWrapper<SysConfig> queryWrapper = QueryGenerator.initQueryWrapper(config);
        List<SysConfig> list = configService.list(queryWrapper);
        ExcelUtil<SysConfig> util = new ExcelUtil<SysConfig>(SysConfig.class);
        return util.exportExcel(list, "参数数据");
    }

    /**
     * 根据参数编号获取详细信息
     */
    @GetMapping(value = "/{configId}")
    @PreAuthorize("@md.hasPermi('system:config:query')")
    @ApiOperation(value = "分页查询参数配置", notes = "")
    public ResponseResult getInfo(@PathVariable Long configId) {
        return ResponseResult.okResult(configService.getById(configId));
    }

    /**
     * 根据参数键名查询参数值
     */
    @GetMapping(value = "/configKey/{configKey}")
    @ApiOperation(value = "根据参数键名查询参数值", notes = "")
    public ResponseResult getConfigKey(@PathVariable String configKey) {
        return ResponseResult.okResult(configService.selectConfigByKey(configKey));
    }

    /**
     * 新增参数配置
     */
    @PostMapping
    @PreAuthorize("@md.hasPermi('system:config:add')")
    @ApiOperation(value = "新增参数管理", notes = "")
    @Log(title = "系统管理-参数配置-新增参数管理", businessType = BusinessType.INSERT)
    public ResponseResult add(@Validated @RequestBody SysConfig config) {
        if (CommonConstant.NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config))) {
            return ResponseResult.errorResult(HttpCodeEnum.DATA_EXIST, "新增参数'" + config.getConfigName() + "'失败，参数键名已存在");
        }
        config.setCreateBy(SecurityUtils.getUsername());
        return ResponseResult.okResult(configService.saveOrUpdate(config));
    }

    /**
     * 修改参数配置
     */

    @PutMapping
    @PreAuthorize("@md.hasPermi('system:config:edit')")
    @ApiOperation(value = "新增参数管理", notes = "")
    @Log(title = "系统管理-参数配置-修改参数管理", businessType = BusinessType.UPDATE)
    public ResponseResult edit(@Validated @RequestBody SysConfig config) {
        if (CommonConstant.NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config))) {
            return ResponseResult.errorResult(HttpCodeEnum.DATA_EXIST, "修改参数'" + config.getConfigName() + "'失败，参数键名已存在");
        }
        return ResponseResult.okResult(configService.updateConfig(config));
    }

    /**
     * 删除参数配置
     */
    @DeleteMapping("/{configIds}")
    @PreAuthorize("@md.hasPermi('system:config:remove')")
    @ApiOperation(value = "删除参数配置", notes = "")
    @Log(title = "系统管理-参数配置-删除参数配置", businessType = BusinessType.DELETE)
    public ResponseResult remove(@PathVariable Long[] configIds) {
        return ResponseResult.okResult(configService.removeByIds(Arrays.asList(configIds)));
    }

    /**
     * 清空缓存
     */

    @DeleteMapping("/clearCache")
    @PreAuthorize("@md.hasPermi('system:config:remove')")
    @ApiOperation(value = "清空缓存参数管理", notes = "")
    @Log(title = "系统管理-参数配置-清空缓存参数配置", businessType = BusinessType.CLEAN)
    public ResponseResult clearCache() {
        configService.clearCache();
        return ResponseResult.okResult();
    }
}
