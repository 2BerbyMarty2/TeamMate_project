import java.util.*;

public class GameVarietyStrategy implements TeamFormationStrategy {


    /**
     * Forms teams while enforcing game variety and team size constraints.
     *
     * <p>Workflow:</p>
     * <ul>
     *   <li>Shuffle players initially to ensure fairness and randomness.</li>
     *   <li>Assign each player to a team that does not exceed the "max 2 per game" rule.</li>
     *   <li>If all teams would violate the game cap, assign the player to the smallest team (fallback).</li>
     * </ul>
     *
     * @param players  the list of all participating Player objects
     * @param teamSize the maximum number of players per team
     * @return a nested ArrayList where each inner list represents a team distributed according to game variety constraints
     */

    @Override
    public ArrayList<ArrayList<Player>> formTeams(ArrayList<Player> players, int teamSize) {
        ArrayList<ArrayList<Player>> teams = new ArrayList<>();

        // Return empty list if no players or invalid team size
        if (players.isEmpty() || teamSize <= 0) return teams;

        int totalTeams = (int) Math.ceil((double) players.size() / teamSize);
        for (int i = 0; i < totalTeams; i++) teams.add(new ArrayList<>());

        // Shuffle input for fairness before processing
        Collections.shuffle(players);

        for (Player p : players) {
            boolean assigned = false;
            String game = p.getPreferredGame();

            // Try to find a team that doesn't exceed max 2 players for this game
            for (ArrayList<Player> team : teams) {
                if (countPlayersWithGame(team, game) < 2 && team.size() < teamSize) {
                    team.add(p);
                    assigned = true;
                    break;
                }
            }

            // Fallback: assign to the smallest team if no valid team found
            if (!assigned) {
                ArrayList<Player> smallestTeam = teams.get(0);
                for (ArrayList<Player> team : teams) {
                    if (team.size() < smallestTeam.size()) {
                        smallestTeam = team;
                    }
                }
                smallestTeam.add(p);
            }
        }

        return teams;
    } // end formTeams


    /**
     * Counts the number of players in a team who prefer a specific game.
     *
     * <p>This method is used to enforce the "max players per game" constraint
     * during team formation.</p>
     *
     * @param team the list of Player objects in the team
     * @param game the game to count within the team
     * @return the number of players in the team who prefer the specified game
     */

    private int countPlayersWithGame(ArrayList<Player> team, String game) {
        int count = 0;
        for (Player p : team) {
            if (p.getPreferredGame().equalsIgnoreCase(game)) {
                count++;
            }
        }
        return count;
    } // end countPlayersWithGame

} // end class