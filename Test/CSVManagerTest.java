import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class CSVManagerTest {

    private static final String TEST_PLAYER_FILE = "test_players.csv";
    private static final String TEST_TEAM_FILE = "test_teams.csv";

    // --- SETUP: Create dummy data before tests ---
    @Before
    public void setUp() throws IOException {
        // Create a dummy player CSV
        try (FileWriter writer = new FileWriter(TEST_PLAYER_FILE)) {
            writer.write("ID,Name,Email,PreferredGame,SkillLevel,PreferredRole,PersonalityScore,PersonalityType\n");
            writer.write("P1,Alice,alice@test.com,Valorant,90,Sniper,85,Leader\n");
            writer.write("P2,Bob,bob@test.com,CS:GO,80,Rifler,50,Thinker\n");
        }
    }

    // --- TEARDOWN: Delete files after tests ---
    @After
    public void tearDown() {
        new File(TEST_PLAYER_FILE).delete();
        new File(TEST_TEAM_FILE).delete();
    }

    // --- TEST 1: Import Logic ---
    // Does it correctly parse the CSV file we just created?
    @Test
    public void testImportPlayers() {
        ArrayList<Player> players = CSVManager.importPlayersFromCSV(TEST_PLAYER_FILE);

        assertNotNull("Player list should not be null", players);
        assertEquals("Should import exactly 2 players", 2, players.size());

        Player p1 = players.get(0);
        assertEquals("ID mismatch", "P1", p1.getID());
        assertEquals("Name mismatch", "Alice", p1.getName());
        assertEquals("Skill mismatch", 90, p1.getSkillLevel());
        assertEquals("Type mismatch", "Leader", p1.getPersonalityType());
    }

    // --- TEST 2: Export Logic ---
    // If we create a list of players and save it, does the file actually get created?
    @Test
    public void testExportPlayers() {
        ArrayList<Player> list = new ArrayList<>();
        list.add(new Player("P3", "Charlie", "c@test.com", "Dota", 50, "Supp", 20, "Balanced"));

        String exportFile = "test_export_players.csv";
        CSVManager.exportPlayersToCSV(exportFile, list);

        File file = new File(exportFile);
        assertTrue("Export file should exist", file.exists());
        assertTrue("File should not be empty", file.length() > 0);

        // Cleanup this specific file
        file.delete();
    }

    // --- TEST 3: Team Export/Import Round Trip ---
    // Can we save teams and load them back exactly as they were?
    @Test
    public void testTeamRoundTrip() {
        // 1. Create Dummy Teams
        ArrayList<ArrayList<Player>> teams = new ArrayList<>();
        ArrayList<Player> team1 = new ArrayList<>();
        team1.add(new Player("T1_P1", "Tom", "t@test.com", "LoL", 99, "Mid", "Leader"));
        teams.add(team1);

        // 2. Export
        CSVManager.exportTeamsToCSV(TEST_TEAM_FILE, teams);

        // 3. Import
        ArrayList<ArrayList<Player>> loadedTeams = CSVManager.importTeamsFromCSV(TEST_TEAM_FILE);

        // 4. Verify
        assertNotNull(loadedTeams);
        assertEquals("Should have 1 team", 1, loadedTeams.size());
        assertEquals("Team 1 should have 1 player", 1, loadedTeams.get(0).size());
        assertEquals("Player Name Check", "Tom", loadedTeams.get(0).get(0).getName());
    }

    // --- TEST 4: Handle Missing File Gracefully ---
    // The program should NOT crash if the file is missing. It should just return an empty list.
    @Test
    public void testMissingFile() {
        ArrayList<Player> players = CSVManager.importPlayersFromCSV("non_existent_file.csv");
        assertNotNull("Should return non-null list even if file missing", players);
        assertTrue("List should be empty", players.isEmpty());
    }
}
