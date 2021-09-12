package it.unifi.stlab._initializer;

import it.unifi.stlab.faultflow.businessLogic.controller.AddSystemController;
import it.unifi.stlab.launcher.systembuilder.*;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class DatabaseInitializer {

    @Inject
    AddSystemController addSystemController = new AddSystemController();

    @PostConstruct
    public void initDB() {
        addSystemController.persistSystem(SimpleModelBuilder.getInstance().getSystem());
        addSystemController.persistSystem(SimpleSystem02Builder.getInstance().getSystem());
        addSystemController.persistSystem(SteamBoilerModelBuilder.getInstance().getSystem());
        addSystemController.persistSystem(PollutionMonitorModelBuilder.getInstance().getSystem());
        addSystemController.persistSystem(PollutionMonitorPreliminaryDesignBuilder.getInstance().getSystem());
        addSystemController.persistSystem(PollutionMonitorTargetDesignBuilder.getInstance().getSystem());
        addSystemController.persistSystem(PollutionMonitorIdealDesignBuilder.getInstance().getSystem());
    }

}
