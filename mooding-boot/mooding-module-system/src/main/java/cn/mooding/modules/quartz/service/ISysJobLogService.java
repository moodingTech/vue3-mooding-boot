package cn.mooding.modules.quartz.service;

import cn.mooding.modules.quartz.entity.SysJob;
import cn.mooding.modules.quartz.entity.SysJobLog;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 定时任务调度日志表 服务类
 * </p>
 *
 * @author BlueFire
 * @since 2021-07-26
 */
public interface ISysJobLogService extends IService<SysJobLog> {

    /**
     * 根据条件分页查询任务日志
     *
     * @param page 分页信息
     * @return 任务日志集合信息
     */
    public IPage<SysJobLog> selectMapsPage(Page<SysJobLog> page, QueryWrapper<SysJobLog> queryWrapper);
    /**
     * 新增任务日志
     *
     * @param jobLog 调度日志信息
     */
    public void addJobLog(SysJobLog jobLog);
    /**
     * 清空任务日志
     */
    public void cleanJobLog();
}
