package org.noamichael.openhatch.impl.schema;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import org.noamichael.openhatch.api.schema.Password;
import org.noamichael.openhatch.util.PasswordUtil;

@Entity
@XmlRootElement
@Inheritance(strategy = InheritanceType.JOINED)
public class UserPassword extends CredentialMember implements Password {
    
    @Id
    private String id = UUID.randomUUID().toString();

    @NotNull(message = "Please enter a password.")
    private String password;

    @OneToOne(cascade = {})
    private UserAccount owner;

    public UserPassword() {
    }

    public UserPassword(String password) {
        this.password = PasswordUtil.hash(password);
    }

    @Override
    public String getSalt() {
        return this.getId();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = PasswordUtil.hash(password);
    }

    @Override
    public UserAccount getOwner() {
        return this.owner;
    }

    @Override
    public void setOwner(UserAccount owner) {
        this.owner = owner;
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
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.password);
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
        final UserPassword other = (UserPassword) obj;
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        return Objects.equals(this.owner, other.owner);
    }

}
