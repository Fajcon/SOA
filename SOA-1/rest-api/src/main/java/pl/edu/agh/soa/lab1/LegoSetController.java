package pl.edu.agh.soa.lab1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Path("/LegoSet")
@Api(value = "/LegoSet")
@ApplicationScoped
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
    @Path("/{legoSetId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLegoSet(@PathParam("legoSetId") Long setNumber) throws Exception {
        System.out.println(legoSets.size());
        LegoSet legoSet = legoSets
                        .stream()
                        .filter(l -> l.getLegoSetNumber().equals(setNumber))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("No LegoSet found with the setNumber: "+ setNumber));
        return Response
                .ok()
                .entity(legoSet).header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .build();
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
        legoSets.add(legoSet);
        System.out.println(legoSets.size());
        return maxNumber;
    }
}
