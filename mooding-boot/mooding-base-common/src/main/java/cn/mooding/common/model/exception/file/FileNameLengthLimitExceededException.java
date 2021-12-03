package cn.mooding.common.model.exception.file;

/**
 * 文件名称超长限制异常类
 * @Author BlueFire
 * @Date 28/8/2021 -上午9:58
 */
public class FileNameLengthLimitExceededException extends FileException
{
    private static final long serialVersionUID = 1L;

    public FileNameLengthLimitExceededException(int defaultFileNameLength)
    {
        super("upload.filename.exceed.length", new Object[] { defaultFileNameLength });
    }
}
