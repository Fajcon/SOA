package pl.edu.agh.soa.lab1;

import org.jboss.annotation.security.SecurityDomain;
import org.jboss.ws.api.annotation.WebContext;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@Stateless
@WebService
@SecurityDomain("my-security-domain")
@DeclareRoles({"TestRole"})
@WebContext(contextRoot="/SOA-1", urlPattern="/Hello", authMethod="BASIC",transportGuarantee="NONE", secureWSDLAccess = false)
@SOAPBinding(style=SOAPBinding.Style.RPC)
public class Hello {
    @RolesAllowed("TestRole")
    @WebMethod(action = "greet_sb")
    @WebResult(name = "Hi")
    public String Hi(){
        return "Hello";
    }
}
