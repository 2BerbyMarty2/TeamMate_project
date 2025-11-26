import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SkillBasedStrategy implements TeamFormationStrategy {

    @Override
    public ArrayList<ArrayList<Player>> formTeams(ArrayList<Player> players, int teamSize) {
        ArrayList<ArrayList<Player>> teams = new ArrayList<>();

        if (players.isEmpty() || teamSize <= 0) {
            System.out.println("Invalid input for skill-based generation.");
            return teams;
        }

        int totalTeams = (int) Math.ceil((double) players.size() / teamSize);

        // Initialize teams
        for (int i = 0; i < totalTeams; i++) {
            teams.add(new ArrayList<>());
        }

        // Sort all players by Skill Level (Highest to Lowest)
        players.sort(Comparator.comparingInt(Player::getSkillLevel).reversed());

        // Distribute using "Snake Draft" logic to balance total skill
        // (e.g. 1, 2, 3, 3, 2, 1, 1, 2...)
        for (int i = 0; i < players.size(); i++) {
            int teamIndex;
            int round = i / totalTeams;

            if (round % 2 == 0) {
                // Even rounds: Forward (0 -> End)
                teamIndex = i % totalTeams;
            } else {
                // Odd rounds: Backward (End -> 0)
                teamIndex = (totalTeams - 1) - (i % totalTeams);
            }

            teams.get(teamIndex).add(players.get(i));
        }

        return teams;
    }
}