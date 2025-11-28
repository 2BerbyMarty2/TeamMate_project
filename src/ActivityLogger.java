import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ActivityLogger {

    private static final String LOG_FILE = "application_log.txt";
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Log a standard information message
    public static void log(String message) {
        writeLine("[INFO]  " + message);
    }

    // Log a warning (e.g., failed login attempts)
    public static void logWarning(String message) {
        writeLine("[WARN]  " + message);
    }

    // Log an error (e.g., file not found)
    public static void logError(String message) {
        writeLine("[ERROR] " + message);
    }

    private static void writeLine(String typeAndMsg) {
        String timestamp = dtf.format(LocalDateTime.now());
        String entry = String.format("%s | %s", timestamp, typeAndMsg);

        // Print to Console (Optional, good for debugging)
        // System.out.println(entry); 

        // Write to File (Append mode = true)
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(entry);
        } catch (IOException e) {
            System.err.println("Failed to write to log file: " + e.getMessage());
        }
    }
}