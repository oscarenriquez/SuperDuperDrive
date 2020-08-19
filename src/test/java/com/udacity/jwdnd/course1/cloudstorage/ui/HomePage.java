package com.udacity.jwdnd.course1.cloudstorage.ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class HomePage {
    @FindBy(id="username")
    private WebElement username;

    @FindBy(id="messageText")
    private WebElement textField;

    @FindBy(id="messageType")
    private WebElement messageType;

    @FindBy(id="submitMessage")
    private WebElement submitButton;

    @FindBy(className = "chatMessageUsername")
    private WebElement firstMessageUsername;

    @FindBy(className = "chatMessageText")
    private WebElement firstMessageText;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void sendChatMessage(String text, String username) {
        this.textField.sendKeys(text);
        this.username.sendKeys(username);
        Select messageTypeSelect = new Select(messageType);
        messageTypeSelect.getFirstSelectedOption();
        this.submitButton.click();
    }

}
