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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final static Logger logger = LoggerFactory.getLogger(HomeController.class);

    private final UserService userService;
    private final FileService fileService;
    private final NoteService noteService;
    private final CredentialService credentialService;

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
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return "result";

    }

    @GetMapping("/downloadFile/{fileId}")
    public void downloadFile(@PathVariable("fileId") Integer fileId, HttpServletResponse response) {
        try {
            final File file = fileService.getFile(fileId);
            response.setContentType(file.getContentType());
            response.setHeader("Content-Disposition", "attachment; filename=\""+file.getFileName()+"\"");
            ServletOutputStream out = response.getOutputStream();
            out.write(file.getFileData());
            out.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @GetMapping("/deleteFile/{fileId}")
    public String deleteFile(@PathVariable("fileId") Integer fileId, Model model) {
        try {
            boolean success = fileService.deleteFile(fileId);
            model.addAttribute("success", success);
        } catch (Exception e) {
            model.addAttribute("success", false);
            model.addAttribute("errorMessage", e.getMessage());
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return "result";
    }

    @PostMapping("/addNote")
    public String addNote(Authentication authentication, Model model, Note note) {
        try {
            final User user = userService.getUser(authentication.getName());
            note.setUser(user);
            boolean success = true;
            if (note.getNoteId() != null) {
                success = noteService.updateNote(note);
            } else {
                noteService.createNote(note);
            }
            model.addAttribute("success", success);
        } catch (Exception e) {
            model.addAttribute("success", false);
            model.addAttribute("errorMessage", e.getMessage());
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return "result";
    }

    @GetMapping("/deleteNote/{noteId}")
    public String deleteNote(@PathVariable("noteId") Integer noteId, Model model) {
        try {
            boolean success = noteService.deleteNote(noteId);
            model.addAttribute("success", success);
        } catch (Exception e) {
            model.addAttribute("success", false);
            model.addAttribute("errorMessage", e.getMessage());
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return "result";
    }

    @PostMapping("/addCredential")
    public String addCredential(Authentication authentication, Model model, Credential credential) {
        try {
            final User user = userService.getUser(authentication.getName());
            credential.setUser(user);
            boolean success = true;
            if(credential.getCredentialId() != null) {
                success = credentialService.updateCredential(credential);
            } else {
                credentialService.createCredential(credential);
            }
            model.addAttribute("success", success);
        } catch (Exception e) {
            model.addAttribute("success", false);
            model.addAttribute("errorMessage", e.getMessage());
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return "result";
    }

    @GetMapping("/deleteCredential/{credentialId}")
    public String deleteCredential(@PathVariable("credentialId") Integer credentialId, Model model) {
        try {
            boolean success = credentialService.deleteCredential(credentialId);
            model.addAttribute("success", success);
        } catch (Exception e) {
            model.addAttribute("success", false);
            model.addAttribute("errorMessage", e.getMessage());
            logger.error(e.getMessage());
            e.printStackTrace();
        }
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
