package cn.mooding.modules.system.controller;


import cn.mooding.common.aspect.annotation.Log;
import cn.mooding.common.model.constant.CommonConstant;
import cn.mooding.common.model.dto.ResponseResult;
import cn.mooding.common.model.enums.BusinessType;
import cn.mooding.common.model.enums.HttpCodeEnum;
import cn.mooding.common.utils.generate.QueryGenerator;
import cn.mooding.common.utils.poi.ExcelUtil;
import cn.mooding.modules.security.utils.SecurityUtils;
import cn.mooding.modules.system.entity.SysDictType;
import cn.mooding.modules.system.entity.SysPost;
import cn.mooding.modules.system.service.ISysDictTypeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 字典类型表 前端控制器
 * </p>
 *
 * @author BlueFire
 * @since 2021-05-14
 */
@RestController
@RequestMapping("/system/dict/type")
public class SysDictTypeController {
    @Autowired
    private ISysDictTypeService dictTypeService;

    /**
     * 分页查询数据字典类型
     *
     * @param pageNo   页码
     * @param pageSize 分页大小
     * @param dictType 数据字典类型
     * @return 分页查询数据字典类型结果
     */
    @GetMapping("/list")
    @PreAuthorize("@md.hasPermi('system:dict:list')")
    @ApiOperation(value = "分页查询数据字典类型", notes = "")
    public ResponseResult list(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                               @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, SysDictType dictType) {
        QueryWrapper<SysDictType> queryWrapper = QueryGenerator.initQueryWrapper(dictType);
        Page<SysDictType> page = new Page<SysDictType>(pageNo, pageSize);
        IPage<SysDictType> pageList = dictTypeService.selectMapsPage(page, queryWrapper);
        return ResponseResult.okResult(pageList);
    }


    @GetMapping("/export")
    @PreAuthorize("@md.hasPermi('system:dict:export')")
    @ApiOperation(value = "导出数据字典类型", notes = "")
    @Log(title = "系统管理-字典类型-导出字典类型", businessType = BusinessType.EXPORT)
    public ResponseResult export(SysDictType dictType) {
        QueryWrapper<SysDictType> queryWrapper = QueryGenerator.initQueryWrapper(dictType);
        List<SysDictType> list = dictTypeService.list(queryWrapper);
        ExcelUtil<SysDictType> util = new ExcelUtil<SysDictType>(SysDictType.class);
        return util.exportExcel(list, "字典类型");
    }

    /**
     * 查询字典类型详细
     */
    @GetMapping(value = "/{dictId}")
    @PreAuthorize("@md.hasPermi('system:dict:query')")
    @ApiOperation(value = "主键查询字典类型", notes = "")
    public ResponseResult getInfo(@PathVariable Long dictId) {
        return ResponseResult.okResult(dictTypeService.getById(dictId));
    }

    /**
     * 新增字典类型
     */

    @PostMapping
    @PreAuthorize("@md.hasPermi('system:dict:add')")
    @ApiOperation(value = "新增字典类型", notes = "")
    @Log(title = "系统管理-字典类型-新增", businessType = BusinessType.INSERT)
    public ResponseResult add(@Validated @RequestBody SysDictType dict) {
        if (CommonConstant.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict))) {
            return ResponseResult.errorResult(HttpCodeEnum.DATA_EXIST, "新增字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dict.setCreateBy(SecurityUtils.getUsername());
        return ResponseResult.okResult(dictTypeService.save(dict));
    }

    /**
     * 修改字典类型
     */

    @PutMapping
    @PreAuthorize("@md.hasPermi('system:dict:edit')")
    @ApiOperation(value = "修改字典类型", notes = "")
    @Log(title = "系统管理-字典类型-修改", businessType = BusinessType.UPDATE)
    public ResponseResult edit(@Validated @RequestBody SysDictType dict) {
        if (CommonConstant.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict))) {
            return ResponseResult.errorResult(HttpCodeEnum.DATA_EXIST, "修改字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        return ResponseResult.okResult(dictTypeService.updateDictType(dict));
    }

    /**
     * 删除字典类型
     */

    @DeleteMapping("/{dictIds}")
    @PreAuthorize("@md.hasPermi('system:dict:remove')")
    @ApiOperation(value = "删除字典类型", notes = "")
    @Log(title = "系统管理-字典类型-删除", businessType = BusinessType.DELETE)
    public ResponseResult remove(@PathVariable Long[] dictIds) {
        return ResponseResult.okResult(dictTypeService.removeByIds(Arrays.asList(dictIds)));
    }

    /**
     * 清空缓存
     */

    @DeleteMapping("/clearCache")
    @PreAuthorize("@md.hasPermi('system:dict:remove')")
    @ApiOperation(value = "清空缓存", notes = "")
    @Log(title = "系统管理-字典类型-清空缓存", businessType = BusinessType.CLEAN)
    public ResponseResult clearCache() {
        dictTypeService.clearCache();
        return ResponseResult.okResult();
    }

    /**
     * 获取字典选择框列表
     */
    @GetMapping("/optionselect")
    public ResponseResult optionselect() {
        List<SysDictType> dictTypes = dictTypeService.selectDictTypeAll();
        return ResponseResult.okResult(dictTypes);
    }
}
