package pl.edu.agh.soa.lab1;

import javax.xml.ws.BindingProvider;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class Client {
    public static void main(String[] args) {
        ClientUtilities clientUtilities = new ClientUtilities();
        LegoSetControllerService legoSetControllerService = new LegoSetControllerService();
        LegoSetController legoSetController = legoSetControllerService.getLegoSetControllerPort();
        clientUtilities.setCredentials((BindingProvider) legoSetController, "testUser", "testUser");
        try {
            addLegoSets(legoSetController);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        LegoSetSoap legoSet = getLegoSet(321L, legoSetController);
        clientUtilities.showLegoSet(legoSet);
        legoSet = getLegoSet(123L, legoSetController);
        clientUtilities.showLegoSet(legoSet);
    }

    private static void addLegoSets(LegoSetController legoSetController) throws URISyntaxException {
        ClientUtilities clientUtilities = new ClientUtilities();
        String pathToImg = Paths.get(Client.class.getResource("/lego123.jpeg").toURI()).toString();
        String base64Img = "";
        try {
            base64Img = clientUtilities.fileToBase64(pathToImg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        legoSetController.addLegoSets("myName", base64Img, 123L);
        legoSetController.addLegoSets("myName2", base64Img, 321L);
    }

    private static LegoSetSoap getLegoSet(Long legoSetNumber, LegoSetController legoSetController){
        ClientUtilities clientUtilities = new ClientUtilities();
        LegoSetSoap response = new LegoSetSoap();
        try {
            response = legoSetController.getLegoSetByNumber(legoSetNumber);
            clientUtilities.base64ToFile(response.boxGraphicBase64);
        } catch (IOException | SetNotFoundException_Exception e) {
            e.printStackTrace();
        }
        return response;
    }


}
