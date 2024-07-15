package it.unifi.stlab.faultflow.endpoint;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import it.unifi.stlab.faultflow.dto.UserProfileUpdateRequestDto;
import it.unifi.stlab.faultflow.model.user.User;
import it.unifi.stlab.faultflow.dao.UserDao;
import it.unifi.stlab.faultflow.model.utils.JwtUtil;
import it.unifi.stlab.faultflow.model.utils.PasswordUtil;

@Path("/profile")
public class UserProfileEndpoint {

    @Inject
    private UserDao userDao;

    @Context
    private HttpServletRequest request;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserProfile() {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        String username = JwtUtil.extractUsername(token);
        if (username == null || !JwtUtil.isTokenValid(token, username)) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
        }
        User user = userDao.getUserByUsername(username);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
        }

        return Response.ok(user).build();
    }

    @PUT
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUserProfile(UserProfileUpdateRequestDto requestDto) {
        String authHeader = this.request.getHeader("Authorization");
        String token = authHeader.substring(7);
        String username = JwtUtil.extractUsername(token);

        if (username == null || !JwtUtil.isTokenValid(token, username)) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
        }

        User user = userDao.getUserByUsername(username);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
        }

        // Verify current password
        if (!PasswordUtil.checkPassword(requestDto.getCurrentPassword(), user.getPassword()) ) {
            return Response.status(Response.Status.FORBIDDEN).entity("Current password is incorrect").build();
        }

        // Update user details
        user.setName(requestDto.getName());
        user.setSurname(requestDto.getSurname());

        // Update password if provided
        if (requestDto.getNewPassword() != null && !requestDto.getNewPassword().isEmpty()) {
            user.setPassword(PasswordUtil.hashPassword(requestDto.getNewPassword()));
        }

        userDao.update(user);
        return Response.ok(user).build();
    }
}
