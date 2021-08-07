package cn.mooding.modules.quartz.service.impl;

import cn.mooding.common.model.exception.TaskException;
import cn.mooding.modules.quartz.entity.SysJob;
import cn.mooding.modules.quartz.entity.SysJobLog;
import cn.mooding.modules.quartz.mapper.SysJobLogMapper;
import cn.mooding.modules.quartz.mapper.SysJobMapper;
import cn.mooding.modules.quartz.service.ISysJobLogService;
import cn.mooding.modules.quartz.utils.ScheduleUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * <p>
 * 定时任务调度日志表 服务实现类
 * </p>
 *
 * @author BlueFire
 * @since 2021-07-26
 */
@Service
public class SysJobLogServiceImpl extends ServiceImpl<SysJobLogMapper, SysJobLog> implements ISysJobLogService {


    /**
     * 分页查询定时任务日志
     *
     * @param page         分页信息
     * @param queryWrapper 查询信息
     * @return
     */
    @Override
    public IPage<SysJobLog> selectMapsPage(Page<SysJobLog> page, QueryWrapper<SysJobLog> queryWrapper) {
        Page<SysJobLog> configPage = baseMapper.selectPage(page, queryWrapper);
        return configPage;
    }


    /**
     * 新增任务日志
     *
     * @param jobLog 调度日志信息
     */
    @Override
    public void addJobLog(SysJobLog jobLog) {
        baseMapper.insert(jobLog);
    }
    /**
     * 清空任务日志
     */
    @Override
    public void cleanJobLog()
    {
        baseMapper.cleanJobLog();
    }
}
