import java.util.ArrayList;
import java.util.Scanner;

public class Test {

    private static Scanner sc = new Scanner(System.in);
    private static final ArrayList<Organizer> organizers = CSVManager.importOrganizersFromCSV("organizers.csv");
    private static final ArrayList<Player> players = CSVManager.importPlayersFromCSV("participants_sample.csv");

    public static void main(String[] args) {
        while (true){
            System.out.println("---------------------------MAIN-MENU---------------------------");
            System.out.println("01. Organizer Log in.");
            System.out.println("02. User Log in.");
            System.out.println("03. EXIT");
            System.out.print("Enter your choice: ");

            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("Organizer Log in");
                    organizerLogin();
                    break;
                case "2":
                    System.out.println("User Log in");
                    user_login();
                    break;
                case "3":
                    System.out.println("EXIT........");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    public static void organizerLogin() {
        System.out.print("Enter Organizer ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        Organizer loggedIn = null;
        for (Organizer o : organizers) {
            if (o.getID().equals(id) && o.getPassword().equals(password)) {
                loggedIn = o;
                break;
            }
        }

        if (loggedIn == null) {
            System.out.println("Invalid ID or password. Returning to main menu.");
            return;
        }

        System.out.println("Welcome " + loggedIn.getName() + "!");
        organizerMenu(loggedIn);
    }

    public static void organizerMenu(Organizer organizer) {
        while (true) {
            System.out.println("========================ORGANIZER MENU========================");
            System.out.println("1. View Current Teams");
            System.out.println("2. Create Teams");
            System.out.println("3. Update Password");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");

            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    ArrayList<ArrayList<Player>> loadedTeams = CSVManager.importTeamsFromCSV("teams_output.csv");
                    TeamManager.printTeams(loadedTeams);
                    break;
                case "2":
                    System.out.println("Creating teams...");
                    TeamManager.runTeamFormationMenu(players, sc);
                    break;
                case "3":
                    System.out.print("Enter new password: ");
                    String newPass = sc.nextLine();
                    organizer.setPassword(newPass);
                    System.out.println("Password updated successfully!");
                    break;
                case "4":
                    System.out.println("Logging out...");
                    return; // Back to main menu
                default:
                    System.out.println("Invalid choice! Try again.");
            }
            System.out.println();
        }
    }
    public static void user_login() {
        System.out.println("========== USER LOGIN ==========");
        System.out.print("Enter your Player ID: ");

        String id = sc.nextLine().trim();
        Player loggedIn = null;

        // Search existing users
        for (Player p : players) {
            if (p.getID().equalsIgnoreCase(id)) {
                loggedIn = p;
                break;
            }
        }

        // If user exists
        if (loggedIn != null) {
            System.out.println("Login successful!");
            viewUserProfile(loggedIn);

            System.out.print("Retake personality survey? (yes/no): ");
            String ans = sc.nextLine().toLowerCase().trim();

            if (ans.equals("yes")) {
                PersonalitySurvey.runSurvey(loggedIn, sc);
                CSVManager.exportPlayersToCSV("participants_sample.csv", players);
            }

            return;
        }

        // If user does NOT exist → register
        System.out.println("No user found with ID: " + id);
        System.out.print("Would you like to register? (yes/no): ");

        String choice = sc.nextLine().trim().toLowerCase();

        if (!choice.equals("yes")) {
            System.out.println("Returning to main menu...");
            return;
        }

        // Registration Inputs
        System.out.println("===== NEW USER REGISTRATION =====");

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        System.out.print("Enter Preferred Game: ");
        String game = sc.nextLine();

        System.out.print("Enter Skill Level (1-100): ");
        int skill = Integer.parseInt(sc.nextLine());

        System.out.print("Enter Preferred Role: ");
        String role = sc.nextLine();

        // Create new Player
        Player newPlayer = new Player(id, name, email, game, skill, role, 0, "Unknown");
        players.add(newPlayer);

        // Run personality survey immediately
        System.out.println("\nYou must now complete a Personality Survey.");
        PersonalitySurvey.runSurvey(newPlayer, sc);

        // Save to CSV
        CSVManager.exportPlayersToCSV("participants_sample.csv", players);

        System.out.println("Registration complete!");
        viewUserProfile(newPlayer);
    }
    public static void viewUserProfile(Player user) {
        System.out.println("=========== USER PROFILE ===========");
        System.out.println(user.toString()); // Uses your Player.toString() override
        System.out.println("====================================");
    }

}
