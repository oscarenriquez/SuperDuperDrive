package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.converter.FileConverter;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/home")
public class HomeController {

    private UserService userService;
    private FileService fileService;
    private NoteService noteService;
    private CredentialService credentialService;

    public HomeController(UserService userService, FileService fileService, NoteService noteService, CredentialService credentialService) {
        this.userService = userService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @GetMapping
    public String render (Model model){
        model.addAttribute("files", fileService.getFiles());
        model.addAttribute("notes", noteService.getNotes());
        model.addAttribute("credentials", credentialService.getCredentials());
        model.addAttribute("note", new Note());
        model.addAttribute("credential", new Credential());
        return "home";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(Authentication authentication, @RequestParam("fileUpload") MultipartFile fileUpload, Model model) {
        final User user = userService.getUser(authentication.getName());
        try {
            fileService.createFile(FileConverter.convert(fileUpload, user));
            model.addAttribute("success", true);
        } catch (IOException e) {
            model.addAttribute("success", false);
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "result";
    }

    @GetMapping("/downloadFile/{fileId}")
    public void downloadFile(@PathVariable("fileId") Integer fileId, HttpServletResponse response) throws IOException {
        final File file = fileService.getFile(fileId);
        response.setContentType(file.getContentType());
        response.setHeader("Content-Disposition", "attachment; filename=\""+file.getFileName()+"\"");
        ServletOutputStream out = response.getOutputStream();
        out.write(file.getFileData());
        out.flush();
    }

    @GetMapping("/deleteFile/{fileId}")
    public String deleteFile(@PathVariable("fileId") Integer fileId, Model model) {
        boolean success = fileService.deleteFile(fileId);
        model.addAttribute("success", success);
        return "result";
    }

    @PostMapping("/addNote")
    public String addNote(Authentication authentication, Model model, Note note) {
        final User user = userService.getUser(authentication.getName());
        note.setUser(user);
        boolean success = true;
        if (note.getNoteId() != null) {
            success = noteService.updateNote(note);
        } else {
            noteService.createNote(note);
        }
        model.addAttribute("success", success);
        return "result";
    }

    @GetMapping("/deleteNote/{noteId}")
    public String deleteNote(@PathVariable("noteId") Integer noteId, Model model) {
        boolean success = noteService.deleteNote(noteId);
        model.addAttribute("success", success);
        return "result";
    }

    @PostMapping("/addCredential")
    public String addCredential(Authentication authentication, Model model, Credential credential) {
        final User user = userService.getUser(authentication.getName());
        credential.setUser(user);
        boolean success = true;
        if(credential.getCredentialId() != null) {
            success = credentialService.updateCredential(credential);
        } else {
            credentialService.createCredential(credential);
        }
        model.addAttribute("success", success);
        return "result";
    }

    @GetMapping("/deleteCredential/{credentialId}")
    public String deleteCredential(@PathVariable("credentialId") Integer credentialId, Model model) {
        boolean success = credentialService.deleteCredential(credentialId);
        model.addAttribute("success", success);
        return "result";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "login";
    }
}
