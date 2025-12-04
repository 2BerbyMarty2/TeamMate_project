import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtils {

    /**
     * Converts a plain text password into a SHA-256 hash.
     *
     * <p>This method securely hashes passwords for storage instead of keeping
     * them in plain text, improving system security.</p>
     *
     * @param password the plain text password to hash
     * @return a hexadecimal string representing the SHA-256 hash of the password
     * @throws RuntimeException if the SHA-256 algorithm is not available
     *         (should never happen in standard Java)
     */

    public static String hashPassword(String password) {
        try {
            // Create a SHA-256 message digest instance
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Compute the hash as a byte array
            byte[] hashedBytes = md.digest(password.getBytes());

            // Convert the byte array to a hexadecimal string
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b)); // format byte as two-digit hex
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            // Wrap in RuntimeException because SHA-256 is guaranteed to exist in standard Java
            throw new RuntimeException("Error hashing password", e);
        }
    } // end hashPassword

}// end class