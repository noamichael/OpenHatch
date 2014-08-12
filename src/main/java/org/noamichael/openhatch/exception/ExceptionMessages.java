package org.noamichael.openhatch.exception;

public class ExceptionMessages {

    public static final String NO_ACCOUNT = "Cannot [%s] : Account [%s] has not yet been added to OpenHatch.";
    public static final String ACCOUNT_EXISTS = "Cannot [%s]: Account [%s] already exists.";
    public static final String NO_MANAGER = "Cannot [%s] credential [%s]: no credential manager found. ";
    public static final String NO_EM = "Please create an EM producer method qualified with @OpenHatch.";
    public static final String NON_ENTITY = "Class [%s] is a non-entity class.";
}
