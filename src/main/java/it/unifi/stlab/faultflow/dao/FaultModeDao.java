package it.unifi.stlab.faultflow.dao;

import it.unifi.stlab.faultflow.model.knowledge.propagation.FaultMode;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import java.util.List;
import java.util.UUID;

@Dependent
@Default
public class FaultModeDao extends BaseDao<FaultMode> {
    public FaultModeDao() {
        super(FaultMode.class);
    }

    public List<FaultMode> getAll() {
        return entityManager.createQuery("SELECT fm FROM FaultMode fm", FaultMode.class).getResultList();
    }

    public FaultMode getReferenceById(UUID uuid) {
        return entityManager.getReference(FaultMode.class, uuid);
    }

    public FaultMode getFaultModeById(UUID faultModeUUID) {
        return entityManager.createQuery("SELECT fm FROM FaultMode fm WHERE fm.uuid=:uuid", FaultMode.class)
                .setParameter("uuid", faultModeUUID).getSingleResult();
    }
}
