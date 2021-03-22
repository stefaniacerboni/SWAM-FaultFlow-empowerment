package it.unifi.stlab.faultflow.dao;

import it.unifi.stlab.faultflow.model.knowledge.composition.Component;
import it.unifi.stlab.faultflow.model.knowledge.composition.CompositionPort;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
@Default
public class CompositionPortDao extends BaseDao<CompositionPort>{
    @Override
    public Optional<CompositionPort> get(UUID uuid) {
        return Optional.ofNullable(entityManager.find(CompositionPort.class, uuid));
    }

    @Override
    public List<CompositionPort> getAll() {
        return null;
    }

    @Override
    public void save(CompositionPort compositionPort) {
        entityManager.persist(compositionPort);
    }

    @Override
    public void update(CompositionPort compositionPort) {
        entityManager.merge(compositionPort);
    }

    @Override
    public void delete(CompositionPort compositionPort) {
        entityManager.remove(compositionPort);
    }
}
