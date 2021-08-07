package cn.mooding.modules.system.service.impl;

import cn.mooding.modules.system.entity.SysUserPost;
import cn.mooding.modules.system.mapper.SysUserPostMapper;
import cn.mooding.modules.system.service.ISysUserPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户与岗位关联表 服务实现类
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-21
 */
@Service
public class SysUserPostServiceImpl extends ServiceImpl<SysUserPostMapper, SysUserPost> implements ISysUserPostService {

}
