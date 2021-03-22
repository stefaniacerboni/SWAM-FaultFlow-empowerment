package it.unifi.stlab._initializer;

import it.unifi.stlab.faultflow.dao.ErrorModeDao;
import it.unifi.stlab.faultflow.dao.FailureModeDao;
import it.unifi.stlab.faultflow.dao.FaultModeDao;
import it.unifi.stlab.faultflow.dao.SystemDao;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class DatabaseInitializer {

	@Inject
	SystemDao systemDao = new SystemDao();
	@Inject
	ErrorModeDao errorModeDao = new ErrorModeDao();
	@Inject
	FaultModeDao faultModeDao = new FaultModeDao();
	@Inject
	FailureModeDao failureModeDao = new FailureModeDao();

	@PostConstruct
	public void initDB() {
		// TODO definisci e salva da qui alcuni sistemi nel DB

	}

}
