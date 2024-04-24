package vn.edu.tdtu.springrealestate.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Getter @Setter
public class AuthenticationResponse {
    private String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }
}
