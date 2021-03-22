package it.unifi.stlab.faultflow.dao;
import it.unifi.stlab.faultflow.model.knowledge.propagation.FaultMode;
import it.unifi.stlab.faultflow.model.knowledge.propagation.PropagationPort;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Default
@Dependent
public class PropagationPortDao extends BaseDao<PropagationPort>{
    @Override
    public Optional<PropagationPort> get(UUID uuid) {
        return Optional.ofNullable(entityManager.find(PropagationPort.class, uuid));
    }

    @Override
    public List<PropagationPort> getAll() {
        return null;
    }

    @Override
    public void save(PropagationPort propagationPort) {
        entityManager.persist(propagationPort);
    }

    @Override
    public void update(PropagationPort propagationPort) {
        entityManager.merge(propagationPort);
    }

    @Override
    public void delete(PropagationPort propagationPort) {
        entityManager.remove(propagationPort);
    }
}
