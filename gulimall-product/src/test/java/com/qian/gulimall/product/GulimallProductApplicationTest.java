//package com.qian.gulimall.product;
//
//import com.aliyun.oss.OSS;
//import com.aliyun.oss.OSSClientBuilder;
//import com.aliyun.oss.model.PutObjectRequest;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.io.ByteArrayInputStream;
//import java.io.FileInputStream;
//import java.io.InputStream;
//
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class GulimallProductApplicationTest {
//
//    @Test
//    public void testUpload() throws Exception {
//        // Endpoint以杭州为例，其它Region请按实际情况填写。
//        String endpoint = "oss-cn-heyuan.aliyuncs.com";
//        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
//        String accessKeyId = "LTAI4G1ZuhzjxUjcuuzi9xRt";
//        String accessKeySecret = "i0cmdrqOV6LxpWAfkWAb20UPVQ0Zyb";
//
//        // 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//
//        // 创建PutObjectRequest对象。
//        String content = "Hello OSS";
//        // <yourObjectName>表示上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
//        PutObjectRequest putObjectRequest = new PutObjectRequest("<yourBucketName>",
//                "<yourObjectName>", new ByteArrayInputStream(content.getBytes()));
//
//// 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
//// ObjectMetadata metadata = new ObjectMetadata();
//// metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
//// metadata.setObjectAcl(CannedAccessControlList.Private);
//// putObjectRequest.setMetadata(metadata);
//
//        InputStream inputStream = new FileInputStream("E:\\photos\\风云\\20200415084459.jpg");
//        ossClient.putObject("gulimall-202008", "qian.jpg", inputStream);
//
//            // 上传字符串。
////        ossClient.putObject(putObjectRequest);
//
//        // 关闭OSSClient。
//        ossClient.shutdown();
//    }
//
//}