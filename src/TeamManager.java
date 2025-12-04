import java.util.ArrayList;
import java.util.Scanner;

public class TeamManager {

    /**
     * Generates teams using any strategy that implements TeamFormationStrategy.
     *
     * <p>This method delegates the actual team formation to the provided
     * strategy instance, allowing flexible use of different algorithms
     * such as BalancedTeamStrategy, SkillBasedStrategy, or UnifiedStrategy.</p>
     *
     * @param players the list of Player objects to assign to teams
     * @param teamSize the desired number of players per team
     * @param strategy the team formation strategy to apply
     * @return a nested ArrayList where each inner list represents a formed team
     */
    public static ArrayList<ArrayList<Player>> generateTeams(
            ArrayList<Player> players,
            int teamSize,
            TeamFormationStrategy strategy) {
        return strategy.formTeams(players, teamSize);
    } // end generateTeams

    /**
     * Prints the teams to the console with detailed player information.
     *
     * <p>For each team, this method displays:</p>
     * <ul>
     *   <li>Team number and average skill level</li>
     *   <li>Each player's name, preferred game, preferred role, skill level, and personality type</li>
     * </ul>
     *
     * <p>The average skill per team is calculated and displayed for comparison
     * between teams.</p>
     *
     * @param teams a nested ArrayList where each inner list represents a team of Player objects
     */

    public static void printTeams(ArrayList<ArrayList<Player>> teams) {
        int teamNumber = 1;
        for (ArrayList<Player> team : teams) {
            // Calculate total and average skill for display
            int totalSkill = 0;
            for (Player p : team) totalSkill += p.getSkillLevel();
            double avgSkill = team.isEmpty() ? 0 : (double) totalSkill / team.size();

            System.out.println("=== Team " + teamNumber + " (Avg Skill: " + String.format("%.1f", avgSkill) + ") ===");

            for (Player p : team) {
                System.out.println(String.format("%-15s | Game: %-10s | Role: %-10s | Skill: %-3d | Type: %s",
                        p.getName(), p.getPreferredGame(), p.getPreferredRole(), p.getSkillLevel(), p.getPersonalityType()));
            }
            System.out.println();
            teamNumber++;
        }
    } // end printTeams


    /**
     * Displays a CLI menu for team formation and executes the chosen strategy.
     *
     * <p>Steps performed by this method include:</p>
     * <ul>
     *   <li>Prompt the user for the desired team size.</li>
     *   <li>Display a list of available team formation strategies.</li>
     *   <li>Instantiate the selected {@link TeamFormationStrategy}.</li>
     *   <li>Generate teams using {@link #generateTeams(ArrayList, int, TeamFormationStrategy)}.</li>
     *   <li>Print the formed teams with {@link #printTeams(ArrayList)}.</li>
     *   <li>Optionally save the teams to a CSV file using {@link CSVManager#exportTeamsToCSV(String, ArrayList)}.</li>
     * </ul>
     *
     * @param players List of registered players
     * @param sc      Scanner object for reading user input
     */

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
        System.out.println("7. Unified Strategy");

        System.out.print("Enter choice (1-7`): ");
        String choice = sc.nextLine();

        TeamFormationStrategy strategy;

        switch (choice) {
            case "7":
                strategy = new UnifiedStrategy();
                System.out.println("Using Unified Strategy...");
                break;
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

        // Generate teams using selected strategy
        ArrayList<ArrayList<Player>> teams = generateTeams(players, teamSize, strategy);

        // Display teams
        printTeams(teams);

        // Optionally save to CSV
        System.out.print("Save teams to CSV? (yes/no): ");
        String save = sc.nextLine().trim().toLowerCase();
        if (save.equals("yes")) {
            CSVManager.exportTeamsToCSV("teams_output.csv", teams);
        }
    }// end runTeamFormationMenu
}// end class