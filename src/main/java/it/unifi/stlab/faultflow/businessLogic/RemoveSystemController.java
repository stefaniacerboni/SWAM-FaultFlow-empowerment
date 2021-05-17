package it.unifi.stlab.faultflow.businessLogic;

import it.unifi.stlab.faultflow.dao.FailureModeDao;
import it.unifi.stlab.faultflow.dao.FaultModeDao;
import it.unifi.stlab.faultflow.dao.SystemDao;
import it.unifi.stlab.faultflow.model.knowledge.composition.Component;
import it.unifi.stlab.faultflow.model.knowledge.composition.System;
import it.unifi.stlab.faultflow.model.knowledge.propagation.ErrorMode;
import it.unifi.stlab.faultflow.model.knowledge.propagation.FaultMode;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.UUID;

@Dependent
@Default
@Transactional
public class RemoveSystemController {

    @Inject
    SystemDao systemDao = new SystemDao();
    @Inject
    FaultModeDao faultModeDao = new FaultModeDao();
    @Inject
    FailureModeDao failureModeDao = new FailureModeDao();

    public void removeSystem(UUID systemUUID) throws Exception {
        System system = systemDao.getReferenceById(systemUUID);
        systemDao.remove(system);
        //orphan child removal automatically removes components, compositionPorts and PropagationPorts.
        for (Component component : system.getComponents()) {
            for (ErrorMode errorMode : component.getErrorModes()) {
                failureModeDao.remove(errorMode.getOutgoingFailure());
                for (FaultMode faultMode : errorMode.getInputFaultModes()) {
                    boolean res = true;
                    if (faultModeDao.getFaultModeById(faultMode.getUuid()) != null)
                        res = faultModeDao.remove(faultModeDao.getFaultModeById(faultMode.getUuid()));
                    if (!res)
                        throw new Exception("Can't remove:" + faultMode.getName() + ", " + faultMode.getUuid());
                }
            }
        }
    }
}
