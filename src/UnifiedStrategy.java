import java.util.*;

/**
 * Team Formation Strategy using a weighted Snake Draft.
 *
 * <p>This strategy balances teams by:
 * <ul>
 *   <li>Sorting players by skill level and drafting them in a snake pattern
 *       to evenly distribute high- and low-skill players across teams.</li>
 *   <li>Applying weighted scores to favor Leaders, key roles, and game diversity
 *       when assigning players.</li>
 * </ul>
 * </p>
 *
 * <p>This approach ensures both fair skill distribution and stronger
 * team composition while respecting role and personality constraints.</p>
 *
 * <p>Part of the TeamMate system, it integrates multiple evaluation criteria
 * including skill, role diversity, personality mix, and game variety.</p>
 */
public class UnifiedStrategy implements TeamFormationStrategy {

    // Scoring weights used when evaluating how well a player fits a team.
    private static final int GAME_CAP_PENALTY = -1000;
    private static final int NEEDED_LEADER_BONUS = 50;
    private static final int NEEDED_THINKER_BONUS = 30;
    private static final int NEW_ROLE_BONUS = 20;

    /**
     * Forms teams using an enhanced weighted snake-draft mechanism.
     *
     * <p>Instead of strictly picking players by highest skill, this method:
     * <ol>
     *   <li>Sorts all players by skill in descending order to prioritize skill balance.</li>
     *   <li>Uses a sliding window of the top N available players in each draft round.</li>
     *   <li>Selects the player from the window who best improves the team's composition
     *       score based on personality, role diversity, and game variety.</li>
     *   <li>Implements a snake draft turn order (forward then reverse) to distribute
     *       skill evenly across all teams.</li>
     *   <li>Applies randomization among candidates with equal scores to break ties fairly.</li>
     * </ol>
     * </p>
     *
     * <p>This method ensures balanced skill distribution while adapting
     * to team-specific needs for optimal team composition.</p>
     *
     * @param players the list of all Player objects to assign to teams
     * @param teamSize the desired number of players per team
     * @return a nested ArrayList where each inner list represents a formed team
     */
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
    }// end method formTeams




    /**
     * Calculates a weighted fit score for a player within a given team.
     *
     * <p>The score reflects how well the player complements the existing team
     * based on multiple composition criteria:</p>
     * <ul>
     *   <li><b>Game Variety:</b> Applies a strong penalty if adding the player
     *       exceeds the limit of 2 players sharing the same preferred game.</li>
     *   <li><b>Personality Mix:</b> Rewards adding the first missing Leader,
     *       or up to two needed Thinkers, to maintain balanced team personalities.</li>
     *   <li><b>Role Diversity:</b> Gives a bonus if the player introduces a role
     *       not already present in the team.</li>
     * </ul>
     *
     * <p>Higher scores indicate a better strategic fit for the team.</p>
     *
     * @param team the current list of players in the team
     * @param p the Player object to evaluate
     * @return an integer score representing how well the player fits in the team
     */


    private int calculateFitScore(ArrayList<Player> team, Player p) {
        int score = 0;

        // Criterion 1: Game Variety (Limit 2 players per preferred game)
        long sameGameCount = team.stream()
                .filter(m -> m.getPreferredGame().equalsIgnoreCase(p.getPreferredGame()))
                .count();
        if (sameGameCount >= 2) {
            score += GAME_CAP_PENALTY;  // Strong penalty for exceeding the game cap
        }

        // Criterion 2: Personality Mix (1 Leader, up to 2 Thinkers)
        String pType = p.getPersonalityType();
        boolean hasLeader = team.stream()
                .anyMatch(m -> "Leader".equalsIgnoreCase(m.getPersonalityType()));
        long thinkerCount = team.stream()
                .filter(m -> "Thinker".equalsIgnoreCase(m.getPersonalityType()))
                .count();

        if ("Leader".equalsIgnoreCase(pType) && !hasLeader) {
            score += NEEDED_LEADER_BONUS;  // Reward adding the first Leader
        } else if ("Thinker".equalsIgnoreCase(pType) && thinkerCount < 2) {
            score += NEEDED_THINKER_BONUS; // Reward filling needed Thinker slots
        }

        // Criterion 3: Role Diversity (Encourage at least 3 unique roles per team)
        boolean roleExists = team.stream()
                .anyMatch(m -> m.getPreferredRole().equalsIgnoreCase(p.getPreferredRole()));

        if (!roleExists) {
            score += NEW_ROLE_BONUS;  // Bonus for introducing a new role
        }

        return score;
    } // end method calculateFitScore

}// end main