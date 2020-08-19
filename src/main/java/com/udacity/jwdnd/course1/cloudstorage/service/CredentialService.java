package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {
    private final Logger logger = LoggerFactory.getLogger(CredentialService.class);

    private CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public List<Credential> getCredentials() {
        logger.info("[getCredentials]: getting list of credentials");
        return this.credentialMapper.getCredentials();
    }

    public Credential getCredential(Integer credentialId) {
        return this.credentialMapper.getCredential(credentialId);
    }

    public void createCredential(Credential credential) {
        this.credentialMapper.saveCredential(credential);
    }

    public boolean updateCredential(Credential credential) {
        return this.credentialMapper.updateCredential(credential);
    }

    public boolean deleteCredential(Integer credentialId) {
        return this.credentialMapper.deleteCredential(credentialId);
    }
}
