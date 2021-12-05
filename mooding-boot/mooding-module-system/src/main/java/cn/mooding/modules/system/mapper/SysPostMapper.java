package cn.mooding.modules.system.mapper;

import cn.mooding.modules.system.entity.SysPost;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 岗位信息表 Mapper 接口
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-21
 */
public interface SysPostMapper extends BaseMapper<SysPost> {

    /**
     * 通过用户ID删除用户和岗位关联
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Select("select a.* from t_sys_post a left join t_sys_user_post b on a.post_id= b.post_id  where a.del_flag<>2 and b.user_id =#{userId} ")
    public List<SysPost> selectSysPostsByUserId(@Param("userId") Long userId);
}
