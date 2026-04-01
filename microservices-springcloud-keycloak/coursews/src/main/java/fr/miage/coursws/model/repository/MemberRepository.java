package fr.miage.coursws.model.repository;

import fr.miage.coursws.model.entity.Member;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MemberRepository extends MongoRepository<Member,String> {
}
