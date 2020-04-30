package pl.edu.agh.soa.lab1;

import javax.jws.WebParam;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Path("/LegoSet")
public class LegoSetController {
    List<LegoSet> legoSets = new ArrayList<LegoSet>();
    Long maxNumber = 1L;
    public LegoSetController() {
        LegoBlock legoBlock = new LegoBlock("red", 1234, "long4");
        LegoBlock legoBlock2 = new LegoBlock("blue", 4321, "long1");
        LegoSet legoSet = new LegoSet
                .LegoSetBuilder()
                .legoSetNumber(maxNumber)
                .name("test")
                .boxGraphicBase64("test")
                .legoPacks(Arrays.asList(new LegoPack(legoBlock, 5L), new LegoPack(legoBlock2, 4L)))
                .build();
        legoSets.add(legoSet);
    }

    @GET
    @Path("/{setNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public LegoSet getLegoSet(@PathParam("setNumber") Long setNumber) throws Exception {
        return legoSets
                .stream()
                .filter(l -> l.getLegoSetNumber().equals(setNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No LegoSet found with the setNumber: "+ setNumber));
    }

    @POST
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Long addLegoSet(@QueryParam("name") String name, @QueryParam("boxGraphic") String boxGraphicBase64) {
        LegoBlock legoBlock = new LegoBlock("red", 1234, "long4");
        LegoBlock legoBlock2 = new LegoBlock("blue", 4321, "long1");
        LegoSet legoSet = new LegoSet
                .LegoSetBuilder()
                .legoSetNumber(maxNumber+=1)
                .name(name)
                .boxGraphicBase64(boxGraphicBase64)
                .legoPacks(Arrays.asList(new LegoPack(legoBlock, 5L), new LegoPack(legoBlock2, 4L)))
                .build();
        return maxNumber;
    }
}
