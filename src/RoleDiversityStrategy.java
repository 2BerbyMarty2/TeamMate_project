import java.util.*;

public class RoleDiversityStrategy implements TeamFormationStrategy {


    /**
    * Forms teams while ensuring a balanced mix of player roles.
    *
    * <p>Workflow:</p>
    * <ul>
    *   <li>Group players by their preferred role.</li>
    *   <li>Shuffle players within each role group to randomize selection order.</li>
    *   <li>Flatten the groups into a single list organized by role chunks.</li>
    *   <li>Assign players to teams in a round-robin manner to distribute roles evenly.</li>
    * </ul>
    *
    * <p>This strategy ensures each team receives a diverse mix of roles for better gameplay.</p>
    *
    * @param players  the list of all participating Player objects
    * @param teamSize the desired number of players per team
    * @return a nested ArrayList where each inner list represents a team with players distributed for role diversity
    */
    @Override
    public ArrayList<ArrayList<Player>> formTeams(ArrayList<Player> players, int teamSize) {
        ArrayList<ArrayList<Player>> teams = new ArrayList<>();

        // Return empty list if no players or invalid team size
        if (players.isEmpty() || teamSize <= 0) return teams;

        int totalTeams = (int) Math.ceil((double) players.size() / teamSize);
        for (int i = 0; i < totalTeams; i++) teams.add(new ArrayList<>());

        // 1. Group players by their preferred role
        Map<String, List<Player>> playersByRole = new HashMap<>();
        for (Player p : players) {
            String role = p.getPreferredRole().trim();
            playersByRole.putIfAbsent(role, new ArrayList<>());
            playersByRole.get(role).add(p);
        }

        // 2. Flatten the role groups into a single list with randomized order within each role
        List<Player> organizedPlayers = new ArrayList<>();
        for (List<Player> roleGroup : playersByRole.values()) {
            Collections.shuffle(roleGroup); // randomize order within roles
            organizedPlayers.addAll(roleGroup);
        }

        // 3. Distribute players in round-robin fashion to ensure role balance across teams
        for (int i = 0; i < organizedPlayers.size(); i++) {
            teams.get(i % totalTeams).add(organizedPlayers.get(i));
        }

        return teams;
    } // end formTeams

}// end class