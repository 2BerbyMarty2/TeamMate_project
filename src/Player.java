/**
 * Represents a player in the TeamMate system.
 *
 * <p>This class stores gameplay preferences, skill metrics, and personality
 * attributes used during team formation, including:</p>
 * <ul>
 *   <li>Player ID, name, and email</li>
 *   <li>Preferred game</li>
 *   <li>Preferred in-game role</li>
 *   <li>Skill level</li>
 *   <li>Personality type and score</li>
 * </ul>
 *
 * <p>These attributes are used by team formation strategies to create
 * balanced and well-matched teams.</p>
 */

public class Player extends User {

    private String preferredGame;     // Player's main game choice
    private int skillLevel;           // Skill rating (1–100)
    private String preferredRole;     // Role preference within the game
    private int personalityScore;     // Survey-derived personality score
    private String personalityType;   // Categorized personality type

    /**
     * Constructs a Player with full personality details.
     *
     * @param ID               Unique player ID
     * @param name             Player's full name
     * @param email            Email address
     * @param preferredGame    Game preference
     * @param skillLevel       Skill rating (1–100)
     * @param preferredRole    Player's preferred team role
     * @param personalityScore Personality score (scaled)
     * @param personalityType  Personality category
     */
    public Player(String ID, String name, String email,
                  String preferredGame, int skillLevel, String preferredRole,
                  int personalityScore, String personalityType) {

        super(ID, name, email);
        this.preferredGame = preferredGame;
        this.skillLevel = skillLevel;
        this.preferredRole = preferredRole;
        this.personalityScore = personalityScore;
        this.personalityType = personalityType;
    }

    /**
     * Constructs a Player without a personality score (used before survey).
     *
     * @param ID               Unique player ID
     * @param name             Player's full name
     * @param email            Email address
     * @param preferredGame    Game preference
     * @param skillLevel       Skill rating
     * @param preferredRole    Team role preference
     * @param personalityType  Initial personality label (optional)
     */
    public Player(String ID, String name, String email,
                  String preferredGame, int skillLevel, String preferredRole,
                  String personalityType) {

        super(ID, name, email);
        this.preferredGame = preferredGame;
        this.skillLevel = skillLevel;
        this.preferredRole = preferredRole;
        this.personalityType = personalityType;
    }

    // --- Getters & Setters ---

    public int getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }

    public String getPersonalityType() {
        return personalityType;
    }

    public void setPersonalityType(String personalityType) {
        this.personalityType = personalityType;
    }

    public String getPreferredGame() {
        return preferredGame;
    }

    public void setPreferredGame(String preferredGame) {
        this.preferredGame = preferredGame;
    }

    public String getPreferredRole() {
        return preferredRole;
    }

    public void setPreferredRole(String preferredRole) {
        this.preferredRole = preferredRole;
    }

    public int getPersonalityScore() {
        return personalityScore;
    }

    public void setPersonalityScore(int personalityScore) {
        this.personalityScore = personalityScore;
    }

    /**
     * Returns a clean, formatted string representing the player's profile.
     */
    @Override
    public String toString() {
        return "========== PLAYER PROFILE ==========\n" +
                "ID               : " + getID() + "\n" +
                "Name             : " + getName() + "\n" +
                "Email            : " + getEmail() + "\n" +
                "Preferred Game   : " + preferredGame + "\n" +
                "Skill Level      : " + skillLevel + "\n" +
                "Preferred Role   : " + preferredRole + "\n" +
                "Personality Score: " + personalityScore + "\n" +
                "Personality Type : " + personalityType + "\n" +
                "====================================";
    }
} // end Player
