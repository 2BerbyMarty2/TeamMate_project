import java.util.*;

public class RoleDiversityStrategy implements TeamFormationStrategy {

    @Override
    public ArrayList<ArrayList<Player>> formTeams(ArrayList<Player> players, int teamSize) {
        ArrayList<ArrayList<Player>> teams = new ArrayList<>();

        if (players.isEmpty() || teamSize <= 0) return teams;

        int totalTeams = (int) Math.ceil((double) players.size() / teamSize);
        for (int i = 0; i < totalTeams; i++) teams.add(new ArrayList<>());

        // Group players by Role
        Map<String, List<Player>> playersByRole = new HashMap<>();
        for (Player p : players) {
            String role = p.getPreferredRole().trim();
            playersByRole.putIfAbsent(role, new ArrayList<>());
            playersByRole.get(role).add(p);
        }

        // Flatten the map back into a list, but organized by role chunks
        List<Player> organizedPlayers = new ArrayList<>();
        for (List<Player> roleGroup : playersByRole.values()) {
            Collections.shuffle(roleGroup);
            // Randomize within the role
            organizedPlayers.addAll(roleGroup);
        }

        // Distribute Round-Robin
        // This ensures Team 1 gets Role A, Team 2 gets Role A... Team 1 gets Role B, etc.
        for (int i = 0; i < organizedPlayers.size(); i++) {
            teams.get(i % totalTeams).add(organizedPlayers.get(i));
        }

        return teams;
    }
}