package fr.miage.coursws.model.converter;

import fr.miage.coursws.model.entity.Member;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;

public class TokenToMemberConverter {
    public static Member convertTokenToMembre(KeycloakAuthenticationToken token){
        KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
        KeycloakSecurityContext session = principal.getKeycloakSecurityContext();
        AccessToken accessToken = session.getToken();

        Member member=new Member();
        member.setUsername(accessToken.getPreferredUsername());
        member.setEmail(accessToken.getEmail());
        member.setFirstname(accessToken.getGivenName());
        member.setLastname(accessToken.getFamilyName());

        return member;
    }
}
