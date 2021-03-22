package it.unifi.stlab.dao;


import it.unifi.stlab.faultflow.model.knowledge.composition.Component;

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
public class ComponentDao extends BaseDao<Component> {

    @Override
    public Optional<Component> get(UUID uuid) {
        return Optional.ofNullable(entityManager.find(Component.class, uuid));
    }

    @Override
    public List<Component> getAll() {
        Query query = entityManager.createQuery("");
        return query.getResultList();    }

    @Override
    public void save(Component component) {
        executeInsideTransaction(entityManager -> entityManager.persist(component));
    }

    @Override
    public void update(Component component) {
    }

    @Override
    public void delete(Component component) {
        executeInsideTransaction(entityManager -> entityManager.remove(component));
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
