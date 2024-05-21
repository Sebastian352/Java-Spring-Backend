package com.project.tennisbackend.api.model;

import jakarta.validation.constraints.*;

public class RegistrationBody {
    @NotNull
    @NotBlank
    @Size(min=6,max = 64)
    private  String username;
    @NotNull
    @NotBlank
    @Size(min=6,max = 64)
    private  String password;


    private boolean tournament;

    private String name;

    public Boolean getTournament() {
        return tournament;
    }

    public void setTournament(Boolean tournament) {
        this.tournament = tournament;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
