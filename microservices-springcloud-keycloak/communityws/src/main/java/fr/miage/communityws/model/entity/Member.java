package fr.miage.communityws.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Document(collection = "members")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Member {

    @Id
    private String _id;
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private List<String> idConversations;

    public Member() {
    }


    public Member(String username, String email, String firstName, String lastName) {
        this._id=username;
        this.username = username;
        this.email = email;
        this.firstname = firstName;
        this.lastname = lastName;
    }

    public Member(String username, String email, String firstname, String lastname, List<String> conversations) {
        this._id=username;
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.idConversations = conversations;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        this._id=username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public List<String> getIdConversations() {
        return idConversations;
    }

    public void setIdConversations(List<String> idConversations) {
        this.idConversations = idConversations;
    }

    @Override
    public String toString() {
        return "Member{" +
                "_id='" + _id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(username, member.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
