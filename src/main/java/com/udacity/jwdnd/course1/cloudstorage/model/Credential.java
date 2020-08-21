package com.udacity.jwdnd.course1.cloudstorage.model;

import javax.validation.constraints.NotNull;

public class Credential {

    private Integer credentialId;
    @NotNull
    private String url;
    @NotNull
    private String username;
    private String key;
    @NotNull
    private String password;
    private String visiblePassword;
    private User user;

    public Credential() {
    }

    public Credential(Integer credentialId, String url, String username, String key, String password, User user) {
        this.credentialId = credentialId;
        this.url = url;
        this.username = username;
        this.key = key;
        this.password = password;
        this.user = user;
    }

    public Integer getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Integer credentialId) {
        this.credentialId = credentialId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getVisiblePassword() {
        return visiblePassword;
    }

    public void setVisiblePassword(String visiblePassword) {
        this.visiblePassword = visiblePassword;
    }
}
