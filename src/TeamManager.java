import java.util.ArrayList;
import java.util.Scanner;

public class TeamManager {

    // Accepts any strategy implementing the interface
    public static ArrayList<ArrayList<Player>> generateTeams(ArrayList<Player> players, int teamSize, TeamFormationStrategy strategy) {
        return strategy.formTeams(players, teamSize);
    }

    public static void printTeams(ArrayList<ArrayList<Player>> teams) {
        int teamNumber = 1;
        for (ArrayList<Player> team : teams) {
            // Calculate total skill for display
            int totalSkill = 0;
            for(Player p : team) totalSkill += p.getSkillLevel();
            double avgSkill = team.isEmpty() ? 0 : (double) totalSkill / team.size();

            System.out.println("=== Team " + teamNumber + " (Avg Skill: " + String.format("%.1f", avgSkill) + ") ===");
            for (Player p : team) {
                System.out.println(String.format("%-15s | Game: %-10s | Role: %-10s | Skill: %-3d | Type: %s",
                        p.getName(), p.getPreferredGame(), p.getPreferredRole(), p.getSkillLevel(), p.getPersonalityType()));
            }
            System.out.println();
            teamNumber++;
        }
    }

    public static void runTeamFormationMenu(ArrayList<Player> players, Scanner sc) {
        System.out.print("Enter desired team size: ");
        int teamSize = sc.nextInt();
        sc.nextLine(); // consume newline

        System.out.println("Choose Formation Strategy:");
        System.out.println("1. Balanced (Personality Mix)");
        System.out.println("2. Competitive (Skill Balance)");
        System.out.println("3. Role Diversity (Mix of Roles)");
        System.out.println("4. Game Variety (Max 2 same game/team)");
        System.out.println("5. Randomized (Pure Luck)");
        System.out.println("6. Hybrid (Personality + Skill + Structure)");

        System.out.print("Enter choice (1-6): ");
        String choice = sc.nextLine();

        TeamFormationStrategy strategy;

        switch (choice) {
            case "2":
                strategy = new SkillBasedStrategy();
                System.out.println("Using Competitive Strategy...");
                break;
            case "3":
                strategy = new RoleDiversityStrategy();
                System.out.println("Using Role Diversity Strategy...");
                break;
            case "4":
                strategy = new GameVarietyStrategy();
                System.out.println("Using Game Variety Strategy...");
                break;
            case "5":
                strategy = new RandomizedStrategy();
                System.out.println("Using Randomized Strategy...");
                break;
            case "6":
                strategy = new HybridStrategy();
                System.out.println("Using Hybrid Multi-Criteria Strategy...");
                break;
            case "1":
            default:
                strategy = new BalancedTeamStrategy();
                System.out.println("Using Balanced/Personality Strategy...");
                break;
        }

        ArrayList<ArrayList<Player>> teams = generateTeams(players, teamSize, strategy);

        printTeams(teams);

        System.out.print("Save teams to CSV? (yes/no): ");
        String save = sc.nextLine().trim().toLowerCase();

        if (save.equals("yes")) {
            CSVManager.exportTeamsToCSV("teams_output.csv", teams);
        }
    }
}