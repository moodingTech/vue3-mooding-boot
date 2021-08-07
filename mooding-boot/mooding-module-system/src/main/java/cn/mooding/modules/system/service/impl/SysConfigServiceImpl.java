package cn.mooding.modules.system.service.impl;

import cn.mooding.common.model.constant.CommonConstant;
import cn.mooding.common.model.constant.Constants;
import cn.mooding.common.utils.redis.RedisCache;
import cn.mooding.common.utils.string.Convert;
import cn.mooding.common.utils.string.StringUtils;
import cn.mooding.modules.system.entity.SysConfig;
import cn.mooding.modules.system.entity.SysPost;
import cn.mooding.modules.system.mapper.SysConfigMapper;
import cn.mooding.modules.system.service.ISysConfigService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * <p>
 * 参数配置表 服务实现类
 * </p>
 *
 * @author BlueFire
 * @since 2021-07-25
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements ISysConfigService {

    @Autowired
    private RedisCache redisCache;

    /**
     * 分页查询参数配置信息
     *
     * @param page         分页信息
     * @param queryWrapper 查询信息
     * @return
     */
    @Override
    public IPage<SysConfig> selectMapsPage(Page<SysConfig> page, QueryWrapper<SysConfig> queryWrapper) {
        Page<SysConfig> configPage = baseMapper.selectPage(page, queryWrapper);
        return configPage;
    }
    /**
     * 修改参数配置
     *
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public int updateConfig(SysConfig config)
    {
        int row = baseMapper.updateById(config);
        if (row > 0)
        {
            redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
        return row;
    }

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数key
     * @return 参数键值
     */
    @Override
    public String selectConfigByKey(String configKey) {
        String configValue = Convert.toStr(redisCache.getCacheObject(getCacheKey(configKey)));
        if (StringUtils.isNotEmpty(configValue)) {
            return configValue;
        }

        QueryWrapper<SysConfig> wrapper = new QueryWrapper<SysConfig>();
        wrapper.lambda().eq(SysConfig::getConfigKey, configKey);

        SysConfig retConfig = baseMapper.selectOne(wrapper);
        if (StringUtils.isNotNull(retConfig)) {
            redisCache.setCacheObject(getCacheKey(configKey), retConfig.getConfigValue());
            return retConfig.getConfigValue();
        }
        return StringUtils.EMPTY;
    }

    /**
     * 校验参数键名是否唯一
     *
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public String checkConfigKeyUnique(SysConfig config) {
        Long configId = StringUtils.isNull(config.getConfigId()) ? -1L : config.getConfigId();
        SysConfig info = baseMapper.checkConfigKeyUnique(config.getConfigKey());
        if (StringUtils.isNotNull(info) && info.getConfigId().longValue() != configId.longValue()) {
            return CommonConstant.NOT_UNIQUE;
        }
        return CommonConstant.UNIQUE;
    }

    /**
     * 清空缓存数据
     */
    @Override
    public void clearCache() {
        Collection<String> keys = redisCache.keys(Constants.SYS_CONFIG_KEY + "*");
        redisCache.deleteObject(keys);
    }

    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    private String getCacheKey(String configKey) {
        return Constants.SYS_CONFIG_KEY + configKey;
    }


    /**
     * 删除cache数据
     *
     * @param configKey 参数键
     */
    private void deleteCacheObject(String configKey) {
        redisCache.deleteObject(Constants.SYS_CONFIG_KEY + configKey);
    }

}
