package fr.miage.communityws.model.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import fr.miage.communityws.model.entity.Member;

import java.util.List;
import java.util.stream.Collectors;

public class ListMemberConverter extends StdConverter<List<Member>, List<Member>> {

    @Override
    public List<Member> convert(List<Member> membres) {
        return membres.stream()
                .map(m -> new Member(m.getUsername(), m.getEmail(), m.getFirstname(), m.getLastname()))
                .collect(Collectors.toList());
    }
}
