package com.qian.gulimall.security.controller;

import com.qian.gulimall.security.validate.image.ImageCodeProcessor;
import com.qian.gulimall.security.validate.sms.SmsCodeProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * ValidateCodeController is
 *
 * @author QIAN
 * Date 2020/04/18
 * Time 16:43
 */
@Slf4j
@RestController
public class ValidateCodeController {

    @Autowired
    private ImageCodeProcessor imageCodeProcessor;

    @Autowired
    private SmsCodeProcessor smsCodeProcessor;


    @GetMapping("/code/image")
    public void createImageCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        imageCodeProcessor.process(new ServletWebRequest(request, response));
    }

    @GetMapping(value = "/code/sms", params = {"mobile"})
    public void createSmsCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        smsCodeProcessor.process(new ServletWebRequest(request, response));
    }
}
