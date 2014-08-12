package org.noamichael.openhatch.api.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.inject.Qualifier;

/**
 * A {@link Qualifier} to signify use with OpenHatch.
 * @author Michael Kucinski
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.PARAMETER, java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.TYPE})
public @interface OpenHatch
{
}