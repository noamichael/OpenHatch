package org.noamichael.openhatch.api.authentication;

import org.noamichael.openhatch.impl.schema.CredentialSet;

/**
 * An object that represents the result of an Authentication.
 *
 * @author Michael Kucinski
 */
public class AuthenticationResult {

    private final boolean result;
    private final CredentialSet validatedCredentialSet;
    
    /**
     * Constructs an {@link AuthenticationResult} with with the result and the
     * validated {@link CredentialSet}.
     * @param result
     * @param validatedCredentialSet 
     */
    public AuthenticationResult(boolean result, CredentialSet validatedCredentialSet) {
        this.result = result;
        this.validatedCredentialSet = validatedCredentialSet;
    }

    /**
     * The result of the Authentication.
     * @return 
     */
    public boolean getResult() {
        return this.result;
    }

    /**
     * The validated Credentials, if any.
     * @return 
     */
    public CredentialSet getValidatedCredentialSet() {
        return validatedCredentialSet;
    }
}
