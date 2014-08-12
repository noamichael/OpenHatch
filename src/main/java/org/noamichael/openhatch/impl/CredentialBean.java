package org.noamichael.openhatch.impl;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * A bean used for holding the current request credentials.
 *
 * @author Michael Kucinski
 */
@Named
@RequestScoped
public class CredentialBean implements Serializable {

    private String loginName;
    private String password;

    public CredentialBean() {
    }

    /**
     * @return the loginName
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * @param loginName the loginName to set
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    

}
