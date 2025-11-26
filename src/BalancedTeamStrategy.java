import java.util.*;

public class BalancedTeamStrategy implements TeamFormationStrategy {

    @Override
    public ArrayList<ArrayList<Player>> formTeams(ArrayList<Player> players, int teamSize) {
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
            // Logic to sort players based on personality type
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

        // Assign Thinkers
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
            enforceGameCap(team, 2);
            ensureRoleDiversity(team, teamSize);
        }

        return teams;
    }

    // Helper methods moved here because they are specific to this strategy
    private void enforceGameCap(ArrayList<Player> team, int maxPerGame) {
        Map<String, Integer> gameCount = new HashMap<>();
        for (Player p : team) {
            gameCount.put(p.getPreferredGame(), gameCount.getOrDefault(p.getPreferredGame(), 0) + 1);
        }
        // Logic to handle game caps (swapping logic could go here)
    }

    private void ensureRoleDiversity(ArrayList<Player> team, int teamSize) {
        Set<String> roles = new HashSet<>();
        for (Player p : team) roles.add(p.getPreferredRole());
        // Logic to check role diversity
    }
}