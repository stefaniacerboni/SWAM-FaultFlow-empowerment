package it.unifi.stlab.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class BaseDao<T> {

    @PersistenceContext
    EntityManager entityManager;

    abstract Optional<T> get(UUID uuid);

    abstract List<T> getAll();

    abstract void save(T t);

    abstract void update(T t);

    abstract void delete(T t);

    public EntityManager getEntityManager() {
        return entityManager;
    }
}