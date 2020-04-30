package com.qian.gulimall.product.exception;

/**
 * Created by IntelliJ IDEA.
 * ProductEnum is 产品枚举
 * 产品异常码：14000
 * @author QIAN
 * Date 2020/04/27
 * Time 08:21
 */
public enum ProductEnum {
    // 数据操作错误定义
    SUCCESS(14200, "成功!"),

    BODY_NOT_MATCH(14400,"请求的数据格式不符!"),

    SIGNATURE_NOT_MATCH(14401,"请求的数字签名不匹配!"),

    NOT_FOUND(14404, "未找到该资源!"),

    INTERNAL_SERVER_ERROR(14500, "服务器内部错误!"),

    SERVER_BUSY(14503,"服务器正忙，请稍后再试!"),

    UNKNOW_EXCEPTION(14000,"系统未知异常"),

    VAILD_EXCEPTION(14001,"参数格式校验失败");

    private int code;

    private String msg;

    ProductEnum(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
