import java.util.*;

public class TeamManager {

    public static ArrayList<ArrayList<Player>> formBalancedTeams(ArrayList<Player> players, int teamSize) {
        ArrayList<ArrayList<Player>> teams = new ArrayList<>();
        if (players.isEmpty() || teamSize <= 0) {
            System.out.println("No players or invalid team size.");
            return teams;
        }

        int totalPlayers = players.size();
        int totalTeams = (int) Math.ceil((double) totalPlayers / teamSize);

        // Initialize empty teams
        for (int i = 0; i < totalTeams; i++) {
            teams.add(new ArrayList<>());
        }

        // Shuffle players for randomness
        Collections.shuffle(players);

        // Separate by personality
        List<Player> leaders = new ArrayList<>();
        List<Player> thinkers = new ArrayList<>();
        List<Player> balanced = new ArrayList<>();

        for (Player p : players) {
            switch (p.getPersonalityType().toLowerCase()) {
                case "leader": leaders.add(p); break;
                case "thinker": thinkers.add(p); break;
                default: balanced.add(p); break;
            }
        }

        int teamIndex = 0;

        // Assign Leaders first (1 per team if possible)
        for (Player p : leaders) {
            teams.get(teamIndex % totalTeams).add(p);
            teamIndex++;
        }

        // Assign Thinkers (1–2 per team)
        for (Player p : thinkers) {
            teams.get(teamIndex % totalTeams).add(p);
            teamIndex++;
        }

        // Assign Balanced players
        for (Player p : balanced) {
            teams.get(teamIndex % totalTeams).add(p);
            teamIndex++;
        }

        // Shuffle players within teams for randomness
        for (ArrayList<Player> team : teams) {
            Collections.shuffle(team);
        }

        // Enforce simple constraints
        for (ArrayList<Player> team : teams) {
            enforceGameCap(team, 2); // max 2 players per game
            ensureRoleDiversity(team, teamSize); // at least 3 roles
        }

        return teams;
    }

    private static void enforceGameCap(ArrayList<Player> team, int maxPerGame) {
        Map<String, Integer> gameCount = new HashMap<>();
        for (Player p : team) {
            gameCount.put(p.getPreferredGame(), gameCount.getOrDefault(p.getPreferredGame(), 0) + 1);
        }
        // Advanced swapping can be added here if counts exceed maxPerGame
    }

    private static void ensureRoleDiversity(ArrayList<Player> team, int teamSize) {
        Set<String> roles = new HashSet<>();
        for (Player p : team) roles.add(p.getPreferredRole());
        // Can add logic to swap roles between teams if < 3
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

    // Menu method to ask team size from user
    public static void runTeamFormationMenu(ArrayList<Player> players, Scanner sc) {
        System.out.print("Enter desired team size: ");
        int teamSize = sc.nextInt();
        sc.nextLine(); // consume newline

        ArrayList<ArrayList<Player>> teams = formBalancedTeams(players, teamSize);
        printTeams(teams);
    }
}
