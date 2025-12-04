import java.util.ArrayList;

/**
 * The TeamFormationStrategy interface defines the contract for all
 * team formation algorithms used in the TeamMate system.
 *
 * <p>Implementing classes must provide logic to divide a list of players
 * into balanced teams according to different strategies such as skill,
 * role diversity, personality type, or a combination thereof.</p>
 *
 * <p>Example strategies include:
 * <ul>
 *   <li>BalancedTeamStrategy – distributes personality types evenly.</li>
 *   <li>SkillBasedStrategy – balances total skill levels using Snake Draft.</li>
 *   <li>RoleDiversityStrategy – ensures a mix of in-game roles.</li>
 *   <li>GameVarietyStrategy – limits maximum players of the same preferred game.</li>
 *   <li>HybridStrategy – combines personality and skill balancing.</li>
 *   <li>RandomizedStrategy – purely random assignment of players.</li>
 *
 * </ul>
 * </p>
 *
 * @author vidura nayanawickrama
 * @version 1.0
 */
public interface TeamFormationStrategy {

    /**
     * Forms teams from a list of players according to a specific strategy.
     *
     * @param players  the list of Player objects to be divided into teams
     * @param teamSize the desired number of players per team
     * @return a nested ArrayList where each inner list represents a team
     */
    ArrayList<ArrayList<Player>> formTeams(ArrayList<Player> players, int teamSize);

} // end interface
