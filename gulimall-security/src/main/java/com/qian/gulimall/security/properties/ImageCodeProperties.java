package com.qian.gulimall.security.properties;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * ImageCodeProperties is
 *
 * @author QIAN
 * Date 2020/04/18
 * Time 16:56
 */
@Data
public class ImageCodeProperties extends SmsCodeProperties {

    private int width = 80;

    private int height = 45;

    private int fontSize = 40;

    ImageCodeProperties() {
        setLength(4);
    }

}
