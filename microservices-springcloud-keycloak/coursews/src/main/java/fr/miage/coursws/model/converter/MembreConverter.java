package fr.miage.coursws.model.converter;


import com.fasterxml.jackson.databind.util.StdConverter;
import fr.miage.coursws.model.entity.Member;

public class MembreConverter extends StdConverter<Member, Member> {

    @Override
    public Member convert(Member membre) {
        return new Member(membre.getUsername(), membre.getEmail(), membre.getFirstname(), membre.getLastname());
    }
}