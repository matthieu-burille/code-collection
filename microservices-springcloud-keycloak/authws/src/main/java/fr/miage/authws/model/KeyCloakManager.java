package fr.miage.authws.model;


import fr.miage.authws.config.KeyCloakProperties;
import fr.miage.authws.exception.UserAlreadyExistsException;
import fr.miage.authws.exception.UserCredentialsException;
import fr.miage.authws.exception.UserNotCompliantException;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class KeyCloakManager {

    @Autowired
    KeyCloakProperties properties;

    public void createUser(User user) throws UserAlreadyExistsException, UserNotCompliantException {

        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(properties.getServerUrl())
                .realm("master")
                .grantType(OAuth2Constants.PASSWORD)
                .clientId("admin-cli")
                //.clientSecret(properties.getClientSecret())
                .username("admin")
                .password("admin")
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
                .build();

        keycloak.tokenManager().getAccessToken();


        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername(user.getUsername());
        userRepresentation.setFirstName(user.getFirstname());
        userRepresentation.setLastName(user.getLastname());
        userRepresentation.setEmail(user.getEmail());
        //userRepresentation.setAttributes(Collections.singletonMap("origin", Arrays.asList("demo")));

        RealmResource realmResource = keycloak.realm(properties.getRealm());
        UsersResource usersRessource = realmResource.users();
        Response response = usersRessource.create(userRepresentation);

        if (response.getStatus() == 201) {
            String userId = CreatedResponseUtil.getCreatedId(response);

            CredentialRepresentation passwordCred = new CredentialRepresentation();
            passwordCred.setTemporary(false);
            passwordCred.setType(CredentialRepresentation.PASSWORD);
            passwordCred.setValue(user.getPassword());

            UserResource userResource = usersRessource.get(userId);

            userResource.resetPassword(passwordCred);

            RoleRepresentation userRealmRole = realmResource.roles()
                    .get("user").toRepresentation();

            userResource.roles().realmLevel()
                    .add(Arrays.asList(userRealmRole));

        } else if (response.getStatus() == 409) {
            throw new UserAlreadyExistsException();
        } else {
            throw new UserNotCompliantException();
        }
    }

    public AccessTokenResponse authentificationUser(User user) throws UserCredentialsException {
        Map<String, Object> clientCredentials = new HashMap<>();
        clientCredentials.put("secret", "");
        clientCredentials.put("grant_type", "password");

        Configuration configuration =
                new Configuration(properties.getServerUrl(), properties.getRealm(), properties.getClientId(), clientCredentials, null);
        AuthzClient authzClient = AuthzClient.create(configuration);
        AccessTokenResponse response = null;
        try {
            response = authzClient.obtainAccessToken(user.getUsername(), user.getPassword());
        } catch (Exception e) {
            throw new UserCredentialsException();
        }
        return response;
    }
}
