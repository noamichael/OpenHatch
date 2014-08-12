package org.noamichael.openhatch.impl.schema;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserPasswordCredentialSet extends CredentialSet {

    private UserAccount account;
    private UserPassword userPassword;

    public UserPasswordCredentialSet(String loginName, String password) {
        this.account = new UserAccount(loginName);
        this.userPassword = new UserPassword(password);
    }

    public UserPasswordCredentialSet(UserAccount account) {
        this.account = account;
    }

    public UserPasswordCredentialSet() {
    }

    public UserPasswordCredentialSet(UserAccount account, UserPassword userPassword) {
        this.account = account;
        this.userPassword = userPassword;
    }

    public String getLoginName() {
        return this.account == null ? null : this.account.getLoginName();
    }

    public String getPassword() {
        return this.userPassword == null ? null : this.userPassword.getPassword();
    }

    /**
     * @return the account
     */
    public UserAccount getAccount() {
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(UserAccount account) {
        this.account = account;
    }

    /**
     * @return the userPassword
     */
    public UserPassword getUserPassword() {
        return userPassword;
    }

    /**
     * @param userPassword the userPassword to set
     */
    public void setUserPassword(UserPassword userPassword) {
        this.userPassword = userPassword;
    }

}
