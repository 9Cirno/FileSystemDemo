package com.filesystem.filesystemdemo.pojo;


import javax.persistence.*;

@Entity
@Table(name="files")
public class File {
    @Id
    private String id;

    private String fileName;

    private String fileType;

    @Lob
    private byte[] data;

    public File() {

    }

    public File(String id, String fileName, String fileType, byte[] data) {
        this.id = id;
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
//collection