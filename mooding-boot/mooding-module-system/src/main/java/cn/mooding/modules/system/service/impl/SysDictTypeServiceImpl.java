package cn.mooding.modules.system.service.impl;

import cn.mooding.common.model.constant.CommonConstant;
import cn.mooding.common.utils.redis.RedisCache;
import cn.mooding.common.utils.spring.SpringUtils;
import cn.mooding.common.utils.string.StringUtils;
import cn.mooding.modules.system.entity.SysDictData;
import cn.mooding.modules.system.entity.SysDictType;
import cn.mooding.modules.system.entity.SysMenu;
import cn.mooding.modules.system.entity.SysPost;
import cn.mooding.modules.system.mapper.SysDictDataMapper;
import cn.mooding.modules.system.mapper.SysDictTypeMapper;
import cn.mooding.modules.system.service.ISysDictTypeService;
import cn.mooding.modules.utils.DictUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 字典类型表 服务实现类
 * </p>
 *
 * @author BlueFire
 * @since 2021-05-14
 */
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements ISysDictTypeService {


    @Autowired
    private SysDictDataMapper dictDataMapper;

    /**
     * 分页查询岗位信息
     *
     * @param page         分页信息
     * @param queryWrapper 查询信息
     * @return
     */
    @Override
    public IPage<SysDictType> selectMapsPage(Page<SysDictType> page, QueryWrapper<SysDictType> queryWrapper) {
        Page<SysDictType> sysPostPage = baseMapper.selectPage(page, queryWrapper);
        return sysPostPage;
    }

    /**
     * 校验字典类型称是否唯一
     *
     * @param dict 字典类型
     * @return 结果
     */
    @Override
    public String checkDictTypeUnique(SysDictType dict) {
        Long dictId = StringUtils.isNull(dict.getDictId()) ? -1L : dict.getDictId();
        QueryWrapper<SysDictType> wrapper = new QueryWrapper<SysDictType>();
        wrapper.lambda().eq(SysDictType::getDictType, dict.getDictType());
        SysDictType dictType = baseMapper.selectOne(wrapper);
        if (StringUtils.isNotNull(dictType) && dictType.getDictId().longValue() != dictId.longValue()) {
            return CommonConstant.NOT_UNIQUE;
        }
        return CommonConstant.UNIQUE;
    }

    /**
     * 清空缓存数据
     */
    @Override
    public void clearCache() {
        DictUtils.clearDictCache();
    }

    /**
     * 根据所有字典类型
     *
     * @return 字典类型集合信息
     */
    @Override
    public List<SysDictType> selectDictTypeAll() {
        return baseMapper.selectDictTypeAll();
    }

    /**
     * 清空缓存数据
     *
     * @param dictType 字典类型
     */
    public void clearCache(String dictType) {

        DictUtils.clearDictCache(dictType);
    }

    /**
     * 修改保存字典类型信息
     *
     * @param dictType 字典类型信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateDictType(SysDictType dictType) {
        SysDictType oldDict = baseMapper.selectById(dictType.getDictId());
        dictDataMapper.updateDictDataType(oldDict.getDictType(), dictType.getDictType());
        int row = baseMapper.updateById(dictType);
        if (row > 0) {
            DictUtils.clearDictCache(dictType.getDictType());
        }
        return row;
    }

}
