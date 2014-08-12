package org.noamichael.openhatch.impl.schema;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;
import org.noamichael.openhatch.api.authentication.AbstractCredentialSet;

/**
 *
 * @author Michael Kucinski
 */
@XmlRootElement
public class CredentialSet<T> implements Serializable, AbstractCredentialSet {

    @Id
    private String id = UUID.randomUUID().toString();

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
}
