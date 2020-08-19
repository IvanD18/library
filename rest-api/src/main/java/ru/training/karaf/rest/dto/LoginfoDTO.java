package ru.training.karaf.rest.dto;

public class LoginfoDTO {
    private String login;
    private String password;
    private boolean rememberMe;

    public LoginfoDTO() {
    }

    public LoginfoDTO(String login, String password, boolean rememberMe) {
        this.login = login;
        this.password = password;
        this.rememberMe = rememberMe;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
