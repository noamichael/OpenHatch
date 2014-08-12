package org.noamichael.openhatch.impl.manager;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Entity;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.noamichael.openhatch.api.annotation.OpenHatch;
import org.noamichael.openhatch.api.authentication.AbstractCredentialSet;
import org.noamichael.openhatch.api.authentication.AuthenticationResult;
import org.noamichael.openhatch.api.authentication.SearchParameter;
import org.noamichael.openhatch.api.manager.CredentialManager;
import org.noamichael.openhatch.api.manager.CredentialSetManager;
import org.noamichael.openhatch.exception.ExceptionMessages;
import org.noamichael.openhatch.exception.UnsupportedCredentialException;
import org.noamichael.openhatch.exception.UnsupportedEntityException;
import org.noamichael.openhatch.impl.OpenHatchSettings;
import org.noamichael.openhatch.impl.schema.CredentialSet;

/**
 *
 * @author michael
 */
@Stateless
public class CredentailManagerImpl implements Serializable, CredentialManager {

    @Inject
    private OpenHatchSettings settings;
    @Inject
    @OpenHatch
    private UserPasswordCredentialManager userPass;

    @PostConstruct
    public void init() {
        if (!settings.isDefaultAuthenticatorDisabled()) {
            this.settings.getCredentialManagers().add(userPass);
        }
    }

    @Override
    public void addCredentialSet(CredentialSet set) {
        CredentialSetManager properManager = findProperManager(set);
        if (properManager == null) {
            String message = String.format(ExceptionMessages.NO_MANAGER, "add", set.getClass().getName());
            throw new UnsupportedCredentialException(message);
        }
        properManager.addCredentialSet(set);
    }

    @Override
    public void removeCredentialSet(CredentialSet set) {
        CredentialSetManager properManager = findProperManager(set);
        if (properManager == null) {
            String message = String.format(ExceptionMessages.NO_MANAGER, "remove", set.getClass().getName());
            throw new UnsupportedCredentialException(message);
        }
        properManager.removeCredentialSet(set);
    }

    @Override
    public void updateCredentialSet(CredentialSet set) {
        CredentialSetManager properManager = findProperManager(set);
        if (properManager == null) {
            String message = String.format(ExceptionMessages.NO_MANAGER, "update", set.getClass().getName());
            throw new UnsupportedCredentialException(message);
        }
        properManager.updateCredentialSet(set);
    }

    @Override
    public <T extends AbstractCredentialSet> AuthenticationResult validateCredentials(T set) {
        CredentialSetManager properManager = findProperManager(set);
        if (properManager == null) {
            String message = String.format(ExceptionMessages.NO_MANAGER, "validate", set.getClass().getName());
            throw new UnsupportedCredentialException(message);
        }
        return properManager.isValid(set);
    }

    private CredentialSetManager<?> findProperManager(AbstractCredentialSet set) {
        List<CredentialSetManager<?>> allManagers = this.settings.getCredentialManagers();
        for (CredentialSetManager manager : allManagers) {
            if (manager.getSupportedCredentialSets().contains(set.getClass())) {
                return manager;
            }
        }
        return null;
    }

    private CredentialSetManager<?> findProperManager(Class<? extends CredentialSet> credentialType) {
        List<CredentialSetManager<?>> allManagers = this.settings.getCredentialManagers();
        for (CredentialSetManager manager : allManagers) {
            if (manager.getSupportedCredentialSets().contains(credentialType)) {
                return manager;
            }
        }
        return null;
    }

    @Override
    public <T> CredentialSet findCredentialSet(SearchParameter<T> param) {
        CredentialSetManager properManager = findProperManager(param.getSupportedClass());
        if (properManager == null) {
            String message = String.format(ExceptionMessages.NO_MANAGER, "find", param.getSupportedClass());
            throw new UnsupportedCredentialException(message);
        }
        return properManager.findCredentialSet(param);
    }

    @Override
    public String getAsXmlString(CredentialSet set) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(set.getClass());
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter writer = new StringWriter();
        m.marshal(set, writer);
        return writer.getBuffer().toString();

    }

    private void checkIfEntity(CredentialSet set) {
        if (!set.getClass().isAnnotationPresent(Entity.class)) {
            String message = String.format(ExceptionMessages.NON_ENTITY, set.getClass());
            throw new UnsupportedEntityException(ExceptionMessages.NON_ENTITY);
        }
    }

    @Override
    public CredentialSet getAsObject(String xml) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(CredentialSet.class);
        Unmarshaller u = context.createUnmarshaller();
        InputStream stream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
        return (CredentialSet) u.unmarshal(stream);

    }

    @Override
    public Collection<CredentialSet> getAllCredentials(Class<? extends CredentialSet> clazz) {
        if (clazz == null) {
            return userPass.getAllCredentials();
        }
        CredentialSetManager properManager = findProperManager(clazz);
        if (properManager == null) {
            String message = String.format(ExceptionMessages.NO_MANAGER, "get all credentials", clazz.getName());
            throw new UnsupportedCredentialException(message);
        }
        return properManager.getAllCredentials();
    }

    @Override
    public Collection<CredentialSet> getAllCredentials() {
        return getAllCredentials(null);
    }
}
