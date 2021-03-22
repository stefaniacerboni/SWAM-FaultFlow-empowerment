package it.unifi.stlab.faultflow.dao;


import it.unifi.stlab.faultflow.model.knowledge.propagation.ErrorMode;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
@Default
public class ErrorModeDao extends BaseDao<ErrorMode> {
    @Override
    public Optional<ErrorMode> get(UUID uuid) {
        return Optional.ofNullable(entityManager.find(ErrorMode.class, uuid));
    }

    @Override
    public List<ErrorMode> getAll() {
        Query query = entityManager.createQuery("");
        return query.getResultList();
    }

    @Override
    public void save(ErrorMode errorMode) {
        entityManager.persist(errorMode);
    }

    @Override
    public void update(ErrorMode errorMode) {
        entityManager.merge(errorMode);
    }

    @Override
    public void delete(ErrorMode errorMode) {
        entityManager.remove(errorMode);
    }
}
