package pl.edu.agh.soa.lab1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import pl.edu.agh.soa.lab1.jwt.JWTTokenNeeded;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import protobuf.LegoSetsProtoBuf.LegoSetId;
import protobuf.LegoSetsProtoBuf.LegoSetsIdProtoBuf;

@Path("/LegoSet")
@Api(value = "/LegoSet")
@ApplicationScoped
public class LegoSetController {
    @EJB
    LegoSetDao legoSetDao = new LegoSetDao();
    List<LegoSet> legoSets = new ArrayList<LegoSet>();
    Long maxNumber = 0L;
    public LegoSetController() {
    }

    @GET
    @Path("/{legoSetId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses({@ApiResponse(code=200, message="Success"), @ApiResponse(code=404, message="Not Found")})
    public Response getLegoSet(@PathParam("legoSetId") Long setNumber) throws Exception {
        LegoSet legoSet;
        try {
            legoSet = legoSetDao.find(setNumber);
        } catch (Exception exception){
            return Response
                    .ok()
                    .status(Response.Status.NOT_FOUND)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET")
                    .build();
        }
        try {
            legoSet.setBoxGraphicBase64(fileToBase64(legoSet.getImgPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response
                .ok()
                .entity(legoSet)
                .status(Response.Status.OK)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET")
                .build();
    }

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses({@ApiResponse(code=200, message="Success")})
    public Response getLegoSet() throws Exception {
        List<LegoSet> result = legoSetDao.findAll();
        return Response
                .ok()
                .entity(result)
                .status(Response.Status.OK)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET")
                .build();
    }

    @POST
    @Path("")
    @JWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses({@ApiResponse(code=201, message="Created")})
    public Response addLegoSet(@QueryParam("name") String name, @QueryParam("boxGraphic") String boxGraphicBase64) {
        try {
            base64ToFile(boxGraphicBase64, name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LegoSet legoSet = new LegoSet
                .LegoSetBuilder()
                .legoSetNumber(maxNumber+=1)
                .name(name)
                .boxGraphicBase64(boxGraphicBase64)
                .imgPath(name)
                .legoPacks(new ArrayList<>())
                .build();
        legoSets.add(legoSet);
        legoSetDao.save(legoSet);
        return Response
                .ok()
                .status(Response.Status.CREATED)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST")
                .build();
    }

    @PUT
    @Path("/{legoSetId}")
    @JWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses({@ApiResponse(code=200, message="Success"), @ApiResponse(code=404, message="Not Found")})
    public Response addLegoBlock(@PathParam("legoSetId") Long setNumber, @QueryParam("color") String color, @QueryParam("partNumber") Long partNumber){
        LegoSet legoSet;
        try {
            legoSet = legoSetDao.find(setNumber);
        } catch (Exception exception){
            return Response
                    .ok()
                    .status(Response.Status.NOT_FOUND)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET")
                    .build();
        }
        LegoBlock legoBlock = new LegoBlock(color, partNumber);
        legoSet.getLegoPacks().add(new LegoPack(legoBlock, 5L));
        legoSetDao.update(legoSet);
        return Response
                .ok()
                .status(Response.Status.OK)
                .entity(legoSet)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "PUT")
                .build();
    }

    /**
     * Example endpoint for protocol buffer.
     */
    @GET
    @Path("/setsId")
    @Produces("application/protobuf")
    @ApiResponses({@ApiResponse(code=200, message="Success")})
    public Response getSetsId() {
        LegoSetsIdProtoBuf.Builder legoSetsIdProtoBufBuilder = LegoSetsIdProtoBuf.newBuilder();
        legoSets.forEach(legoSet -> legoSetsIdProtoBufBuilder
                .addLegoSetId(LegoSetId.newBuilder().setLegoSetId(Math.toIntExact(legoSet.getLegoSetNumber())))
                .build()
        );
        LegoSetsIdProtoBuf legoSetsIdProtoBuf = legoSetsIdProtoBufBuilder.build();
        return Response
                .ok(legoSetsIdProtoBuf)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET")
                .build();
    }

    public void base64ToFile(String imgBase64, String imgPath) throws IOException {
        byte[] decodedImg = Base64.getDecoder().decode(imgBase64.getBytes(StandardCharsets.UTF_8));
        java.nio.file.Path destinationFile = Paths.get(imgPath);
        Files.write(destinationFile, decodedImg);
    }

    public String fileToBase64(String path) throws IOException {
        byte[] fileContent = Files.readAllBytes(new File(path).toPath());
        return Base64.getEncoder().encodeToString(fileContent);
    }
}
