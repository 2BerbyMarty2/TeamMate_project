import java.util.*;

public class GameVarietyStrategy implements TeamFormationStrategy {

    @Override
    public ArrayList<ArrayList<Player>> formTeams(ArrayList<Player> players, int teamSize) {
        ArrayList<ArrayList<Player>> teams = new ArrayList<>();

        if (players.isEmpty() || teamSize <= 0) return teams;

        int totalTeams = (int) Math.ceil((double) players.size() / teamSize);
        for (int i = 0; i < totalTeams; i++) teams.add(new ArrayList<>());

        // Shuffle input for fairness before processing
        Collections.shuffle(players);

        for (Player p : players) {
            boolean assigned = false;
            String game = p.getPreferredGame();

            // 1. Try to find a team that doesn't violate the "Max 2 per Game" rule
            for (ArrayList<Player> team : teams) {
                if (countPlayersWithGame(team, game) < 2 && team.size() < teamSize) {
                    team.add(p);
                    assigned = true;
                    break;
                }
            }

            // 2. If all valid spots are taken, just put them in the smallest team (Fallback)
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
    }

    private int countPlayersWithGame(ArrayList<Player> team, String game) {
        int count = 0;
        for (Player p : team) {
            if (p.getPreferredGame().equalsIgnoreCase(game)) {
                count++;
            }
        }
        return count;
    }
}