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
<<<<<<< HEAD
    @Select("select * from t_sys_user a where a.user_name= #{userName}")
=======
    @Select("select * from t_sys_user a where del_flag<>2 and a.user_name= #{userName}")
>>>>>>> master
    public SysUser selectUserByUserName(@Param("userName") String userName);

    /**
     * 修改登陆信息
<<<<<<< HEAD
     * @param userId 用户id
     * @param loginIP   登陆Ip
     */
    @Update("set t_sys_user set login_ip =#{loginIP} ,login_date=now() where user_id=#{userId}")
    public int updateUserLoginInfo(@Param("userId") Long userId, @Param("loginIP") String loginIP);
=======
     *
     * @param userId  用户id
     * @param loginIP 登陆Ip
     */
    @Update("update t_sys_user set login_ip =#{loginIP} ,login_date=now() where user_id=#{userId}")
    public int updateUserLoginInfo(@Param("userId") Long userId, @Param("loginIP") String loginIP);

    /**
     * 修改用户用户状态
     *
     * @param userId 用户id
     * @param status 用户状态
     * @return 结果
     */
    @Update("update t_sys_user set status =#{status}  where user_id=#{userId} ")
    public int updateUserStatus(@Param("userId") Long userId, @Param("status") String status);

    /**
     * 重置用户密码
     *
     * @param userId   用户id
     * @param password 新密码
     * @return 结果
     */
    @Update("update t_sys_user set password =#{password}  where user_id=#{userId}")
    public int resetPwd(@Param("userId") Long userId, @Param("password") String password);
>>>>>>> master
}
