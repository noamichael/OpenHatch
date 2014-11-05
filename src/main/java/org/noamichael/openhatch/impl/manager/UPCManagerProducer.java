package org.noamichael.openhatch.impl.manager;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.noamichael.openhatch.api.annotation.OpenHatch;
import org.noamichael.openhatch.exception.ExceptionMessages;
import org.noamichael.openhatch.exception.UnsatisfiedEMException;
import org.noamichael.openhatch.impl.OpenHatchSettings;

/**
 *
 * @author Michael Kucinski
 */
@ApplicationScoped
public class UPCManagerProducer {

    @Inject
    @OpenHatch
    private Instance<EntityManager> emInstance;
    private UserPasswordCredentialManager manager;
    @Inject
    private OpenHatchSettings settings;

    @PostConstruct
    public void init() {
        String userType = settings.getUserAccountType().getSimpleName();
        this.manager = new UserPasswordCredentialManager(getEntityManager(), userType);
    }

    @Produces
    @OpenHatch
    public UserPasswordCredentialManager getUPCM() {
        return manager;
    }

    private EntityManager getEntityManager() throws UnsatisfiedEMException {
        if ((this.emInstance.isUnsatisfied())
                || (this.emInstance
                .isAmbiguous())) {
            throw new UnsatisfiedEMException(ExceptionMessages.NO_EM);
        }

        return this.emInstance.get();
    }

}
