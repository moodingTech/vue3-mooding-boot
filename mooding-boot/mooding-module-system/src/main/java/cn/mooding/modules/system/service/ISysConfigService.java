package cn.mooding.modules.system.service;

import cn.mooding.modules.system.entity.SysConfig;
import cn.mooding.modules.system.entity.SysPost;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 参数配置表 服务类
 * </p>
 *
 * @author BlueFire
 * @since 2021-07-25
 */
public interface ISysConfigService extends IService<SysConfig> {
    /**
     * 根据条件分页查询参数配置
     *
     * @param page 分页信息
     * @return 参数配置集合信息
     */
    public IPage<SysConfig> selectMapsPage(Page<SysConfig> page, QueryWrapper<SysConfig> queryWrapper);

    /**
     * 修改参数配置
     *
     * @param config 参数配置信息
     * @return 结果
     */
    public int updateConfig(SysConfig config);

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数键名
     * @return 参数键值
     */
    public String selectConfigByKey(String configKey);

    /**
     * 校验参数键名是否唯一
     *
     * @param config 参数信息
     * @return 结果
     */
    public String checkConfigKeyUnique(SysConfig config);

    /**
     * 清空缓存数据
     */
    public void clearCache();
}
