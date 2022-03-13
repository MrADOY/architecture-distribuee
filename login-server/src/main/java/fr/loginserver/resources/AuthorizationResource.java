package fr.loginserver.resources;

import fr.loginserver.modeles.Utilisateur;
import fr.loginserver.repositories.UtilisateurRepository;
import fr.loginserver.resources.payload.ConnexionPayloadRequest;
import fr.loginserver.resources.payload.ConnexionPayloadResponse;
import fr.loginserver.resources.payload.InscriptionPayloadRequest;
import fr.loginserver.resources.payload.InscriptionPayloadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/authorization")
public class AuthorizationResource {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("register")
    public ResponseEntity<InscriptionPayloadResponse> register(@RequestBody InscriptionPayloadRequest inscriptionPayloadRequest) {
            if(utilisateurRepository.existsByEmail(inscriptionPayloadRequest.getEmail())) {
                return new ResponseEntity("Email déjà utilisé",
                        HttpStatus.BAD_REQUEST);
            }

            Utilisateur utilisateur = Utilisateur.builder()
                    .email(inscriptionPayloadRequest.getEmail())
                    .motDePasse(inscriptionPayloadRequest.getMotDePasse())
                    .nom(inscriptionPayloadRequest.getNom())
                    .prenom(inscriptionPayloadRequest.getPrenom())
                    .build();

        Utilisateur resultat = utilisateurRepository.save(utilisateur);

        InscriptionPayloadResponse body = InscriptionPayloadResponse.builder()
                .id(resultat.getId())
                .email(resultat.getEmail())
                .nom(resultat.getNom())
                .prenom(resultat.getPrenom())
                .build();

        return ResponseEntity.ok(body);
    }

    @PostMapping("login")
    public ResponseEntity<ConnexionPayloadResponse> login(@RequestBody ConnexionPayloadRequest loginPayloadRequest) {
        return utilisateurRepository.findByEmailAndMotDePasse(loginPayloadRequest.getEmail(), loginPayloadRequest.getMotDePasse())
                .map(utilisateur -> ResponseEntity.ok(jwtTokenProvider.generateToken(utilisateur.getId())))
                .orElse(new ResponseEntity("Email ou mot de passe invalide", HttpStatus.UNAUTHORIZED));
    }


    @GetMapping("is-authorized")
    public ResponseEntity isAuthorize(@RequestHeader("Authorization") String jwt) {
        if(StringUtils.hasText(jwt) && jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);
            if (jwtTokenProvider.validateToken(jwt)) {
                Long userId = jwtTokenProvider.getUserIdFromJWT(jwt);
                Optional<Utilisateur> user = utilisateurRepository.findById(userId);
                if(user.isPresent()) {
                    return ResponseEntity.ok().build();
                }
            }
        }
        return new ResponseEntity("Vous n'êtes pas autorisé à appeler la ressource", HttpStatus.UNAUTHORIZED);
    }
}
