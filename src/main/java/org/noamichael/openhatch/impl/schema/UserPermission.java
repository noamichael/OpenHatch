package org.noamichael.openhatch.impl.schema;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import org.noamichael.openhatch.api.schema.Permission;

@Entity
@XmlRootElement
public class UserPermission implements Permission {

    @Column(unique = true)
    @NotNull(message = "Please enter a permission name.")
    private String name;

    @Id
    private final String id = UUID.randomUUID().toString();

    public UserPermission() {
    }

    public UserPermission(String name) {
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
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.name);
        hash = 89 * hash + Objects.hashCode(this.id);
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
        final UserPermission other = (UserPermission) obj;
        return true;
    }
    
}
