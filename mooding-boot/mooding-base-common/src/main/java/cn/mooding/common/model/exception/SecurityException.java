package cn.mooding.common.model.exception;

/**
 * 自定义安全异常
 * @Author BlueFire
 * @Date 22/3/2021 -上午8:18
 */
public class SecurityException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    private Integer code;

    private String message;

    public SecurityException(String message)
    {
        this.message = message;
    }

    public SecurityException(String message, Integer code)
    {
        this.message = message;
        this.code = code;
    }

    public SecurityException(String message, Throwable e)
    {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return message;
    }

    public Integer getCode()
    {
        return code;
    }
}

