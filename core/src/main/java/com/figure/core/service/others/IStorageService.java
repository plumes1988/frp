package com.figure.core.service.others;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Map;

public interface IStorageService {
    Map<String, Object> store(MultipartFile file, String uploadPath);

    Map<String, Object> store(InputStream dataStream, String uploadPath, String fileName);
}
