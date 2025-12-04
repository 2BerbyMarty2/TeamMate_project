import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ActivityLogger {

// File: ActivityLogger.java
// Utility class for logging application events with timestamps and severity levels.

    private static final String LOG_FILE = "application_log.txt"; // Log file path
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // Timestamp format

    /**
    * Logs an informational message.
    *
    * <p>This is typically used for general events such as successful operations
    * or routine system updates.</p>
    *
    * @param message the message describing the event
    */
    public static void log(String message) {
        writeLine("[INFO]  " + message);
    } // end log



    /**
    * Logs a warning message.
    *
    * <p>Typical examples include failed login attempts or minor recoverable issues.</p>
    *
    * @param message the message describing the warning
    */
    public static void logWarning(String message) {
        writeLine("[WARN]  " + message);
    } // end logWarning



    /**
    * Logs an error message.
    *
    * <p>Typical examples include exceptions, file-not-found errors, or other critical failures.</p>
    *
    * @param message the message describing the error
    */
    public static void logError(String message) {
        writeLine("[ERROR] " + message);
    } // end logError




    /**
    * Writes a formatted log entry to the log file with a timestamp.
    *
    * <p>Each entry follows the format: YYYY-MM-DD HH:MM:SS | [LEVEL] Message</p>
    *
    * @param typeAndMsg the log level and message combined
    */
    private static void writeLine(String typeAndMsg) {
        String timestamp = dtf.format(LocalDateTime.now()); // Generate current timestamp
        String entry = String.format("%s | %s", timestamp, typeAndMsg); // Combine timestamp and message

        // Optional: Print to console for real-time debugging
        // System.out.println(entry);

        // Append log entry to file
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(entry);
        } catch (IOException e) {
            // Print error to console if logging fails
            System.err.println("Failed to write to log file: " + e.getMessage());
        }
    }// end writeLine

} // end class