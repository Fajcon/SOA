package pl.edu.agh.soa.lab1.jwt;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

@javax.ws.rs.NameBinding
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface JWTTokenNeeded {

}
