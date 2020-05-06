package pl.edu.agh.soa.lab1.jwt;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class KeyGeneratorImpl implements KeyGenerator {
    @Override
    public Key generateKey() {
        String keyString = "kluczkluczkluczkluczkluczklucz";
        return new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "DES");
    }
}