package fr.miage.communityws.model.manager;

import fr.miage.communityws.exception.MemberAlreadyExistsException;
import fr.miage.communityws.exception.MemberNotFoundException;
import fr.miage.communityws.model.entity.Conversation;
import fr.miage.communityws.model.entity.Member;
import fr.miage.communityws.model.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class MemberManager {

    @Autowired
    private MemberRepository memberRepository;

    public Collection<Member> findAllMember(){
        return memberRepository.findAll();
    }

    public Member findMemberById(String username) throws MemberNotFoundException {
        Optional<Member> opt= memberRepository.findById(username);
        if(opt.isEmpty()){
            throw new MemberNotFoundException();
        }
        return opt.get();
    }

    public Member createMember(Member member) throws MemberAlreadyExistsException {
            if(memberRepository.existsById(member.getUsername())){
                throw new MemberAlreadyExistsException();
            }
            Member res= memberRepository.save(member);
            return res;
    }

    public void saveMember(Member member) throws MemberNotFoundException {
        if(!memberRepository.existsById(member.getUsername())){
            throw new MemberNotFoundException();
        }
        memberRepository.save(member);
    }

    public void addConversations(Conversation conversation){
        for(Member member : conversation.getMembers()) {
            boolean exists = memberRepository.existsById(member.getUsername());
            if (exists) {
                List<String> conversations = member.getIdConversations();
                if(conversations==null){
                    conversations=new ArrayList<>();
                }
                conversations.add(conversation.get_id());
                member.setIdConversations(conversations);
                memberRepository.save(member);
            }
        }
    }


}
