package cn.mooding.modules.system.service.impl;

import cn.mooding.modules.system.entity.SysOperLog;
import cn.mooding.modules.system.mapper.SysOperLogMapper;
import cn.mooding.modules.system.service.ISysOperLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志记录 服务实现类
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-26
 */
@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements ISysOperLogService {
    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    @Override
    public void insertOperlog(SysOperLog operLog) {
        save(operLog);
    }

    /**
     * 清空操作日志
     */
    @Override
    public void cleanOperLog()
    {
        baseMapper.cleanOperLog();
    }
}
