package mx.edu.utez.poeta.config;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class JwtResponse implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String jwtToken;

    public JwtResponse(){}

    public JwtResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }

}
