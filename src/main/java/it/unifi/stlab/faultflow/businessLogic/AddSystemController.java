package it.unifi.stlab.faultflow.businessLogic;

import it.unifi.stlab.faultflow.dao.*;
import it.unifi.stlab.faultflow.model.knowledge.composition.Component;
import it.unifi.stlab.faultflow.model.knowledge.composition.CompositionPort;
import it.unifi.stlab.faultflow.model.knowledge.composition.System;
import it.unifi.stlab.faultflow.model.knowledge.propagation.ErrorMode;
import it.unifi.stlab.faultflow.model.knowledge.propagation.FaultMode;
import it.unifi.stlab.faultflow.model.knowledge.propagation.PropagationPort;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Dependent
@Default
public class AddSystemController {

    @Inject
    SystemDao systemDao = new SystemDao();
    @Inject
    ComponentDao componentDao = new ComponentDao();
    @Inject
    ErrorModeDao errorModeDao = new ErrorModeDao();
    @Inject
    FaultModeDao faultModeDao = new FaultModeDao();
    @Inject
    FailureModeDao failureModeDao = new FailureModeDao();
    @Inject
    PropagationPortDao propagationPortDao = new PropagationPortDao();
    @Inject
    CompositionPortDao compositionPortDao = new CompositionPortDao();

    @Transactional
    public void persistSystem(System system) {
        for (Component component : system.getComponents()) {
            for (ErrorMode errorMode : component.getErrorModes()) {
                failureModeDao.save(errorMode.getOutgoingFailure());
                for (FaultMode faultMode : errorMode.getInputFaultModes()) {
                    faultModeDao.save(faultMode);
                }
                errorModeDao.save(errorMode);
            }
            componentDao.save(component);
        }
        for (Component component : system.getComponents()) {
            for (CompositionPort compositionPort : component.getCompositionPorts()) {
                compositionPortDao.save(compositionPort);
            }
            for (PropagationPort propagationPort : component.getPropagationPorts()) {
                propagationPortDao.save(propagationPort);
            }
        }
        systemDao.save(system);
    }
}
