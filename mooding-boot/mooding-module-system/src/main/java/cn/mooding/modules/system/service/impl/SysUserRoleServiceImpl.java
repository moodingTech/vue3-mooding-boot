package cn.mooding.modules.system.service.impl;

import cn.mooding.modules.system.entity.SysUserRole;
import cn.mooding.modules.system.mapper.SysUserRoleMapper;
import cn.mooding.modules.system.service.ISysUserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户和角色关联表 服务实现类
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-21
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {
    @Override
    public List<Long> getRoleIdsByUserId(Long useerId) {
        //  List<SysUserRole> sysUserRoles = this.baseMapper.selectList(new QueryWrapper<SysUserRole>().select("role_id").eq("user_id", useerId));
        LambdaQueryWrapper<SysUserRole> lambdaQueryWrapper =
                new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .select(SysUserRole::getRoleId).eq(SysUserRole::getUserId, useerId);
        List<Long> collect = this.baseMapper.selectObjs(lambdaQueryWrapper).stream()
                .map(o -> (Long) o)
                .collect(Collectors.toList());
        return collect;
    }
}
