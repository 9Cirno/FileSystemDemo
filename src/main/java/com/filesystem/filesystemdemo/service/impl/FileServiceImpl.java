package com.filesystem.filesystemdemo.service.impl;

import com.filesystem.filesystemdemo.pojo.File;
import com.filesystem.filesystemdemo.repository.FileRepository;
import com.filesystem.filesystemdemo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Transactional
    public File storeFile(MultipartFile file, String uuid) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            // Check if the file's name contains invalid characters
//            if(fileName.contains("..")) {
//                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + fileName);

            File dbFile = new File(uuid, fileName, file.getContentType(), file.getBytes());

            return fileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Transactional
    public File getFile(String id) {
        return fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found with id " + id));
    }


    @Transactional
    public Boolean deleteFile(String id) {
        if (fileRepository.existsById(id)) {
            fileRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }



    @Transactional
    public Boolean updateFile(MultipartFile file, String id) {
        if (fileRepository.existsById(id)) {
            try {
                fileRepository.updateFile(file.getBytes(),file.getName(),file.getContentType(),id);
            } catch (IOException ex) {
                return false;
            }
            return true;
        } else {
            return false;
        }

    }

}
