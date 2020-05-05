package com.ptak.OrganizerEureka.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AuthenticationRequest {
    private String username;
    private String password;

    public AuthenticationRequest() {
    }

    ;
}
