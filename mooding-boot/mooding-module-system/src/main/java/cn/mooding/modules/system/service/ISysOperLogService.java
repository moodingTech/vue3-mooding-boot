package cn.mooding.modules.system.service;

import cn.mooding.modules.system.entity.SysOperLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 操作日志记录 服务类
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-26
 */
public interface ISysOperLogService extends IService<SysOperLog> {
    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    public void insertOperlog(SysOperLog operLog);

    /**
     * 清空操作日志
     */
    public void cleanOperLog();
}
