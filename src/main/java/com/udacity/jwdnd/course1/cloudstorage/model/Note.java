package com.udacity.jwdnd.course1.cloudstorage.model;

import javax.validation.constraints.NotNull;

public class Note {

    private Integer noteId;
    @NotNull
    private String noteTitle;
    @NotNull
    private String noteDescription;
    private User user;

    public Note() {
    }

    public Note(Integer noteId, String noteTitle, String noteDescription, User user) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.user = user;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
