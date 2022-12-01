package com.example.expenseapp.helpers;

public class RegistrationBody {
    private String login;
    private String password;
    private String name;
    private String lastName;
    private String url;

    public RegistrationBody(String login, String password, String name, String lastName, String url) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.url = url;
    }

    public RegistrationBody(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public RegistrationBody(String name, String lastName, String login) {
        this.name = name;
        this.lastName = lastName;
        this.login = login;
    }

    public RegistrationBody() {
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "RegistrationBody{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
