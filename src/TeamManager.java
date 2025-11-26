import java.util.ArrayList;
import java.util.Scanner;

public class TeamManager {

    // Now accepts a 'Strategy' instead of hardcoding the logic
    public static ArrayList<ArrayList<Player>> generateTeams(ArrayList<Player> players, int teamSize, TeamFormationStrategy strategy) {
        return strategy.formTeams(players, teamSize);
    }

    public static void printTeams(ArrayList<ArrayList<Player>> teams) {
        int teamNumber = 1;
        for (ArrayList<Player> team : teams) {
            System.out.println("=== Team " + teamNumber + " ===");
            for (Player p : team) {
                System.out.println(p.getName() + " | Game: " + p.getPreferredGame() +
                        " | Role: " + p.getPreferredRole() +
                        " | Skill: " + p.getSkillLevel() +
                        " | Personality: " + p.getPersonalityType());
            }
            System.out.println();
            teamNumber++;
        }
    }

    public static void runTeamFormationMenu(ArrayList<Player> players, Scanner sc) {
        System.out.print("Enter desired team size: ");
        int teamSize = sc.nextInt();
        sc.nextLine(); // consume newline

        // Create the specific strategy we want to use
        TeamFormationStrategy strategy = new BalancedTeamStrategy();

        // Pass the strategy to the generator
        System.out.println("Generating teams using Balanced Strategy...");
        ArrayList<ArrayList<Player>> teams = generateTeams(players, teamSize, strategy);

        printTeams(teams);

        System.out.print("Save teams to CSV? (yes/no): ");
        String save = sc.nextLine().trim().toLowerCase();

        if (save.equals("yes")) {
            CSVManager.exportTeamsToCSV("teams_output.csv", teams);
        }
    }
}