package it.unifi.stlab.faultflow.dto;

public class LoginResponseDto {
    private String message;
    private String token;

    public LoginResponseDto(String message, String token) {
        this.message = message;
        this.token = token;
    }

    // Getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

