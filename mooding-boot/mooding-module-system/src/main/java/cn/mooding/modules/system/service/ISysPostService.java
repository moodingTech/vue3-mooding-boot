package cn.mooding.modules.system.service;

import cn.mooding.modules.system.entity.SysPost;
import cn.mooding.modules.system.entity.SysUser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 岗位信息表 服务类
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-21
 */
public interface ISysPostService extends IService<SysPost> {
    /**
     * 根据条件分页查询岗位列表
     *
     * @param   page 分页信息
     * @return 岗位信息集合信息
     */
    public IPage<SysPost> selectMapsPage(Page<SysPost> page, QueryWrapper<SysPost> queryWrapper);
    /**
     * 校验岗位名称
     *
     * @param post 岗位信息
     * @return 结果
     */
    public String checkPostNameUnique(SysPost post);

    /**
     * 校验岗位编码
     *
     * @param post 岗位信息
     * @return 结果
     */
    public String checkPostCodeUnique(SysPost post);


    /**
     * 查询所有岗位
     *
     * @return 岗位列表
     */
    public List<SysPost> selectPostAll();
}
