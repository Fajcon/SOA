package pl.edu.agh.soa.lab1.jwt;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class KeyGeneratorImpl implements KeyGenerator {
    @Override
    public Key generateKey() {
        String keyString = "7z9IkrVj4PPzVtS9PhcsnB3m_ZXbQMfvTmpuba2zoSZRV2AQ_J";
        return new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "DES");
    }
}