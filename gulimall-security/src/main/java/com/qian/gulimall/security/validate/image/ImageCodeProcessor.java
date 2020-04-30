package com.qian.gulimall.security.validate.image;

import com.qian.gulimall.security.validate.AbstractValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * ImageCodeProcessor is
 *
 * @author QIAN
 * Date 2020/04/18
 * Time 17:10
 */

@Component
public class ImageCodeProcessor extends AbstractValidateCodeProcessor <ImageCode, ImageCodeGenerator> {

    @Autowired
    private ImageCodeGenerator imageCodeGenerator;

    @Override
    protected ImageCodeGenerator getValidateCodeGenerator() {
        return imageCodeGenerator;
    }

    @Override
    protected void send(ServletWebRequest request, ImageCode validateCode) throws IOException {
        request.getResponse().addHeader("Content-Type", "image/jpeg");
        ImageIO.write(validateCode.getImage(), "JPEG", request.getResponse().getOutputStream());
    }

}
