package pl.edu.agh.soa.lab1;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@Stateless
@WebService
public class HelloWorld
{
    @WebMethod(action = "Hello")
    @WebResult(name = "greet")
    public String Hello(@WebParam(name="answer") String name){ //nazwa parametru, który jest przekazywany w zap
        return "Hellooo" + name;
    }
}