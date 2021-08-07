package cn.mooding.common.model.constant;

/**
 * 缓存常量
 * @Author BlueFire
 * @Date 21/3/2021 -下午4:45
 */
public class CacheConstant {
    /**
     * 字典信息缓存
     */
    public static final String SYS_DICT_CACHE = "sys:cache:dict";
    /**
     * 表字典信息缓存
     */
    public static final String SYS_DICT_TABLE_CACHE = "sys:cache:dictTable";

    /**
     * 数据权限配置缓存
     */
    public static final String SYS_DATA_PERMISSIONS_CACHE = "sys:cache:permission:datarules";

    /**
     * 缓存用户信息
     */
    public static final String SYS_USERS_CACHE = "sys:cache:user";

    /**
     * 全部部门信息缓存
     */
    public static final String SYS_DEPARTS_CACHE = "sys:cache:depart:alldata";


    /**
     * 全部部门ids缓存
     */
    public static final String SYS_DEPART_IDS_CACHE = "sys:cache:depart:allids";


    /**
     * 测试缓存key
     */
    public static final String TEST_DEMO_CACHE = "test:demo";

    /**
     * 字典信息缓存
     */
    public static final String SYS_DYNAMICDB_CACHE = "sys:cache:dbconnect:dynamic:";
    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";
    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;
    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";
}
