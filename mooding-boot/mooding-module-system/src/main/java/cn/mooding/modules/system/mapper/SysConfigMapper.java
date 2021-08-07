package cn.mooding.modules.system.mapper;

import cn.mooding.modules.system.entity.SysConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 参数配置表 Mapper 接口
 * </p>
 *
 * @author BlueFire
 * @since 2021-07-25
 */
public interface SysConfigMapper extends BaseMapper<SysConfig> {

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数键名
     * @return 参数配置信息
     */
    @Select("select * from t_sys_config where config_key=#{configKey}")
    public SysConfig checkConfigKeyUnique(@Param("configKey") String configKey);
}
