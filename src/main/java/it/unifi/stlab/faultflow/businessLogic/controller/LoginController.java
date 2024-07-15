package it.unifi.stlab.faultflow.businessLogic.controller;

import it.unifi.stlab.faultflow.dao.UserDao;
import it.unifi.stlab.faultflow.dto.LoginResponseDto;
import it.unifi.stlab.faultflow.model.user.User;
import it.unifi.stlab.faultflow.model.utils.JwtUtil;
import it.unifi.stlab.faultflow.model.utils.PasswordUtil;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Dependent
@Default
@Transactional
public class LoginController {

    @Inject
    UserDao userDao;

    public LoginResponseDto login(User user) {
        User existingUser = userDao.getUserByUsername(user.getUsername());
        if (existingUser != null && PasswordUtil.checkPassword(user.getPassword(), existingUser.getPassword())) {
            // Password matches, generate a JWT token
            String token = JwtUtil.generateToken(user.getUsername());
            return new LoginResponseDto("User logged in successfully", token);
        } else {
            // Password does not match
            throw new RuntimeException("Invalid username or password");
        }
    }

    public void signup(User user) {
        // Hash the password before storing
        user.setPassword(PasswordUtil.hashPassword(user.getPassword()));
        userDao.save(user);
    }
}
