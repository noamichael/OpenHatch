package org.noamichael.openhatch.api.manager;

import java.io.Serializable;
import org.noamichael.openhatch.impl.schema.CredentialMember;
import org.noamichael.openhatch.impl.schema.UserRole;

public interface RoleManager extends Serializable {

    public void grantRole(CredentialMember paramUserAccount, UserRole paramUserRole);

    public void revokeRole(CredentialMember paramUserAccount, UserRole paramUserRole);

    public boolean hasRole(CredentialMember paramUserAccount, UserRole paramUserRole);

    public UserRole createRole(String paramString);

    public UserRole findRole(String paramString);
}
