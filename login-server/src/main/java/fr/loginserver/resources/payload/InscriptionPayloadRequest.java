package fr.loginserver.resources.payload;

import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InscriptionPayloadRequest implements Serializable {
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
}
