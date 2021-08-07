package cn.mooding.modules.system.service;

import cn.mooding.modules.system.entity.SysDept;
import cn.mooding.modules.system.entity.SysMenu;
import cn.mooding.modules.system.entity.SysUser;
import cn.mooding.modules.system.model.vo.TreeSelect;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-21
 */
public interface ISysDeptService extends IService<SysDept> {

    /**
     * 构建前端所需要下拉树结构
     *
     * @param depts 部门列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts);

    /**
     * 构建前端所需要树结构
     *
     * @param depts 部门列表
     * @return 树结构列表
     */
    public List<SysDept> buildDeptTree(List<SysDept> depts);

    /**
     * 根据登陆用户获取具有的部门ids
     */
    public Set<String> getLoginUserDeptIds();
    /**
     * 查询部门管理数据
     *
     * @param queryWrapper 封装部门查询部门信息
     * @return 部门信息集合
     */
    public List<SysDept> selectDeptList(QueryWrapper<SysDept> queryWrapper);

    //根据角色ID查询部门树信息
    public List<Integer> selectDeptListByRoleId(Long roleId);
    /**
     * 新增保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    public int insertDept(SysDept dept);
    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    public String checkDeptNameUnique(SysDept dept);
    /**
     * 根据ID查询所有子部门（正常状态）
     *
     * @param deptId 部门ID
     * @return 子部门数
     */
    public int selectNormalChildrenDeptById(Long deptId);
    /**
     * 修改保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    public int updateDept(SysDept dept);
    /**
     * 是否存在部门子节点
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public boolean hasChildByDeptId(Long deptId);
    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public boolean checkDeptExistUser(Long deptId);
    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public int deleteDeptById(Long deptId);
}
