package cn.mooding.modules.system.mapper;

import cn.mooding.modules.system.entity.SysDictType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 字典类型表 Mapper 接口
 * </p>
 *
 * @author BlueFire
 * @since 2021-05-14
 */
public interface SysDictTypeMapper extends BaseMapper<SysDictType> {
    /**
     * 根据所有字典类型
     *
     * @return 字典类型集合信息
     */
    @Select("select dict_id, dict_name, dict_type, status, create_by, create_time, remark    from t_sys_dict_type ")
    public List<SysDictType> selectDictTypeAll();
}
