package fr.loginserver.resources.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Builder
@Getter
@Setter
public class InscriptionPayloadResponse implements Serializable {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
}
