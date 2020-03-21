package pl.edu.agh.soa.lab1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.annotation.security.SecurityDomain;
import org.jboss.ws.api.annotation.WebContext;
import pl.edu.agh.soa.lab1.exceptions.SetNotFoundException;
import pl.edu.agh.soa.lab1.model.LegoBlock;
import pl.edu.agh.soa.lab1.model.LegoPack;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.io.*;
import java.util.Collections;

@Stateless
@WebService
@SecurityDomain("my-security-domain")
@DeclareRoles({"TestRole"})
@WebContext(contextRoot = "/SOA-1", urlPattern = "/LegoSet", authMethod = "BASIC", transportGuarantee = "NONE", secureWSDLAccess = false)
public class LegoSetController {
    @RolesAllowed("TestRole")
    @WebMethod(action = "find")
    @WebResult(name = "getLegoSet")
    public LegoSet getLegoSetByNumber(@WebParam(name = "number") Long no) throws SetNotFoundException {
        ObjectMapper mapper = new ObjectMapper();
        LegoSet legoSet = new LegoSet();
        try {
            legoSet = mapper.readValue(new File(String.valueOf(no)), LegoSet.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return legoSet;
    }

    @RolesAllowed("TestRole")
    @WebMethod(action = "add")
    @WebResult(name = "addLegoSets")
    public Long addLegoSets(@WebParam(name = "name") String name, @WebParam(name = "boxGraphic") String boxGraphicBase64, @WebParam(name = "SetNumber") Long setNumber) {
        LegoBlock legoBlock = new LegoBlock("red", 1234, "long4");
        LegoSet legoSet = new LegoSet
                .LegoSetBuilder()
                .legoSetNumber(setNumber)
                .name(name)
                .boxGraphicBase64(boxGraphicBase64)
                .legoPacks(Collections.singletonList(new LegoPack(legoBlock, 5L)))
                .build();
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonString = mapper.writeValueAsString(legoSet);
            System.out.println(jsonString);
            FileOutputStream outputStream = new FileOutputStream(String.valueOf(legoSet.getLegoSetNumber()));
            byte[] strToBytes = jsonString.getBytes();
            outputStream.write(strToBytes);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return legoSet.getLegoSetNumber();
    }
}
