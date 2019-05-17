package com.filesystem.filesystemdemo.controller;

import com.filesystem.filesystemdemo.pojo.bo.FileBO;
import com.filesystem.filesystemdemo.service.impl.FileServiceImpl;
import com.filesystem.filesystemdemo.pojo.File;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



import java.util.UUID;


@RestController
public class FileController {

    @Autowired
    private FileServiceImpl fileService;


    /**
     * This is post request handler for upload file in database.
     * @param MultipartFile file.
     * @return ResponseEntity<Resource>.
     */
    @PostMapping("/storage/documents/")
    public ResponseEntity<String> uploadFile(@RequestParam(value = "file") MultipartFile file)throws Exception{
        //get a unique id for length of 20
        String id = UUID.randomUUID().toString().replace("-", "").substring(0,20).toUpperCase();
        File uploadedFile = fileService.storeFile(file,id);
        return new ResponseEntity<>(id, HttpStatus.CREATED);

    }

    /**
     * This is get request handler for query file from database.
     * @param String file id.
     * @return ResponseEntity<Resource>.
     */
    @GetMapping("/storage/documents/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        // Load file from database
        File dbFile = fileService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .contentLength(dbFile.getData().length)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }

    /**
     * This is put request handler for update file in database.
     * @param String file id.
     * @return ResponseEntity<Resource>.
     */
    @PutMapping("/storage/documents/{fileId}")
    public ResponseEntity updateFile(@RequestParam(value = "file") MultipartFile file,@PathVariable String fileId)throws Exception{

        fileService.updateFile(file,fileId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    /**
     * This is delete request handler for delete file in database.
     * @param String file id.
     * @return ResponseEntity<Resource>.
     */
    @DeleteMapping("/storage/documents/{fileId}")
    public ResponseEntity deleteFile(@PathVariable String fileId){
        fileService.deleteFile(fileId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
