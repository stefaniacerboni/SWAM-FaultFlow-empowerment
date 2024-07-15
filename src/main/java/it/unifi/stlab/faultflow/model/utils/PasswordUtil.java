package it.unifi.stlab.faultflow.model.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Method to hash a password using bcrypt
    public static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    // Method to verify a password against a hashed password
    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
}
