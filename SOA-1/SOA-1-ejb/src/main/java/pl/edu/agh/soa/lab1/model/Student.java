package pl.edu.agh.soa.lab1.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;
import java.util.List;

public class Student {
    private String firstName;
    private String lastName;
    private String album;
    private List<Course> courses;

    public Student(){}

    public Student(String firstName, String lastName, String album, List<Course> courses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.album = album;
        this.courses = courses;
    }

    public Student(String firstName, String lastName, String album) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.album = album;
        this.courses = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastNamel) {
        this.lastName = lastNamel;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    @XmlElementWrapper(name = "courses")
    @XmlElement(name = "course")
    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course){
        courses.add(course);
    }

    @Override
    public String toString() {
        return "Student{" +
                "firstName='" + firstName + '\'' +
                ", lastNamel='" + lastName + '\'' +
                ", album='" + album + '\'' +
                ", courses=" + courses +
                '}';
    }

}
