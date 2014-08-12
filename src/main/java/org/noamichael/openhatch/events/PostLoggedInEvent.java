package org.noamichael.openhatch.events;

import org.noamichael.openhatch.impl.schema.UserAccount;

/**
 *
 * @author Michael
 */
public class PostLoggedInEvent {
    private final UserAccount account;

    public PostLoggedInEvent(UserAccount account) {
        this.account = account;
    }

    /**
     * @return the account
     */
    public UserAccount getAccount() {
        return account;
    }
  
}
