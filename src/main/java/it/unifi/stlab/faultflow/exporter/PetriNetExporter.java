package it.unifi.stlab.faultflow.exporter;

import it.unifi.stlab.faultflow.exporter.strategies.BasicExportToXPN;
import it.unifi.stlab.faultflow.exporter.strategies.OrderByComponentToXPN;
import it.unifi.stlab.faultflow.model.knowledge.composition.System;
import it.unifi.stlab.faultflow.translator.PetriNetTranslator;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;

public class PetriNetExporter {
    public static PetriNetTranslator exportPetriNetFromSystem(System system, PetriNetExportMethod method) throws JAXBException, FileNotFoundException {
        PetriNetTranslator pnt = new PetriNetTranslator();
        pnt.translate(system, method);
        File dir = new File("export/");
        dir.mkdir();
        XPNExporter.export(new File("export/"+system.getName() +"_"+ method.toString() + "_Fault2Failure.xpn"),
                new OrderByComponentToXPN(system, pnt.getPetriNet(), pnt.getMarking()));
        XPNExporter.export(new File("export/"+system.getName() +"_"+ method.toString() + "_Fault2Failure_Basic.xpn"),
                new BasicExportToXPN(pnt.getPetriNet(), pnt.getMarking()));
        return pnt;
    }
    public static void exportPetriNet(PetriNetTranslator pnt) throws JAXBException, FileNotFoundException{
        XPNExporter.export(new File("export/PetriNet_"+pnt.getName()+".xpn"),
                new BasicExportToXPN(pnt.getPetriNet(), pnt.getMarking()));
    }
}
