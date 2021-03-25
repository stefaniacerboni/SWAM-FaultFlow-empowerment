package it.unifi.stlab.faultflow.dao;


import it.unifi.stlab.faultflow.model.knowledge.propagation.BooleanExpression;
import it.unifi.stlab.faultflow.model.knowledge.propagation.ErrorMode;
import it.unifi.stlab.faultflow.model.knowledge.propagation.PropagationPort;
import it.unifi.stlab.faultflow.model.operational.Error;
import it.unifi.stlab.faultflow.model.utils.BooleanExpressionConverter;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
@Default
public class ErrorModeDao extends BaseDao<ErrorMode> {
    @Inject
    BooleanExpressionConverter booleanExpressionConverter;
    @Override
    public Optional<ErrorMode> get(UUID uuid) {
        return Optional.ofNullable(entityManager.find(ErrorMode.class, uuid));
    }

    @Override
    public List<ErrorMode> getAll() {
        Query query = entityManager.createQuery("SELECT em FROM ErrorMode em");
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

    public ErrorMode getErrorModeByFailureModeID(UUID failureModeID){
        TypedQuery<ErrorMode> query = entityManager.createQuery(
                "select em from ErrorMode em JOIN FETCH em.outgoingFailure where em.outgoingFailure.uuid= :failureModeID",
                ErrorMode.class);
        return query.setParameter("failureModeID", failureModeID).getSingleResult();

    }

    public ErrorMode getErrorModeById(UUID errorModeUUID){
        return entityManager.createQuery("SELECT em FROM ErrorMode em JOIN FETCH em.outgoingFailure WHERE em.uuid=:uuid", ErrorMode.class)
                .setParameter("uuid", errorModeUUID).getSingleResult();
    }

    public List<ErrorMode> getErrorModesWithFailuresFetched(){
        return entityManager.createQuery("SELECT em FROM ErrorMode em JOIN FETCH em.outgoingFailure", ErrorMode.class).getResultList();
    }
}
