package pl.edu.agh.soa.lab1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.xml.ws.BindingProvider;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class ClientUtilities {
    public void setCredentials(BindingProvider provider, String user, String password) {
        provider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, user);
        provider.getRequestContext().put(BindingProvider. PASSWORD_PROPERTY, password);
    }

    public String fileToBase64(String path) throws IOException {
        byte[] fileContent = Files.readAllBytes(new File(path).toPath());
        return Base64.getEncoder().encodeToString(fileContent);
    }

    public void base64ToFile(String imgBase64) throws IOException {
        byte[] decodedImg = Base64.getDecoder().decode(imgBase64.getBytes(StandardCharsets.UTF_8));
        Path destinationFile = Paths.get("responseImage.jpeg");
        Files.write(destinationFile, decodedImg);
    }

    public void showLegoSet(LegoSetSoap legoSet){
        legoSet.setBoxGraphicBase64("*****");
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println(mapper.writeValueAsString(legoSet));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
