package com.filesystem.filesystemdemo.service;

import com.filesystem.filesystemdemo.pojo.File;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    public File storeFile(MultipartFile file,String uuid);

    public File getFile(String id);

    public Boolean deleteFile(String id);

    public Boolean updateFile(MultipartFile file, String id);
}

