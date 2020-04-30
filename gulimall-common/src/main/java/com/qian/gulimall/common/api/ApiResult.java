package com.qian.gulimall.common.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * <p>
 * <b>ApiResult</b> 基类，所有返回对象应该要继承。
 * </p>
 *
 * @author QIAN
 * @version $Id$
 * @since 2018-01-17
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class ApiResult<T> implements Serializable {

    public static final Logger logger = LoggerFactory.getLogger(ApiResult.class);

    public static final ApiResult SUCCESS = new ApiResult <Void>(true, "成功");

    private boolean success;

    private String description;

    private T data;

    private ApiResult() {
    }

    private ApiResult(boolean success, String description) {
        this.success = success;
        this.description = description;
    }

    private ApiResult(boolean success, String description, T data) {
        this.success = success;
        this.description = description;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static ApiResult <Void> sucess() {
        return SUCCESS;
    }

    public static <T> ApiResult <T> success(T data) {
        return new ApiResult <>(true, "成功", data);
    }

    public static <T> ApiResult <T> fail(String description) {
        if (logger.isErrorEnabled()) {
            logger.error("调用失败：{}", description);
        }
        return new ApiResult(false, description);
    }
}
