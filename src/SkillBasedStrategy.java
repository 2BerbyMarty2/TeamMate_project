import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SkillBasedStrategy implements TeamFormationStrategy {

    /**
     * Forms teams based purely on skill levels using a Snake Draft approach.
     *
     * <p>Workflow:</p>
     * <ul>
     *   <li>Sort all players in descending order of skill (high → low).</li>
     *   <li>Distribute players across teams in a "snake" pattern:
     *       <ul>
     *         <li>Even rounds: assign from first to last team.</li>
     *         <li>Odd rounds: assign from last to first team.</li>
     *       </ul>
     *   </li>
     *   <li>This ensures each team receives a fair distribution of skill,
     *       preventing any team from having all the highest-skilled players.</li>
     * </ul>
     *
     * @param players  the list of all participating Player objects
     * @param teamSize the desired number of players per team
     * @return a nested ArrayList where each inner list represents a team balanced by skill
     */

    @Override
    public ArrayList<ArrayList<Player>> formTeams(ArrayList<Player> players, int teamSize) {
        ArrayList<ArrayList<Player>> teams = new ArrayList<>();

        // Validate input
        if (players.isEmpty() || teamSize <= 0) {
            System.out.println("Invalid input for skill-based generation.");
            return teams;
        }

        int totalTeams = (int) Math.ceil((double) players.size() / teamSize);

        // Initialize empty teams
        for (int i = 0; i < totalTeams; i++) {
            teams.add(new ArrayList<>());
        }

        // Sort players by skill descending
        players.sort(Comparator.comparingInt(Player::getSkillLevel).reversed());

        // Distribute players using Snake Draft to balance skill across teams
        for (int i = 0; i < players.size(); i++) {
            int teamIndex;
            int round = i / totalTeams;

            if (round % 2 == 0) {
                // Even rounds: forward assignment
                teamIndex = i % totalTeams;
            } else {
                // Odd rounds: backward assignment
                teamIndex = (totalTeams - 1) - (i % totalTeams);
            }

            teams.get(teamIndex).add(players.get(i));
        }

        return teams;
    } // end formTeams

}// end class