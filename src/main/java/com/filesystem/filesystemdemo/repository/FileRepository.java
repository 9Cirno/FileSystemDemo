package com.filesystem.filesystemdemo.repository;

import com.filesystem.filesystemdemo.pojo.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface FileRepository extends JpaRepository<File,String> {
    @Transactional
    @Modifying
    @Query(value="update File f set f.data = ?1,f.fileName= ?2, f.fileType = ?3 where f.id = ?4")
    int updateFile(byte[] data, String fileName, String fileType, String id);
}
