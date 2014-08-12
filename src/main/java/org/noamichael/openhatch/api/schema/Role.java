package org.noamichael.openhatch.api.schema;

import java.io.Serializable;
import java.util.List;
import org.noamichael.openhatch.impl.schema.UserPermission;

/**
 * An interface describing a Role.
 *
 * @author Michael Kucinski
 */
public interface Role extends Serializable {

    /**
     *
     * @return The name of this role. This is used for comparisons.
     */
    public String getName();

    /**
     * Sets the name of the role.
     *
     * @param paramString
     */
    public void setName(String paramString);

    /**
     * Returns the unique ID of this role.
     *
     * @return
     */
    public String getId();

    /**
     *
     * @return The Permissions for this role.
     */
    public List<UserPermission> getPermissions();

    /**
     * Sets the permissions for this role.
     *
     * @param paramList
     */
    public void setPermissions(List<UserPermission> paramList);
}
