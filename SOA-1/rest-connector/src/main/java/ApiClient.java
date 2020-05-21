import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import pl.edu.agh.soa.lab1.LegoSet;
import pl.edu.agh.soa.lab1.Storage;
import protobuf.LegoSetsProtoBuf;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

public class ApiClient {
    public static void main(String[] args) throws URISyntaxException {
        ApiClient apiClient = new ApiClient();
        String token = apiClient.getToken("login", "password");
        System.out.println(token);
        apiClient.addLegoSet("test", "/lego123.jpeg", token);
        apiClient.getLegoSets();
        apiClient.getLegoSetById(1L);
        apiClient.addLegoBlock(1L, "red", 1234L, token);
        apiClient.getLegoSetById(1L);
        apiClient.getProtoBufId();

        apiClient.addStorage("testowy_magazyn", token, 1L);
        apiClient.addLegoSetToStorage(1L, 2L, token);
        apiClient.getStorageById(1L);

        apiClient.getStorageByName("testowy_magazyn");
    }

    public void getLegoSets() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/SOA-1-web/api/LegoSet");
        List<LegoSet> legoSetList = target.request(MediaType.APPLICATION_JSON).get(new GenericType<List<LegoSet>>() {
        });
        client.close();
        System.out.println(legoSetList.toString());
    }

    public void getStorageByName(String name) {
        Client client = ClientBuilder.newClient();
        Response response = client.target("http://localhost:8080/SOA-1-web/api/Storage/byName/")
                .path(String.valueOf(name))
                .request(MediaType.APPLICATION_JSON)
                .get();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            List<Storage> storages = response.readEntity(new GenericType<List<Storage>>() {});
            client.close();
            System.out.println(storages.toString());
        } else {
            System.out.println("Error: " + response.getStatus());
        }
    }

    public void getLegoSetById(Long legoSetId) {
        Client client = ClientBuilder.newClient();
        Response response = client.target("http://localhost:8080/SOA-1-web/api/LegoSet/")
                .path(String.valueOf(legoSetId))
                .request(MediaType.APPLICATION_JSON)
                .get();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            LegoSet legoSet = response.readEntity(LegoSet.class);
            client.close();
            try {
                base64ToFile(legoSet.getBoxGraphicBase64());
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(legoSet.toString());
        } else {
            System.out.println("Error: " + response.getStatus());
        }
    }

    public void getStorageById(Long storageId) {
        Client client = ClientBuilder.newClient();
        Response response = client.target("http://localhost:8080/SOA-1-web/api/Storage/")
                .path(String.valueOf(storageId))
                .request(MediaType.APPLICATION_JSON)
                .get();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            Storage storage = response.readEntity(Storage.class);
            client.close();
            System.out.println(storage.toString());
        } else {
            System.out.println("Error: " + response.getStatus());
        }
    }

    public String getToken(String login, String password) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/SOA-1-web/api/auth/login");
        Form form = new Form();
        form.param("login", login);
        form.param("password", password);
        Response response = target
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        MultivaluedMap<String, String> headers = response.getStringHeaders();
        String token = headers.getFirst("Authorization");
        return token;
    }

    public void addLegoSet(String name, String imgPath, String token) throws URISyntaxException {
        String pathToImg = Paths.get(ApiClient.class.getResource(imgPath).toURI()).toString();
        String base64Img = "";
        try {
            base64Img = fileToBase64(pathToImg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://localhost:8080/SOA-1-web/api/LegoSet")
                .queryParam("name", name)
                .queryParam("boxGraphic", base64Img);

        Response response = target.request().header("Authorization", token).post(null);
        response.close();
    }

    public void addStorage(String name, String token, Long legoSetId){
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://localhost:8080/SOA-1-web/api/Storage")
                .queryParam("name", name)
                .queryParam("legoSetId", legoSetId);
        Response response = target.request().header("Authorization", token).post(null);
        response.close();
    }


    public void addLegoBlock(Long legoSetId, String color, Long partNumber, String token) {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://localhost:8080/SOA-1-web/api/LegoSet/")
                .path(String.valueOf(legoSetId))
                .queryParam("color", color)
                .queryParam("partNumber", partNumber);
        Response response = target.request().header("Authorization", token).put(null);
        response.close();
    }

    public void addLegoSetToStorage(Long legoSetId, Long storageId, String token){
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://localhost:8080/SOA-1-web/api/Storage/")
                .path(String.valueOf(storageId))
                .queryParam("legoSetId", legoSetId);
        Response response = target.request().header("Authorization", token).put(null);
        response.close();
    }

    public void getProtoBufId() {
        Client client = ClientBuilder.newBuilder().register(WidgetProtocMessageBodyProvider.class).build();
        WebTarget target = client.target("http://localhost:8080/SOA-1-web/api/LegoSet/setsId");
        LegoSetsProtoBuf.LegoSetsIdProtoBuf legoSetsIdProtoBuf = target
                .request()
                .get(LegoSetsProtoBuf.LegoSetsIdProtoBuf.class);
        for (LegoSetsProtoBuf.LegoSetId legoSetId : legoSetsIdProtoBuf.getLegoSetIdList()) {
            System.out.println(legoSetId.getLegoSetId());
        }

    }

    public String fileToBase64(String path) throws IOException {
        byte[] fileContent = Files.readAllBytes(new File(path).toPath());
        return Base64.getEncoder().encodeToString(fileContent);
    }

    public void base64ToFile(String imgBase64) throws IOException {
        byte[] decodedImg = Base64.getDecoder().decode(imgBase64.getBytes(StandardCharsets.UTF_8));
        Path destinationFile = Paths.get("rest-connector/src/main/resources/responseImage.jpeg");
        Files.write(destinationFile, decodedImg);
    }
}
