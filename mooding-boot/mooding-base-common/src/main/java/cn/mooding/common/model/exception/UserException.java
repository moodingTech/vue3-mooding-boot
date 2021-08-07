package cn.mooding.common.model.exception;

/**
 * 用户信息异常类
 * @Author BlueFire
 * @Date 23/3/2021 -下午10:33
 */
public class UserException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args)
    {
        super("user", code, args, null);
    }
}

