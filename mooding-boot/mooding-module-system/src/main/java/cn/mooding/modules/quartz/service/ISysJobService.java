package cn.mooding.modules.quartz.service;

import cn.mooding.common.model.exception.TaskException;
import cn.mooding.modules.quartz.entity.SysJob;
import cn.mooding.modules.system.entity.SysConfig;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.quartz.SchedulerException;

/**
 * <p>
 * 定时任务调度表 服务类
 * </p>
 *
 * @author BlueFire
 * @since 2021-07-26
 */
public interface ISysJobService extends IService<SysJob> {
    /**
     * 根据条件分页查询定时任务信息
     *
     * @param page 分页信息
     * @return 定时任务集合信息
     */
    public IPage<SysJob> selectMapsPage(Page<SysJob> page, QueryWrapper<SysJob> queryWrapper);

    /**
     * 新增任务
     *
     * @param job 调度信息
     * @return 结果
     */
    public int insertJob(SysJob job) throws SchedulerException, TaskException;

    /**
     * 更新任务
     *
     * @param job 调度信息
     * @return 结果
     */
    public int updateJob(SysJob job) throws SchedulerException, TaskException;

    /**
     * 任务调度状态修改
     *
     * @param job 调度信息
     * @return 结果
     */
    public int changeStatus(SysJob job) throws SchedulerException;

    /**
     * 暂停任务
     *
     * @param job 调度信息
     * @return 结果
     */
    public int pauseJob(SysJob job) throws SchedulerException;

    /**
     * 恢复任务
     *
     * @param job 调度信息
     * @return 结果
     */
    public int resumeJob(SysJob job) throws SchedulerException;

    /**
     * 立即运行任务
     *
     * @param job 调度信息
     * @return 结果
     */
    public void run(SysJob job) throws SchedulerException;

    /**
     * 批量删除调度信息
     *
     * @param jobIds 需要删除的任务ID
     * @return 结果
     */
    public void deleteJobByIds(Long[] jobIds) throws SchedulerException;

    /**
     * 删除任务后，所对应的trigger也将被删除
     *
     * @param job 调度信息
     * @return 结果
     */
    public int deleteJob(SysJob job) throws SchedulerException;
}
