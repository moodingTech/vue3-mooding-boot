package cn.mooding.modules.system.service.impl;

import cn.mooding.common.model.exception.BaseException;
import cn.mooding.common.utils.redis.RedisCache;
import cn.mooding.common.utils.string.StringUtils;
import cn.mooding.modules.security.utils.SecurityUtils;
import cn.mooding.modules.system.controller.SysOperLogController;
import cn.mooding.modules.system.entity.SysRole;
import cn.mooding.modules.system.entity.SysUser;
import cn.mooding.modules.system.entity.SysUserPost;
import cn.mooding.modules.system.entity.SysUserRole;
import cn.mooding.modules.system.mapper.*;
import cn.mooding.modules.system.service.*;
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
import java.util.function.Consumer;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-21
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysUserPostMapper sysUserPostMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private ISysConfigService configService;

    /**
     * 分页查询用户信息
     *
     * @param page         分页信息
     * @param queryWrapper 查询信息
     * @return
     */
    @Override
    public IPage<SysUser> selectMapsPage(Page<SysUser> page, QueryWrapper<SysUser> queryWrapper) {
        //判断是否是管理员，不是管理员需判断用户的权限
        if (!SecurityUtils.isAdmin()) {
            SysUser user = SecurityUtils.getLoginUser().getUser();
            StringBuffer sqlString = new StringBuffer();
            QueryWrapper<SysUser> wrapper = new QueryWrapper<SysUser>();
            for (SysRole role : user.getRoles()) {
                String dataScope = role.getDataScope();
                //全部数据权限
                if ("1".equals(dataScope)) {
                    sqlString = new StringBuffer();
                    break;
                    //自定数据权限
                } else if ("2".equals(dataScope)) {
                    sqlString.append(StringUtils.format(
                            "  union  SELECT dept_id FROM t_sys_role_dept WHERE role_id = {}   ",
                            role.getRoleId()));
                    //部门数据权限
                } else if ("3".equals(dataScope)) {
                    sqlString.append(StringUtils.format("  union select {} from dual  ", user.getDeptId()));
//                    queryWrapper.and(wrapper -> wrapper.inSql("dept_id", StringUtils.format(" OR dept_id = {} ", user.getDeptId())));
                    //部门及以下数据权限
                } else if ("4".equals(dataScope)) {
                    sqlString.append(StringUtils.format(
                            "  union SELECT dept_id FROM t_sys_dept WHERE dept_id = {} or find_in_set( {} , ancestors ) ",
                            user.getDeptId(), user.getDeptId()));
                    //仅本人数据权限
                }
//                else if ("5".equals(dataScope)) {
//
//                    sqlString.append(StringUtils.format(" OR user_id = {} ", user.getUserId()));
//                }

            }

            if (StringUtils.isNotEmpty(sqlString.toString())) {
                String s = sqlString.toString().substring(8);
                queryWrapper.lambda().and(w -> w.inSql(SysUser::getDeptId, s).or().eq(SysUser::getUserId, user.getUserId().toString()));
            } else {
                queryWrapper.lambda().and(w -> w.eq(SysUser::getUserId, user.getUserId().toString()));
            }
        }
        IPage<SysUser> pageList = baseMapper.selectPage(page, queryWrapper);
        return pageList;
    }

    @Override
    public SysUser selectUserByUserName(String username) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>();
        if (StringUtils.isNotEmpty(username)) {
            wrapper.eq(SysUser::getUserName, username);
        }
        SysUser sysUser = baseMapper.selectOne(wrapper);
        // 查询部门信息
        sysUser.setDept(sysDeptMapper.selectById(sysUser.getDeptId()));
        // 查询角色信息
        List<Long> roleIds = sysUserRoleMapper.getRoleIdsByUserId(sysUser.getUserId());
        List<SysRole> roles = sysRoleMapper.selectBatchIds(roleIds);
        sysUser.setRoles(roles);
        sysUser.setRoleIds(roleIds.toArray(new Long[roleIds.size()]));
        return sysUser;
    }

    /**
     * 新增用户
     *
     * @param sysUser 用户信息
     */
    @Transactional
    @Override
    public void addSysUser(SysUser sysUser) {
        // 对密码进行加密
        sysUser.setPassword(SecurityUtils.encryptPassword(sysUser.getPassword()));
        //1.保存用户信息
        baseMapper.insert(sysUser);
        // 2.新增用户岗位关联
        insertUserPost(sysUser);
        // 3.新增用户与角色管理
        insertUserRole(sysUser);


    }

    /**
     * 修改保存用户登陆信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateUserLoginInfo(SysUser user) {
        Long userId = user.getUserId();
        return baseMapper.updateUserLoginInfo(user.getUserId(), user.getLoginIp());
    }

    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateUser(SysUser user) {
        Long userId = user.getUserId();
        // 删除用户与角色关联
        sysUserRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(user);
        // 删除用户与岗位关联
        sysUserPostMapper.deleteUserPostByUserId(userId);
        // 新增用户与岗位管理
        insertUserPost(user);
        return baseMapper.updateById(user);
    }

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteUserByIds(Long[] userIds) {
        int count = 0;
        for (Long userId : userIds) {
//            checkUserAllowed(new SysUser(userId));

            // 删除用户与角色关联
//            sysUserRoleMapper.deleteUserRoleByUserId(userId);
            // 删除用户与岗位关联
//            sysUserPostMapper.deleteUserPostByUserId(userId);
            SysUser sysUser = baseMapper.selectById(userId);
            sysUser.setDelFlag("2");
            count = baseMapper.updateById(sysUser);
        }

        return count;
    }

    /**
     * 新增用户岗位信息
     *
     * @param user 用户对象
     */
    public void insertUserPost(SysUser user) {
        Long[] posts = user.getPostIds();
        if (posts != null && posts.length > 0) {
            // 新增用户与岗位管理
            List<SysUserPost> list = new ArrayList<SysUserPost>();
            for (Long postId : posts) {
                SysUserPost up = new SysUserPost();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            }
            if (list.size() > 0) {
                sysUserPostMapper.batchUserPost(list);
            }
        }
    }

    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRole(SysUser user) {
        Long[] roles = user.getRoleIds();
        if (roles != null && roles.length > 0) {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (Long roleId : roles) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(user.getUserId());
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0) {
                sysUserRoleMapper.batchUserRole(list);
            }
        }
    }


    /**
     * 校验用户名是否已经存在
     *
     * @param user 用户
     * @return
     */
    @Override
    public boolean checkUserNameUnique(SysUser user) {
        int count = 0;
        List<SysUser> sysUsers = baseMapper.selectList(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserName, user.getUserName()));
        for (SysUser sysUser : sysUsers) {
            if (ObjectUtils.isNotEmpty(user) && ObjectUtils.isNotEmpty(user.getUserId()) && sysUser.getUserId() != user.getUserId()) {
                count++;
            }
        }
        return count > 0;
    }

    /**
     * 校验手机号是否已经存在
     *
     * @param user 用户
     * @return
     */
    @Override
    public boolean checkPhoneUnique(SysUser user) {
        int count = 0;
        List<SysUser> sysUsers = baseMapper.selectList(new LambdaQueryWrapper<SysUser>().eq(SysUser::getPhonenumber, user.getPhonenumber()));
        for (SysUser sysUser : sysUsers) {
            if (ObjectUtils.isNotEmpty(user) && ObjectUtils.isNotEmpty(user.getUserId()) && sysUser.getUserId() != user.getUserId()) {
                count++;
            }
        }
        return count > 0;
    }

    /**
     * 校验邮箱是否已经存在
     *
     * @param user 用户
     * @return
     */
    public boolean checkEmailUnique(SysUser user) {
        int count = 0;
        List<SysUser> sysUsers = baseMapper.selectList(new LambdaQueryWrapper<SysUser>().eq(SysUser::getEmail, user.getEmail()));
        for (SysUser sysUser : sysUsers) {
            if (ObjectUtils.isNotEmpty(user) && ObjectUtils.isNotEmpty(user.getUserId()) && sysUser.getUserId() != user.getUserId()) {
                count++;
            }
        }
        return count > 0;
    }

    /**
     * 导入用户数据
     *
     * @param userList        用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(userList) || userList.size() == 0) {
            throw new BaseException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String password = configService.selectConfigByKey("sys.user.initPassword");
        for (SysUser user : userList) {
            try {
                // 验证是否存在这个用户
                SysUser u = baseMapper.selectUserByUserName(user.getUserName());
                if (StringUtils.isNull(u)) {
                    user.setPassword(SecurityUtils.encryptPassword(password));
                    user.setCreateBy(operName);
                    baseMapper.insert(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 导入成功");
                } else if (isUpdateSupport) {
                    user.setUpdateBy(operName);
                    this.updateUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + user.getUserName() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + user.getUserName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new BaseException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
