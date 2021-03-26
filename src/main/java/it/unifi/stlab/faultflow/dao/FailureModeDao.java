package it.unifi.stlab.faultflow.dao;

import it.unifi.stlab.faultflow.model.knowledge.propagation.FailureMode;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
@Default
public class FailureModeDao extends BaseDao<FailureMode> {
    @Override
    public Optional<FailureMode> get(UUID uuid) {
        return Optional.ofNullable(entityManager.find(FailureMode.class, uuid));
    }

    @Override
    public List<FailureMode> getAll() {
        return entityManager.createQuery("SELECT fm FROM FailureMode fm", FailureMode.class).getResultList();
    }

    @Override
    public void save(FailureMode failureMode) {
        entityManager.persist(failureMode);
    }

    @Override
    public void update(FailureMode failureMode) {
        entityManager.merge(failureMode);
    }

    @Override
    public void delete(FailureMode failureMode) {
        entityManager.remove(failureMode);
    }
}
