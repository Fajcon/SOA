package pl.edu.agh.soa.lab1;

import io.swagger.jaxrs.config.BeanConfig;

import javax.servlet.ServletConfig;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;


@ApplicationPath("/api")
public class LegoSetApp extends Application {
    public LegoSetApp(@Context ServletConfig servletConfig) {
        super();

        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setTitle("Lego Rest Api");
        beanConfig.setHost("localhost:8080");
        beanConfig.setBasePath("/SOA-1-web/api");
        beanConfig.setResourcePackage(LegoSetController.class.getPackage().getName());
        beanConfig.setScan(true);
    }

}
