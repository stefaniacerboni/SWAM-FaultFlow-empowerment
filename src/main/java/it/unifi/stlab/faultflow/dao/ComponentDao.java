package it.unifi.stlab.faultflow.dao;


import it.unifi.stlab.faultflow.model.knowledge.composition.Component;
import it.unifi.stlab.faultflow.model.knowledge.propagation.ErrorMode;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
@Default
public class ComponentDao extends BaseDao<Component> {

    @Override
    public Optional<Component> get(UUID uuid) {
        return Optional.ofNullable(entityManager.find(Component.class, uuid));
    }

    @Override
    public List<Component> getAll() {
        return entityManager.createQuery("SELECT c FROM Component c " +
                "JOIN FETCH Component.errorModes", Component.class)
                .getResultList();
    }

    @Override
    public void save(Component component) {
        entityManager.persist(component);
    }

    @Override
    public void update(Component component) {
        entityManager.merge(component);
    }

    @Override
    public void delete(Component component) {
        entityManager.remove(component);
    }

    public Component getComponentByErrorModeUUID(ErrorMode errorMode) {
        return entityManager.createQuery("SELECT c FROM Component c " +
                "JOIN FETCH c.errorModes ce " +
                "WHERE ce.uuid = :uuid", Component.class)
                .setParameter("uuid", errorMode.getUuid())
                .getSingleResult();
    }

    public Component getComponentById(UUID componentUUID) {
        return entityManager.createQuery("SELECT c FROM Component c JOIN FETCH c.errorModes WHERE c.uuid=:uuid", Component.class)
                .setParameter("uuid", componentUUID).getSingleResult();
    }
}
