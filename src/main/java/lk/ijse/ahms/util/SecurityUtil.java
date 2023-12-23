package lk.ijse.ahms.util;

import java.util.Base64;

public class SecurityUtil {

    public static String encoder(String value){
        byte[] encodedBytes = Base64.getEncoder().encode(value.getBytes());
        String encodedString = new String(encodedBytes);
        return encodedString;
    }

    public static String decoder(String value){
        byte[] decodedBytes = Base64.getDecoder().decode(value.getBytes());
        String decodedString = new String(decodedBytes);
        return decodedString;
    }
}
