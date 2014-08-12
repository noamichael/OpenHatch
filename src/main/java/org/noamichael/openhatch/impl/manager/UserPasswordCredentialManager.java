package org.noamichael.openhatch.impl.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.noamichael.openhatch.api.authentication.AbstractCredentialSet;
import org.noamichael.openhatch.api.authentication.AuthenticationResult;
import org.noamichael.openhatch.api.authentication.SearchParameter;
import org.noamichael.openhatch.api.manager.CredentialSetManager;
import org.noamichael.openhatch.exception.AccountCreationException;
import org.noamichael.openhatch.exception.ExceptionMessages;
import org.noamichael.openhatch.impl.schema.CredentialMember;
import org.noamichael.openhatch.impl.schema.CredentialSet;
import org.noamichael.openhatch.impl.schema.LoginNameSearchParam;
import org.noamichael.openhatch.impl.schema.UserAccount;
import org.noamichael.openhatch.impl.schema.UserPassword;
import org.noamichael.openhatch.impl.schema.UserPasswordCredentialSet;
import org.noamichael.openhatch.impl.schema.UserPermission;
import org.noamichael.openhatch.impl.schema.UserRole;

/**
 *
 * @author Michael Kucinski
 */
public class UserPasswordCredentialManager implements CredentialSetManager<UserPasswordCredentialSet> {

    private final EntityManager entityManager;
    private final List<Class<? extends AbstractCredentialSet>> supportedDefaultCredentials;
    private final String userAccountType;

    public UserPasswordCredentialManager(EntityManager entityManager, String userAccountType) {
        this.entityManager = entityManager;
        this.supportedDefaultCredentials = new ArrayList();
        this.supportedDefaultCredentials.add(UserPasswordCredentialSet.class);
        if (userAccountType != null) {
            this.userAccountType = userAccountType;
        } else {
            this.userAccountType = "UserAccount";
        }
    }

    @Override
    public AuthenticationResult isValid(UserPasswordCredentialSet credential) {
        UserAccount foundAccount = findAccountByLoginName(credential.getLoginName());
        UserPassword foundPassword = findPasswordByAccount(foundAccount);
        if ((foundAccount == null) || (foundPassword == null)
                || (!foundPassword.getPassword()
                .equals(credential.getUserPassword().getPassword()))) {
            return new AuthenticationResult(false, null);
        }
        return new AuthenticationResult(true, new UserPasswordCredentialSet(foundAccount));
    }

    @Override
    public List<Class<? extends AbstractCredentialSet>> getSupportedCredentialSets() {
        return this.supportedDefaultCredentials;
    }

    private UserPassword findPasswordByAccount(UserAccount account) {
        Query q = getEntityManager().createQuery("SELECT P FROM UserPassword P WHERE P.owner = :owner");
        q.setParameter("owner", account);
        List result = q.getResultList();
        return result.isEmpty() ? null : (UserPassword) result.get(0);
    }

    public UserAccount findAccountByLoginName(String loginName) {
        Query q = getEntityManager().createQuery(getSelection() + " WHERE U.loginName = :loginName");
        q.setParameter("loginName", loginName);
        List result = q.getResultList();
        return result.isEmpty() ? null : (UserAccount) result.get(0);
    }

    @Override
    public void updateCredentialSet(UserPasswordCredentialSet userPass) {
        if (findAccountByLoginName(userPass.getLoginName()) == null) {
            String message = String.format(ExceptionMessages.NO_ACCOUNT, "update account", userPass.getLoginName());
            throw new AccountCreationException(message);
        }
        getEntityManager().merge(userPass.getAccount());
        getEntityManager().merge(userPass.getUserPassword());
    }

    @Override
    public void addCredentialSet(UserPasswordCredentialSet userPass) {
        if (findAccountByLoginName(userPass.getLoginName()) != null) {
            String message = String.format(ExceptionMessages.ACCOUNT_EXISTS, "add account", userPass.getLoginName());
            throw new AccountCreationException(message);
        }
        userPass.getUserPassword().setOwner(userPass.getAccount());
        getEntityManager().persist(userPass.getUserPassword());
        getEntityManager().persist(userPass.getAccount());
    }

    @Override
    public void removeCredentialSet(UserPasswordCredentialSet userPass) {
        if (findAccountByLoginName(userPass.getLoginName()) == null) {
            String message = String.format(ExceptionMessages.NO_ACCOUNT, "remove account", userPass.getLoginName());
            throw new AccountCreationException(message);
        }
        Query q = getEntityManager().createQuery("SELECT P FROM UserPassword P WHERE P.owner = :owner");
        q.setParameter("owner", userPass.getAccount());
        UserPassword p = (UserPassword) q.getResultList().get(0);
        getEntityManager().remove(p);
        getEntityManager().remove(userPass.getAccount());
    }

    @Override
    public CredentialSet<UserPasswordCredentialSet> findCredentialSet(SearchParameter param) {
        if (param instanceof LoginNameSearchParam) {
            LoginNameSearchParam nameParam = (LoginNameSearchParam) param;
            UserAccount a = this.findAccountByLoginName(nameParam.getParameter());
            if (a != null) {
                return new UserPasswordCredentialSet(a);
            }
        }
        return null;
    }

    /**
     * @return the entityManager
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public void grantPermission(CredentialMember member, UserPermission permission) {
        if (member instanceof UserAccount) {
            UserAccount account = (UserAccount) member;
            if (!account.getPermissions().contains(permission)) {
                account.getPermissions().add(permission);
                getEntityManager().merge(account);
            }
        }
    }

    @Override
    public void grantRole(CredentialMember member, UserRole role) {
        if (member instanceof UserAccount) {
            UserAccount account = (UserAccount) member;
            if (!account.getRoles().contains(role)) {
                account.getRoles().add(role);
                getEntityManager().merge(account);
            }
        }
    }

    @Override
    public void revokeRole(CredentialMember member, UserRole role) {
        if (member instanceof UserAccount) {
            UserAccount account = (UserAccount) member;
            account.getRoles().remove(role);
            getEntityManager().merge(account);
        }

    }

    @Override
    public void revokePermission(CredentialMember member, UserPermission permission) {
        if (member instanceof UserAccount) {
            UserAccount account = (UserAccount) member;
            account.getPermissions().remove(permission);
            getEntityManager().merge(account);
        }
    }

    @Override
    public boolean hasPermission(CredentialMember member, UserPermission permission) {
        if (member instanceof UserAccount) {
            UserAccount account = (UserAccount) member;
            if (account.getPermissions().contains(permission)) {
                return true;
            }
            List rolePermissions = new ArrayList();
            for (UserRole role : account.getRoles()) {
                rolePermissions.addAll(role.getPermissions());
            }
            return rolePermissions.contains(permission);
        }
        return false;
    }

    @Override
    public boolean hasRole(CredentialMember member, UserRole role) {
        if (member instanceof UserAccount) {
            UserAccount account = (UserAccount) member;
            return account.getRoles().contains(role);
        }
        return false;

    }

    @Override
    public Collection<CredentialSet> getAllCredentials() {
        Query q = this.entityManager.createQuery(getSelection());
        List<UserAccount> results = q.getResultList();
        Collection<CredentialSet> all = new ArrayList();
        results.stream().forEach(act -> {
            all.add(new UserPasswordCredentialSet(act));
        });
        return all;
    }

    private String getSelection() {
        return String.format("SELECT U FROM %s U", userAccountType);
    }
}
