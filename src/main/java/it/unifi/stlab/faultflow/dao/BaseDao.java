package it.unifi.stlab.faultflow.dao;

import it.unifi.stlab.faultflow.model.knowledge.BaseEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class BaseDao<T extends BaseEntity> {

    private final Class<T> tClass;
    @PersistenceContext
    protected EntityManager entityManager;

    protected BaseDao(Class<T> tClass) {
        this.tClass = tClass;
    }

    public T findById(Long id) {
        return entityManager.find(tClass, id);
    }

    public T getReferenceById(Long id) {
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
            return false;
        }
        return true;
    }
}

