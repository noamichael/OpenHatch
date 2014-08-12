package org.noamichael.openhatch.impl;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.BeanManager;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.noamichael.openhatch.api.authentication.AuthenticationResult;
import org.noamichael.openhatch.api.annotation.OpenHatch;
import org.noamichael.openhatch.api.manager.CredentialManager;
import org.noamichael.openhatch.events.PostLoggedInEvent;
import org.noamichael.openhatch.events.PostLoggedOutEvent;
import org.noamichael.openhatch.impl.schema.UserAccount;
import org.noamichael.openhatch.impl.schema.UserPasswordCredentialSet;

@Named
@SessionScoped
public class LoginBean implements Serializable {

    @Inject
    private CredentialManager cm;
    @Inject
    private BeanManager bm;
    private String loginName;
    private String password;
    private boolean loggedIn;
    private UserAccount activeAccount;

    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void login() {
        AuthenticationResult result = this.cm.validateCredentials(new UserPasswordCredentialSet(this.loginName, this.password));
        if (result.getResult()) {
            UserPasswordCredentialSet set = (UserPasswordCredentialSet) result.getValidatedCredentialSet();
            this.activeAccount = set.getAccount();
            bm.fireEvent(new PostLoggedInEvent(activeAccount));
            this.loggedIn = true;
            this.loginName = null;
            this.password = null;
        } else {
            addMsg("Login failed.", FacesMessage.SEVERITY_ERROR);
        }
    }

    public void logout() {
        this.loggedIn = false;
        this.activeAccount = null;
        bm.fireEvent(new PostLoggedOutEvent(activeAccount));
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }

    public void addMsg(String message, FacesMessage.Severity severity) {
        FacesMessage msg = new FacesMessage(message);
        msg.setSeverity(severity);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    @OpenHatch
    @Produces
    public UserAccount getActiveAccount() {
        return this.activeAccount;
    }

    public boolean isLoggedIn() {
        return this.loggedIn;
    }
}
