package pl.edu.agh.soa.lab1;

import pl.edu.agh.soa.lab1.model.LegoBlock;
import pl.edu.agh.soa.lab1.model.LegoSet;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Stateless
@WebService
public class LegoSetController {
    @WebMethod(action = "find")
    @WebResult(name = "getLegoSet")
    public LegoSet getLegoSetByNumber(@WebParam(name = "number") String no) {
//        List<LegoSet> legoSets = Arrays.asList(new LegoSet("Olek", "Czajka", "1234", Collections.singletonList(new LegoBlock("SOA", 5))),
//                new LegoSet("Ala", "Sójka", "2345", Arrays.asList(new LegoBlock("SOA", 5), new LegoBlock("SD", 10))));
//        for (LegoSet x : legoSets) {
//            if (x.getBoxGraphic().equalsIgnoreCase(no)) {
//                return x;
//            }
//        }
        return null;
    }
}
