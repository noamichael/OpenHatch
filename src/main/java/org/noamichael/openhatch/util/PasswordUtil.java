package org.noamichael.openhatch.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Michael
 */
public class PasswordUtil {

    public static String hash(String password) {
        String pass = password + reverseString(password);
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(pass.getBytes());
            byte[] bytes = md.digest();
            String sb = "";
            for (byte b : bytes) {
                sb = sb + Integer.toHexString(b & 0xFF);
            }
            pass = sb;
        } catch (NoSuchAlgorithmException ex) {
            System.err.println("NO SUCH ALGORITHM!");
        }
        return pass;
    }

    private static String reverseString(String string) {
        char[] finalString = new char[string.length()];
        int index = 0;
        for (int i = string.length() - 1; i > 0; i--) {
            finalString[index] = string.charAt(i);
            index++;
        }

        return String.valueOf(finalString);
    }
}
