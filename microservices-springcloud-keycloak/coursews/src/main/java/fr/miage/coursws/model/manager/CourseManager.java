package fr.miage.coursws.model.manager;

import fr.miage.coursws.exception.CourseNotCompliantException;
import fr.miage.coursws.exception.CourseNotFoundException;
import fr.miage.coursws.model.entity.Course;
import fr.miage.coursws.model.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class CourseManager {

    @Autowired
    private CourseRepository courseRepository;

    public Collection<Course> findAllCourse(){
        return courseRepository.findAll();
    }

    public Course saveCourse(Course course) throws CourseNotCompliantException {
        if (course.getTitle()==null || course.getBody()==null || course.getCategory()==null || course.getAcademicLevel()==null || course.getLocation()==null){
            throw new CourseNotCompliantException();
        }
        return courseRepository.save(course);
    }

    public Course findCourseById(String id) throws CourseNotFoundException {
        Optional<Course> opt = courseRepository.findById(id);
        if(opt.isEmpty()){
            throw new CourseNotFoundException();
        }
        return opt.get();
    }

    public Collection<Course> findCourseByCategory(String category){
        return courseRepository.findByCategory(category);
    }

    public void deleteCourse(String idCourse) throws CourseNotFoundException {
        boolean exists= courseRepository.existsById(idCourse);
        if(!exists){
            throw new CourseNotFoundException();
        }
        courseRepository.deleteById(idCourse);
    }
}
