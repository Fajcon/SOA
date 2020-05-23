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
import java.util.Collections;
import java.util.List;

@Path("/Storage")
@Api(value = "/Storage")
@ApplicationScoped
public class StorageController {
    @EJB
    StorageDao storageDao;
    @EJB
    LegoSetDao legoSetDao;

    @GET
    @Path("/{storageId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses({@ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 404, message = "Not Found")})
    public Response getLegoSet(@PathParam("storageId") Long storageId) throws Exception {
        Storage storage;
        try {
            storage = storageDao.find(storageId);
        } catch (Exception exception) {
            return Response
                    .ok()
                    .status(Response.Status.NOT_FOUND)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET")
                    .build();
        }
        return Response
                .ok()
                .entity(storage)
                .status(Response.Status.OK)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET")
                .build();
    }

    @POST
    @Path("")
    @JWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses({@ApiResponse(code = 201, message = "Created")})
    public Response addStorage(@QueryParam("name") String name) {
        Storage storage = new Storage
                .StorageBuilder()
                .name(name)
                .legoSets(Collections.emptyList())
                .build();
        storageDao.save(storage);
        return Response
                .ok()
                .status(Response.Status.CREATED)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST")
                .build();
    }

    @PUT
    @Path("/{storageId}")
    @JWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses({@ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 404, message = "Not Found")})
    public Response addLegoSetToStorage(@PathParam("storageId") Long storageId, @QueryParam("legoSetId") Long legoSetId) {
        try {
            Storage storage = storageDao.find(storageId);
            LegoSet legoSet = legoSetDao.find(legoSetId);
        } catch (Exception exception) {
            return Response
                    .ok()
                    .status(Response.Status.NOT_FOUND)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET")
                    .build();
        }
        storageDao.update(storageId, legoSetId);
        return Response
                .ok()
                .status(Response.Status.OK)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "PUT")
                .build();
    }

    @GET
    @Path("/byName/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses({@ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 404, message = "Not Found")})
    public Response getStoragesByName(@PathParam("name") String name){
        List<Storage> result = storageDao.findByName(name);
        return Response
                .ok()
                .entity(result)
                .status(Response.Status.OK)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET")
                .build();
    }
}
