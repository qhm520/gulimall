package com.qian.gulimall.security.validate.image;

import com.qian.gulimall.security.validate.ValidateCode;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by IntelliJ IDEA.
 * ImageCode is  图片验证码
 *
 * @author QIAN
 * Date 2020/04/18
 * Time 16:28
 */
@Data
public class ImageCode extends ValidateCode implements Serializable {
    private static final long serialVersionUID = 3913724671863834332L;

    public ImageCode(String code, int expireIn) {
        super(code, expireIn);
    }

    /**
     * 图片验证码
     */
    private BufferedImage image;

    public ImageCode(BufferedImage image, String code, int expireIn) {
        super(code, expireIn);
        this.image = image;
    }

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        super(code, expireTime);
        this.image = image;
    }
}
