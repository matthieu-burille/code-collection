package fr.miage.coursws.model.repository;


import fr.miage.coursws.model.entity.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CourseRepository extends MongoRepository<Course,String> {

    public List<Course> findByCategory(String category);

}
