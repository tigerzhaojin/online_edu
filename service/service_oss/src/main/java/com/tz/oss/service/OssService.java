package com.tz.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    String upLoadAvatar(MultipartFile file);

}
