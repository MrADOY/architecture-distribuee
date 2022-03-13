package fr.loginserver.resources.payload;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ConnexionPayloadRequest implements Serializable {
    private String email;
    private String motDePasse;
}
