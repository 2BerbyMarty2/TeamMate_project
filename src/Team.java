import java.util.List;

public class Team {
    private String teamName;
    private String teamDescription;
    private int teamSize;
    private List<Player> teamMembers;

    public Team(String teamName, String teamDescription, int teamSize, List<Player> teamMembers) {
        this.teamName = teamName;
        this.teamDescription = teamDescription;
        this.teamSize = teamSize;
        this.teamMembers = teamMembers;
    }
    //Getters and Setters

    public String getTeamName() {
        return teamName;
    }
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    public String getTeamDescription() {
        return teamDescription;
    }
    public void setTeamDescription(String teamDescription) {
        this.teamDescription = teamDescription;
    }
    public int getTeamSize() {
        return teamSize;
    }
    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }
    public List<Player> getTeamMembers() {
        return teamMembers;
    }
}
