package it.unifi.stlab.services;

import it.unifi.stlab.fault2failure.knowledge.utils.BasicModelBuilder;
import it.unifi.stlab.fault2failure.knowledge.utils.DecemberModelBuilder;

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
		return Response.ok(DecemberModelBuilder.getInstance().getSystem()).build();
	}

}
