package org.noamichael.openhatch.api.manager;

import java.io.Serializable;
import org.noamichael.openhatch.impl.schema.CredentialMember;
import org.noamichael.openhatch.impl.schema.UserPermission;
import org.noamichael.openhatch.impl.schema.UserRole;

public interface PermissionManager extends Serializable {

    public void grantPermission(CredentialMember paramCredentialMember, UserPermission paramUserPermission);

    public void grantPermission(UserRole paramUserRole, UserPermission paramUserPermission);

    public void revokePermission(CredentialMember paramCredentialMember, UserPermission paramUserPermission);

    public void revokePermission(UserRole paramUserRole, UserPermission paramUserPermission);

    boolean hasPermission(CredentialMember paramCredentialMember, UserPermission paramUserPermission);

    public UserPermission createPermission(String paramString);

    public UserPermission findPermission(String paramString);
}
