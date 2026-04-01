package fr.miage.authws.controller;

import fr.miage.authws.exception.UserAlreadyExistsException;
import fr.miage.authws.exception.UserCredentialsException;
import fr.miage.authws.exception.UserNotCompliantException;
import fr.miage.authws.model.KeyCloakManager;
import fr.miage.authws.model.User;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    @Autowired
    private KeyCloakManager keyCloakManager;

    /*@GetMapping(value = "/anonymous")
    public ResponseEntity<String> getAnonymous() {
        return ResponseEntity.ok("Hello Anonymous");
    }*/

    /*@RolesAllowed("user")
    @GetMapping(value = "/user")
    public ResponseEntity<String> getUser(KeycloakAuthenticationToken token) {
        SimpleKeycloakAccount principal = (SimpleKeycloakAccount) token.getDetails();
        //KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
        KeycloakSecurityContext session = principal.getKeycloakSecurityContext();
        AccessToken accessToken = session.getToken();
        String username = accessToken.getPreferredUsername();
        return ResponseEntity.ok("Hello User");
    }*/

    @PostMapping(value = "/sign-in")
    public ResponseEntity signIn(@RequestBody User user) {
        try {
            keyCloakManager.createUser(user);
            return ResponseEntity.status(201).build();
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(409).build();
        } catch (UserNotCompliantException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(path = "/login")
    public ResponseEntity login(@RequestBody User user) {
        AccessTokenResponse response = null;
        try {
            response = keyCloakManager.authentificationUser(user);
            return ResponseEntity.status(201).body(response);
        } catch (UserCredentialsException e) {
            return ResponseEntity.status(401).build();
        }
    }

}

