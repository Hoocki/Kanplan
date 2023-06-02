package ru.isu.webproject.kanplan.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SignupRequest {
    private String username;
    private String mail;
    private String password;
}

