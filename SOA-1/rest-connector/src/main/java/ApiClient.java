import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import pl.edu.agh.soa.lab1.LegoSet;
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
        apiClient.addLegoBlock(1L, "red", 1234L, "long_4", token);
        apiClient.getLegoSetById(1L);
        apiClient.getProtoBufId();
    }

    public void getLegoSets(){
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/SOA-1-web/api/LegoSet");
        List<LegoSet> legoSetList = target.request(MediaType.APPLICATION_JSON).get(new GenericType<List<LegoSet>>() {});
        client.close();
        System.out.println(legoSetList.toString());
    }

    public void getLegoSetById(Long legoSetId){
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/SOA-1-web/api/LegoSet/");
        LegoSet legoSet = target.path(String.valueOf(legoSetId)).request(MediaType.APPLICATION_JSON).get(LegoSet.class);
        client.close();
        try {
            base64ToFile(legoSet.getBoxGraphicBase64());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(legoSet.toString());
    }

    public String getToken(String login, String password){
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
        String value = response.readEntity(String.class);
        System.out.println(value);
        response.close();
    }

    public void addLegoBlock(Long legoSetId, String color, Long partNumber, String name,String token){
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://localhost:8080/SOA-1-web/api/LegoSet/")
                .path(String.valueOf(legoSetId))
                .queryParam("color", color)
                .queryParam("partNumber", partNumber)
                .queryParam("name", name);
        Response response = target.request().header("Authorization", token).put(null);
        response.close();
    }

    public void getProtoBufId(){
        Client client = ClientBuilder.newBuilder().register(WidgetProtocMessageBodyProvider.class).build();
        WebTarget target = client.target("http://localhost:8080/SOA-1-web/api/LegoSet/setsId");
        LegoSetsProtoBuf.LegoSetsIdProtoBuf legoSetsIdProtoBuf = target
                .request()
                .get(LegoSetsProtoBuf.LegoSetsIdProtoBuf.class);
        for(LegoSetsProtoBuf.LegoSetId legoSetId : legoSetsIdProtoBuf.getLegoSetIdList()){
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
