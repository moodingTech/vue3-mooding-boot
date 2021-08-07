package cn.mooding.modules.system.mapper;

import cn.mooding.modules.system.entity.SysDept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-21
 */
public interface SysDeptMapper extends BaseMapper<SysDept> {
    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    public List<SysDept> selectDeptList(SysDept dept);

    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    public Set<Long> getDeptIdsByRoleId(SysDept dept);

    /**
     * 根据角色ID查询部门树信息
     *
     * @param roleId            角色ID
     * @param deptCheckStrictly 部门树选择项是否关联显示
     * @return 选中部门列表
     */
    public List<Integer> selectDeptListByRoleId(@Param("roleId") Long roleId, @Param("deptCheckStrictly") boolean deptCheckStrictly);

    /**
     * 根据角色ID查询部门
     *
     * @param roleId 角色ID
     * @return 选中部门列表
     */
    @Select("select  CONCAT_WS (',',ancestors ,  dept_id) as ancestors from t_sys_dept a left join t_sys_role_dept b on a.dept_id =b.dept_id where b.role_id=#{roleId}")
    public List<SysDept> selectDeptListByRoleId(@Param("roleId") Long roleId);
    /**
     * 校验部门名称是否唯一
     *
     * @param deptName 部门名称
     * @param parentId 父部门ID
     * @return 结果
     */
    public SysDept checkDeptNameUnique(@Param("deptName") String deptName, @Param("parentId") Long parentId);

    /**
     * 根据ID查询所有子部门（正常状态）
     *
     * @param deptId 部门ID
     * @return 子部门数
     */
    @Select("select count(*) from t_sys_dept where status = 0 and del_flag = '0' and find_in_set(#{deptId}, ancestors)")
    public int selectNormalChildrenDeptById(@Param("deptId")Long deptId);


    /**
     * 根据ID查询所有子部门
     *
     * @param deptId 部门ID
     * @return 部门列表
     */
    @Select("select * from t_sys_dept where find_in_set(#{deptId}, ancestors)")
    public List<SysDept> selectChildrenDeptById(@Param("deptId")Long deptId);

    /**
     * 修改子元素关系
     *
     * @param depts 子元素
     * @return 结果
     */
    public int updateDeptChildren(@Param("depts") List<SysDept> depts);
    /**
     * 修改所在部门的父级部门状态
     *
     * @param dept 部门
     */
    public void updateDeptStatus(SysDept dept);

    /**
     * 是否存在子节点
     *
     * @param deptId 部门ID
     * @return 结果
     */

    @Select("select count(1) from t_sys_dept  where del_flag = '0' and parent_id = #{deptId} limit 1")
    public int hasChildByDeptId(@Param("deptId") Long deptId);

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Select("select count(1) from t_sys_user where dept_id = #{deptId} and del_flag = '0'")
    public int checkDeptExistUser(@Param("deptId") Long deptId);

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Update("update t_sys_dept set del_flag = '2' where dept_id = #{deptId}")
    public int deleteDeptById(@Param("deptId") Long deptId);

}
