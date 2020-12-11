package it.unifi.stlab.services;

import it.unifi.stlab.dto.inputsystemdto.faulttree.*;
import it.unifi.stlab.exporter.XPNExporter;
import it.unifi.stlab.exporter.strategies.OrderByComponentToXPN;
import it.unifi.stlab.fault2failure.knowledge.composition.System;
import it.unifi.stlab.fault2failure.knowledge.translator.PetriNetTranslator;
import it.unifi.stlab.mapper.FaultTreeMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;

@Path("/ft")
public class FaultTreeService {

	@POST
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSystem(InputFaultTreeDto inputFaultTreeDto) throws JAXBException, FileNotFoundException {
		return Response.ok(FaultTreeMapper.systemToOutputSystem(FaultTreeMapper.generateSystemFromFaultTree(inputFaultTreeDto))).build();
	}

	@POST
	@Path("/petri")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPetriNet(InputFaultTreeDto inputFaultTreeDto){
		PetriNetTranslator pnt = new PetriNetTranslator();
		pnt.translate(FaultTreeMapper.generateSystemFromFaultTree(inputFaultTreeDto));
		return Response.ok(FaultTreeMapper.petriNetToJSON(pnt.getPetriNet())).build();
	}

	@POST
	@Path("/xpn")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getPetriNetXPN(InputFaultTreeDto inputFaultTreeDto) throws JAXBException, FileNotFoundException{
		PetriNetTranslator pnt = new PetriNetTranslator();
		System sys = FaultTreeMapper.generateSystemFromFaultTree(inputFaultTreeDto);
		pnt.translate(sys);
		File out = new File("prova.xpn");
		XPNExporter.export(out, new OrderByComponentToXPN(sys, pnt.getPetriNet(), pnt.getMarking()));
		return Response.ok(out).header("Content-Disposition", "attachment; filename=" + "PetriNet.xpn").build();
	}

}
