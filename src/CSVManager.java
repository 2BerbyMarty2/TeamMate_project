import java.io.*;
import java.util.ArrayList;

public class CSVManager {

    // Export list of players to CSV
    public static void exportPlayersToCSV(String filename, ArrayList<Player> players) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.append("ID,Name,Email,PreferredGame,SkillLevel,PreferredRole,PersonalityScore,PersonalityType\n");
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
            br.readLine(); // skip header
            String line;
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
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading CSV: " + e.getMessage());
        }
        return players;
    }

    // Export list of organizers to CSV
    public static void exportOrganizersToCSV(String filename, ArrayList<Organizer> organizers) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.append("ID,Name,Email,EventName,Position,Password\n");
            for (Organizer o : organizers) {
                writer.append(o.getID()).append(",")
                        .append(o.getName()).append(",")
                        .append(o.getEmail()).append(",")
                        .append(o.getEventName()).append(",")
                        .append(o.getPosition()).append(",")
                        .append(o.getPassword()).append("\n");
            }
            System.out.println("Organizers exported to " + filename + " successfully!");
        } catch (IOException e) {
            System.out.println("Error writing CSV: " + e.getMessage());
        }
    }

    // Import list of organizers from CSV
    public static ArrayList<Organizer> importOrganizersFromCSV(String filename) {
        ArrayList<Organizer> organizers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            br.readLine(); // skip header
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 6) continue;

                String ID = parts[0];
                String name = parts[1];
                String email = parts[2];
                String eventName = parts[3];
                String position = parts[4];
                String password = parts[5];

                organizers.add(new Organizer(ID, name, email, eventName, position, password));
                count++;
            }
            System.out.println("Imported " + count + " organizers from " + filename);
        } catch (IOException e) {
            System.out.println("Error reading CSV: " + e.getMessage());
        }
        return organizers;
    }

    public static void exportTeamsToCSV(String filename, ArrayList<ArrayList<Player>> teams) {
        try (FileWriter writer = new FileWriter(filename)) {

            // Header
            writer.append("TeamNumber,PlayerID,Name,Email,Game,Role,SkillLevel,PersonalityScore,PersonalityType\n");

            int teamNumber = 1;
            for (ArrayList<Player> team : teams) {
                for (Player p : team) {
                    writer.append(String.valueOf(teamNumber)).append(",")
                            .append(p.getID()).append(",")
                            .append(p.getName()).append(",")
                            .append(p.getEmail()).append(",")
                            .append(p.getPreferredGame()).append(",")
                            .append(p.getPreferredRole()).append(",")
                            .append(String.valueOf(p.getSkillLevel())).append(",")
                            .append(String.valueOf(p.getPersonalityScore())).append(",")
                            .append(p.getPersonalityType()).append("\n");
                }
                teamNumber++;
            }

            System.out.println("Teams exported successfully to " + filename);

        } catch (IOException e) {
            System.out.println("Error exporting teams: " + e.getMessage());
        }
    }
    public static ArrayList<ArrayList<Player>> importTeamsFromCSV(String filename) {
        ArrayList<ArrayList<Player>> teams = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            String line = br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length != 9) continue;

                int teamNum = Integer.parseInt(parts[0]);

                String id = parts[1];
                String name = parts[2];
                String email = parts[3];
                String game = parts[4];
                String role = parts[5];
                int skill = Integer.parseInt(parts[6]);
                int personalityScore = Integer.parseInt(parts[7]);
                String personalityType = parts[8];

                // Create player object
                Player p = new Player(id, name, email, game, skill, role, personalityScore, personalityType);

                // Ensure team list is large enough
                while (teams.size() < teamNum) {
                    teams.add(new ArrayList<>());
                }

                // Add player to the correct team
                teams.get(teamNum - 1).add(p);
            }

            System.out.println("Teams imported successfully from " + filename);

        } catch (IOException e) {
            System.out.println("Error reading teams CSV: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing number: " + e.getMessage());
        }

        return teams;
    }

}
