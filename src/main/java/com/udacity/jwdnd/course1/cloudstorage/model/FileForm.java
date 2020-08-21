package com.udacity.jwdnd.course1.cloudstorage.model;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

public class FileForm {

    @NotNull
    private MultipartFile fileUpload;

    public FileForm() {
    }

    public FileForm(MultipartFile fileUpload) {
        this.fileUpload = fileUpload;
    }

    public MultipartFile getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(MultipartFile fileUpload) {
        this.fileUpload = fileUpload;
    }
}
