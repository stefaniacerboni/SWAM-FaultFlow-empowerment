package it.unifi.stlab.faultflow.dao;


import it.unifi.stlab.faultflow.model.knowledge.composition.Component;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@Dependent
@Default
public class ComponentDao extends BaseDao<Component> {

    @Inject
    public ComponentDao() {
        super(Component.class);
    }

    public List<Component> getAll() {
        return entityManager.createQuery("SELECT c FROM Component c " +
                "JOIN FETCH Component.errorModes", Component.class)
                .getResultList();
    }

    public Component getComponentByErrorModeUUID(UUID errorModeUUID) {
        return entityManager.createQuery("SELECT c FROM Component c " +
                "JOIN FETCH c.errorModes ce " +
                "WHERE ce.uuid = :uuid", Component.class)
                .setParameter("uuid", errorModeUUID)
                .getSingleResult();
    }

    public Component getComponentById(UUID componentUUID) {
        return entityManager.createQuery("SELECT c FROM Component c JOIN FETCH c.errorModes WHERE c.uuid=:uuid", Component.class)
                .setParameter("uuid", componentUUID).getSingleResult();
    }

}
