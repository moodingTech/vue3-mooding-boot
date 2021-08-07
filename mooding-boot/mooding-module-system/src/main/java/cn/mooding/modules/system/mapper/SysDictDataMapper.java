package cn.mooding.modules.system.mapper;

import cn.mooding.modules.system.entity.SysDictData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 字典数据表 Mapper 接口
 * </p>
 *
 * @author BlueFire
 * @since 2021-05-14
 */
public interface SysDictDataMapper extends BaseMapper<SysDictData> {

    /**
     * 同步修改字典类型
     *
     * @param oldDictType 旧字典类型
     * @param newDictType 新旧字典类型
     * @return 结果
     */
    @Select("update t_sys_dict_data set dict_type = #{newDictType} where dict_type = #{oldDictType}")
    public int updateDictDataType(@Param("oldDictType") String oldDictType, @Param("newDictType") String newDictType);
}
