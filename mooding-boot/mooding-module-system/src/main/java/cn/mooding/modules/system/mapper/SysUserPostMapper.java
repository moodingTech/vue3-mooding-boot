package cn.mooding.modules.system.mapper;

import cn.mooding.modules.system.entity.SysUserPost;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户与岗位关联表 Mapper 接口
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-21
 */
public interface SysUserPostMapper extends BaseMapper<SysUserPost> {
    /**
     * 批量新增用户岗位信息
     *
     * @param userPostList 用户角色列表
     * @return 结果
     */
    public int batchUserPost(List<SysUserPost> userPostList);

    /**
     * 通过用户ID删除用户和岗位关联
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Delete("delete from t_sys_user_post where  user_id =#{userId} ")
    public int deleteUserPostByUserId(@Param("userId") Long userId);
}
