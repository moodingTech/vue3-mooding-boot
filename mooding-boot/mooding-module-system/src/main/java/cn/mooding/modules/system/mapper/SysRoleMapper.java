package cn.mooding.modules.system.mapper;

import cn.mooding.modules.system.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 角色信息表 Mapper 接口
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-21
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {
<<<<<<< HEAD
    @Select("select a.* from t_sys_role a left join t_sys_user_role b on a.role_id= b.role_id where b.user_id=#{userId} ")
=======
    @Select("select a.* from t_sys_role a left join t_sys_user_role b on a.role_id= b.role_id where a.del_flag<>2 and b.user_id=#{userId}  ")
>>>>>>> master
    public List<SysRole> getRolesByUserId(@Param("userId") Long userId);
}
