package ru.job4j.service;

/**
 * the class for creating instances for creating json object for response,
 * when the application returns the login and password of the new registered domain.
 */
public class JsonRegistration {

    private boolean registration;

    private String login;

    private String password;

    public JsonRegistration() {
    }

    public JsonRegistration(boolean registration, String login, String password) {
        this.registration = registration;
        this.login = login;
        this.password = password;
    }

    public boolean isRegistration() {
        return registration;
    }

    public void setRegistration(boolean registration) {
        this.registration = registration;
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
