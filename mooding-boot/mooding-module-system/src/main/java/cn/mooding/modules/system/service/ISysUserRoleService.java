package cn.mooding.modules.system.service;

import cn.mooding.modules.system.entity.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户和角色关联表 服务类
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-21
 */
public interface ISysUserRoleService extends IService<SysUserRole> {
    public List<Long> getRoleIdsByUserId(Long useerId);

}
