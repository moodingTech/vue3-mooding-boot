package cn.mooding.modules.system.service.impl;

import cn.mooding.common.model.BaseEntity;
import cn.mooding.common.model.exception.BaseException;
import cn.mooding.common.utils.spring.SpringUtils;
import cn.mooding.common.utils.string.StringUtils;
import cn.mooding.modules.security.utils.SecurityUtils;
import cn.mooding.modules.system.entity.*;
import cn.mooding.modules.system.mapper.SysRoleDeptMapper;
import cn.mooding.modules.system.mapper.SysRoleMapper;
import cn.mooding.modules.system.mapper.SysRoleMenuMapper;
import cn.mooding.modules.system.mapper.SysUserRoleMapper;
import cn.mooding.modules.system.service.ISysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-21
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
    private static final Logger log = LoggerFactory.getLogger(SysRoleServiceImpl.class);

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;
    @Autowired
    private SysRoleDeptMapper roleDeptMapper;
    @Autowired
    private SysUserRoleMapper userRoleMapper;

    /**
     * 分页查询角色信息
     *
     * @param page         分页信息
     * @param queryWrapper 查询信息
     * @return
     */
    @Override
    public IPage<SysRole> selectMapsPage(Page<SysRole> page, QueryWrapper<SysRole> queryWrapper) {
        //判断是否是管理员，不是管理员需判断用户的权限
        if (!SecurityUtils.isAdmin()) {
            SysUser user = SecurityUtils.getLoginUser().getUser();
            List<SysRole> collect = user.getRoles().stream().filter(w -> !w.getDataScope().equals("5")).collect(Collectors.toList());
            Long[] roleIds = collect.stream().map(SysRole::getRoleId).toArray(Long[]::new);
            if (roleIds != null && roleIds.length > 0) {
                queryWrapper.lambda().in(SysRole::getRoleId, roleIds);
            }else {
                queryWrapper.lambda().in(SysRole::getRoleId, -1);
            }

        }

        IPage<SysRole> pageList = baseMapper.selectPage(page, queryWrapper);
        return pageList;
    }

    /**
     * 根据角色ids批量查询角色
     *
     * @param roleIds
     * @return List<SysRole>
     */
    @Override
    public List<SysRole> getRolesByRoleIds(List<Long> roleIds) {
        List<SysRole> roleList = this.baseMapper.selectBatchIds(roleIds);
        return roleList;
    }

    /**
     * 根据用户id批量查询角色
     *
     * @param userId
     * @return List<SysRole>
     */
    @Override
    public List<SysRole> getRoleByUserId(Long userId) {
        List<SysRole> roleList = this.baseMapper.getRolesByUserId(userId);
        return roleList;
    }

    /**
     * 校验角色是否唯一
     *
     * @param role 角色信息
     * @return 结果 0 校验是唯一的, 1 角色名不唯一, 2角色权限不唯一
     */
    @Override
    public int checkRoleUnique(SysRole role) {
        int count = 0;
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<SysRole>().ne(SysRole::getDelFlag, 2).and(i -> i.eq(SysRole::getRoleName, role.getRoleName()).or().eq(SysRole::getRoleKey, role.getRoleKey()));
        List<SysRole> sysRoles = baseMapper.selectList(queryWrapper.orderByDesc(SysRole::getRoleId));
        for (SysRole sysUser : sysRoles) {
            if (ObjectUtils.isNotEmpty(role.getRoleId()) && sysUser.getRoleId() != role.getRoleId()) {
                if (sysUser.getRoleName().equals(role.getRoleName())) {
                    return count = 1;
                } else if (sysUser.getRoleKey().equals(role.getRoleKey())) {
                    return count = 2;
                }
            }
        }
        return count;
    }

    /**
     * 新增保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertRole(SysRole role) {
        // 新增角色信息
        baseMapper.insert(role);
        return insertRoleMenu(role);
    }

    /**
     * 修改保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateRole(SysRole role) {
        // 修改角色信息
        baseMapper.updateById(role);
        // 删除角色与菜单关联
        roleMenuMapper.deleteRoleMenuByRoleId(role.getRoleId());
        return insertRoleMenu(role);
    }

    /**
     * 新增角色菜单信息
     *
     * @param role 角色对象
     */
    public int insertRoleMenu(SysRole role) {
        int rows = 1;
        // 新增用户与角色管理
        List<SysRoleMenu> list = new ArrayList<SysRoleMenu>();
        for (Long menuId : role.getMenuIds()) {
            SysRoleMenu rm = new SysRoleMenu();
            rm.setRoleId(role.getRoleId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (list.size() > 0) {
            rows = roleMenuMapper.batchRoleMenu(list);
        }
        return rows;
    }

    /**
     * 修改数据权限信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional
    public int authDataScope(SysRole role) {
        // 修改角色信息
        baseMapper.updateById(role);
        // 删除角色与部门关联
        roleDeptMapper.deleteRoleDeptByRoleId(role.getRoleId());
        // 新增角色和部门信息（数据权限）
        return insertRoleDept(role);
    }

    /**
     * 新增角色部门信息(数据权限)
     *
     * @param role 角色对象
     */
    public int insertRoleDept(SysRole role) {
        int rows = 1;
        // 新增角色与部门（数据权限）管理
        List<SysRoleDept> list = new ArrayList<SysRoleDept>();
        for (Long deptId : role.getDeptIds()) {
            SysRoleDept rd = new SysRoleDept();
            rd.setRoleId(role.getRoleId());
            rd.setDeptId(deptId);
            list.add(rd);
        }
        if (list.size() > 0) {
            rows = roleDeptMapper.batchRoleDept(list);
        }
        return rows;
    }

    /**
     * 修改角色状态
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public int updateRoleStatus(SysRole role) {
        SysRole sysRole = baseMapper.selectById(role.getRoleId());
        sysRole.setStatus(role.getStatus());
        return baseMapper.updateById(sysRole);
    }

    /**
     * 批量删除角色信息
     *
     * @param roleIds 需要删除的角色ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteRoleByIds(Long[] roleIds) {
        int count = 0;
        for (Long roleId : roleIds) {
//            checkRoleAllowed(new SysRole(roleId));
            SysRole role = baseMapper.selectById(roleId);
            if (countUserRoleByRoleId(roleId) > 0) {
                throw new BaseException(String.format("%1$s已分配,不能删除", role.getRoleName()));
            }
            //删除角色与菜单关联
            roleMenuMapper.deleteRoleMenuByRoleId(roleId);
            // 删除角色与部门关联
            roleDeptMapper.deleteRoleDeptByRoleId(roleId);
            SysRole sysRole = baseMapper.selectById(roleId);
            sysRole.setDelFlag("2");
            baseMapper.updateById(sysRole);
            count++;
        }
        return count;
        // 删除角色与菜单关联
//        roleMenuMapper.deleteRoleMenu(roleIds);
//        // 删除角色与部门关联
//        roleDeptMapper.deleteRoleDept(roleIds);
//        return roleMapper.deleteRoleByIds(roleIds);
    }

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    public int countUserRoleByRoleId(Long roleId) {
        return userRoleMapper.countUserRoleByRoleId(roleId);
    }

    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    @Override
    public List<SysRole> selectRoleAll() {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<SysRole>().ne(SysRole::getDelFlag, 2);

        return baseMapper.selectList(queryWrapper);
    }

}
