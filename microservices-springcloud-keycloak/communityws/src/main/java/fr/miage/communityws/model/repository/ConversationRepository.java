package fr.miage.communityws.model.repository;


import fr.miage.communityws.model.entity.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConversationRepository extends MongoRepository<Conversation,String> {

}
