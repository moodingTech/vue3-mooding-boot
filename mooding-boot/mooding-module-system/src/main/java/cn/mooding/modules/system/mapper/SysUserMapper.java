package cn.mooding.modules.system.mapper;

import cn.mooding.modules.system.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-21
 */

public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Select("select * from t_sys_user a where a.user_name= #{userName}")
    public SysUser selectUserByUserName(@Param("userName") String userName);

    /**
     * 修改登陆信息
     * @param userId 用户id
     * @param loginIP   登陆Ip
     */
    @Update("set t_sys_user set login_ip =#{loginIP} ,login_date=now() where user_id=#{userId}")
    public int updateUserLoginInfo(@Param("userId") Long userId, @Param("loginIP") String loginIP);
}
