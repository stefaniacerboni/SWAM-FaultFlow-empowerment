package it.unifi.stlab.faultflow.dao;

import it.unifi.stlab.faultflow.model.knowledge.composition.System;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import java.util.List;

@Dependent
@Default
public class SystemDao extends BaseDao<System> {

    public SystemDao() {
        super(System.class);
    }

    public List<System> getAll() {
        return entityManager.createQuery("SELECT s FROM System s LEFT JOIN FETCH s.components", System.class).getResultList();
    }
}
