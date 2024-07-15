package it.unifi.stlab.faultflow.businessLogic.exception;

public class NoEntityFoundException extends RuntimeException {

    private final String entityClass;
    private final String entityExternalID;

    public NoEntityFoundException(String entityClass, String entityExternalID) {
        this.entityClass = entityClass;
        this.entityExternalID = entityExternalID;
    }

    public String getEntityClass() {
        return entityClass;
    }

    public String getEntityExternalID() {
        return entityExternalID;
    }
}
