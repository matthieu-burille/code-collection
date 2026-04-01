package fr.miage.communityws.model.manager;

import fr.miage.communityws.exception.ConversationNotFoundException;
import fr.miage.communityws.model.entity.Conversation;
import fr.miage.communityws.model.entity.Message;
import fr.miage.communityws.model.repository.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConversationManager {

    @Autowired
    private ConversationRepository conversationRepository;

    public Conversation findConversationById(String id) throws ConversationNotFoundException {
        Optional<Conversation> opt= conversationRepository.findById(id);
        if(opt.isEmpty()){
            throw new ConversationNotFoundException();
        }
        return opt.get();
    }

    public Conversation saveConversation(Conversation conversation){
        Conversation res= conversationRepository.save(conversation);
        return res;
    }

    public Message writeMessage(String id, Message message) throws ConversationNotFoundException {
        Conversation conversation=findConversationById(id);
        List<Message> messages= conversation.getMessages();
        Integer index= messages.isEmpty() ? 0 : messages.get(messages.size()-1).getIndex()+1;
        message.setIndex(index);
        messages.add(message);
        conversation.setMessages(messages);
        conversationRepository.save(conversation);
        return message;
    }
}
