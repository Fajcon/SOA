package pl.edu.agh.soa.lab1;

import io.undertow.util.FileUtils;

import javax.xml.ws.BindingProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class Client {
    public static void main(String[] args) {
        LegoSetControllerService legoSetControllerService = new LegoSetControllerService();
        LegoSetController legoSetController = legoSetControllerService.getLegoSetControllerPort();
        setCredentials((BindingProvider) legoSetController, "usertwo", "usertwo");
        System.out.println(addLegoSet(legoSetController));
        System.out.println(getLegoSet(123L, legoSetController));
    }

    private static void setCredentials(BindingProvider provider, String user, String password) {
        provider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, user);
        provider.getRequestContext().put(BindingProvider. PASSWORD_PROPERTY, password);
    }

    private static String fileToBase64(String path) throws IOException {
        byte[] fileContent = Files.readAllBytes(new File(path).toPath());
        return Base64.getEncoder().encodeToString(fileContent);
    }

    private static void base64ToFile(String imgBase64) throws IOException {
        byte[] decodedImg = Base64.getDecoder().decode(imgBase64.getBytes(StandardCharsets.UTF_8));
        Path destinationFile = Paths.get("responseImage.jpeg");
        Files.write(destinationFile, decodedImg);
    }

    private static Long addLegoSet(LegoSetController legoSetController){
        String pathToImg = "/home/kuba/SOA/SOA/SOA-1/SOA-1-connector/src/main/resources/lego123.jpeg";
        String base64Img = "";
        try {
            base64Img = fileToBase64(pathToImg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Long newSetNumber = legoSetController.addLegoSets("myName", base64Img, 123L);
        return newSetNumber;
    }

    private static LegoSet getLegoSet(Long legoSetNumber, LegoSetController legoSetController){
        LegoSet response = new LegoSet();
        try {
            response = legoSetController.getLegoSetByNumber(123L);
            base64ToFile(response.boxGraphicBase64);
        } catch (IOException | SetNotFoundException_Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
