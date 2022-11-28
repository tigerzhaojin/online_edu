package com.tz.oss.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.tz.oss.service.OssService;
import com.tz.oss.utils.ConstanPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
//    upload avatar to OSS
    @Override
    public String upLoadAvatar(MultipartFile file) {
// Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = ConstanPropertiesUtils.END_POINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ConstanPropertiesUtils.KEY_ID;
        String accessKeySecret =ConstanPropertiesUtils.KEY_SECRET;
        // 填写Bucket名称，例如examplebucket。
        String bucketName = ConstanPropertiesUtils.BUCKET_NAME;
        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
        String objectName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        objectName=uuid+objectName;
        String datePath = new DateTime().toString("yyyy/MM/dd/");
        objectName=datePath+objectName;
        // 填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。
        // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
//        String filePath= "D:\\localpath\\examplefile.txt";

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 创建OSSClient实例。
            InputStream inputStream = file.getInputStream();
            // 创建PutObject请求。
            ossClient.putObject(bucketName, objectName, inputStream);
            ossClient.shutdown();
            String url="";
//            https://online-edu-tz.oss-cn-beijing.aliyuncs.com/20060219003.jpg
            url="https://"+bucketName+"."+endpoint+"/"+objectName;
            return url;
        } catch (Exception e) {
            ossClient.shutdown();
            return null;
        }

    }
}
