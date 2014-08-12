package org.noamichael.openhatch.api.schema;

import java.io.Serializable;
import org.noamichael.openhatch.impl.schema.UserAccount;

public interface Password extends Serializable {

    /**
     *
     * @return The unique identifier of this password.
     */
    public String getId();

    /**
     *
     * @return The hashed and salted password.
     */
    public String getPassword();

    /**
     * Returns the salt for the password.
     *
     * @return
     */
    public String getSalt();

    /**
     *
     * @return The owner of this password.
     */
    public UserAccount getOwner();

    /**
     * Set the owner of this password.
     *
     * @param paramUserAccount
     */
    public void setOwner(UserAccount paramUserAccount);
}
