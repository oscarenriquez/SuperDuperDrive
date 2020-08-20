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
     * @return
     */
    public List<Note> getNotes() {
        return this.noteMapper.getNotes();
    }

    /**
     * get Note by Id
     * @param noteId
     * @return
     */
    public Note getNote(Integer noteId) {
        return this.noteMapper.getNote(noteId);
    }

    /**
     * create new Note
     * @param note
     */
    public void createNote(Note note) {
        this.noteMapper.saveNote(note);
    }

    /**
     * Update new Note
     * @param note
     * @return
     */
    public boolean updateNote(Note note) {
        return this.noteMapper.updateNote(note);
    }

    /**
     * Delete note by Id
     * @param noteId
     * @return
     */
    public boolean deleteNote(Integer noteId) {
        return this.noteMapper.deleteNote(noteId);
    }
}
