package org.noamichael.openhatch.impl.manager;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.noamichael.openhatch.api.annotation.OpenHatch;
import org.noamichael.openhatch.api.manager.CredentialSetManager;
import org.noamichael.openhatch.api.manager.RoleManager;
import org.noamichael.openhatch.exception.ExceptionMessages;
import org.noamichael.openhatch.exception.UnsatisfiedEMException;
import org.noamichael.openhatch.exception.UnsupportedCredentialException;
import org.noamichael.openhatch.impl.OpenHatchSettings;
import org.noamichael.openhatch.impl.RegisterBean;
import org.noamichael.openhatch.impl.schema.CredentialMember;
import org.noamichael.openhatch.impl.schema.CredentialSet;
import org.noamichael.openhatch.impl.schema.UserRole;

@Stateless
public class RoleManagerImpl implements RoleManager {

    @Inject
    private OpenHatchSettings settings;

    @Inject
    @OpenHatch
    private Instance<EntityManager> emInstance;

    @Override
    public void grantRole(CredentialMember member, UserRole role) {
        CredentialSetManager properManager = this.findProperManager(member.getCredentialSetType());
        if (properManager == null) {
            String message = String.format(ExceptionMessages.NO_MANAGER, "grant role", member.getCredentialSetType());
            throw new UnsupportedCredentialException(message);
        }
        properManager.grantRole(member, role);
    }

    @Override
    public void revokeRole(CredentialMember member, UserRole role) {
        CredentialSetManager properManager = this.findProperManager(member.getCredentialSetType());
        if (properManager == null) {
            String message = String.format(ExceptionMessages.NO_MANAGER, "revoke role", member.getCredentialSetType());
            throw new UnsupportedCredentialException(message);
        }
        properManager.revokeRole(member, role);
    }

    @Override
    public boolean hasRole(CredentialMember member, UserRole role) {
        if(member == null || role == null){
            return false;
        }
        CredentialSetManager properManager = this.findProperManager(member.getCredentialSetType());
        if (properManager == null) {
            String message = String.format(ExceptionMessages.NO_MANAGER, "check role", member.getCredentialSetType());
            throw new UnsupportedCredentialException(message);
        }
        return properManager.hasRole(member, role);
    }

    @Override
    public UserRole createRole(String roleName) {
        UserRole role = findRole(roleName);
        if(role != null){
            return role;
        }
        role = createUserRole(roleName);
        getEntityManager().persist(role);
        return role;
    }

    private EntityManager getEntityManager() throws UnsatisfiedEMException {
        if ((this.emInstance.isUnsatisfied())
                || (this.emInstance
                .isAmbiguous())) {
            throw new UnsatisfiedEMException("Please create an EM producer method qualified with @OpenHatch.");
        }

        return (EntityManager) this.emInstance.get();
    }

    private UserRole createUserRole(String roleName) {
        if (this.settings.getUserRoleType() != null) {
            Class rt = this.settings.getUserRoleType();
            try {
                Object newObject = rt.getConstructor(String.class).newInstance(roleName);
                return (UserRole) newObject;
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(RegisterBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new UserRole(roleName);
    }

    @Override
    public UserRole findRole(String roleName) {
        Query q = getEntityManager().createQuery("SELECT R From UserRole R WHERE R.name = :roleName");
        q.setParameter("roleName", roleName);
        List result = q.getResultList();
        return result.isEmpty() ? null : (UserRole) result.get(0);
    }

    private CredentialSetManager<?> findProperManager(Class<? extends CredentialSet> credentialType) {
        List<CredentialSetManager<?>> allManagers = this.settings.getCredentialManagers();
        for (CredentialSetManager manager : allManagers) {
            if (manager.getSupportedCredentialSets().contains(credentialType)) {
                return manager;
            }
        }
        return null;
    }
}
