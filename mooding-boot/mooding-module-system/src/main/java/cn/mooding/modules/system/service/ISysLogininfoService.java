package cn.mooding.modules.system.service;

import cn.mooding.modules.system.entity.SysLogininfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统访问记录 服务类
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-26
 */
public interface ISysLogininfoService extends IService<SysLogininfo> {
    /**
     * 新增系统登录日志
     *
     * @param logininfo 访问日志对象
     */
    public void insertLogininfo(SysLogininfo logininfo);

    /**
     * 清空系统登录日志
     */
    public void cleanLogininfor();

}
