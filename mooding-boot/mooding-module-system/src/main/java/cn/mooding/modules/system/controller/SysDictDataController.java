package cn.mooding.modules.system.controller;


import cn.mooding.common.aspect.annotation.Log;
import cn.mooding.common.model.dto.ResponseResult;
import cn.mooding.common.model.enums.BusinessType;
import cn.mooding.common.utils.generate.QueryGenerator;
import cn.mooding.common.utils.poi.ExcelUtil;
import cn.mooding.common.utils.string.StringUtils;
import cn.mooding.modules.security.utils.SecurityUtils;
import cn.mooding.modules.system.entity.SysDictData;
import cn.mooding.modules.system.entity.SysDictType;
import cn.mooding.modules.system.service.ISysDictDataService;
import cn.mooding.modules.system.service.ISysDictTypeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 字典数据表 前端控制器
 * </p>
 *
 * @author BlueFire
 * @since 2021-05-14
 */
@RestController
@RequestMapping("/system/dict/data")
@Api(tags = "系统字典数据")
public class SysDictDataController {
    @Autowired
    private ISysDictDataService dictDataService;

    /**
     * 根据字典类型查询字典数据信息
     */
    @GetMapping(value = "/type/{dictType}")
    @ApiOperation(value = "字典类型查询字典数据", notes = "")
    public ResponseResult dictType(@PathVariable String dictType) {
        List<SysDictData> data = dictDataService.selectDictDataByType(dictType);
        if (StringUtils.isNull(data)) {
            data = new ArrayList<SysDictData>();
        }
        return ResponseResult.okResult(data);
    }

    /**
     * 分页查询数据字典列表
     *
     * @param pageNo   页码
     * @param pageSize 分页大小
     * @param dictData 查询信息
     * @return 分页数据字典数据
     */
    @GetMapping("/list")
    @PreAuthorize("@md.hasPermi('system:dict:list')")
    @ApiOperation(value = "分页字典数据", notes = "")
    public ResponseResult list(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                               @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, SysDictData dictData) {
        QueryWrapper<SysDictData> queryWrapper = QueryGenerator.initQueryWrapper(dictData);
        Page<SysDictData> page = new Page<SysDictData>(pageNo, pageSize);
        IPage<SysDictData> pageList = dictDataService.selectMapsPage(page, queryWrapper);
        return ResponseResult.okResult(pageList);
    }


    @GetMapping("/export")
    @PreAuthorize("@md.hasPermi('system:dict:export')")
    @ApiOperation(value = "导出字典数据", notes = "")
    @Log(title = "系统管理-字典数据-导出字典数据", businessType = BusinessType.EXPORT)
    public ResponseResult export(SysDictData dictData) {
        QueryWrapper<SysDictData> queryWrapper = QueryGenerator.initQueryWrapper(dictData);
        queryWrapper.lambda().orderByAsc(SysDictData::getDictSort);
        List<SysDictData> list = dictDataService.list(queryWrapper);
        ExcelUtil<SysDictData> util = new ExcelUtil<SysDictData>(SysDictData.class);
        return util.exportExcel(list, "字典数据");
    }

    /**
     * 查询字典数据详细
     */
    @GetMapping(value = "/{dictCode}")
    @PreAuthorize("@md.hasPermi('system:dict:query')")
    @ApiOperation(value = "主键字典数据", notes = "")
    public ResponseResult getInfo(@PathVariable Long dictCode) {
        return ResponseResult.okResult(dictDataService.getById(dictCode));
    }

    /**
     * 新增字典数据
     */

    @PostMapping
    @PreAuthorize("@md.hasPermi('system:dict:add')")
    @ApiOperation(value = "新增字典数据", notes = "")
    @Log(title = "系统管理-字典数据-新增字典数据", businessType = BusinessType.INSERT)
    public ResponseResult add(@Validated @RequestBody SysDictData dict) {
        dict.setCreateBy(SecurityUtils.getUsername());
        return ResponseResult.okResult(dictDataService.saveOrUpdate(dict));
    }

    /**
     * 修改保存字典类型
     */

    @PutMapping
    @PreAuthorize("@md.hasPermi('system:dict:edit')")
    @ApiOperation(value = "修改字典数据", notes = "")
    @Log(title = "系统管理-字典数据-修改字典数据", businessType = BusinessType.UPDATE)
    public ResponseResult edit(@Validated @RequestBody SysDictData dict) {
        return ResponseResult.okResult(dictDataService.updateDictData(dict));
    }

    /**
     * 删除字典类型
     */

    @DeleteMapping("/{dictCodes}")
    @PreAuthorize("@md.hasPermi('system:dict:remove')")
    @ApiOperation(value = "删除字典数据", notes = "")
    @Log(title = "系统管理-字典数据-删除字典数据", businessType = BusinessType.DELETE)
    public ResponseResult remove(@PathVariable Long[] dictCodes) {
        return ResponseResult.okResult(dictDataService.removeByIds(Arrays.asList(dictCodes)));
    }
}
