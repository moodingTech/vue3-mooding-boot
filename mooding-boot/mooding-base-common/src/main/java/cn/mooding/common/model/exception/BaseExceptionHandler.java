package cn.mooding.common.model.exception;

import cn.mooding.common.model.dto.ResponseResult;
import cn.mooding.common.model.enums.HttpCodeEnum;
import cn.mooding.common.utils.string.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.connection.PoolException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 全局异常处理器
 *
 * @Author BlueFire
 * @Date 21/3/2021 -上午11:05
 */
@Slf4j
@RestControllerAdvice
public class BaseExceptionHandler {
    /**
     * 处理自定义异常
     */
    @ExceptionHandler(BaseException.class)
    public ResponseResult handleRRException(BaseException e) {
        log.error(e.getMessage(), e);
        return ResponseResult.errorResult(201,e.getMessage());
    }

    /**
     * 路径不存在异常
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseResult handlerNoFoundException(Exception e) {
        log.error(e.getMessage(), e);
        // return Result.error(404, "路径不存在，请检查路径是否正确");
        return ResponseResult.errorResult(HttpCodeEnum.SERVER_ERROR);
    }

    /**
     * 数据库数据存在异常
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseResult handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        // return Result.error("数据库中已存在该记录");
        return ResponseResult.errorResult(HttpCodeEnum.SERVER_ERROR);
    }
    /**
     * 权限异常
     */
/*    @ExceptionHandler({UnauthorizedException.class, AuthorizationException.class,AuthenticationException.class})
    public ResponseResult handleAuthorizationException(AuthorizationException e) {
        log.error(e.getMessage(), e);
        // return Result.noauth("没有权限，请联系管理员授权");
        return ResponseResult.errorResult(HttpCodeEnum.NO_OPERATOR_AUTH);
    }*/

    /**
     * 业务异常
     */
    @ExceptionHandler(SecurityException.class)
    public ResponseResult businessException(SecurityException e) {
        if (StringUtils.isNull(e.getCode())) {
            return ResponseResult.errorResult(500, e.getMessage());
        }
        return ResponseResult.errorResult(e.getCode(), e.getMessage());
    }

    /**
     * spring默认上传大小100MB 超出大小捕获异常MaxUploadSizeExceededException
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseResult handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error(e.getMessage(), e);
        // return Result.error("文件大小超出10MB限制, 请压缩或降低文件质量! ");
        return ResponseResult.errorResult(HttpCodeEnum.SERVER_ERROR);

    }

    /**
     * 段太长,超出数据库字段的长度
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseResult handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error(e.getMessage(), e);
        // return Result.error("字段太长,超出数据库字段的长度");
        return ResponseResult.errorResult(HttpCodeEnum.DATA_TOO_LENGTH);
    }

    /**
     * Redis 连接异常
     */
    @ExceptionHandler(PoolException.class)
    public ResponseResult handlePoolException(PoolException e) {
        log.error(e.getMessage(), e);
        // return Result.error("Redis 连接异常!");
        return ResponseResult.errorResult(HttpCodeEnum.SERVER_ERROR);
    }

    /**
     * 其他异常捕获
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult handleException(Exception e) {
        log.error(e.getMessage(), e);
        // return Result.error("操作失败，"+e.getMessage());
        return ResponseResult.errorResult(HttpCodeEnum.SERVER_ERROR);
    }
}
