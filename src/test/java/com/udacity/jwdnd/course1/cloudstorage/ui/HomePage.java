package com.udacity.jwdnd.course1.cloudstorage.ui;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    public static final String LOGOUT_BUTTON_TEXT = "Logout";
    private WebDriver driver;

    @FindBy(css="#logoutDiv>form>button")
    private WebElement logoutButton;

    @FindBy(id="nav-files-tab")
    private WebElement filesTabLink;

    @FindBy(id="nav-notes-tab")
    private WebElement notesTabLink;

    @FindBy(id="nav-credentials-tab")
    private WebElement credentialsTabLink;

    //Notes
    @FindBy(css="#nav-notes>button.btn-info")
    private WebElement showNoteModalButton;

    @FindBy(css="#nav-notes table")
    private WebElement notesTable;

    @FindBy(id="note-title")
    private WebElement noteTitle;

    @FindBy(id="note-description")
    private WebElement noteDescription;

    @FindBy(id="noteSubmitForm")
    private WebElement noteSubmitButton;

    //Credentials
    @FindBy(css="#nav-credentials>button.btn-info")
    private WebElement showCredentialModalButton;

    @FindBy(css="#nav-credentials table")
    private WebElement credentialsTable;

    @FindBy(id="credential-url")
    private WebElement credentialUrl;

    @FindBy(id="credential-username")
    private WebElement credentialUsername;

    @FindBy(id="credential-password")
    private WebElement credentialPassword;

    @FindBy(id="credentialSubmitForm")
    private WebElement credentialSubmitButton;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getLogoutButtonText() {
        if (logoutButton != null) {
            return logoutButton.getText();
        }
        return null;
    }

    public void logout() {
        logoutButton.click();
    }

    public void addNote(String noteTitle, String noteDescription) {
        this.noteTitle.sendKeys(noteTitle);
        this.noteDescription.sendKeys(noteDescription);
        this.noteSubmitButton.click();
    }

    public void updateNote(Integer noteId, String noteTitle, String noteDescription) {
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("document.getElementById('note-id').value = "+noteId);
        this.noteTitle.clear();
        this.noteTitle.sendKeys(noteTitle);
        this.noteDescription.clear();
        this.noteDescription.sendKeys(noteDescription);
        this.noteSubmitButton.click();
    }

    public void deleteLastNoteAdded() {
        final WebElement deleteNoteButton = this.notesTable.findElement(By.cssSelector("tr:nth-child(1) > td > .btn-danger"));
        deleteNoteButton.click();
    }

    public Note getLastNoteAdded() {
        WebElement updateNoteButton = null;
        try {
            updateNoteButton = this.notesTable.findElement(By.cssSelector("tr:nth-child(1) > td > .btn-success"));
        } catch (NoSuchElementException e) {
        }

        if(updateNoteButton != null) {
            final Note note = new Note(
                    Integer.valueOf(updateNoteButton.getAttribute("noteId")),
                    updateNoteButton.getAttribute("noteTitle"),
                    updateNoteButton.getAttribute("noteDescription"),
                    null
            );
            return note;
        }
        return null;
    }

    public void addCredential(String url, String username, String password) {
        this.credentialUrl.sendKeys(url);
        this.credentialUsername.sendKeys(username);
        this.credentialPassword.sendKeys(password);
        this.credentialSubmitButton.click();
    }

    public void updateCredential(Integer credentialId, String url, String username, String password) {
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("document.getElementById('credential-id').value = "+credentialId);
        this.credentialUrl.clear();
        this.credentialUrl.sendKeys(url);
        this.credentialUsername.clear();
        this.credentialUsername.sendKeys(username);
        this.credentialPassword.clear();
        this.credentialPassword.sendKeys(password);
        this.credentialSubmitButton.click();
    }

    public Credential deleteLastCredentialAdded() {
        final Credential credential = this.getLastCredentialAdded();
        final WebElement deleteCredentialButton = this.credentialsTable.findElement(By.cssSelector("tr:nth-child(1) > td > .btn-danger"));
        deleteCredentialButton.click();
        return credential;
    }

    public Credential getLastCredentialAdded() {
        WebElement updateCredentialButton = null;
        try {
            updateCredentialButton = this.credentialsTable.findElement(By.cssSelector("tr:nth-child(1) > td > .btn-success"));
        }catch (NoSuchElementException e) {
        }

        if(updateCredentialButton != null) {
            final Credential credential = new Credential(
                    Integer.valueOf(updateCredentialButton.getAttribute("credentialId")),
                    updateCredentialButton.getAttribute("url"),
                    updateCredentialButton.getAttribute("username"),
                    null,
                    updateCredentialButton.getAttribute("password"),
                    null
            );
            return credential;
        }
        return null;
    }

    public void navigateToNotes(){
        notesTabLink.click();
    }

    public void showNoteModal() {
        showNoteModalButton.click();
    }

    public void showNoteModalUpdate() {
        final WebElement updateNoteButton = this.notesTable.findElement(By.cssSelector("tr:nth-child(1) > td > .btn-success"));
        updateNoteButton.click();
    }

    public void navigateToCredentials(){
        credentialsTabLink.click();
    }

    public void showCredentialModal(){
        showCredentialModalButton.click();
    }

    public void showCredentialModalUpdate() {
        final WebElement updateCredentialButton = this.credentialsTable.findElement(By.cssSelector("tr:nth-child(1) > td > .btn-success"));
        updateCredentialButton.click();
    }

}
