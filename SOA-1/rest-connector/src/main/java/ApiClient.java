import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

public class ApiClient {
    public static void main(String[] args) {
        getLegoSet();
    }
    public static void getLegoSet(){
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/SOA-1-web/api/LegoSet");
        Response response = target.request().get();
        String resp = response.readEntity(String.class);
        System.out.println(resp);
    }
}
