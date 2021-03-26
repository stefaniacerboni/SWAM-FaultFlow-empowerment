package it.unifi.stlab.faultflow.dao;

import it.unifi.stlab.faultflow.model.knowledge.composition.System;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@Dependent
@Default
public class SystemDao extends BaseDao<System> {

    @Override
    public Optional<System> get(UUID uuid) {
        return Optional.ofNullable(entityManager.find(System.class, uuid));
    }

    @Override
    public List<System> getAll() {
        return entityManager.createQuery("SELECT s FROM System s", System.class).getResultList();
    }

    @Override
    public void save(System system) {
        entityManager.persist(system);
    }

    @Override
    public void update(System system) {
        entityManager.merge(system);
    }

    @Override
    public void delete(System system) {
        entityManager.remove(system);
    }
}
