import java.io.*;
import java.util.ArrayList;

public class CSVManager {

    // Export list of players to CSV
    public static void exportPlayersToCSV(String filename, ArrayList<Player> players) {
        try (FileWriter writer = new FileWriter(filename)) {
            // Write header
            writer.append("ID,Name,Email,PreferredGame,SkillLevel,PreferredRole,PersonalityScore,PersonalityType\n");

            // Write each player
            for (Player p : players) {
                writer.append(p.getID()).append(",")
                        .append(p.getName()).append(",")
                        .append(p.getEmail()).append(",")
                        .append(p.getPreferredGame()).append(",")
                        .append(String.valueOf(p.getSkillLevel())).append(",")
                        .append(p.getPreferredRole()).append(",")
                        .append(String.valueOf(p.getPersonalityScore())).append(",")
                        .append(p.getPersonalityType()).append("\n");
            }

            System.out.println("Players exported to " + filename + " successfully!");

        } catch (IOException e) {
            System.out.println("Error writing CSV: " + e.getMessage());
        }
    }

    // Import list of players from CSV
    public static ArrayList<Player> importPlayersFromCSV(String filename) {
        ArrayList<Player> players = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine();
            int count = 0;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length != 8) continue;

                String ID = parts[0];
                String name = parts[1];
                String email = parts[2];
                String game = parts[3];
                int skill = Integer.parseInt(parts[4]);
                String role = parts[5];
                int personalityScore = Integer.parseInt(parts[6]);
                String personalityType = parts[7];

                players.add(new Player(ID, name, email, game, skill, role, personalityScore, personalityType));
                count++;
            }

            System.out.println("Imported " + count + " players from " + filename);

        } catch (IOException e) {
            System.out.println("Error reading CSV: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing number: " + e.getMessage());
        }
        return players;
    }
}
