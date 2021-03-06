package pl.edu.agh.soa.lab1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.annotation.security.SecurityDomain;
import org.jboss.ws.api.annotation.WebContext;
import pl.edu.agh.soa.lab1.exceptions.SetNotFoundException;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.io.*;
import java.util.Arrays;

@Stateless
@WebService
@SecurityDomain("my-security-domain")
@DeclareRoles({"TestRole"})
@WebContext(contextRoot = "/SOA-1", urlPattern = "/LegoSet", authMethod = "BASIC", transportGuarantee = "NONE", secureWSDLAccess = false)
public class LegoSetController {
    @RolesAllowed("TestRole")
    @WebMethod(action = "find")
    @WebResult(name = "getLegoSet")
    public LegoSetSoap getLegoSetByNumber(@WebParam(name = "number") Long no) throws SetNotFoundException {
        ObjectMapper mapper = new ObjectMapper();
        LegoSetSoap legoSetSoap = new LegoSetSoap();
        try {
            legoSetSoap = mapper.readValue(new File("legoSet" + no), LegoSetSoap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return legoSetSoap;
    }

    @RolesAllowed("TestRole")
    @WebMethod(action = "add")
    @WebResult(name = "addLegoSets")
    public Long addLegoSets(@WebParam(name = "name") String name, @WebParam(name = "boxGraphic") String boxGraphicBase64, @WebParam(name = "SetNumber") Long setNumber) {
        LegoBlock legoBlock = new LegoBlock("red", 1234L);
        LegoBlock legoBlock2 = new LegoBlock("blue", 4321L);

        LegoSetSoap legoSetSoap = new LegoSetSoap
                .LegoSetSoapBuilder()
                .legoSetNumber(setNumber)
                .name(name)
                .boxGraphicBase64(boxGraphicBase64)
                .legoPacks(Arrays.asList(new LegoPack(legoBlock, 5L), new LegoPack(legoBlock2, 4L)))
                .build();
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonString = mapper.writeValueAsString(legoSetSoap);
            FileOutputStream outputStream = new FileOutputStream("legoSet" + legoSetSoap.getLegoSetNumber());
            byte[] strToBytes = jsonString.getBytes();
            outputStream.write(strToBytes);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return legoSetSoap.getLegoSetNumber();
    }


}
