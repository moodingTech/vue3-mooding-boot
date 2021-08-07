package cn.mooding.modules.system.service;

import cn.mooding.modules.system.entity.SysDictData;
import cn.mooding.modules.system.entity.SysDictType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 字典数据表 服务类
 * </p>
 *
 * @author BlueFire
 * @since 2021-05-14
 */
public interface ISysDictDataService extends IService<SysDictData> {
    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    public List<SysDictData> selectDictDataByType(String dictType);
    /**
     * 根据条件分页查询字典数据
     *
     * @param page 字典数据信息
     * @return 字典数据集合信息
     */
    IPage<SysDictData> selectMapsPage(Page<SysDictData> page, QueryWrapper<SysDictData> queryWrapper);
    /**
     * 修改保存字典数据信息
     *
     * @param dictData 字典数据信息
     * @return 结果
     */
    public int updateDictData(SysDictData dictData);
}
