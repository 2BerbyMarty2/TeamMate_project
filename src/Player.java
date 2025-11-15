public class Player extends User {
    private String preferredGame;
    private int skillLevel;
    private String preferredRole;
    private int personalityScore;
    private String personalityType;

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
    public Player(String ID, String name, String email, String preferredGame, int skillLevel, String preferredRole) {
        super(ID, name, email);
        this.preferredGame = preferredGame;
        this.skillLevel = skillLevel;
        this.preferredRole = preferredRole;
    }


    // Getters and setters
    public String getPreferredGame() { return preferredGame; }
    public void setPreferredGame(String preferredGame) { this.preferredGame = preferredGame; }

    public int getSkillLevel() { return skillLevel; }
    public void setSkillLevel(int skillLevel) { this.skillLevel = skillLevel; }

    public String getPreferredRole() { return preferredRole; }
    public void setPreferredRole(String preferredRole) { this.preferredRole = preferredRole; }

    public int getPersonalityScore() { return personalityScore; }
    public void setPersonalityScore(int personalityScore) { this.personalityScore = personalityScore; }

    public String getPersonalityType() { return personalityType; }
    public void setPersonalityType(String personalityType) { this.personalityType = personalityType; }

    @Override
    public String toString() {
        return "\nID: " + getID() + ", Name: " + getName() + ", Email: " + getEmail() +
                ", Game: " + preferredGame + ", Skill: " + skillLevel +
                ", Role: " + preferredRole +
                ", PersonalityScore: " + personalityScore +
                ", PersonalityType: " + personalityType;
    }
}
