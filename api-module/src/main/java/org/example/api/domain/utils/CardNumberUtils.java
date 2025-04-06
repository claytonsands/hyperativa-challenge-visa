package org.example.api.domain.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Component
public class CardNumberUtils {

    @Value("${app.encryption.key}")
    private String secretKey;

    public byte[] encrypt(String input) throws Exception {
        SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
    }

    public String mask(String cardNumber) {
        return cardNumber.replaceAll("(\\d{6})\\d{6}(\\d{4})", "$1******$2");
    }
}
