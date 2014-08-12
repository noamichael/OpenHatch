package org.noamichael.openhatch.api.authentication;

import org.noamichael.openhatch.impl.schema.CredentialSet;

/**
 * A parameter to be used in a CredentialSet search.
 *
 * @author Michael Kucinski
 * @param <T> The DataType which will be used to search for a credential.
 */
public interface SearchParameter<T> {

    /**
     * The parameter of the search.
     * @return 
     */
    public T getParameter();

    /**
     * The class which this search parameter can be used to find.
     *
     * @return
     */
    public Class<? extends CredentialSet> getSupportedClass();
}
