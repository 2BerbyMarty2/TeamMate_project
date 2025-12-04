import java.util.*;

public class HybridStrategy implements TeamFormationStrategy {


    /**
     * Forms balanced teams by considering both personality types and skill levels.
     *
     * <p>Workflow:</p>
     * <ul>
     *   <li>Separate players into personality buckets: Leaders, Thinkers, Balanced.</li>
     *   <li>Sort each bucket by skill (descending) to prioritize stronger players within each type.</li>
     *   <li>Merge the buckets into a single "Smart List" to maintain personality-first distribution.</li>
     *   <li>Distribute players using a Snake Draft to balance skill across teams while preserving
     *       the intended personality spread.</li>
     * </ul>
     *
     * @param players  the list of Player objects to assign to teams
     * @param teamSize the desired number of players per team
     * @return a nested ArrayList where each inner list represents a team balanced by personality and skill
     */

    @Override
    public ArrayList<ArrayList<Player>> formTeams(ArrayList<Player> players, int teamSize) {
        ArrayList<ArrayList<Player>> teams = new ArrayList<>();

        // Return empty list if no players or invalid team size
        if (players.isEmpty() || teamSize <= 0) return teams;

        int totalTeams = (int) Math.ceil((double) players.size() / teamSize);
        for (int i = 0; i < totalTeams; i++) teams.add(new ArrayList<>());

        // 1. Separate players into personality buckets
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

        // 3. Merge buckets into a single "Smart List" (Leaders → Thinkers → Balanced)
        List<Player> sortedList = new ArrayList<>();
        sortedList.addAll(leaders);
        sortedList.addAll(thinkers);
        sortedList.addAll(balanced);

        // 4. Distribute players using Snake Draft for fair skill balance across teams
        for (int i = 0; i < sortedList.size(); i++) {
            int teamIndex;
            int round = i / totalTeams;

            if (round % 2 == 0) {
                // Even rounds: assign from first team to last
                teamIndex = i % totalTeams;
            } else {
                // Odd rounds: assign from last team to first
                teamIndex = (totalTeams - 1) - (i % totalTeams);
            }

            teams.get(teamIndex).add(sortedList.get(i));
        }

        return teams;
    } // end formTeams

}// end class