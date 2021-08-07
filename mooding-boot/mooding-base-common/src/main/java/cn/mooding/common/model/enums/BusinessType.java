package cn.mooding.common.model.enums;

/**
 * 业务操作类型
 *
 * @Author BlueFire
 * @Date 26/3/2021-上午8:51
 */
public enum BusinessType {
    // 其它
    OTHER,
    // 新增
    INSERT,
    // 修改
    UPDATE,
    // 删除
    DELETE,
    // 授权
    GRANT,
    // 导出
    EXPORT,
    // 导入
    IMPORT,
    // 强退
    FORCE,
    // 生成代码
    GENCODE,
    // 清空数据
    CLEAN,
    // 查询
    SELECT,
}
