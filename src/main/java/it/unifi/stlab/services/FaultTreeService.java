package it.unifi.stlab.services;

import it.unifi.stlab.DTO.faultTree.*;
import it.unifi.stlab.fault2failure.knowledge.translator.PetriNetTranslator;
import it.unifi.stlab.mapper.FaultTreeMapper;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

@Path("/ft")
public class FaultTreeService {

	@POST
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSomething(InputFaultTreeDto inputFaultTreeDto) throws JAXBException, FileNotFoundException {
		//PetriNetTranslator pnt = new PetriNetTranslator();
		//pnt.translate(FaultTreeMapper.generateSystemFromFaultTree(inputFaultTreeDto));
		//return Response.ok(FaultTreeMapper.petriNetToJSON(pnt.getPetriNet())).build();
		return Response.ok(FaultTreeMapper.systemToOutputSystem(FaultTreeMapper.generateSystemFromFaultTree(inputFaultTreeDto))).build();
	}

}
