package com.qian.gulimall.admin.exception;

/***
 * 错误码和错误信息定义类
 * 1. 错误码定义规则为5为数字
 * 2. 前两位表示业务场景，最后三位表示错误码。例如：100001。10:通用 001:系统未知异常
 * 3. 维护错误码后需要维护错误描述，将他们定义为枚举形式
 * 错误码列表：
 *  10: 通用
 *      001：参数格式校验
 *  11: 商品
 *  12: 订单
 *  13: 购物车
 *  14: 物流
 *
 *
 */
public enum AdminEnum {

    // 数据操作错误定义
    SUCCESS(10200, "成功!"),

    BODY_NOT_MATCH(10400,"请求的数据格式不符!"),

    SIGNATURE_NOT_MATCH(10401,"请求的数字签名不匹配!"),

    NOT_FOUND(10404, "未找到该资源!"),

    INTERNAL_SERVER_ERROR(10500, "服务器内部错误!"),

    SERVER_BUSY(10503,"服务器正忙，请稍后再试!"),

    UNKNOW_EXCEPTION(10000,"系统未知异常"),

    VAILD_EXCEPTION(10001,"参数格式校验失败");


    private int code;

    private String msg;

    AdminEnum(int code, String msg){
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
