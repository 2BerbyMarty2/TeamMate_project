import org.junit.Test;
import static org.junit.Assert.*;

public class PasswordUtilsTest {

    @Test
    public void testHashConsistency() {
        String password = "mySecretPassword123";

        // Hashing the same password twice should result in the SAME hash
        String hash1 = PasswordUtils.hashPassword(password);
        String hash2 = PasswordUtils.hashPassword(password);

        assertEquals("Hashes should be identical for same input", hash1, hash2);
    }

    @Test
    public void testHashDifference() {
        String pass1 = "password";
        String pass2 = "Password";

        String hash1 = PasswordUtils.hashPassword(pass1);
        String hash2 = PasswordUtils.hashPassword(pass2);

        assertNotEquals("Hashes must be different for different inputs", hash1, hash2);
    }

    @Test
    public void testHashLength() {
        // SHA-256 always produces 64 hex characters
        String hash = PasswordUtils.hashPassword("short");
        assertEquals("SHA-256 hash length should be 64 characters", 64, hash.length());
    }
}
