package cn.mooding.modules.system.controller;


import cn.mooding.common.aspect.annotation.Log;
import cn.mooding.common.model.constant.CommonConstant;
import cn.mooding.common.model.dto.ResponseResult;
import cn.mooding.common.model.enums.BusinessType;
import cn.mooding.common.model.enums.HttpCodeEnum;
import cn.mooding.common.utils.generate.QueryGenerator;
import cn.mooding.common.utils.string.StringUtils;
import cn.mooding.modules.security.utils.SecurityUtils;
import cn.mooding.modules.system.entity.SysDept;
import cn.mooding.modules.system.entity.SysUser;
import cn.mooding.modules.system.service.ISysDeptService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部门表 前端控制器
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-21
 */
@RestController
@Api(tags = "系统部门")
@RequestMapping("/system/dept")
public class SysDeptController {
    private static final Logger log = LoggerFactory.getLogger(SysDeptController.class);

    @Autowired
    private ISysDeptService deptService;

    /**
     * 获取部门列表
     */
    @GetMapping("/list")
    @PreAuthorize("@md.hasPermi('system:dept:list')")
    @ApiOperation(value = "系统管理-部门管理-获取部门列表", notes = "")
    public ResponseResult list(SysDept dept) {
        QueryWrapper<SysDept> queryWrapper = QueryGenerator.initQueryWrapper(dept);
        queryWrapper.lambda().eq(SysDept::getDelFlag, 0);
        List<SysDept> depts = deptService.selectDeptList(queryWrapper);
        return ResponseResult.okResult(depts);
    }

    /**
     * 查询部门列表（排除节点）
     */
    @GetMapping("/list/exclude/{deptId}")
    @PreAuthorize("@md.hasPermi('system:dept:list')")
    @ApiOperation(value = "系统管理-部门管理-查询部门列表（排除节点）", notes = "")
    public ResponseResult excludeChild(@PathVariable(value = "deptId", required = false) Long deptId) {
        QueryWrapper<SysDept> wrapper = new QueryWrapper<SysDept>();
        //限制部门状态为正常
        wrapper.lambda().eq(SysDept::getStatus, 0).eq(SysDept::getDelFlag, 0);
        List<SysDept> depts = deptService.selectDeptList(wrapper);
        Iterator<SysDept> it = depts.iterator();
        while (it.hasNext()) {
            SysDept d = (SysDept) it.next();
            if (d.getDeptId().intValue() == deptId
                    || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), deptId + "")) {
                it.remove();
            }
        }
        return ResponseResult.okResult(depts);
    }

    /**
     * 获取部门下拉树列表
     */
    @GetMapping("/treeselect")
    @ApiOperation(value = "获取部门下拉树列表", notes = "")
    public ResponseResult treeselect() {
        QueryWrapper<SysDept> wrapper = new QueryWrapper<SysDept>();
        //限制部门状态为正常
        wrapper.lambda().eq(SysDept::getStatus, 0).eq(SysDept::getDelFlag, 0);
        List<SysDept> depts = deptService.selectDeptList(wrapper);
        return ResponseResult.okResult(deptService.buildDeptTreeSelect(depts));
    }

    /**
     * 加载对应角色部门列表树
     */
    @GetMapping(value = "/roleDeptTreeselect/{roleId}")
    @PreAuthorize("@md.hasPermi('system:dept:list')")
    @ApiOperation(value = "加载对应角色部门列表树", notes = "")
    public ResponseResult roleDeptTreeselect(@PathVariable("roleId") Long roleId) {
        QueryWrapper<SysDept> wrapper = new QueryWrapper<SysDept>();
        //限制部门状态为正常
        wrapper.lambda().eq(SysDept::getStatus, 0).eq(SysDept::getDelFlag, 0);
        List<SysDept> depts = deptService.selectDeptList(wrapper);

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("checkedKeys", deptService.selectDeptListByRoleId(roleId));
        data.put("depts", deptService.buildDeptTreeSelect(depts));
        return ResponseResult.okResult(data);
    }


    /**
     * 根据部门编号获取详细信息
     */
    @GetMapping(value = "/{deptId}")
    @PreAuthorize("@md.hasPermi('system:dept:query')")
    @ApiOperation(value = "根据部门编号查询详细信息", notes = "")
    public ResponseResult getInfo(@PathVariable Long deptId) {
        return ResponseResult.okResult(deptService.getById(deptId));
    }

    /**
     * 新增部门
     */

    @PostMapping
    @PreAuthorize("@md.hasPermi('system:dept:add')")
    @ApiOperation(value = "新增部门", notes = "")
    @Log(title = "系统管理-部门管理-新增部门", businessType = BusinessType.INSERT)
    public ResponseResult add(@Validated @RequestBody SysDept dept) {
        if (CommonConstant.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
            return ResponseResult.errorResult(HttpCodeEnum.DATA_EXIST, "新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
        dept.setCreateBy(SecurityUtils.getUsername());
        return ResponseResult.okResult(deptService.insertDept(dept));
    }

    /**
     * 修改部门
     */
    @PutMapping
    @PreAuthorize("@md.hasPermi('system:dept:edit')")
    @ApiOperation(value = "修改部门", notes = "")
    @Log(title = "系统管理-部门管理-修改部门", businessType = BusinessType.UPDATE)
    public ResponseResult edit(@Validated @RequestBody SysDept dept) {
        if (CommonConstant.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
            return ResponseResult.errorResult(HttpCodeEnum.DATA_EXIST, "修改部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        } else if (dept.getParentId().equals(dept.getDeptId())) {
            return ResponseResult.errorResult(HttpCodeEnum.PARAM_ERR, "修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
        } else if (StringUtils.equals(CommonConstant.DEPT_DISABLE, dept.getStatus())
                && deptService.selectNormalChildrenDeptById(dept.getDeptId()) > 0) {
            return ResponseResult.errorResult(HttpCodeEnum.PARAM_ERR, "该部门包含未停用的子部门！");
        }
        return ResponseResult.okResult(deptService.updateDept(dept));
    }

    /**
     * 删除部门
     */
    @DeleteMapping("/{deptId}")
    @PreAuthorize("@md.hasPermi('system:dept:remove')")
    @ApiOperation(value = "删除部门", notes = "")
    @Log(title = "系统管理-部门管理-删除部门", businessType = BusinessType.DELETE)
    public ResponseResult remove(@PathVariable Long deptId) {
        if (deptService.hasChildByDeptId(deptId)) {
            return ResponseResult.errorResult(HttpCodeEnum.PARAM_ERR, "存在下级部门,不允许删除");
        }
        if (deptService.checkDeptExistUser(deptId)) {
            return ResponseResult.errorResult(HttpCodeEnum.PARAM_ERR, "部门存在用户,不允许删除");
        }
        return ResponseResult.okResult(deptService.deleteDeptById(deptId));
    }
}
