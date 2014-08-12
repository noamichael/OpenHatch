package org.noamichael.openhatch.api.schema;

import java.io.Serializable;
import java.util.List;
import org.noamichael.openhatch.impl.schema.UserPermission;
import org.noamichael.openhatch.impl.schema.UserRole;

/**
 * An interface describing the behavior of an Account.
 * @author michael
 */
public interface Account extends Serializable {

    /**
     *
     * @return Gets the unique identifier of this account.
     */
    public String getId();

    /**
     *
     * @return Returns the login name for this account.
     */
    public String getLoginName();

    /**
     * Sets the login name for this account.
     *
     * @param paramString
     */
    public void setLoginName(String paramString);

    /**
     *
     * @return Returns all of the permissions for this user.
     */
    public List<UserPermission> getPermissions();

    /**
     * Sets all the permissions for this user.
     *
     * @param paramList
     */
    public void setPermissions(List<UserPermission> paramList);

    /**
     *
     * @return Gets all of the Roles for this user.
     */
    public List<UserRole> getRoles();

    /**
     * Sets all of the roles for this user.
     *
     * @param paramList
     */

    public void setRoles(List<UserRole> paramList);
}
