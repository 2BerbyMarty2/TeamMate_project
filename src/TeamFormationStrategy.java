import java.util.ArrayList;

public interface TeamFormationStrategy {
    // The contract: All strategies must implement this method
    ArrayList<ArrayList<Player>> formTeams(ArrayList<Player> players, int teamSize);
}