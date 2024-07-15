package it.unifi.stlab.faultflow.dao;

import it.unifi.stlab.faultflow.model.knowledge.BaseEntity;
import it.unifi.stlab.faultflow.model.knowledge.propagation.ErrorMode;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

public abstract class BaseDao<T extends BaseEntity> {

    private final Class<T> tClass;
    @PersistenceContext
    protected EntityManager entityManager;

    protected BaseDao(Class<T> tClass) {
        this.tClass = tClass;
    }

    public T findById(UUID id) {
        return entityManager.find(tClass, id);
    }

    public T getReferenceById(UUID id) {
        return entityManager.getReference(tClass, id);
    }

    public void save(T entity) {
        this.entityManager.persist(entity);
    }

    public void update(T entity) {
        this.entityManager.merge(entity);
    }

    public boolean remove(T entity) {
        try {
            this.entityManager.remove(entity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    public void delete(T entity) {
        entityManager.remove(entity);
    }

}

