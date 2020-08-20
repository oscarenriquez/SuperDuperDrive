package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    private final Logger logger = LoggerFactory.getLogger(FileService.class);

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    /**
     * obtain all files
     * @return
     */
    public List<File> getFiles() {
        logger.info("[getFiles]: getting list of files");
        return this.fileMapper.getFiles();
    }

    /**
     * Obtain File by Id
     * @param fileId
     * @return
     */
    public File getFile(Integer fileId) {
        return this.fileMapper.getFile(fileId);
    }

    /**
     * Create new File
     * @param file
     */
    public void createFile(File file) {
        this.fileMapper.saveFile(file);
    }

    /**
     * Update File
     * @param file
     * @return
     */
    public boolean updateFile(File file) {
        return this.fileMapper.updateFile(file);
    }

    /**
     * Delete file by Id
     * @param fileId
     * @return
     */
    public boolean deleteFile(Integer fileId) {
        return this.fileMapper.deleteFile(fileId);
    }
}
