package org.noamichael.openhatch.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.Entity;
import org.noamichael.openhatch.api.manager.CredentialSetManager;
import org.noamichael.openhatch.exception.UnsupportedEntityException;
import org.noamichael.openhatch.impl.schema.UserAccount;
import org.noamichael.openhatch.impl.schema.UserPassword;
import org.noamichael.openhatch.impl.schema.UserPermission;
import org.noamichael.openhatch.impl.schema.UserRole;

@Named
@ApplicationScoped
public class OpenHatchSettings implements Serializable {

    private Class<? extends UserAccount> userAccountType;
    private Class<? extends UserPassword> userPasswordType;
    private Class<? extends UserRole> userRoleType;
    private Class<? extends UserPermission> userPermissionType;
    private final List<CredentialSetManager<?>> credentialManagers;
    private boolean defaultAuthenticatorDisabled;

    public OpenHatchSettings() {
        this.credentialManagers = new ArrayList();
        this.userAccountType = UserAccount.class;
    }

    public Class getUserAccountType() {
        return this.userAccountType;
    }

    public void setUserAccountType(Class<? extends UserAccount> userAccountType) {
        checkIfEntity(userAccountType);
        this.userAccountType = userAccountType;
    }

    public Class getUserPasswordType() {
        return this.userPasswordType;
    }

    public void setUserPasswordType(Class<? extends UserPassword> userPasswordType) {
        checkIfEntity(userPasswordType);
        this.userPasswordType = userPasswordType;
    }

    public Class<? extends UserRole> getUserRoleType() {
        return this.userRoleType;
    }

    public void setUserRoleType(Class<? extends UserRole> userRoleType) {
        checkIfEntity(userRoleType);
        this.userRoleType = userRoleType;
    }

    public Class<? extends UserPermission> getUserPermissionType() {
        return this.userPermissionType;
    }

    public void setUserPermissionType(Class<? extends UserPermission> userPermissionType) {
        checkIfEntity(userPermissionType);
        this.userPermissionType = userPermissionType;
    }

    private void checkIfEntity(Class clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new UnsupportedEntityException(clazz.getName() + " is a non entity class.");
        }
    }

    public List<CredentialSetManager<?>> getCredentialManagers() {
        return this.credentialManagers;
    }

    /**
     * @return the defaultAuthenticatorDisabled
     */
    public boolean isDefaultAuthenticatorDisabled() {
        return defaultAuthenticatorDisabled;
    }

    /**
     * @param defaultAuthenticatorDisabled the defaultAuthenticatorDisabled to set
     */
    public void setDefaultAuthenticatorDisabled(boolean defaultAuthenticatorDisabled) {
        this.defaultAuthenticatorDisabled = defaultAuthenticatorDisabled;
    }
}
