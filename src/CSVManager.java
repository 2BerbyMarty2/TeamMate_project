import java.io.*;
import java.util.ArrayList;

public class CSVManager {

    /**
    * Exports a list of Player objects to a CSV file.
    *
    * <p>Each player's details are written as a CSV row with the following columns:</p>
    * <ul>
    *   <li>ID</li>
    *   <li>Name</li>
    *   <li>Email</li>
    *   <li>PreferredGame</li>
    *   <li>SkillLevel</li>
    *   <li>PreferredRole</li>
    *   <li>PersonalityScore</li>
    *   <li>PersonalityType</li>
    * </ul>
    *
    * @param filename the name of the CSV file to create or overwrite
    * @param players  the list of Player objects to export
    */
    public static void exportPlayersToCSV(String filename, ArrayList<Player> players) {
        try (FileWriter writer = new FileWriter(filename)) {
            // Write CSV header
            writer.append("ID,Name,Email,PreferredGame,SkillLevel,PreferredRole,PersonalityScore,PersonalityType\n");

            // Write each player's data
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
            // Handle file writing errors
            System.out.println("Error writing CSV: " + e.getMessage());
        }
    } // end exportPlayersToCSV


    /**
    * Imports a list of Player objects from a CSV file.
    *
    * <p>Expected CSV format (8 columns):</p>
    * <ul>
    *   <li>ID</li>
    *   <li>Name</li>
    *   <li>Email</li>
    *   <li>PreferredGame</li>
    *   <li>SkillLevel</li>
    *   <li>PreferredRole</li>
    *   <li>PersonalityScore</li>
    *   <li>PersonalityType</li>
    * </ul>
    *
    * @param filename the CSV file to read from
    * @return an ArrayList of Player objects containing all successfully loaded players
    */
    public static ArrayList<Player> importPlayersFromCSV(String filename) {
        ArrayList<Player> players = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            br.readLine(); // Skip header line

            String line;
            int count = 0;

            // Read each row and convert to Player object
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                // Ensure correct number of columns
                if (parts.length != 8) {
                    continue; // Skip malformed rows
                }

                // Extract fields
                String id = parts[0];
                String name = parts[1];
                String email = parts[2];
                String game = parts[3];
                int skill = Integer.parseInt(parts[4]);
                String role = parts[5];
                int personalityScore = Integer.parseInt(parts[6]);
                String personalityType = parts[7];

                // Create and store player object
                players.add(new Player(id, name, email, game, skill, role, personalityScore, personalityType));
                count++;
            }

            System.out.println("Imported " + count + " players from " + filename);

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading CSV: " + e.getMessage());
        }

        return players;
    } // end importPlayersFromCSV


    /**
    * Exports a list of Organizer objects to a CSV file.
    *
    * <p>Each organizer is written as a CSV row with the following columns:</p>
    * <ul>
    *   <li>ID</li>
    *   <li>Name</li>
    *   <li>Email</li>
    *   <li>EventName</li>
    *   <li>Position</li>
    *   <li>Password</li>
    * </ul>
    *
    * @param filename   the CSV file to write to
    * @param organizers the list of Organizer objects to export
    */
    public static void exportOrganizersToCSV(String filename, ArrayList<Organizer> organizers) {
        try (FileWriter writer = new FileWriter(filename)) {

            // Write CSV header row
            writer.append("ID,Name,Email,EventName,Position,Password\n");

            // Write organizer details line by line
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
            // Handle any file writing errors
            System.out.println("Error writing CSV: " + e.getMessage());
        }
    } // end exportOrganizersToCSV


    /**
    * Imports a list of Organizer objects from a CSV file.
    *
    * <p>Expected CSV format (6 columns):</p>
    * <ul>
    *   <li>ID</li>
    *   <li>Name</li>
    *   <li>Email</li>
    *   <li>EventName</li>
    *   <li>Position</li>
    *   <li>Password</li>
    * </ul>
    *
    * @param filename the CSV file to read from
    * @return an ArrayList of Organizer objects containing all successfully loaded organizers
    */
    public static ArrayList<Organizer> importOrganizersFromCSV(String filename) {
        ArrayList<Organizer> organizers = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            br.readLine(); // Skip header row

            String line;
            int count = 0;

            // Read each line and parse to Organizer object
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                // Ensure correct number of fields
                if (parts.length != 6) {
                    continue; // Skip malformed rows
                }

                // Extract organizer fields
                String id = parts[0];
                String name = parts[1];
                String email = parts[2];
                String eventName = parts[3];
                String position = parts[4];
                String password = parts[5];

                // Create organizer object and store it
                organizers.add(new Organizer(id, name, email, eventName, position, password));
                count++;
            }

            System.out.println("Imported " + count + " organizers from " + filename);

        } catch (IOException e) {
            System.out.println("Error reading CSV: " + e.getMessage());
        }

        return organizers;
    } // end importOrganizersFromCSV


    /**
    * Exports all generated teams to a CSV file.
    *
    * <p>Each row represents a single player and contains the following columns:</p>
    * <ul>
    *   <li>TeamNumber</li>
    *   <li>PlayerID</li>
    *   <li>Name</li>
    *   <li>Email</li>
    *   <li>Game</li>
    *   <li>Role</li>
    *   <li>SkillLevel</li>
    *   <li>PersonalityScore</li>
    *   <li>PersonalityType</li>
    * </ul>
    *
    * @param filename the CSV file to write to
    * @param teams    a nested ArrayList where each inner list represents a team of Player objects
    */
    public static void exportTeamsToCSV(String filename, ArrayList<ArrayList<Player>> teams) {
        try (FileWriter writer = new FileWriter(filename)) {

            // Write header row
            writer.append("TeamNumber,PlayerID,Name,Email,Game,Role,SkillLevel,PersonalityScore,PersonalityType\n");

            int teamNumber = 1;

            // Write all players team by team
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
    } // end exportTeamsToCSV


    /**
    * Imports team data from a CSV file and reconstructs the list of teams.
    *
    * <p>Expected CSV format (9 columns):</p>
    * <ul>
    *   <li>TeamNumber</li>
    *   <li>PlayerID</li>
    *   <li>Name</li>
    *   <li>Email</li>
    *   <li>Game</li>
    *   <li>Role</li>
    *   <li>SkillLevel</li>
    *   <li>PersonalityScore</li>
    *   <li>PersonalityType</li>
    * </ul>
    *
    * <p>Teams are rebuilt dynamically by expanding the list as required.</p>
    *
    * @param filename the CSV file to read from
    * @return a nested ArrayList where each inner list represents a team of Player objects
    */
    public static ArrayList<ArrayList<Player>> importTeamsFromCSV(String filename) {
        ArrayList<ArrayList<Player>> teams = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            br.readLine(); // Skip header line
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                // Ensure correct number of columns
                if (parts.length != 9) {
                    continue; // Skip malformed rows
                }

                // Extract team number
                int teamNum = Integer.parseInt(parts[0]);

                // Extract player information
                String id = parts[1];
                String name = parts[2];
                String email = parts[3];
                String game = parts[4];
                String role = parts[5];
                int skill = Integer.parseInt(parts[6]);
                int personalityScore = Integer.parseInt(parts[7]);
                String personalityType = parts[8];

                // Create reconstructed Player object
                Player p = new Player(id, name, email, game, skill, role, personalityScore, personalityType);

                // Ensure the team list is large enough to hold this team number
                while (teams.size() < teamNum) {
                    teams.add(new ArrayList<>());
                }

                // Add the player to the appropriate team (teamNum is 1-based)
                teams.get(teamNum - 1).add(p);
            }

            System.out.println("Teams imported successfully from " + filename);

        } catch (IOException e) {
            System.out.println("Error reading teams CSV: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing numeric field: " + e.getMessage());
        }

        return teams;
    } // end importTeamsFromCSV

}