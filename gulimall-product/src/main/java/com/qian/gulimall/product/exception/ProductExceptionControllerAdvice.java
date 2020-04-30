package com.qian.gulimall.product.exception;

import com.qian.gulimall.common.exception.GulimallAuthenticationException;
import com.qian.gulimall.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * AdminExceptionControllerAdvice is 集中处理所有异常
 *
 * @author QIAN
 * Date 2020/04/18
 * Time 17:42
 */
@Slf4j
@RestControllerAdvice
public class ProductExceptionControllerAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("数据校验出现问题{}，异常类型：{}", e.getMessage(), e.getClass());
        BindingResult bindingResult = e.getBindingResult();

        Map <String, String> errorMap = new HashMap <>();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return R.error(ProductEnum.VAILD_EXCEPTION.getCode(), ProductEnum.VAILD_EXCEPTION.getMsg()).put("data", errorMap);
    }

    @ExceptionHandler(value = Throwable.class)
    public R handleException(Throwable throwable) {

        log.error("错误：", throwable);
        return R.error(ProductEnum.UNKNOW_EXCEPTION.getCode(), ProductEnum.UNKNOW_EXCEPTION.getMsg());
    }


    /**
     * NoHandlerFoundException 404 异常处理
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public R handlerNoHandlerFoundException(NoHandlerFoundException exception) {
        return R.error(ProductEnum.NOT_FOUND.getCode(), ProductEnum.NOT_FOUND.getMsg());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public R handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return R.error("数据库中已存在该记录");
    }

    @ExceptionHandler(GulimallAuthenticationException.class)
    public R handleGulimallAuthenticationException(GulimallAuthenticationException e) {
        log.error(e.getMessage(), e);
        return R.error(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public R handleAccessDeniedException(AccessDeniedException e) {
        log.error(e.getMessage(), e);
        return R.error(ProductEnum.SIGNATURE_NOT_MATCH.getCode(), "没有权限访问");
    }

    /**
     * Exception 类捕获 500 异常处理
     */
    @ExceptionHandler(value = Exception.class)
    public R handlerException(Exception e) {
        log.error(e.getMessage(), e);
        return R.error(ProductEnum.INTERNAL_SERVER_ERROR.getCode(), ProductEnum.INTERNAL_SERVER_ERROR.getMsg());
    }
}
