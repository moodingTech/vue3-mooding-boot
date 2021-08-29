package cn.mooding.common.model.exception.file;

/**
 * 文件名大小限制异常类
 * @Author BlueFire
 * @Date 28/8/2021 -上午9:56
 */
public class FileSizeLimitExceededException extends FileException
{
    private static final long serialVersionUID = 1L;

    public FileSizeLimitExceededException(long defaultMaxSize)
    {
        super("upload.exceed.maxSize", new Object[] { defaultMaxSize });
    }
}