package it.unifi.stlab.faultflow.dao;

import it.unifi.stlab.faultflow.model.knowledge.composition.CompositionPort;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import java.util.List;

@Dependent
@Default
public class CompositionPortDao extends BaseDao<CompositionPort> {

    @Inject
    public CompositionPortDao() {
        super(CompositionPort.class);
    }

    public List<CompositionPort> getAll() {
        return entityManager.createQuery("SELECT cp FROM CompositionPort cp", CompositionPort.class).getResultList();
    }
}
