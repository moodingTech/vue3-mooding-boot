package cn.mooding.modules.quartz.mapper;

import cn.mooding.modules.quartz.entity.SysJobLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 定时任务调度日志表 Mapper 接口
 * </p>
 *
 * @author BlueFire
 * @since 2021-07-26
 */
public interface SysJobLogMapper extends BaseMapper<SysJobLog> {


    /**
     * 清空任务日志
     */
    @Update(" truncate table t_sys_job_log ")
    public void cleanJobLog();
}
