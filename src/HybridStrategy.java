import java.util.*;

public class HybridStrategy implements TeamFormationStrategy {

    @Override
    public ArrayList<ArrayList<Player>> formTeams(ArrayList<Player> players, int teamSize) {
        ArrayList<ArrayList<Player>> teams = new ArrayList<>();

        if (players.isEmpty() || teamSize <= 0) return teams;

        int totalTeams = (int) Math.ceil((double) players.size() / teamSize);
        for (int i = 0; i < totalTeams; i++) teams.add(new ArrayList<>());

        // 1. Buckets for Personality
        List<Player> leaders = new ArrayList<>();
        List<Player> thinkers = new ArrayList<>();
        List<Player> balanced = new ArrayList<>();

        for (Player p : players) {
            String type = p.getPersonalityType() == null ? "" : p.getPersonalityType().toLowerCase();
            switch (type) {
                case "leader": leaders.add(p); break;
                case "thinker": thinkers.add(p); break;
                default: balanced.add(p); break;
            }
        }

        // 2. Sort each bucket by Skill (High -> Low)
        Comparator<Player> skillSorter = Comparator.comparingInt(Player::getSkillLevel).reversed();
        leaders.sort(skillSorter);
        thinkers.sort(skillSorter);
        balanced.sort(skillSorter);

        // 3. Combine them back into one master "Smart List"
        // This ensures we distribute the best Leaders first, then best Thinkers, etc.
        List<Player> sortedList = new ArrayList<>();
        sortedList.addAll(leaders);
        sortedList.addAll(thinkers);
        sortedList.addAll(balanced);

        // 4. Distribute using "Snake Draft" to balance the Skill within the Personality spread
        for (int i = 0; i < sortedList.size(); i++) {
            int teamIndex;
            int round = i / totalTeams;

            if (round % 2 == 0) {
                teamIndex = i % totalTeams;
            } else {
                teamIndex = (totalTeams - 1) - (i % totalTeams);
            }
            teams.get(teamIndex).add(sortedList.get(i));
        }

        return teams;
    }
}