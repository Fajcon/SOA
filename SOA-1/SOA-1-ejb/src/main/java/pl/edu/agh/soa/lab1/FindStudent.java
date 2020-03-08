package pl.edu.agh.soa.lab1;

import pl.edu.agh.soa.lab1.model.Course;
import pl.edu.agh.soa.lab1.model.Student;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Stateless
@WebService
public class FindStudent {
    @WebMethod(action = "find")
    @WebResult(name = "getStudent")
    public Student find(@WebParam(name = "indexno") String no) {
        List<Student> students = Arrays.asList(new Student("Olek", "Czajka", "1234", Collections.singletonList(new Course("SOA", 5))),
                new Student("Ala", "Sójka", "2345", Arrays.asList(new Course("SOA", 5), new Course("SD", 10))));
        for (Student x : students) {
            if (x.getAlbum().equalsIgnoreCase(no)) {
                return x;
            }
        }
        return null;
    }
}
