package org.noamichael.openhatch.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.event.Event;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.noamichael.openhatch.api.manager.CredentialManager;
import org.noamichael.openhatch.events.PostUserCreatedEvent;
import org.noamichael.openhatch.impl.schema.LoginNameSearchParam;
import org.noamichael.openhatch.impl.schema.UserAccount;
import org.noamichael.openhatch.impl.schema.UserPassword;
import org.noamichael.openhatch.impl.schema.UserPasswordCredentialSet;

@Named
@ViewScoped
public class RegisterBean implements Serializable {

    @Inject
    private OpenHatchSettings settings;

    @Inject
    private CredentialManager cm;

    @Inject
    private Event<PostUserCreatedEvent> em;
    private String loginName;
    private String password;
    private String confirmPassword;

    public String register() {
        trimFields();

        if (this.loginName.isEmpty()) {
            addMsg("Login name can't be empty.", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        if (this.password.isEmpty()) {
            addMsg("Password cannot be empty.", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        if (!this.confirmPassword.equals(this.password)) {
            addMsg("Passwords do not match.", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        if (this.cm.findCredentialSet(new LoginNameSearchParam(this.getLoginName())) != null) {
            addMsg("Login Name already taken.", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        UserPasswordCredentialSet credentials = new UserPasswordCredentialSet(createUserAccount(loginName), createUserPassword(password));
        this.cm.addCredentialSet(credentials);
        this.em.fire(new PostUserCreatedEvent(credentials.getAccount()));
        this.loginName = null;
        this.password = null;
        addMsg("Account Created.", FacesMessage.SEVERITY_INFO);
        return null;
    }

    private void trimFields() {
        this.confirmPassword = this.confirmPassword.trim();
        this.password = this.password.trim();
        this.loginName = this.loginName.trim();
    }

    public void addMsg(String message, FacesMessage.Severity severity) {
        FacesMessage msg = new FacesMessage(message);
        msg.setSeverity(severity);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    private UserAccount createUserAccount(String loginName) {
        if (this.settings.getUserAccountType() != null) {
            Class at = this.settings.getUserAccountType();
            try {
                Object newObject = at.getConstructor(String.class).newInstance(loginName);
                return (UserAccount) newObject;
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(RegisterBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new UserAccount(loginName);
    }

    public UserPassword createUserPassword(String password) {
        if (this.settings.getUserPasswordType() != null) {
            Class pt = this.settings.getUserPasswordType();
            try {
                Object newObject = pt.getConstructor(String.class).newInstance(this.loginName);
                return (UserPassword) newObject;
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(RegisterBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new UserPassword(password);
    }

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

    public String getConfirmPassword() {
        return this.confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
