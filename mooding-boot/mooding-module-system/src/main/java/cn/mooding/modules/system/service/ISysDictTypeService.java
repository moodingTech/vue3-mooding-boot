package cn.mooding.modules.system.service;

import cn.mooding.modules.system.entity.SysDictData;
import cn.mooding.modules.system.entity.SysDictType;
import cn.mooding.modules.system.entity.SysPost;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 字典类型表 服务类
 * </p>
 *
 * @author BlueFire
 * @since 2021-05-14
 */
public interface ISysDictTypeService extends IService<SysDictType> {
    /**
     * 根据条件分页查询用户列表
     *
     * @param   page 分页信息
     * @return 字典类型表集合信息
     */
    public IPage<SysDictType> selectMapsPage(Page<SysDictType> page, QueryWrapper<SysDictType> queryWrapper);
    /**
     * 校验字典类型称是否唯一
     *
     * @param dictType 字典类型
     * @return 结果
     */
    public String checkDictTypeUnique(SysDictType dictType);
    /**
     * 清空缓存数据
     */
    public void clearCache();
    /**
     * 根据所有字典类型
     *
     * @return 字典类型集合信息
     */
    public List<SysDictType> selectDictTypeAll();

    /**
     * 清空缓存数据
     * @param dictType 字典类型
     */
    public void clearCache(String dictType);

    /**
     * 修改保存字典类型信息
     *
     * @param dictType 字典类型信息
     * @return 结果
     */
    public int updateDictType(SysDictType dictType);

}
