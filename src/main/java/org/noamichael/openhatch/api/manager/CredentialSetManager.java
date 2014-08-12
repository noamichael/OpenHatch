package org.noamichael.openhatch.api.manager;

import java.util.Collection;
import java.util.List;
import org.noamichael.openhatch.api.authentication.AbstractCredentialSet;
import org.noamichael.openhatch.api.authentication.AuthenticationResult;
import org.noamichael.openhatch.api.authentication.SearchParameter;
import org.noamichael.openhatch.impl.schema.CredentialMember;
import org.noamichael.openhatch.impl.schema.CredentialSet;
import org.noamichael.openhatch.impl.schema.UserPermission;
import org.noamichael.openhatch.impl.schema.UserRole;

public interface CredentialSetManager<T extends AbstractCredentialSet> {

    public AuthenticationResult isValid(T credential);

    public CredentialSet<T> findCredentialSet(SearchParameter param);

    public void updateCredentialSet(T credentialSet);

    public void removeCredentialSet(T credentialSet);

    public void addCredentialSet(T credentialSet);

    public void grantPermission(CredentialMember member, UserPermission permission);

    public void grantRole(CredentialMember member, UserRole role);

    public void revokeRole(CredentialMember member, UserRole role);

    public void revokePermission(CredentialMember member, UserPermission permission);
    
    public boolean hasPermission(CredentialMember member, UserPermission permission);
    
    public boolean hasRole(CredentialMember member, UserRole role);
    
    public Collection<CredentialSet> getAllCredentials();

    public List<Class<? extends AbstractCredentialSet>> getSupportedCredentialSets();
}
