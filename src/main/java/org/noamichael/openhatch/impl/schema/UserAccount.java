package org.noamichael.openhatch.impl.schema;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import org.noamichael.openhatch.api.schema.Account;

@Entity
@XmlRootElement
@Inheritance(strategy = InheritanceType.JOINED)
public class UserAccount extends CredentialMember implements Account {

    @Id
    private String id = UUID.randomUUID().toString();

    @NotNull(message = "Please enter a login name.")
    @Column
    private String loginName;

    @OneToMany(cascade = {javax.persistence.CascadeType.MERGE})
    private List<UserPermission> permissions = new ArrayList();

    @OneToMany(cascade = {javax.persistence.CascadeType.MERGE})
    private List<UserRole> roles = new ArrayList();

    public UserAccount() {
    }

    public UserAccount(String loginName) {
        this.loginName = loginName.toLowerCase();
    }

    @Override
    public String getLoginName() {
        return this.loginName;
    }

    @Override
    public void setLoginName(String loginName) {
        this.loginName = loginName.toLowerCase();
    }

    @Override
    public List<UserPermission> getPermissions() {
        return this.permissions;
    }

    @Override
    public void setPermissions(List<UserPermission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public List<UserRole> getRoles() {
        return this.roles;
    }

    @Override
    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    @Override
    public Class<? extends CredentialSet> getCredentialSetType() {
        return UserPasswordCredentialSet.class;
    }

    /**
     * @return the id
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.id);
        hash = 71 * hash + Objects.hashCode(this.loginName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserAccount other = (UserAccount) obj;
        return Objects.equals(this.loginName, other.loginName);
    }

}
