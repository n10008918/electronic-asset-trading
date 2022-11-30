package ServerSideApp;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The Hash password function that takes the user entered password and converts it to a hash
 */
public class HashPassword {

    /**
     * @param passwordToHash the password that has been entered by the user
     * @return the hash value of the password
     */
    public static String StringToHash(String passwordToHash) {
        try {
            //
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(passwordToHash.getBytes());

            BigInteger numb = new BigInteger(1, digest);

            StringBuilder hash = new StringBuilder(numb.toString(16));

            while (hash.length() < 32) {
                hash.insert(0, "0");
            }
            return hash.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}



