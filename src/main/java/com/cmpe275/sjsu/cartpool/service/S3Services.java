package com.cmpe275.sjsu.cartpool.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3Services {
    public String uploadFile(MultipartFile multipartFile);
    public String deleteFile(String fileUrl);
}
