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

    public List<Note> getNotes() {
        return this.noteMapper.getNotes();
    }

    public Note getNote(Integer noteId) {
        return this.noteMapper.getNote(noteId);
    }

    public void createNote(Note note) {
        this.noteMapper.saveNote(note);
    }

    public boolean updateNote(Note note) {
        return this.noteMapper.updateNote(note);
    }

    public boolean deleteNote(Integer noteId) {
        return this.noteMapper.deleteNote(noteId);
    }
}
