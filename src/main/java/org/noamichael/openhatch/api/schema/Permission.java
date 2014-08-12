package org.noamichael.openhatch.api.schema;

import java.io.Serializable;

/**
 * An interface describing Permissions.
 *
 * @author Michael Kucinski
 */
public interface Permission extends Serializable {

    /**
     *
     * @return Gets the unique name for this permission. This is used for comparisons. 
     */
    public String getName();

    /**
     * Sets the unique name for this permission.
     *
     * @param paramString
     */
    public void setName(String paramString);

    /**
     * Returns the unique Id for this Permission.
     *
     * @return
     */
    public String getId();
}
