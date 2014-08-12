package org.noamichael.openhatch.events;

import org.noamichael.openhatch.impl.schema.UserAccount;

public class PostUserCreatedEvent {

    private final UserAccount createdUser;

    public PostUserCreatedEvent(UserAccount createdUser) {
        this.createdUser = createdUser;
    }

    public UserAccount getCreatedUser() {
        return this.createdUser;
    }
}
