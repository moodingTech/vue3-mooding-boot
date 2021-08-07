package cn.mooding.modules.system.service.impl;

import cn.mooding.modules.system.entity.SysLogininfo;
import cn.mooding.modules.system.mapper.SysLogininfoMapper;
import cn.mooding.modules.system.service.ISysLogininfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统访问记录 服务实现类
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-26
 */
@Service
public class SysLogininfoServiceImpl extends ServiceImpl<SysLogininfoMapper, SysLogininfo> implements ISysLogininfoService {

    /**
     * 新增系统登录日志
     *
     * @param logininfo 访问日志对象
     */
    @Override
    public void insertLogininfo(SysLogininfo logininfo) {
        this.save(logininfo);
    }

    /**
     * 清空系统登录日志
     */
    @Override
    public void cleanLogininfor() {
        baseMapper.cleanLogininfor();
    }
}
