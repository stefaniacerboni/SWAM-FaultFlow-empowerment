package it.unifi.stlab.services;

import it.unifi.stlab.dto.inputsystemdto.InputSystemDto;
import it.unifi.stlab.dto.inputsystemdto.faulttree.InputFaultTreeDto;
import it.unifi.stlab.exporter.XPNExporter;
import it.unifi.stlab.exporter.strategies.BasicExportToXPN;
import it.unifi.stlab.exporter.strategies.OrderByComponentToXPN;
import it.unifi.stlab.fault2failure.knowledge.composition.System;
import it.unifi.stlab.fault2failure.knowledge.translator.PetriNetTranslator;
import it.unifi.stlab.mapper.FaultTreeMapper;
import it.unifi.stlab.mapper.SystemMapper;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;

@Path("/system")
public class SystemService {
    @POST
    @Path("/xpn")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPetriNetXPN(InputSystemDto inputSystemDto) throws JAXBException, FileNotFoundException {
        PetriNetTranslator pnt = new PetriNetTranslator();
        System sys = SystemMapper.BddToSystem(inputSystemDto.getBdd());
        FaultTreeMapper.decorateSystem(inputSystemDto.getFaultTree(), sys);
        pnt.translate(sys);
        File out = new File("prova.xpn");
        XPNExporter.export(out, new OrderByComponentToXPN(sys, pnt.getPetriNet(), pnt.getMarking()));
        return Response.ok(out).header("Content-Disposition", "attachment; filename=" + "PetriNet.xpn").build();
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSystem(InputSystemDto inputSystemDto){
        System sys = SystemMapper.BddToSystem(inputSystemDto.getBdd());
        FaultTreeMapper.decorateSystem(inputSystemDto.getFaultTree(), sys);
        return Response.ok(FaultTreeMapper.systemToOutputSystem(sys)).build();
    }
}
