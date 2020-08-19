package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES ")
    List<Note> getNotes();

    @Select("SELECT * FROM NOTES WHERE noteId = #{noteId}")
    Note getNote(Integer noteId);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) values (#{noteTitle}, #{noteDescription}, #{user.userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    void saveNote(Note note);

    @Update("UPDATE NOTES SET noteTitle=#{noteTitle}, notedescription=#{noteDescription}, userid=#{user.userId} WHERE noteId = #{noteId}")
    boolean updateNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId}")
    boolean deleteNote(Integer noteId);
}
