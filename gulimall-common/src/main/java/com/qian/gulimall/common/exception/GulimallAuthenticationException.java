package com.qian.gulimall.common.exception;

/**
 * Created by IntelliJ IDEA.
 * AuthenticationException is
 *
 * @author QIAN
 * Date 2020/04/22
 * Time 13:30
 */
public class GulimallAuthenticationException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;

    public GulimallAuthenticationException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public GulimallAuthenticationException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public GulimallAuthenticationException(int code, String msg) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public GulimallAuthenticationException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
