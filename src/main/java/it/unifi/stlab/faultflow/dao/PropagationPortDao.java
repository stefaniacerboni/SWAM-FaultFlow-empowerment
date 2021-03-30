package it.unifi.stlab.faultflow.dao;

import it.unifi.stlab.faultflow.model.knowledge.propagation.PropagationPort;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import java.util.List;
import java.util.UUID;

@Default
@Dependent
public class PropagationPortDao extends BaseDao<PropagationPort> {
    public PropagationPortDao() {
        super(PropagationPort.class);
    }

    public List<PropagationPort> getAll() {
        return entityManager.createQuery("SELECT pp FROM PropagationPort pp", PropagationPort.class).getResultList();
    }

    public List<PropagationPort> getPropagationPortsByExoFaultMode(UUID exoFaultUUID) {
        return entityManager.createQuery("SELECT pp FROM PropagationPort pp " +
                "JOIN FETCH pp.exogenousFaultMode " +
                "JOIN FETCH pp.propagatedFailureMode " +
                "JOIN FETCH pp.affectedComponent " +
                "WHERE pp.exogenousFaultMode.uuid=:uuid", PropagationPort.class)
                .setParameter("uuid", exoFaultUUID).getResultList();
    }
}
