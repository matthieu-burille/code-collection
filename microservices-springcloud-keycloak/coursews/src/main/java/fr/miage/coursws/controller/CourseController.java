package fr.miage.coursws.controller;


import fr.miage.coursws.exception.CourseNotCompliantException;
import fr.miage.coursws.exception.CourseNotFoundException;
import fr.miage.coursws.exception.MemberNotFoundException;
import fr.miage.coursws.model.converter.TokenToMemberConverter;
import fr.miage.coursws.model.entity.Course;
import fr.miage.coursws.model.entity.Member;
import fr.miage.coursws.model.manager.CourseManager;
import fr.miage.coursws.model.manager.MemberManager;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class CourseController {

    @Autowired
    private CourseManager courseManager;

    @Autowired
    private MemberManager memberManager;



    /**
     * Permet de voir tout les cours
     * @param category
     * @return
     */
    @GetMapping(value = "/course")
    public ResponseEntity findAllCourse(KeycloakAuthenticationToken token, @RequestParam(required = false) String category){
        Collection<Course> courses=null;
        if (category!=null){
            courses = courseManager.findCourseByCategory(category);
        }
        else{
            courses=courseManager.findAllCourse();
        }
        return ResponseEntity.ok(courses);
    }




    /**
     * Permet à un utilisateur connecté de publier un cours
     * @param course
     * @return
     */
    @PostMapping(value = "/course")
    public ResponseEntity postCourse(KeycloakAuthenticationToken token,@RequestBody Course course){
        Member member= TokenToMemberConverter.convertTokenToMembre(token);
        course.setAuthor(member);
        try {
            Course res=courseManager.saveCourse(course);
            memberManager.addCourse(member.getUsername(), res.get_id());
            return ResponseEntity.status(201).body(course);
        } catch (CourseNotCompliantException e) {
            return ResponseEntity.badRequest().build();
        } catch (MemberNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * Permet à un utilisateur de selectionner un cours en particuliers
     * @param idCourse
     * @return
     */
    @GetMapping(value = "/course/{idCourse}")
    public ResponseEntity getCourseId(KeycloakAuthenticationToken token,@PathVariable String idCourse){
        try {
            Course course = courseManager.findCourseById(idCourse);
            return ResponseEntity.ok(course);
        } catch (CourseNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/course/{idCourse}")
    public ResponseEntity putCourse(KeycloakAuthenticationToken token, @PathVariable String idCourse, @RequestBody Course course){
        Member member= TokenToMemberConverter.convertTokenToMembre(token);
        try {
            ResponseEntity res=null;
            Course course1 = courseManager.findCourseById(idCourse);
            System.out.println(member.getUsername());
            System.out.println(course1.getAuthor());
            if(member.equals(course1.getAuthor())) {
                course.set_id(idCourse);
                course.setAuthor(member);
                course = courseManager.saveCourse(course);
                res= ResponseEntity.status(201).body(course);
            }
            else{
                res= ResponseEntity.status(401).build();
            }
            return res;
        } catch (CourseNotCompliantException e) {
            return ResponseEntity.badRequest().build();
        } catch (CourseNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Permet à un utilisateur de supprimer ses cours
     * @param idCourse
     * @return
     */
    @DeleteMapping(value = "/course/{idCourse}")
    public ResponseEntity deleteCourse(KeycloakAuthenticationToken token, @PathVariable String idCourse) {
        Member member= TokenToMemberConverter.convertTokenToMembre(token);
        try {
            ResponseEntity res=null;
            Course course = courseManager.findCourseById(idCourse);
            if(member.getUsername().equals(course.getAuthor())){
                courseManager.deleteCourse(idCourse);
                memberManager.deleteCourse(member.getUsername(), idCourse);
                res= ResponseEntity.noContent().build();
            }
            else{
                res= ResponseEntity.status(401).build();
            }
            return res;
        }
        catch (CourseNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        catch (MemberNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }





}
