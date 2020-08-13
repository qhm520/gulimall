package com.qian.gulimall.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Base64Utils;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * Base64ToMultipart is Base64转换成File
 *
 * @author QIAN
 * Date 2020/08/12
 * Time 12:09
 */
public class Base64ToMultipart {

    /**
     * Base64转换成图片
     * @param base64
     * @return
     */
    public static R base64ToMultipart(String base64) {
        try {
            String dataPrix = "";
            String data = "";
            if(StringUtils.isBlank(base64)){
                return R.error("上传失败，上传图片数据为空");
            }else{
                String [] d = base64.split("base64,");
                if(d != null && d.length == 2){
                    dataPrix = d[0];
                    data = d[1];
                }else{
                    return R.error("上传失败，数据不合法");
                }
            }
            String suffix = "";
            if("data:image/jpeg;".equalsIgnoreCase(dataPrix)){//data:image/jpeg;base64,base64编码的jpeg图片数据
                suffix = "jpg";
            } else if("data:image/png;".equalsIgnoreCase(dataPrix)){//data:image/png;base64,base64编码的png图片数据
                suffix = "png";
            } else if("data:image/gif;".equalsIgnoreCase(dataPrix)){//data:image/gif;base64,base64编码的gif图片数据
                suffix = "gif";
            } else{
                return R.error("上传图片格式不合法");
            }

            byte[] imageByte = Base64Utils.decodeFromString(data);
            BASE64DecodedMultipartFile multipartFile = new BASE64DecodedMultipartFile(imageByte, dataPrix);
            return R.ok().put("multipartFile", multipartFile);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("base64转转图片失败");
        }
    }
}
