package org.noamichael.openhatch.impl.schema;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import org.noamichael.openhatch.api.schema.Role;

@Entity
@XmlRootElement
public class UserRole implements Role {

    @NotNull(message = "Please enter a role name.")
    @Column
    private String name;

    @OneToMany(cascade = {javax.persistence.CascadeType.MERGE})
    private List<UserPermission> permissions = new ArrayList();

    @Id
    private final String id = UUID.randomUUID().toString();

    public UserRole() {
    }

    public UserRole(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getId() {
        return this.id;
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
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.name);
        hash = 37 * hash + Objects.hashCode(this.permissions);
        hash = 37 * hash + Objects.hashCode(this.id);
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
        final UserRole other = (UserRole) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
    
}
