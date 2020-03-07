package pl.edu.agh.soa.lab1;

import javax.ejb.Stateless;
import javax.jws.WebResult;
import javax.jws.WebService;

@Stateless
@WebService
public class Hello {
    @WebResult
    public String Hi(){
        return "Hello";
    }
}
