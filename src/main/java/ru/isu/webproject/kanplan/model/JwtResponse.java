package ru.isu.webproject.kanplan.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String mail;
    private List<String> roles;

    public JwtResponse(String token, Long id, String username, String mail, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.mail = mail;
        this.roles = roles;
    }
}

