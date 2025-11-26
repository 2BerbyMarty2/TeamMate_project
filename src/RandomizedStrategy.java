import java.util.ArrayList;
import java.util.Collections;

public class RandomizedStrategy implements TeamFormationStrategy {

    @Override
    public ArrayList<ArrayList<Player>> formTeams(ArrayList<Player> players, int teamSize) {
        ArrayList<ArrayList<Player>> teams = new ArrayList<>();

        if (players.isEmpty() || teamSize <= 0) return teams;

        int totalTeams = (int) Math.ceil((double) players.size() / teamSize);
        for (int i = 0; i < totalTeams; i++) teams.add(new ArrayList<>());

        // The core logic: Pure Shuffle
        Collections.shuffle(players);

        // Simple sequential distribution
        for (int i = 0; i < players.size(); i++) {
            teams.get(i % totalTeams).add(players.get(i));
        }

        return teams;
    }
}
