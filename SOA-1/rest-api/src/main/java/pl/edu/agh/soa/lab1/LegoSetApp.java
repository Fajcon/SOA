package pl.edu.agh.soa.lab1;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class LegoSetApp extends Application {
    public LegoSetApp(){

    }
    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<>();
        s.add(Hello.class);
        s.add(LegoSetController.class);
        return s;
    }
}
