package it.unifi.stlab.faultflow.dao;

import it.unifi.stlab.faultflow.model.knowledge.composition.System;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import java.util.List;
import java.util.UUID;

@Dependent
@Default
public class SystemDao extends BaseDao<System> {

    public SystemDao() {
        super(System.class);
    }

    public List<System> getAll() {
        return entityManager.createQuery("SELECT DISTINCT s FROM System s LEFT JOIN FETCH s.components", System.class).getResultList();
    }

    public System getSystemById(UUID systemUUID){
        return entityManager.createQuery("SELECT DISTINCT s FROM System s LEFT JOIN FETCH s.components WHERE s.uuid = :uuid", System.class)
                .setParameter("uuid", systemUUID).getSingleResult();
    }
}
