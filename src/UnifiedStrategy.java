import java.util.*;

public class UnifiedStrategy implements TeamFormationStrategy {

    // Weights for our scoring logic
    private static final int GAME_CAP_PENALTY = -1000;
    private static final int NEEDED_LEADER_BONUS = 50;
    private static final int NEEDED_THINKER_BONUS = 30;
    private static final int NEW_ROLE_BONUS = 20;

    @Override
    public ArrayList<ArrayList<Player>> formTeams(ArrayList<Player> players, int teamSize) {
        ArrayList<ArrayList<Player>> teams = new ArrayList<>();

        if (players.isEmpty() || teamSize <= 0) return teams;

        int totalTeams = (int) Math.ceil((double) players.size() / teamSize);
        for (int i = 0; i < totalTeams; i++) teams.add(new ArrayList<>());

        // 1. Sort all players by Skill (High -> Low) to handle Criterion #4 (Skill Balance)
        // We do this globally so the draft always prioritizes skill distribution first.
        players.sort(Comparator.comparingInt(Player::getSkillLevel).reversed());

        // We use a LinkedList to easily remove players as they are picked
        LinkedList<Player> availablePlayers = new LinkedList<>(players);

        // 2. Execute a "Weighted Snake Draft"
        int currentRound = 0;

        while (!availablePlayers.isEmpty()) {
            // Determine turn order: 0,1,2 then 2,1,0
            for (int i = 0; i < totalTeams && !availablePlayers.isEmpty(); i++) {

                int teamIndex = (currentRound % 2 == 0) ? i : (totalTeams - 1 - i);
                ArrayList<Player> currentTeam = teams.get(teamIndex);

                // Stop filling this team if it's full (handles uneven remainders)
                if (currentTeam.size() >= teamSize && availablePlayers.size() > (totalTeams * teamSize - teams.size())) {
                    // Logic to prevent overfilling if not strictly necessary
                }

                // 3. Selection: Look at the top N candidates to find the best fit
                // We don't look at ALL players, only the top few high-skill ones remaining.
                // This keeps Skill Balance tight while allowing for Personality/Game optimization.
                int windowSize = Math.min(availablePlayers.size(), totalTeams + 2);
                Player bestPick = null;
                int bestScore = Integer.MIN_VALUE;

                // Create a sub-list of candidates to evaluate
                List<Player> candidates = new ArrayList<>();
                for(int c=0; c<windowSize; c++) candidates.add(availablePlayers.get(c));

                // Criterion #5: Randomization. Shuffle candidates so ties are broken randomly.
                Collections.shuffle(candidates);

                for (Player candidate : candidates) {
                    int score = calculateFitScore(currentTeam, candidate);

                    if (score > bestScore) {
                        bestScore = score;
                        bestPick = candidate;
                    }
                }

                // Assign the winner
                currentTeam.add(bestPick);
                availablePlayers.remove(bestPick);
            }
            currentRound++;
        }

        return teams;
    }

    // This helper method calculates how well a player fits the criteria for a specific team
    private int calculateFitScore(ArrayList<Player> team, Player p) {
        int score = 0;

        // --- Criterion #1: Game/Sport Variety (Max 2 per game) ---
        long sameGameCount = team.stream()
                .filter(m -> m.getPreferredGame().equalsIgnoreCase(p.getPreferredGame()))
                .count();
        if (sameGameCount >= 2) {
            score += GAME_CAP_PENALTY; // Big penalty if we break the rule
        }

        // --- Criterion #3: Personality Mix (1 Leader, 1-2 Thinkers) ---
        String pType = p.getPersonalityType();
        boolean hasLeader = team.stream().anyMatch(m -> "Leader".equalsIgnoreCase(m.getPersonalityType()));
        long thinkerCount = team.stream().filter(m -> "Thinker".equalsIgnoreCase(m.getPersonalityType())).count();

        if ("Leader".equalsIgnoreCase(pType) && !hasLeader) {
            score += NEEDED_LEADER_BONUS; // High value on the first leader
        } else if ("Thinker".equalsIgnoreCase(pType) && thinkerCount < 2) {
            score += NEEDED_THINKER_BONUS; // Value on needed thinkers
        }

        // --- Criterion #2: Role Diversity (At least 3 different roles) ---
        boolean roleExists = team.stream()
                .anyMatch(m -> m.getPreferredRole().equalsIgnoreCase(p.getPreferredRole()));

        if (!roleExists) {
            score += NEW_ROLE_BONUS; // Reward new-role to the mix
        }

        return score;
    }
}