package fr.miage.communityws.controller;

import fr.miage.communityws.exception.MemberAlreadyExistsException;
import fr.miage.communityws.exception.MemberNotFoundException;
import fr.miage.communityws.model.converter.TokenToMemberConverter;
import fr.miage.communityws.model.entity.Member;
import fr.miage.communityws.model.manager.MemberManager;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController {

    @Autowired
    private MemberManager memberManager;

    @GetMapping(value = "/member")
    public ResponseEntity getAllMembers(KeycloakAuthenticationToken token){
        return ResponseEntity.ok().body(memberManager.findAllMember());
    }

    @PostMapping(value = "/member")
    public ResponseEntity postMember(KeycloakAuthenticationToken token){
        try {
            Member member= TokenToMemberConverter.convertTokenToMembre(token);
            Member res= memberManager.createMember(member);
            return ResponseEntity.status(201).body(res);
        } catch (MemberAlreadyExistsException e) {
            return ResponseEntity.status(409).build();
        }
    }

    @GetMapping(value = "/member/{username}")
    public ResponseEntity getMemberById(KeycloakAuthenticationToken token, @PathVariable String username){
        Member curMember= TokenToMemberConverter.convertTokenToMembre(token);
        if (!curMember.getUsername().equals(username)){
            return ResponseEntity.status(401).build();
        }
        try {
            Member member=memberManager.findMemberById(username);
            return ResponseEntity.ok(member);
        }
        catch (MemberNotFoundException e) {
            return ResponseEntity.noContent().build();
        }
    }

}
