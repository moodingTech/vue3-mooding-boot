package cn.mooding.modules.system.mapper;

import cn.mooding.modules.system.entity.SysLogininfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 系统访问记录 Mapper 接口
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-26
 */
public interface SysLogininfoMapper extends BaseMapper<SysLogininfo> {

    /**
     * 清空系统登录日志
     *
     * @return 结果
     */
    @Update(" truncate table t_sys_logininfo ")
    public int cleanLogininfor();
}
