package fr.miage.communityws.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.miage.communityws.model.converter.ListMemberConverter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(collection = "conversations")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Conversation {

    @Id
    private String _id;
    private List<Message> messages=new ArrayList<>();

    @JsonSerialize(converter = ListMemberConverter.class)
    private List<Member> members;

    public Conversation() {
    }

    public Conversation(String _id) {
        this._id = _id;
    }

    public Conversation(String id, List<Message> messages, List<Member> members) {
        this._id = id;
        this.messages = messages;
        this.members = members;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "id='" + _id + '\'' +
                ", messages=" + messages +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conversation that = (Conversation) o;
        return Objects.equals(_id, that._id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id);
    }
}
