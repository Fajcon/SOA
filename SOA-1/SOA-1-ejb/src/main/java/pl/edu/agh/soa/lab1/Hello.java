package pl.edu.agh.soa.lab1;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import org.jboss.security.SecurityDomain;

@Stateless
@WebService
@SecurityDomain("cxfservice-security-domain")
@DeclareRoles({"TestRole"})
@WebContext(contextRoot="/SOA-1", urlPattern="/*", authMethod="BASIC",
        transportGuarantee="NONE", secureWSDLAccess = false)
@SOAPBinding(style=SOAPBinding.Style.RPC)
public class Hello {
    @RolesAllowed("TestRole")
    @WebMethod(action = "greet_sb")
    @WebResult(name = "Hi")
    public String Hi(){
        return "Hello";
    }
}
