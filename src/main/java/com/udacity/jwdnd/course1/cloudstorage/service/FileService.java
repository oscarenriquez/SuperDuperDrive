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
     * @return List of files
     */
    public List<File> getFiles(Integer userId) {
        logger.info("[getFiles]: getting list of files");
        return this.fileMapper.getFiles(userId);
    }

    /**
     * Obtain File by Id
     * @param fileId Integer
     * @return File
     */
    public File getFile(Integer fileId) {
        return this.fileMapper.getFile(fileId);
    }

    /**
     * Obtain File by User Id and File Name
     * @param userId Integer
     * @param fileName String
     * @return File
     */
    public File getFile(Integer userId, String fileName) {
        return this.fileMapper.getFileByName(userId, fileName);
    }

    /**
     * Create new File
     * @param file model
     */
    public void createFile(File file) {
        this.fileMapper.saveFile(file);
    }

    /**
     * Update File
     * @param file model
     * @return boolean
     */
    public boolean updateFile(File file) {
        return this.fileMapper.updateFile(file);
    }

    /**
     * Delete file by Id
     * @param fileId Integer
     * @return boolean
     */
    public boolean deleteFile(Integer fileId) {
        return this.fileMapper.deleteFile(fileId);
    }
}
