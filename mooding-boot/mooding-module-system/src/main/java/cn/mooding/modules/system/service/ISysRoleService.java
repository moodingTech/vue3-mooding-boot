package cn.mooding.modules.system.service;

import cn.mooding.modules.system.entity.SysRole;
import cn.mooding.modules.system.entity.SysUser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-21
 */
public interface ISysRoleService extends IService<SysRole> {
    //分页查询用户具有的角色
    public IPage<SysRole> selectMapsPage (Page<SysRole> page, QueryWrapper<SysRole> queryWrapper);
    //根据角色ids批量查询角色
    public List<SysRole> getRolesByRoleIds(List<Long> roleIds);

    //根据用户id批量查询角色
    public List<SysRole> getRoleByUserId(Long userId);

    // 校验角色是否唯一
    public int checkRoleUnique(SysRole role);

    //新增保存角色信息
    public int insertRole(SysRole role);

    //修改保存角色信息
    public int updateRole(SysRole role);

   //修改数据权限信息
    public int authDataScope(SysRole role);

    //修改角色状态
    public int updateRoleStatus(SysRole role);

    //批量删除角色信息
    public int deleteRoleByIds(Long[] roleIds);

    //通过角色ID查询角色使用数量
    public int countUserRoleByRoleId(Long roleId);

    //查询所有角色
    public List<SysRole> selectRoleAll();
}
