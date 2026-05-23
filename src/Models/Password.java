package Models;
//Imports are for Hashing and Secure Random Generation, as well as Base64 encoding for storage
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;



public class Password {

    // Variables for hashing parameters
    private static final int ITERATIONS = 600000; 
    private static final int HASH_LENGTH_BITS = 256;
    private static final int SALT_LENGTH_BYTES = 16;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";

    /**
     * Creates a secure password hash from a plaintext password. This method trims whitespace and converts the password to a char array for secure handling.
     * @param password The plaintext password to hash.
     * @return A string containing the iteration count, salt, and hash, encoded in Base64, separated by colons.
     */
    private String createPassword(String password){
        password = password.trim();
        char[] passwordChars = password.toCharArray();//Converts String to Char array as Chars don't linger in memory as Strings do
        return createHash(passwordChars);
    }

    /**
     * Generates a hash for the given password . It creates a random salt, hashes the password with the specified parameters, and returns a formatted string.
     * @param password The password as a char array to be hashed.
     * @return A string in the format "iterations:salt:hash" where salt and hash are Base64 encoded.
     * @throws Exception
     */
    public static String createHash(char[] password) {
        //Generates a random salt, a piece of data added to a password before hashing, makes brute-forcing much harder
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH_BYTES];
        random.nextBytes(salt);

        // Configure specifications and generate hash
        try {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, HASH_LENGTH_BITS);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
        byte[] hash = skf.generateSecret(spec).getEncoded();

        return ITERATIONS + ":" + 
               Base64.getEncoder().encodeToString(salt) + ":" + 
               Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm not found: " + ALGORITHM, e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("Error generating password hash", e);
        }
    }

    /**
     * Verifies an input password against a stored hash record. It parses the stored record, hashes the input password with the same parameters, and compares the results.
     * @param inputPassword The password to verify, as a char array.
     * @param storedRecord The stored hash record in the format "iterations:salt:hash".
     * @return True if the password is correct, false otherwise.
     * @throws Exception If the stored record is malformed or if hashing fails.
     */
    public static boolean verifyPassword(char[] inputPassword, String storedRecord) {
        // Parse the stored database record
        String[] parts = storedRecord.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = Base64.getDecoder().decode(parts[1]);
        byte[] storedHash = Base64.getDecoder().decode(parts[2]);

        //Hash incoming password with same parameters and salt as storedRecord, then compare to stored hash
        try {
        PBEKeySpec spec = new PBEKeySpec(inputPassword, salt, iterations, storedHash.length * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
        byte[] testHash = skf.generateSecret(spec).getEncoded();

        //Secure Anti-Timing Attack comparison
        return MessageDigest.isEqual(storedHash, testHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm not found: " + ALGORITHM, e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("Error verifying password hash", e);
        }
    }
    
}
