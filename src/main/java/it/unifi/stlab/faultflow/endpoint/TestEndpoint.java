package it.unifi.stlab.faultflow.endpoint;

import it.unifi.stlab.launcher.systembuilder.SimpleSystem02Builder;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/test")
public class TestEndpoint {

	@GET
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response restTest() {
		return Response.ok(SimpleSystem02Builder.getInstance().getSystem()).build();
	}

}
