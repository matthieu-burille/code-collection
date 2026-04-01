package fr.miage.coursws.model.entity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.miage.coursws.model.converter.MembreConverter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "courses")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Course {

    @Id
    private String _id;
    private String title;
    private String body;
    private String category;
    private String academicLevel;
    private String location;

    @JsonSerialize(converter = MembreConverter.class)
    private Member author;


    public Course(String idCourse, String title, String body, String category, String academicLevel, String location, Member author) {
        this._id = idCourse;
        this.title = title;
        this.body = body;
        this.category = category;
        this.academicLevel = academicLevel;
        this.location = location;
        this.author = author;
    }

    public Course() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAcademicLevel() {
        return academicLevel;
    }

    public void setAcademicLevel(String academicLevel) {
        this.academicLevel = academicLevel;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Member getAuthor() {
        return author;
    }

    public void setAuthor(Member author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(_id, course._id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id);
    }
}
