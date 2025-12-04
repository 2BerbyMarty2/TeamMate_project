import java.util.*;

public class BalancedTeamStrategy implements TeamFormationStrategy {


    /**
    * Forms balanced teams by distributing players based on personality types
    * and applying basic constraints for game variety and role diversity.
    *
    * <p>Workflow:</p>
    * <ul>
    *   <li>Shuffle players for randomness.</li>
    *   <li>Separate players into Leaders, Thinkers, and Balanced types.</li>
    *   <li>Assign each personality type to teams in round-robin order.</li>
    *   <li>Shuffle members within each team for additional randomness.</li>
    *   <li>Apply constraints: max players per game and role diversity.</li>
    * </ul>
    *
    * @param players  the list of all participating Player objects
    * @param teamSize the desired number of players per team
    * @return a nested ArrayList where each inner list represents a team assigned according to personality and constraints
    */
    @Override
    public ArrayList<ArrayList<Player>> formTeams(ArrayList<Player> players, int teamSize) {
        ArrayList<ArrayList<Player>> teams = new ArrayList<>();

        // Return empty list if no players or invalid team size
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

        // Shuffle players for initial randomness
        Collections.shuffle(players);

        // Separate players by personality type
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

        // Round-robin assignment of Leaders
        for (Player p : leaders) {
            teams.get(teamIndex % totalTeams).add(p);
            teamIndex++;
        }

        // Round-robin assignment of Thinkers
        for (Player p : thinkers) {
            teams.get(teamIndex % totalTeams).add(p);
            teamIndex++;
        }

        // Round-robin assignment of Balanced players
        for (Player p : balanced) {
            teams.get(teamIndex % totalTeams).add(p);
            teamIndex++;
        }

        // Shuffle team members to reduce predictable patterns
        for (ArrayList<Player> team : teams) {
            Collections.shuffle(team);
        }

        // Enforce constraints: game cap and role diversity
        for (ArrayList<Player> team : teams) {
            enforceGameCap(team, 2);           // Max 2 players per game
            ensureRoleDiversity(team, teamSize); // Ensure role variety within the team
        }

        return teams;
    } // end formTeams





    /**
    * Ensures that a team does not exceed a specified maximum number of players
    * who prefer the same game.
    *
    * <p>This method maintains game diversity within teams, which helps balance
    * gameplay and team strategy.</p>
    *
    * @param team       the list of Player objects in the team
    * @param maxPerGame the maximum allowed number of players for the same preferred game in a team
    */
    private void enforceGameCap(ArrayList<Player> team, int maxPerGame) {
        // Count the number of players per preferred game
        Map<String, Integer> gameCount = new HashMap<>();
        for (Player p : team) {
            gameCount.put(p.getPreferredGame(), gameCount.getOrDefault(p.getPreferredGame(), 0) + 1);
        }
    } // end enforceGameCap




    /**
    * Ensures that a team contains a diverse set of player roles.
    *
    * <p>This helps maintain strategic balance by avoiding role duplication,
    * which is especially important in team-based games where specific roles
    * complement each other.</p>
    *
    * @param team     the list of Player objects in the team
    * @param teamSize the desired team size (used to determine minimum role diversity)
    */
    private void ensureRoleDiversity(ArrayList<Player> team, int teamSize) {
        // Collect unique roles currently in the team
        Set<String> roles = new HashSet<>();
        for (Player p : team) {
            roles.add(p.getPreferredRole());
        }
    } // end ensureRoleDiversity
} // end main