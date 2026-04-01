package fr.miage.communityws.controller;

import fr.miage.communityws.exception.ConversationNotFoundException;
import fr.miage.communityws.model.converter.TokenToMemberConverter;
import fr.miage.communityws.model.entity.Conversation;
import fr.miage.communityws.model.entity.Member;
import fr.miage.communityws.model.entity.Message;
import fr.miage.communityws.model.manager.ConversationManager;
import fr.miage.communityws.model.manager.MemberManager;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ConversationController {

    @Autowired
    ConversationManager conversationManager;

    @Autowired
    MemberManager memberManager;

    @PostMapping(value = "/conversation")
    public ResponseEntity postConversation(KeycloakAuthenticationToken token, @RequestBody Conversation conversation){
        Member member= TokenToMemberConverter.convertTokenToMembre(token);
        Conversation res= conversationManager.saveConversation(conversation);
        memberManager.addConversations(res);
        return ResponseEntity.ok(res);
    }

    @GetMapping(value = "/conversation/{id}")
    public ResponseEntity getConversation(KeycloakAuthenticationToken token, @PathVariable String id){
        Member curMember= TokenToMemberConverter.convertTokenToMembre(token);
        try {
            Conversation res= conversationManager.findConversationById(id);
            if (!res.getMembers().contains(curMember)){
                return ResponseEntity.status(401).build();
            }
            return ResponseEntity.ok(res);
        } catch (ConversationNotFoundException e) {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "/conversation/{id}")
    public ResponseEntity postMessage(KeycloakAuthenticationToken token, @PathVariable String id, @RequestBody Message message){
        try {
            Member member= TokenToMemberConverter.convertTokenToMembre(token);
            Conversation conversation = conversationManager.findConversationById(id);
            if (!conversation.getMembers().contains(member)){
                return ResponseEntity.status(401).build();
            }
            message.setIdMember(member.getUsername());
            Message res= conversationManager.writeMessage(id, message);

            return ResponseEntity.status(201).body(res);
        } catch (ConversationNotFoundException e) {
            return ResponseEntity.noContent().build();
        }
    }
}
