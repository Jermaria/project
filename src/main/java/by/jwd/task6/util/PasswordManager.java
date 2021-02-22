package by.jwd.task6.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Optional;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class PasswordManager {
    private static final Logger logger = LogManager.getLogger();
    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final int HASH_ITERATION_NUM = 1001;
    private static final int FINAL_LENGTH = 25;

    public static String generateSalt() {       
        byte[]salt = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
    
    public static Optional<String> generateSaltedPassword(char[] password, String salt) {
        Optional<String> passwordOptional = Optional.empty();
        int iterationNum, finalLength;
        byte[] saltBytes;
        
        iterationNum = HASH_ITERATION_NUM ;
        finalLength = FINAL_LENGTH;
        saltBytes = Base64.getDecoder().decode(salt);
        KeySpec spec = new PBEKeySpec(password, saltBytes, iterationNum, finalLength);
        String algorithm = ALGORITHM;
        
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(algorithm);
            byte[] saltedPassword;
            saltedPassword = factory.generateSecret(spec).getEncoded();
            passwordOptional = Optional.of(Base64.getEncoder().encodeToString(saltedPassword));
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.ERROR, e);
        } catch (InvalidKeySpecException e) {
            logger.log(Level.ERROR, e);
        }
        return passwordOptional;
    }
}
