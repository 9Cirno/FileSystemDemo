package com.filesystem.filesystemdemo.pojo.bo;

public class FileBO {
    private String type;
    private String length;

    public FileBO(String type, String length){
        this.type = type;
        this.length = length;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }
}
