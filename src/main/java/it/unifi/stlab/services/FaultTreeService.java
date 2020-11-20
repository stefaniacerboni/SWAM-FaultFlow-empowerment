package it.unifi.stlab.services;

import it.unifi.stlab.DTO.faultTree.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/ft")
public class FaultTreeService {

	@POST
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSomething(InputFaultTreeDto inputFaultTreeDto) {
		return Response.ok(inputFaultTreeDto).build();
	}

}
