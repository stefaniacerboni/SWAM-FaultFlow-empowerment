package it.unifi.stlab.exporter;

import it.unifi.stlab.exporter.strategies.BasicExportToXPN;
import it.unifi.stlab.exporter.strategies.OrderByComponentToXPN;
import it.unifi.stlab.fault2failure.knowledge.composition.System;
import it.unifi.stlab.fault2failure.knowledge.translator.PetriNetTranslator;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;

public class PetriNetExporter {
    public static PetriNetTranslator exportPetriNetFromSystem(System system, PetriNetExportMethod method) throws JAXBException, FileNotFoundException {
        PetriNetTranslator pnt = new PetriNetTranslator();
        pnt.translate(system, method);
        XPNExporter.export(new File(system.getName() + "_Fault2Failure.xpn"),
                new OrderByComponentToXPN(system, pnt.getPetriNet(), pnt.getMarking()));
        XPNExporter.export(new File(system.getName() + "_Fault2Failure_Basic.xpn"),
                new BasicExportToXPN(pnt.getPetriNet(), pnt.getMarking()));
        return pnt;
    }
}