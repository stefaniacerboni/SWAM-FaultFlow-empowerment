package it.unifi.stlab.faultflow.endpoint;
import it.unifi.stlab.faultflow.businessLogic.controller.LoginController;
import it.unifi.stlab.faultflow.dto.LoginRequestDto;
import it.unifi.stlab.faultflow.dto.LoginResponseDto;
import it.unifi.stlab.faultflow.dto.inputsystemdto.InputSystemDto;
import it.unifi.stlab.faultflow.mapper.FaultTreeMapper;
import it.unifi.stlab.faultflow.mapper.SystemMapper;
import it.unifi.stlab.faultflow.model.knowledge.composition.System;
import it.unifi.stlab.faultflow.model.user.User;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
public class LoginEndpoint {

    @Inject
    LoginController loginController;

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUser(LoginRequestDto loginRequestDto) {
        User user = new User(loginRequestDto.getUsername(), loginRequestDto.getPassword());
        LoginResponseDto response = loginController.login(user);
        return Response.ok(response).build();
    }


    @POST
    @Path("/signup")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signupUser(LoginRequestDto signupRequest) {
        User newUser = new User(signupRequest.getUsername(), signupRequest.getPassword());
        loginController.signup(newUser);
        return Response.status(Response.Status.CREATED).entity("User created successfully").build();
    }
}
