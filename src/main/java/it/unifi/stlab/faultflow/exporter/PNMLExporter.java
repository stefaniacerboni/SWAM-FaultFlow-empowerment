package it.unifi.stlab.faultflow.exporter;

import it.unifi.stlab.faultflow.exporter.jaxb.PNML.PnmlType;
import it.unifi.stlab.faultflow.exporter.strategies.ExportStrategy;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileNotFoundException;

public class PNMLExporter {
    public static void export(File output, ExportStrategy strategy)
            throws JAXBException, FileNotFoundException {

        JAXBContext context = JAXBContext.newInstance(PnmlType.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        PnmlType pnmlType = (PnmlType) strategy.translate();
        marshaller.marshal(pnmlType, output);
    }
}
