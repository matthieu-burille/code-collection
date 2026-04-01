package fr.miage.coursws.model.manager;

import fr.miage.coursws.exception.MemberAlreadyExistsException;
import fr.miage.coursws.exception.MemberNotFoundException;
import fr.miage.coursws.model.entity.Course;
import fr.miage.coursws.model.entity.Member;
import fr.miage.coursws.model.repository.MemberRepository;
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

    public boolean existsByUsername(String username){
        return memberRepository.existsById(username);
    }

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


    public void addCourse(String username, String idCourse) throws MemberNotFoundException {
        Member member= findMemberById(username);
        List<String> courses = member.getIdCourses();
        if(courses==null){
            courses=new ArrayList<>();
        }
        courses.add(idCourse);
        member.setIdCourses(courses);
        memberRepository.save(member);
    }

    public void deleteCourse(String username, String idCourse) throws MemberNotFoundException {
        Member member= findMemberById(username);
        List<String> courses= member.getIdCourses();
        courses.remove(idCourse);
        member.setIdCourses(courses);
        memberRepository.save(member);
    }



}
