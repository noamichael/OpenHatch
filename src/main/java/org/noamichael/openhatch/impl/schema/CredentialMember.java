package org.noamichael.openhatch.impl.schema;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Michael Kucinski
 */
@XmlRootElement
public abstract class CredentialMember implements Serializable {

    public abstract Class<? extends CredentialSet> getCredentialSetType();
}
