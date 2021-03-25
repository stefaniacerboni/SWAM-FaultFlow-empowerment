package it.unifi.stlab.faultflow.dao;
import it.unifi.stlab.faultflow.model.knowledge.propagation.ExogenousFaultMode;
import it.unifi.stlab.faultflow.model.knowledge.propagation.FaultMode;
import it.unifi.stlab.faultflow.model.knowledge.propagation.PropagationPort;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.persistence.TypedQuery;
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

        return entityManager.createQuery("SELECT pp FROM PropagationPort pp", PropagationPort.class).getResultList();
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

    public PropagationPort getPropagationPortByExoFaultMode(UUID exoFaultUUID){
        return entityManager.createQuery("SELECT pp FROM PropagationPort pp " +
                "JOIN FETCH pp.exogenousFaultMode " +
                "JOIN FETCH pp.propagatedFailureMode " +
                "JOIN FETCH pp.affectedComponent " +
                "WHERE pp.exogenousFaultMode.uuid=:uuid", PropagationPort.class)
                .setParameter("uuid", exoFaultUUID).getSingleResult();
    }

    public  List<PropagationPort> getPropPortsFetched(){
        return entityManager.createQuery("SELECT pp FROM PropagationPort pp JOIN FETCH pp.exogenousFaultMode JOIN FETCH pp.propagatedFailureMode JOIN FETCH pp.affectedComponent", PropagationPort.class).getResultList();
    }
}
