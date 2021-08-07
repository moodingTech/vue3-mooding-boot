package cn.mooding.modules.system.mapper;

import cn.mooding.modules.system.entity.SysOperLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 操作日志记录 Mapper 接口
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-26
 */
public interface SysOperLogMapper extends BaseMapper<SysOperLog> {

    /**
     * 清空操作日志
     */
    @Update(" truncate table t_sys_oper_log ")
    public void cleanOperLog();
}
