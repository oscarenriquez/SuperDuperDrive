package com.udacity.jwdnd.course1.cloudstorage.converter;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class FileConverter {

    public static File convert(MultipartFile file, User user) throws IOException {
        return new File(null, user, file.getOriginalFilename(), file.getContentType(), String.valueOf(file.getSize()), file.getBytes());
    }
}
