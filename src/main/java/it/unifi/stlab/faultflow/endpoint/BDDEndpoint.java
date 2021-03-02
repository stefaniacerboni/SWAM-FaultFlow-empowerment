package it.unifi.stlab.faultflow.endpoint;

import it.unifi.stlab.faultflow.dto.inputsystemdto.bdd.InputBddDto;
import it.unifi.stlab.faultflow.model.knowledge.composition.System;
import it.unifi.stlab.faultflow.mapper.SystemMapper;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/bdd")
public class BDDEndpoint {

	@POST
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSystem(InputBddDto inputBddDto) {
		System sys = SystemMapper.BddToSystem(inputBddDto);

		return Response.ok(SystemMapper.systemToOutputSystem(sys)).build();
	}
}
