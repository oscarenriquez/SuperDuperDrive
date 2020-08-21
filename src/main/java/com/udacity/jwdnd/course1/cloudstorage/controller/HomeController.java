package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.converter.FileConverter;
import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@ControllerAdvice
@RequestMapping("/home")
public class HomeController {
    private final static Logger logger = LoggerFactory.getLogger(HomeController.class);

    private final EncryptionService encryptionService;
    private final FileService fileService;
    private final NoteService noteService;
    private final CredentialService credentialService;

    public HomeController(EncryptionService encryptionService, FileService fileService, NoteService noteService, CredentialService credentialService) {
        this.encryptionService = encryptionService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @GetMapping
    public String render (Authentication authentication, Model model){
        logger.info("Rendering home page for authed user: "+ authentication.getName());
        final User userAuthed = (User) authentication.getPrincipal();
        model.addAttribute("files", fileService.getFiles(userAuthed.getUserId()));
        model.addAttribute("notes", noteService.getNotes(userAuthed.getUserId()));
        List<Credential> credentials = credentialService.getCredentials(userAuthed.getUserId());
        credentials.forEach(credential -> credential.setVisiblePassword(encryptionService.decryptValue(credential.getPassword())));
        model.addAttribute("credentials", credentials);
        model.addAttribute("note", new Note());
        model.addAttribute("credential", new Credential());
        model.addAttribute("fileForm", new FileForm());
        model.addAttribute("error", false);
        model.addAttribute("errorMessage", "");
        return "home";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(Authentication authentication,
                             @Valid FileForm fileForm,
                             BindingResult bindingResult,
                             Model model ) {

        final User user = (User) authentication.getPrincipal();
        try {
            File file = fileService.getFile(user.getUserId(), fileForm.getFileUpload().getOriginalFilename());
            if (file != null) {
                model.addAttribute("success", false);
                model.addAttribute("errorMessage", "The file already exists in the database!");
                return "result";
            }
            fileService.createFile(FileConverter.convert(fileForm.getFileUpload(), user));
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
    public String addNote(Authentication authentication, Model model, @Valid Note note, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return "home";
            }
            final User user = (User) authentication.getPrincipal();
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
    public String addCredential(Authentication authentication, Model model, @Valid Credential credential, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return "home";
            }
            final User user = (User) authentication.getPrincipal();
            Credential credentialChecked = credentialService.getCredential(user.getUserId(), credential.getUsername());
            if (credentialChecked != null && !credentialChecked.getCredentialId().equals(credential.getCredentialId())) {
                model.addAttribute("success", false);
                model.addAttribute("errorMessage", "The Username already exists in the database!");
                return "result";
            }

            credential.setUser(user);
            credential.setVisiblePassword(credential.getPassword());
            credential.setPassword(encryptionService.encryptValue(credential.getVisiblePassword()));
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
    public String logout(HttpServletRequest request, HttpServletResponse response, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        model.addAttribute("logout", true);
        return "login";
    }

    @ExceptionHandler({MaxUploadSizeExceededException.class})
    public String conflict(Model model) {
        model.addAttribute("error", true);
        model.addAttribute("errorMessage", "The field fileUpload exceeds its maximum permitted size of 10 Megabytes");
        return "appError";
    }
}
