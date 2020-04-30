package com.qian.gulimall.common.security;

import com.google.gson.Gson;
import com.qian.gulimall.common.entity.vo.LoginInfoVo;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by IntelliJ IDEA.
 * RequestHandleUtils is 请求内容处理工具类
 *
 * @author QIAN
 * Date 2020/04/20
 * Time 16:24
 */
public class RequestHandleUtils {

    /**
     * 获取登录post提交的数据
     *
     * @param req
     * @return
     */
    public static String loginInfo(HttpServletRequest req) {
        try {
            StringBuffer sb = new StringBuffer();
            InputStream is = req.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String s = "";
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 登录信息
     * @param request
     * @return
     */
    public static LoginInfoVo convertToLoginInfoVo(HttpServletRequest request) {
        String loginInfo = loginInfo(request);
        if (StringUtils.isNotBlank(loginInfo)) {
            return new Gson().fromJson(loginInfo, LoginInfoVo.class);
        }
        return null;
    }
}
