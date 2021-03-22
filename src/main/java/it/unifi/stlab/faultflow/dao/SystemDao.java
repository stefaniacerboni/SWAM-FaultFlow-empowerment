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
        Query query = entityManager.createQuery("");
        return query.getResultList();
    }

    @Override
    public void save(System system) {
        entityManager.persist(system);
    }

    @Override
    public void update(System system) {

    }

    @Override
    public void delete(System system) {
        executeInsideTransaction(entityManager -> entityManager.remove(system));
    }

    private void executeInsideTransaction(Consumer<EntityManager> action) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            action.accept(entityManager);
            tx.commit();
        }
        catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }
}
