package cn.mooding.modules.system.service;

import cn.mooding.modules.system.entity.SysUser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-21
 */
public interface ISysUserService extends IService<SysUser> {
    // 分页查询用户信息
    public IPage<SysUser> selectMapsPage(Page<SysUser> page, QueryWrapper<SysUser> queryWrapper);

    //通过用户名查询用户信息
    public SysUser selectUserByUserName(String username);

    // 添加用户信息
    public void addSysUser(SysUser sysUser);

    //校验用户名是否已经存在
    public boolean checkUserNameUnique(SysUser user);

    //校验手机号码是否已经存在
    public boolean checkPhoneUnique(SysUser user);

    //校验邮箱是否已经存在
    public boolean checkEmailUnique(SysUser user);

    // 修改用户信息
    public int updateUser(SysUser user);

    // 修改用户信息
    public int updateUserLoginInfo(SysUser user);

    //批量删除用户信息
    public int deleteUserByIds(Long[] userIds);

    /**
     * 导入用户数据
     *
     * @param userList        用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName);
}
