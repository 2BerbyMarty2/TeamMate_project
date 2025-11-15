import java.util.ArrayList;
import java.util.Scanner;

public class Test {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        ArrayList<Player> players = new ArrayList<>();

        players = CSVManager.importPlayersFromCSV("participants_sample.csv");
        TeamManager.runTeamFormationMenu(players, sc);

        Player newPlayer = new Player("P011", "Alice", "alice@example.com", "Chess", 5, "Strategist");

        PersonalitySurvey.runSurvey(newPlayer, sc);

        System.out.println(newPlayer.getName() + " is a " + newPlayer.getPersonalityType());

    }
}
