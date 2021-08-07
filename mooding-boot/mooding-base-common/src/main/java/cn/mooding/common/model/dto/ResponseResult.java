package cn.mooding.common.model.dto;

import cn.mooding.common.model.enums.HttpCodeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 通用的结果返回类
 *
 * @Author BlueFire
 * @Date 21/3/2021 -上午9:20
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "接口返回对象", description = "接口返回对象")
public class ResponseResult<T> implements Serializable {
    /**
     * 成功标志
     */
    @ApiModelProperty(value = "成功标志")
    private boolean success = true;
    /**
     * 返回代码
     */
    @ApiModelProperty(value = "返回代码")
    private Integer code;
    /**
     * 返回处理消息
     */
    @ApiModelProperty(value = "返回处理消息")
    private String message = "操作成功！";

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * 时间戳
     */
    @ApiModelProperty(value = "时间戳")
    private long timestamp = System.currentTimeMillis();

    private T data;

    public ResponseResult() {
        this.success = true;
        this.code = 200;
    }

    public ResponseResult(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public static ResponseResult errorResult(int code, String msg) {
        ResponseResult result = new ResponseResult();
        result.success = false;
        return result.error(code, msg);
    }

    public static ResponseResult okResult(int code, String msg) {
        ResponseResult result = new ResponseResult();
        result.success = true;
        return result.ok(code, null, msg);
    }

    public static ResponseResult okResult(Object data) {
        ResponseResult result = setHttpCodeEnum(HttpCodeEnum.SUCCESS, HttpCodeEnum.SUCCESS.getMessage());
        if (data != null) {
            result.setData(data);
        }
        result.success = true;
        return result;
    }

    public static ResponseResult okResult() {
        ResponseResult result = setHttpCodeEnum(HttpCodeEnum.SUCCESS, HttpCodeEnum.SUCCESS.getMessage());
        result.success = true;
        return result;
    }

    public static ResponseResult errorResult(HttpCodeEnum enums) {
        return setHttpCodeEnum(enums, enums.getMessage(), false);
    }

    public static ResponseResult errorResult(HttpCodeEnum enums, String message) {
        return setHttpCodeEnum(enums, message, false);
    }

    public static ResponseResult setHttpCodeEnum(HttpCodeEnum enums) {
        return okResult(enums.getCode(), enums.getMessage());
    }

    private static ResponseResult setHttpCodeEnum(HttpCodeEnum enums, String message) {
        return okResult(enums.getCode(), message);
    }

    private static ResponseResult setHttpCodeEnum(HttpCodeEnum enums, String message, boolean success) {
        return okResult(enums.getCode(), message);
    }

    public ResponseResult<?> error(Integer code, String msg) {
        this.code = code;
        this.message = msg;
        this.success = false;
        return this;
    }

    public ResponseResult<?> ok(Integer code, T data) {
        this.code = code;
        this.data = data;
        this.success = true;
        return this;
    }

    public ResponseResult<?> ok(Integer code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.message = msg;
        this.success = true;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}