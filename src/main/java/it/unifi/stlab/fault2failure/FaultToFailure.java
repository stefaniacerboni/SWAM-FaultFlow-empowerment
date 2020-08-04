package it.unifi.stlab.fault2failure;

import it.unifi.stlab.exporter.XPNExporter;
import it.unifi.stlab.exporter.jaxb.TpnEditor;
import it.unifi.stlab.exporter.strategies.BasicExportStrategy;
import it.unifi.stlab.exporter.strategies.ExportStrategy;
import it.unifi.stlab.exporter.strategies.OrderByComponentStrategy;
import it.unifi.stlab.fault2failure.knowledge.composition.System;
import it.unifi.stlab.fault2failure.knowledge.translator.PetriNetTranslator;
import it.unifi.stlab.fault2failure.knowledge.utils.NewModelExample;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;

public class FaultToFailure {

	public static void main( String[] args ) throws JAXBException, FileNotFoundException {

		NewModelExample.build();
		System s = NewModelExample.getSystem();
		PetriNetTranslator pnt = new PetriNetTranslator();
		pnt.translate(s);
		XPNExporter.export(new File("Fault2Failure.xpn"), new OrderByComponentStrategy(s, pnt.getPetriNet(), pnt.getMarking()));
		XPNExporter.export(new File("Fault2Failure_Basic.xpn"), new BasicExportStrategy(pnt.getPetriNet(), pnt.getMarking()));
	}
}
