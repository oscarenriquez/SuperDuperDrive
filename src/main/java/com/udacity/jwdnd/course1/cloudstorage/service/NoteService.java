package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NoteService {
    private final Logger logger = LoggerFactory.getLogger(NoteService.class);

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    /**
     * obtain list of notes
     * @return List of notes
     */
    public List<Note> getNotes(Integer userId) {
        return this.noteMapper.getNotes(userId);
    }

    /**
     * get Note by Id
     * @param noteId Integer
     * @return note
     */
    public Note getNote(Integer noteId) {
        return this.noteMapper.getNote(noteId);
    }

    /**
     * create new Note
     * @param note model
     */
    public void createNote(Note note) {
        this.noteMapper.saveNote(note);
    }

    /**
     * Update new Note
     * @param note model
     * @return boolean
     */
    public boolean updateNote(Note note) {
        return this.noteMapper.updateNote(note);
    }

    /**
     * Delete note by Id
     * @param noteId Integer
     * @return boolean
     */
    public boolean deleteNote(Integer noteId) {
        return this.noteMapper.deleteNote(noteId);
    }
}
