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

    /**
     * Obtain all the credentials
     * @return
     */
    public List<Credential> getCredentials() {
        logger.info("[getCredentials]: getting list of credentials");
        return this.credentialMapper.getCredentials();
    }

    /**
     * get Credential by Id
     * @param credentialId
     * @return
     */
    public Credential getCredential(Integer credentialId) {
        return this.credentialMapper.getCredential(credentialId);
    }

    /**
     * Create a new credential in DB
     * @param credential
     */
    public void createCredential(Credential credential) {
        this.credentialMapper.saveCredential(credential);
    }

    /**
     * Update Credential
     * @param credential
     * @return
     */
    public boolean updateCredential(Credential credential) {
        return this.credentialMapper.updateCredential(credential);
    }

    /**
     * Delete credential by Id
     * @param credentialId
     * @return
     */
    public boolean deleteCredential(Integer credentialId) {
        return this.credentialMapper.deleteCredential(credentialId);
    }
}
