package it.unifi.stlab.services;

import it.unifi.stlab.utils.builder.SimpleSystem02Builder;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/test")
public class TestService {

	@GET
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response restTest() {
		return Response.ok(SimpleSystem02Builder.getInstance().getSystem()).build();
	}

}
