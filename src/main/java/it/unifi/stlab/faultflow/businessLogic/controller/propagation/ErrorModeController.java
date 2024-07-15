package it.unifi.stlab.faultflow.businessLogic.controller.propagation;


import it.unifi.stlab.faultflow.businessLogic.controller.BaseController;
import it.unifi.stlab.faultflow.businessLogic.exception.DuplicatedEntityException;
import it.unifi.stlab.faultflow.businessLogic.exception.NoEntityFoundException;
import it.unifi.stlab.faultflow.dao.ErrorModeDao;
import it.unifi.stlab.faultflow.model.knowledge.propagation.ErrorMode;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@Dependent
@Default
public class ErrorModeController implements BaseController<ErrorMode> {

    @Inject
    private ErrorModeDao errorModeDao;

    public void add(ErrorMode errorMode) {
        if (errorModeDao.findById(errorMode.getUuid()) != null) {
            throw new DuplicatedEntityException("ErrorMode", errorMode.getUuid().toString());
        } else {
            errorModeDao.save(errorMode);
        }
    }

    public ErrorMode find(String uuid) {
        ErrorMode errorMode = errorModeDao.findById(UUID.fromString(uuid));

        if (errorMode == null) {
            throw new NoEntityFoundException("ErrorMode", uuid);
        } else {
            return errorMode;
        }
    }

    public List<ErrorMode> findAll() {
        return errorModeDao.findAll();
    }

    public ErrorMode findByFailureID(String id) {
        ErrorMode errorMode = errorModeDao.getErrorModeByFailureModeID(UUID.fromString(id));

        if (errorMode == null) {
            throw new NoEntityFoundException("FailureMode", id);
        } else {
            return errorMode;
        }
    }

    public void update(ErrorMode errorMode) {
        try {
            errorModeDao.update(errorMode);
        } catch (IllegalArgumentException e) {
            throw new NoEntityFoundException("ErrorMode", errorMode.getUuid().toString());
        }
    }

    public void delete(ErrorMode errorMode) {
        try {
            errorModeDao.delete(errorMode);
        } catch (IllegalArgumentException e) {
            throw new NoEntityFoundException("ErrorMode", errorMode.getUuid().toString());
        }
    }
}
