import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TeamManagerTest {

    // --- TEST 1: Verify Delegation Logic ---
    // Does the Manager correctly take players, pass them to a strategy, and return the result?
    @Test
    public void testGenerateTeamsIntegration() {
        // 1. Setup Data
        ArrayList<Player> players = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            players.add(new Player("ID" + i, "Name" + i, "email@test.com", "Game", 50, "Role", "Balanced"));
        }

        // 2. Choose a Strategy (We use RandomizedStrategy as it's simple)
        TeamFormationStrategy strategy = new RandomizedStrategy();

        // 3. Execute Manager Method
        ArrayList<ArrayList<Player>> teams = TeamManager.generateTeams(players, 4, strategy);

        // 4. Assertions
        assertNotNull("Resulting teams list should not be null", teams);
        assertEquals("Should create exactly 3 teams (12 / 4 = 3)", 3, teams.size());
        assertEquals("Each team should have 4 players", 4, teams.get(0).size());
    }

    // --- TEST 2: Handling Empty Input ---
    // If we pass zero players, does it crash?
    @Test
    public void testGenerateTeamsEmpty() {
        ArrayList<Player> players = new ArrayList<>();
        TeamFormationStrategy strategy = new SkillBasedStrategy();

        ArrayList<ArrayList<Player>> teams = TeamManager.generateTeams(players, 5, strategy);

        assertNotNull(teams);
        assertTrue("Teams list should be empty if no players provided", teams.isEmpty());
    }

    // --- TEST 3: Output Robustness (Smoke Test) ---
    // This doesn't check the text content strictly, but ensures printTeams() doesn't crash
    // when given valid data.
    @Test
    public void testPrintTeamsNoCrash() {
        ArrayList<ArrayList<Player>> teams = new ArrayList<>();
        ArrayList<Player> team1 = new ArrayList<>();
        team1.add(new Player("P1", "TestPlayer", "e", "G", 100, "R", "L"));
        teams.add(team1);

        try {
            TeamManager.printTeams(teams);
        } catch (Exception e) {
            fail("printTeams threw an exception: " + e.getMessage());
        }
    }
}