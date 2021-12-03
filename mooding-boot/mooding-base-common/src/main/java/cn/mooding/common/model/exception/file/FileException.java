package cn.mooding.common.model.exception.file;

import cn.mooding.common.model.exception.BaseException;

/**
 * 文件信息异常类
 * @Author BlueFire
 * @Date 28/8/2021 -上午9:57
 */
public class FileException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args)
    {
        super("file", code, args, null);
    }

}
