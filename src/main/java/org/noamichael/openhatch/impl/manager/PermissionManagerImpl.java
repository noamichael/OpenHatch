package org.noamichael.openhatch.impl.manager;

import java.io.Serializable;
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
import org.noamichael.openhatch.api.manager.PermissionManager;
import org.noamichael.openhatch.exception.ExceptionMessages;
import org.noamichael.openhatch.exception.UnsatisfiedEMException;
import org.noamichael.openhatch.exception.UnsupportedCredentialException;
import org.noamichael.openhatch.impl.OpenHatchSettings;
import org.noamichael.openhatch.impl.RegisterBean;
import org.noamichael.openhatch.impl.schema.CredentialMember;
import org.noamichael.openhatch.impl.schema.CredentialSet;
import org.noamichael.openhatch.impl.schema.UserPermission;
import org.noamichael.openhatch.impl.schema.UserRole;

@Stateless
public class PermissionManagerImpl
        implements Serializable, PermissionManager {

    @Inject
    private OpenHatchSettings settings;

    @Inject
    @OpenHatch
    private Instance<EntityManager> emInstance;

    @Override
    public void grantPermission(CredentialMember member, UserPermission permission) {
        CredentialSetManager properManager = this.findProperManager(member.getCredentialSetType());
        if (properManager == null) {
            String message = String.format(ExceptionMessages.NO_MANAGER, "grant permission", member.getCredentialSetType());
            throw new UnsupportedCredentialException(message);
        }
        properManager.grantPermission(member, permission);
    }

    @Override
    public void revokePermission(CredentialMember member, UserPermission permission) {
        CredentialSetManager properManager = this.findProperManager(member.getCredentialSetType());
        if (properManager == null) {
            String message = String.format(ExceptionMessages.NO_MANAGER, "revoke permission", member.getCredentialSetType());
            throw new UnsupportedCredentialException(message);
        }
        properManager.revokePermission(member, permission);
    }

    @Override
    public void grantPermission(UserRole role, UserPermission permission) {
        if (!role.getPermissions().contains(permission)) {
            role.getPermissions().add(permission);
            getEntityManager().merge(role);
        }
    }

    @Override
    public void revokePermission(UserRole role, UserPermission permission) {
        role.getPermissions().remove(permission);
        getEntityManager().merge(role);
    }

    @Override
    public boolean hasPermission(CredentialMember member, UserPermission permission) {
        CredentialSetManager properManager = this.findProperManager(member.getCredentialSetType());
        if (properManager == null) {
            String message = String.format(ExceptionMessages.NO_MANAGER, "check permission", member.getCredentialSetType());
            throw new UnsupportedCredentialException(message);
        }
        return properManager.hasPermission(member, permission);
    }

    @Override
    public UserPermission createPermission(String name) {
        UserPermission permission = findPermission(name);
        if(permission != null){
            return permission;
        }
        permission = createUserPermission(name);
        getEntityManager().persist(permission);
        return permission;
    }

    private EntityManager getEntityManager() throws UnsatisfiedEMException {
        if ((this.emInstance.isUnsatisfied())
                || (this.emInstance
                .isAmbiguous())) {
            throw new UnsatisfiedEMException(ExceptionMessages.NO_EM);
        }

        return (EntityManager) this.emInstance.get();
    }

    private UserPermission createUserPermission(String permissionName) {
        if (this.settings.getUserPermissionType() != null) {
            Class pt = this.settings.getUserPermissionType();
            try {
                Object newObject = pt.getConstructor(String.class).newInstance(permissionName);
                return (UserPermission) newObject;
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(RegisterBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new UserPermission(permissionName);
    }

    @Override
    public UserPermission findPermission(String permissionName) {
        Query q = getEntityManager().createQuery("SELECT P FROM UserPermission P Where P.name = :permissionName");
        q.setParameter("permissionName", permissionName);
        List result = q.getResultList();
        return result.isEmpty() ? null : (UserPermission) result.get(0);
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
