import java.util.ArrayList;
import java.util.Collections;

public class RandomizedStrategy implements TeamFormationStrategy {
    /**
    * Forms teams by randomly shuffling players and distributing them sequentially.
    *
    * <p>This is a simple, non-weighted strategy ideal for casual or icebreaker events.
    * Players are assigned to teams in a round-robin fashion after shuffling.</p>
    *
    * @param players  the list of all participating Player objects
    * @param teamSize the desired number of players per team
    * @return a nested ArrayList where each inner list represents a team with players randomly distributed
    */
    @Override
    public ArrayList<ArrayList<Player>> formTeams(ArrayList<Player> players, int teamSize) {
        ArrayList<ArrayList<Player>> teams = new ArrayList<>();

        // Return empty list if no players or invalid team size
        if (players.isEmpty() || teamSize <= 0) return teams;

        int totalTeams = (int) Math.ceil((double) players.size() / teamSize);

        // Initialize empty teams
        for (int i = 0; i < totalTeams; i++) teams.add(new ArrayList<>());

        // Shuffle players for random distribution
        Collections.shuffle(players);

        // Sequentially assign shuffled players to teams in round-robin fashion
        for (int i = 0; i < players.size(); i++) {
            teams.get(i % totalTeams).add(players.get(i));
        }

        return teams;
    } // end formTeams

} // end class
