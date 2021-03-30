package it.unifi.stlab.faultflow.dao;

import it.unifi.stlab.faultflow.model.knowledge.propagation.FailureMode;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import java.util.List;

@Dependent
@Default
public class FailureModeDao extends BaseDao<FailureMode> {
    public FailureModeDao() {
        super(FailureMode.class);
    }

    public List<FailureMode> getAll() {
        return entityManager.createQuery("SELECT fm FROM FailureMode fm", FailureMode.class).getResultList();
    }
}
