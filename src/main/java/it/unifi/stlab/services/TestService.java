package it.unifi.stlab.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/test")
public class TestService {

	@GET
	@Path("")
	public Response restTest() {
		return Response.ok("FaultFlow Rest Services are up and running!").build();
	}

}
