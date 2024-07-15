package it.unifi.stlab.faultflow.businessLogic.exception;

public class DuplicatedEntityException extends RuntimeException {

    private final String entityClass;
    private final String entityExternalID;

    public DuplicatedEntityException(String entityClass, String entityExternalID) {
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
