package cn.mooding.modules.system.service.impl;

import cn.mooding.common.model.constant.CommonConstant;
import cn.mooding.common.model.exception.BaseException;
import cn.mooding.common.utils.generate.QueryGenerator;
import cn.mooding.common.utils.string.StringUtils;
import cn.mooding.modules.security.LoginUser;
import cn.mooding.modules.security.utils.SecurityUtils;
import cn.mooding.modules.system.entity.SysDept;
import cn.mooding.modules.system.entity.SysRole;
import cn.mooding.modules.system.mapper.*;
import cn.mooding.modules.system.model.vo.TreeSelect;
import cn.mooding.modules.system.service.ISysDeptService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-21
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {

    @Autowired
    private SysRoleMapper roleMapper;

    /**
     * 查询部门管理数据
     *
<<<<<<< HEAD
     * @param dept 部门信息
=======
     * @param queryWrapper 部门信息
>>>>>>> master
     * @return 部门信息集合
     */
    @Override
    public List<SysDept> selectDeptList(QueryWrapper<SysDept> queryWrapper) {
//        QueryWrapper<SysDept> queryWrapper = QueryGenerator.initQueryWrapper(dept);
        if (!SecurityUtils.isAdmin()) {
            Set<String> deptIds = getLoginUserDeptIds();
            if (deptIds != null && deptIds.size() > 0) {
                queryWrapper.lambda().in(SysDept::getDeptId, deptIds);
            } else {
                queryWrapper.lambda().in(SysDept::getDeptId, -1);
            }
        }
        List<SysDept> list = baseMapper.selectList(queryWrapper);
        return list;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param depts 部门列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts) {
        List<SysDept> deptTrees = buildDeptTree(depts);
        return deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 构建前端所需要树结构
     *
     * @param depts 部门列表
     * @return 树结构列表
     */
    @Override
    public List<SysDept> buildDeptTree(List<SysDept> depts) {
        List<SysDept> returnList = new ArrayList<SysDept>();
        List<Long> tempList = new ArrayList<Long>();
        for (SysDept dept : depts) {
            tempList.add(dept.getDeptId());
        }
        for (Iterator<SysDept> iterator = depts.iterator(); iterator.hasNext(); ) {
            SysDept dept = (SysDept) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getParentId())) {
                recursionFn(depts, dept);
                returnList.add(dept);
            }
        }
        if (returnList.isEmpty()) {
            returnList = depts;
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SysDept> list, SysDept t) {
        // 得到子节点列表
        List<SysDept> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysDept tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysDept> getChildList(List<SysDept> list, SysDept t) {
        List<SysDept> tlist = new ArrayList<SysDept>();
        Iterator<SysDept> it = list.iterator();
        while (it.hasNext()) {
            SysDept n = (SysDept) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getDeptId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysDept> list, SysDept t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }

    /**
     * 根据登陆用户获取具有的部门ids
     */
    @Override
    public Set<String> getLoginUserDeptIds() {
        Set<String> deptIds = new HashSet<String>();
        //1.获取当前登陆的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //2.根据用户查看具有的角色
        List<SysRole> sysRoles = loginUser.getUser().getRoles();
        Set<String> collect = new HashSet<String>();
        for (SysRole sysRole : sysRoles) {
            String dataScope = sysRole.getDataScope();
            if (dataScope.equals("1")) {
                QueryWrapper<SysDept> wrapper = new QueryWrapper<SysDept>();
                wrapper.lambda().eq(SysDept::getDelFlag, "0");
                wrapper.select(" distinct ancestors||','|| dept_id as ancestors ");
                List<SysDept> sysDepts = baseMapper.selectList(wrapper);
                collect.addAll(sysDepts.stream().map(SysDept::getAncestors).collect(Collectors.toSet()));
            } else if (dataScope.equals("2")) {
                // 自定义数据部门权限
                List<SysDept> sysDepts = baseMapper.selectDeptListByRoleId(sysRole.getRoleId());
                collect.addAll(sysDepts.stream().map(SysDept::getAncestors).collect(Collectors.toSet()));
            } else if (dataScope.equals("3")) {
                //部门数据权限
                collect.add(loginUser.getUser().getDept().getAncestors() + "," + loginUser.getUser().getDeptId());
            } else if (dataScope.equals("4")) {
                // 部门及以下数据权限
                QueryWrapper<SysDept> wrapper = new QueryWrapper<SysDept>();
                wrapper.lambda().eq(SysDept::getDelFlag, "0");
                wrapper.select(" distinct CONCAT_WS (',',ancestors ,  dept_id) as ancestors ");
                wrapper.lambda().inSql(SysDept::getDeptId, StringUtils.format(
                        "  SELECT dept_id FROM t_sys_dept WHERE dept_id = {} or find_in_set( {} , ancestors ) ",
                        loginUser.getUser().getDeptId(), loginUser.getUser().getDeptId()));
                List<SysDept> sysDepts = baseMapper.selectList(wrapper);
                collect.addAll(sysDepts.stream().map(SysDept::getAncestors).collect(Collectors.toSet()));
            } else if (dataScope.equals("5")) {
                collect.add("-1");
            }
        }
        for (String aa : collect) {
            deptIds.addAll(new HashSet<String>(Arrays.asList(aa.split(","))));
        }
        return deptIds;
    }


    /**
     * 根据角色ID查询部门树信息
     *
     * @param roleId 角色ID
     * @return 选中部门列表
     */
    @Override
    public List<Integer> selectDeptListByRoleId(Long roleId) {
        SysRole role = roleMapper.selectById(roleId);
        return baseMapper.selectDeptListByRoleId(roleId, /*role.isDeptCheckStrictly()*/true);
    }

    /**
     * 新增保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int insertDept(SysDept dept) {
        SysDept info = baseMapper.selectById(dept.getParentId());
        // 如果父节点不为正常状态,则不允许新增子节点
        if (!CommonConstant.DEPT_NORMAL.equals(info.getStatus())) {
            throw new BaseException("部门停用，不允许新增");
        }
        dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
        return baseMapper.insert(dept);
    }

    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public String checkDeptNameUnique(SysDept dept) {
        Long deptId = StringUtils.isNull(dept.getDeptId()) ? -1L : dept.getDeptId();
        SysDept info = baseMapper.checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
        if (StringUtils.isNotNull(info) && info.getDeptId().longValue() != deptId.longValue()) {
            return CommonConstant.NOT_UNIQUE;
        }
        return CommonConstant.UNIQUE;
    }

    /**
     * 根据ID查询所有子部门（正常状态）
     *
     * @param deptId 部门ID
     * @return 子部门数
     */
    @Override
    public int selectNormalChildrenDeptById(Long deptId) {
        return baseMapper.selectNormalChildrenDeptById(deptId);
    }

    /**
     * 修改保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int updateDept(SysDept dept) {
        SysDept newParentDept = baseMapper.selectById(dept.getParentId());
        SysDept oldDept = baseMapper.selectById(dept.getDeptId());
        if (StringUtils.isNotNull(newParentDept) && StringUtils.isNotNull(oldDept)) {
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
            String oldAncestors = oldDept.getAncestors();
            dept.setAncestors(newAncestors);
            updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
        }
        int result = baseMapper.updateById(dept);
        if (CommonConstant.DEPT_NORMAL.equals(dept.getStatus())) {
            // 如果该部门是启用状态，则启用该部门的所有上级部门
            updateParentDeptStatus(dept);
        }
        return result;
    }

    /**
     * 修改子元素关系
     *
     * @param deptId       被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors) {
        List<SysDept> children = baseMapper.selectChildrenDeptById(deptId);
        for (SysDept child : children) {
            child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
        }
        if (children.size() > 0) {
            baseMapper.updateDeptChildren(children);
        }
    }

    /**
     * 修改该部门的父级部门状态
     *
     * @param dept 当前部门
     */
    private void updateParentDeptStatus(SysDept dept) {
//        dept = baseMapper.selectById(dept.getDeptId());
        baseMapper.updateDeptStatus(dept);
    }

    /**
     * 是否存在子节点
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public boolean hasChildByDeptId(Long deptId) {
        int result = baseMapper.hasChildByDeptId(deptId);
        return result > 0 ? true : false;
    }

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkDeptExistUser(Long deptId) {
        int result = baseMapper.checkDeptExistUser(deptId);
        return result > 0 ? true : false;
    }

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public int deleteDeptById(Long deptId) {
        return baseMapper.deleteDeptById(deptId);
    }
}
