package org.noamichael.openhatch.impl.schema;

import org.noamichael.openhatch.api.authentication.SearchParameter;

/**
 *
 * @author Michael Kucinski
 */
public class LoginNameSearchParam implements SearchParameter<String> {

    private final String parameter;

    public LoginNameSearchParam(String parameter) {
        this.parameter = parameter.toLowerCase();
    }

    /**
     * @return the parameter
     */
    @Override
    public String getParameter() {
        return parameter;
    }

    @Override
    public Class<? extends CredentialSet> getSupportedClass() {
        return UserPasswordCredentialSet.class;
    }

}
