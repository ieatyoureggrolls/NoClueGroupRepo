package Models;
//Imports are for Hashing and Secure Random Generation, as well as Base64 encoding for storage
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;



public class Password {

    // Variables for hashing parameters
    private static final int ITERATIONS = 600000; 
    private static final int HASH_LENGTH_BITS = 256;
    private static final int SALT_LENGTH_BYTES = 16;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";


    private String createPassword(String password) throws Exception {
        password = password.trim(); // Remove leading/trailing whitespace
        char[] passwordChars = password.toCharArray();
        return createHash(passwordChars);
        // Store hashedPassword securely in the database
    }

    // 1. Create a secure password record
    public static String createHash(char[] password) throws Exception {
        // Generate random salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH_BYTES];
        random.nextBytes(salt);

        // Configure specifications and generate hash
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, HASH_LENGTH_BITS);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
        byte[] hash = skf.generateSecret(spec).getEncoded();

        // Format to store in database: "iterations:saltBase64:hashBase64"
        return ITERATIONS + ":" + 
               Base64.getEncoder().encodeToString(salt) + ":" + 
               Base64.getEncoder().encodeToString(hash);
    }

    // 2. Verify a user login attempt
    public static boolean verifyPassword(char[] inputPassword, String storedRecord) throws Exception {
        // Parse the stored database record
        String[] parts = storedRecord.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = Base64.getDecoder().decode(parts[1]);
        byte[] storedHash = Base64.getDecoder().decode(parts[2]);

        // Hash the incoming password using identical parameters
        PBEKeySpec spec = new PBEKeySpec(inputPassword, salt, iterations, storedHash.length * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
        byte[] testHash = skf.generateSecret(spec).getEncoded();

        // Anti-Timing Attack comparison
        return MessageDigest.isEqual(storedHash, testHash);
    }

    // Self-test method to exercise hashing and verification logic.
    public static void selfTest() {
        try {
            System.out.println("Step 1: generate hash record");
            char[] secret = "correct horse battery staple".toCharArray();
            String record = createHash(secret);
            System.out.println("  -> record created");
            // WARNING: the following prints sensitive data for testing purposes only
            System.out.println("  -> unhashed password: " + new String(secret));
            String[] parts = record.split(":");
            System.out.println("  -> stored hash (base64): " + (parts.length > 2 ? parts[2] : "<missing>"));
            System.out.println("Step 2: validate record format");
            System.out.println("  -> parts length=" + parts.length);
            if (parts.length != 3) {
                throw new IllegalStateException("stored record must have 3 parts (iterations:salt:hash)");
            }

            System.out.println("Step 3: verify correct password");
            if (!verifyPassword(secret, record)) {
                throw new IllegalStateException("correct password did not verify");
            }
            System.out.println("  -> correct password verified");

            System.out.println("Step 4: verify incorrect password is rejected");
            char[] wrong = "tr0ub4dor&3".toCharArray();
            if (verifyPassword(wrong, record)) {
                throw new IllegalStateException("incorrect password verified successfully");
            }
            System.out.println("  -> incorrect password rejected");

            System.out.println("Step 5: check malformed record handling");
            boolean threw = false;
            try {
                verifyPassword(secret, "not:enough");
            } catch (Exception e) {
                threw = true;
                System.out.println("  -> malformed record threw: " + e.getClass().getSimpleName());
            }
            if (!threw) {
                throw new IllegalStateException("malformed record did not throw an exception");
            }

            System.out.println("Step 6: cleanup sensitive data");
            java.util.Arrays.fill(secret, '\0');
            java.util.Arrays.fill(wrong, '\0');
            System.out.println("  -> cleaned sensitive arrays");

            System.out.println("OK: Password.selfTest passed");
        } catch (Throwable t) {
            t.printStackTrace();
            System.err.println("FAIL: Password.selfTest failed");
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        selfTest();
    }


    
}
