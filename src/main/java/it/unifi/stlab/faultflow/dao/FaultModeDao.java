package it.unifi.stlab.faultflow.dao;

import it.unifi.stlab.faultflow.model.knowledge.propagation.FaultMode;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
@Default
public class FaultModeDao extends BaseDao<FaultMode> {
    @Override
    public Optional<FaultMode> get(UUID uuid) {
        return Optional.ofNullable(entityManager.find(FaultMode.class, uuid));
    }

    @Override
    public List<FaultMode> getAll() {
        return entityManager.createQuery("SELECT fm FROM FaultMode fm", FaultMode.class).getResultList();
    }

    @Override
    public void save(FaultMode faultMode) {
        entityManager.persist(faultMode);
    }

    @Override
    public void update(FaultMode faultMode) {
        entityManager.merge(faultMode);
    }

    @Override
    public void delete(FaultMode faultMode) {
        entityManager.remove(faultMode);
    }

    public FaultMode getReferenceById(UUID uuid) {
        return entityManager.getReference(FaultMode.class, uuid);
    }
}
