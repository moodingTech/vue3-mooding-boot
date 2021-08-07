package cn.mooding.modules.quartz.mapper;

import cn.mooding.modules.quartz.entity.SysJob;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 定时任务调度表 Mapper 接口
 * </p>
 *
 * @author BlueFire
 * @since 2021-07-26
 */
public interface SysJobMapper extends BaseMapper<SysJob> {

    /**
     * 查询所有调度任务
     *
     * @return 调度任务列表
     */
    @Select("select * from t_sys_job ")
    public List<SysJob> selectJobAll();
}
