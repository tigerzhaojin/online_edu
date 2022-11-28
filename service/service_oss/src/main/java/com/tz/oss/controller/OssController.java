package com.tz.oss.controller;

import com.tz.commonutils.R;
import com.tz.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {
    @Autowired
    private OssService ossService;

    @PostMapping
    public R uploadOssFile(MultipartFile file){
//        返回上传后的路径
        String url= ossService.upLoadAvatar(file);
        return R.ok().data("url",url);
    }
}
