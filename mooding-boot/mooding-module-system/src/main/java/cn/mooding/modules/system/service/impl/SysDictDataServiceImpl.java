package cn.mooding.modules.system.service.impl;

import cn.mooding.common.utils.string.StringUtils;
import cn.mooding.modules.system.entity.SysDictData;
import cn.mooding.modules.system.entity.SysDictType;
import cn.mooding.modules.system.mapper.SysDictDataMapper;
import cn.mooding.modules.system.service.ISysDictDataService;
import cn.mooding.modules.utils.DictUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 字典数据表 服务实现类
 * </p>
 *
 * @author BlueFire
 * @since 2021-05-14
 */
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements ISysDictDataService {
    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    @Override
    public List<SysDictData> selectDictDataByType(String dictType)
    {
        List<SysDictData> dictDatas = DictUtils.getDictCache(dictType);
        if (ObjectUtils.isNotEmpty(dictDatas))
        {
            return dictDatas;
        }
        QueryWrapper<SysDictData> wrapper = new QueryWrapper<SysDictData>();
        wrapper.lambda().eq(SysDictData::getStatus, "0");
        wrapper.lambda().eq(SysDictData::getDictType, dictType);
        wrapper.orderByAsc("dict_sort");
        dictDatas = baseMapper.selectList(wrapper);
        if (StringUtils.isNotEmpty(dictDatas))
        {
            DictUtils.setDictCache(dictType, dictDatas);
            return dictDatas;
        }
        return null;
    }
    /**
     * 分页查询岗位信息
     *
     * @param page         分页信息
     * @param queryWrapper 查询信息
     * @return
     */
    @Override
    public IPage<SysDictData> selectMapsPage(Page<SysDictData> page, QueryWrapper<SysDictData> queryWrapper) {
        Page<SysDictData> sysPostPage = baseMapper.selectPage(page, queryWrapper);
        return sysPostPage;
    }
    /**
     * 修改保存字典数据信息
     *
     * @param dictData 字典数据信息
     * @return 结果
     */
    @Override
    public int updateDictData(SysDictData dictData)
    {
        int row = baseMapper.updateById(dictData);
        if (row > 0)
        {
            DictUtils.clearDictCache(dictData.getDictType());
        }
        return row;
    }

}
