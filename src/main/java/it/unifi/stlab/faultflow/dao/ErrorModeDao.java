package it.unifi.stlab.faultflow.dao;


import it.unifi.stlab.faultflow.model.knowledge.propagation.ErrorMode;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;

@Dependent
@Default
public class ErrorModeDao extends BaseDao<ErrorMode> {

    public ErrorModeDao() {
        super(ErrorMode.class);
    }

    public List<ErrorMode> getAll() {
        return entityManager.createQuery("SELECT em FROM ErrorMode em", ErrorMode.class).getResultList();
    }

    public ErrorMode getErrorModeByFailureModeID(UUID failureModeID) {
        TypedQuery<ErrorMode> query = entityManager.createQuery(
                "select em from ErrorMode em JOIN FETCH em.outgoingFailure where em.outgoingFailure.uuid= :failureModeID",
                ErrorMode.class);
        return query.setParameter("failureModeID", failureModeID).getSingleResult();

    }

    public ErrorMode getErrorModeById(UUID errorModeUUID) {
        return entityManager.createQuery("SELECT em FROM ErrorMode em JOIN FETCH em.outgoingFailure WHERE em.uuid=:uuid", ErrorMode.class)
                .setParameter("uuid", errorModeUUID).getSingleResult();
    }
}
