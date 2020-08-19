package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES ")
    List<File> getFiles();

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File getFile(Integer fileId);

    @Insert("INSERT INTO FILES (userId, filename, contentType, fileSize, fileData) values (#{user.userId}, #{fileName}, #{contentType}, #{fileSize}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    void saveFile(File file);

    @Update("UPDATE FILES SET filename=#{fileName}, contentType=#{contentType}, fileSize=#{fileSize}, fileData=#{fileData}, userId=#{user.userId} WHERE fileId = #{fileId}")
    boolean updateFile(File file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    boolean deleteFile(Integer fileId);
}
