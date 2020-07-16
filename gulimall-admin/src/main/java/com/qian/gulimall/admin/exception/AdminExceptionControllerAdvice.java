package com.qian.gulimall.admin.exception;

import com.qian.gulimall.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
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
public class AdminExceptionControllerAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("数据校验出现问题{}，异常类型：{}", e.getMessage(), e.getClass());
        BindingResult bindingResult = e.getBindingResult();

        Map <String, String> errorMap = new HashMap <>();
        bindingResult.getFieldErrors().forEach(fieldError ->
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage())
        );
        return R.error(AdminEnum.VAILD_EXCEPTION.getCode(), AdminEnum.VAILD_EXCEPTION.getMsg()).put("data", errorMap);
    }

    @ExceptionHandler(value = Throwable.class)
    public R handleException(Throwable throwable) {

        log.error("错误：", throwable);
        return R.error(AdminEnum.UNKNOW_EXCEPTION.getCode(), AdminEnum.UNKNOW_EXCEPTION.getMsg());
    }


    /**
     * NoHandlerFoundException 404 异常处理
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public R handlerNoHandlerFoundException(NoHandlerFoundException e) {
        e.printStackTrace();
        return R.error(AdminEnum.NOT_FOUND.getCode(), AdminEnum.NOT_FOUND.getMsg());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public R handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return R.error("数据库中已存在该记录");
    }


 /*   @ExceptionHandler(value = RedisConnectionException.class)
    public R handlerRedisConnectionException(RedisConnectionException e) {
        e.printStackTrace();
//        log.error(e.getMessage());
        return R.error(AdminEnum.INTERNAL_SERVER_ERROR.getCode(), AdminEnum.INTERNAL_SERVER_ERROR.getMsg());
    }

    @ExceptionHandler(value = RedisCommandTimeoutException.class)
    public R handlerRedisCommandTimeoutException(RedisCommandTimeoutException e) {
        e.printStackTrace();
//        log.error(e.getMessage());
        return R.error(AdminEnum.INTERNAL_SERVER_ERROR.getCode(), AdminEnum.INTERNAL_SERVER_ERROR.getMsg());
    }*/


    /**
     * Exception 类捕获 500 异常处理
     */
    @ExceptionHandler(value = Exception.class)
    public R handlerException(Exception e) {
        e.printStackTrace();
//        log.error(e.getMessage());
        return R.error(AdminEnum.INTERNAL_SERVER_ERROR.getCode(), AdminEnum.INTERNAL_SERVER_ERROR.getMsg());
    }
}
