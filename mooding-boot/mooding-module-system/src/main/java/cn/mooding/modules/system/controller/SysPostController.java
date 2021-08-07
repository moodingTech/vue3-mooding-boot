package cn.mooding.modules.system.controller;


import cn.mooding.common.aspect.annotation.Log;
import cn.mooding.common.model.constant.CommonConstant;
import cn.mooding.common.model.dto.ResponseResult;
import cn.mooding.common.model.enums.BusinessType;
import cn.mooding.common.model.enums.HttpCodeEnum;
import cn.mooding.common.utils.generate.QueryGenerator;
import cn.mooding.common.utils.poi.ExcelUtil;
import cn.mooding.modules.security.utils.SecurityUtils;
import cn.mooding.modules.system.entity.SysPost;
import cn.mooding.modules.system.entity.SysUser;
import cn.mooding.modules.system.service.ISysPostService;
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
 * 岗位信息表 前端控制器
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-21
 */
@RestController
@RequestMapping("/system/post")
public class SysPostController {
    @Autowired
    private ISysPostService postService;

    /**
     * 获取岗位列表
     */
    @GetMapping("/list")
    @PreAuthorize("@md.hasPermi('system:post:list')")
    @ApiOperation(value = "分页获取岗位列表", notes = "")
    public ResponseResult<IPage<SysPost>> list(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                               @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, SysPost post) {
        QueryWrapper<SysPost> queryWrapper = QueryGenerator.initQueryWrapper(post);
        Page<SysPost> page = new Page<SysPost>(pageNo, pageSize);
        queryWrapper.lambda().notIn(SysPost::getDelFlag, "2");

        IPage<SysPost> pageList = postService.selectMapsPage(page, queryWrapper);
        return ResponseResult.okResult(pageList);
    }

    @GetMapping("/export")
    @PreAuthorize("@md.hasPermi('system:post:export')")
    @ApiOperation(value = "导出数据", notes = "")
    @Log(title = "系统管理-岗位管理-导出数据", businessType = BusinessType.EXPORT)
    public ResponseResult export(SysPost post) {
        QueryWrapper<SysPost> queryWrapper = QueryGenerator.initQueryWrapper(post);
        List<SysPost> list = postService.list(queryWrapper);
        ExcelUtil<SysPost> util = new ExcelUtil<SysPost>(SysPost.class);
        return util.exportExcel(list, "岗位数据");
    }

    /**
     * 根据岗位编号获取详细信息
     */

    @GetMapping(value = "/{postId}")
    @PreAuthorize("@md.hasPermi('system:post:query')")
    @ApiOperation(value = "根据岗位编号获取详细信息", notes = "")
    public ResponseResult getInfo(@PathVariable Long postId) {
        return ResponseResult.okResult(postService.getById(postId));
    }

    /**
     * 新增岗位
     */
    @PostMapping
    @PreAuthorize("@md.hasPermi('system:post:add')")
    @ApiOperation(value = "新增岗位", notes = "")
    @Log(title = "系统管理-岗位管理-新增岗位", businessType = BusinessType.INSERT)
    public ResponseResult add(@Validated @RequestBody SysPost post) {
        if (CommonConstant.NOT_UNIQUE.equals(postService.checkPostNameUnique(post))) {
            return ResponseResult.errorResult(HttpCodeEnum.DATA_EXIST, "新增岗位'" + post.getPostName() + "'失败，岗位名称已存在");
        } else if (CommonConstant.NOT_UNIQUE.equals(postService.checkPostCodeUnique(post))) {
            return ResponseResult.errorResult(HttpCodeEnum.DATA_EXIST, "新增岗位'" + post.getPostName() + "'失败，岗位编码已存在");
        }
        post.setCreateBy(SecurityUtils.getUsername());
        return ResponseResult.okResult(postService.save(post));
    }

    /**
     * 修改岗位
     */
    @PutMapping
    @PreAuthorize("@md.hasPermi('system:post:edit')")
    @ApiOperation(value = "修改岗位", notes = "")
    @Log(title = "系统管理-岗位管理-修改岗位", businessType = BusinessType.UPDATE)
    public ResponseResult edit(@Validated @RequestBody SysPost post) {
        if (CommonConstant.NOT_UNIQUE.equals(postService.checkPostNameUnique(post))) {
            return ResponseResult.errorResult(HttpCodeEnum.DATA_EXIST, "修改岗位'" + post.getPostName() + "'失败，岗位名称已存在");
        } else if (CommonConstant.NOT_UNIQUE.equals(postService.checkPostCodeUnique(post))) {
            return ResponseResult.errorResult(HttpCodeEnum.DATA_EXIST, "修改岗位'" + post.getPostName() + "'失败，岗位编码已存在");
        }
        post.setUpdateBy(SecurityUtils.getUsername());
        return ResponseResult.okResult(postService.updateById(post));
    }

    /**
     * 删除岗位
     */

    @DeleteMapping("/{postIds}")
    @PreAuthorize("@md.hasPermi('system:post:remove')")
    @ApiOperation(value = "删除岗位", notes = "")
    @Log(title = "系统管理-岗位管理-删除岗位", businessType = BusinessType.DELETE)
    public ResponseResult remove(@PathVariable Long[] postIds) {
        return ResponseResult.okResult(postService.removeByIds(Arrays.asList(postIds)));
    }

    /**
     * 获取岗位选择框列表
     */
    @GetMapping("/optionselect")
    @ApiOperation(value = "获取岗位选择框列表", notes = "")
    public ResponseResult optionselect() {
        List<SysPost> posts = postService.selectPostAll();
        return ResponseResult.okResult(posts);
    }

}
