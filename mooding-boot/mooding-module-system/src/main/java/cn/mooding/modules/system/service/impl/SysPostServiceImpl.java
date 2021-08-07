package cn.mooding.modules.system.service.impl;

import cn.mooding.common.model.constant.CommonConstant;
import cn.mooding.common.utils.string.StringUtils;
import cn.mooding.modules.system.entity.SysMenu;
import cn.mooding.modules.system.entity.SysPost;
import cn.mooding.modules.system.mapper.SysPostMapper;
import cn.mooding.modules.system.service.ISysPostService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 岗位信息表 服务实现类
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-21
 */
@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements ISysPostService {
    /**
     * 分页查询岗位信息
     *
     * @param page         分页信息
     * @param queryWrapper 查询信息
     * @return
     */
    @Override
    public IPage<SysPost> selectMapsPage(Page<SysPost> page, QueryWrapper<SysPost> queryWrapper) {
        Page<SysPost> sysPostPage = baseMapper.selectPage(page, queryWrapper);
        return sysPostPage;
    }
    /**
     * 校验岗位名称是否唯一
     *
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public String checkPostNameUnique(SysPost post)
    {
        Long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
        QueryWrapper<SysPost> wrapper = new QueryWrapper<SysPost>();
        wrapper.lambda().eq(SysPost::getPostName,post.getPostName()).eq(SysPost::getDelFlag,0);
        SysPost info = baseMapper.selectOne(wrapper);
        if (StringUtils.isNotNull(info) && info.getPostId().longValue() != postId.longValue())
        {
            return CommonConstant.NOT_UNIQUE;
        }
        return CommonConstant.UNIQUE;
    }

    /**
     * 校验岗位编码是否唯一
     *
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public String checkPostCodeUnique(SysPost post)
    {
        Long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
        QueryWrapper<SysPost> wrapper = new QueryWrapper<SysPost>();
        wrapper.lambda().eq(SysPost::getPostCode,post.getPostCode()).eq(SysPost::getDelFlag,0);
        SysPost info = baseMapper.selectOne(wrapper);
        if (StringUtils.isNotNull(info) && info.getPostId().longValue() != postId.longValue())
        {
            return CommonConstant.NOT_UNIQUE;
        }
        return CommonConstant.UNIQUE;
    }
    /**
     * 查询所有岗位
     *
     * @return 岗位列表
     */
    @Override
    public List<SysPost> selectPostAll()
    {
        QueryWrapper<SysPost> wrapper = new QueryWrapper<SysPost>();
        wrapper.lambda().eq(SysPost::getDelFlag,0);
        List<SysPost> sysPosts = baseMapper.selectList(wrapper);
        return sysPosts;
    }
}
