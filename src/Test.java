import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Test {

    private static Scanner sc = new Scanner(System.in);
    private static final HashMap<String, Organizer> organizerMap = loadOrganizerMap();
    private static final ArrayList<Player> players = CSVManager.importPlayersFromCSV("participants_sample.csv");

    private static HashMap<String, Organizer> loadOrganizerMap() {
        ArrayList<Organizer> list = CSVManager.importOrganizersFromCSV("organizers.csv");
        HashMap<String, Organizer> map = new HashMap<>();
        for (Organizer o : list) {
            map.put(o.getID(), o);
        }
        return map;
    }

    public static void main(String[] args) {
        ActivityLogger.log("Application Started."); // LOGGING

        while (true){
            System.out.println("---------------------------MAIN-MENU---------------------------");
            System.out.println("01. Organizer Log in.");
            System.out.println("02. Player Log in.");
            System.out.println("03. EXIT");
            System.out.print("Enter your choice: ");

            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("Organizer Log in");
                    organizerLogin();
                    break;
                case "2":
                    System.out.println("Player Log in");
                    user_login();
                    break;
                case "3":
                    System.out.println("EXIT........");
                    ActivityLogger.log("Application Closed by User."); // LOGGING
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

        Organizer loggedIn = organizerMap.get(id);
        String inputHash = PasswordUtils.hashPassword(password);

        if (loggedIn != null && loggedIn.getPassword().equals(inputHash)) {
            ActivityLogger.log("Organizer Login Success: " + loggedIn.getName() + " (" + loggedIn.getID() + ")"); // LOGGING
            System.out.println("Welcome " + loggedIn.getName() + "!");
            organizerMenu(loggedIn);
        } else {
            ActivityLogger.logWarning("Organizer Login Failed: Invalid Credentials for ID " + id); // LOGGING
            System.out.println("Invalid ID or password. Returning to main menu.");
        }
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

                    String hashedPass = PasswordUtils.hashPassword(newPass);
                    organizer.setPassword(hashedPass);

                    ArrayList<Organizer> listToSave = new ArrayList<>(organizerMap.values());
                    CSVManager.exportOrganizersToCSV("organizers.csv", listToSave);

                    ActivityLogger.log("Organizer Password Changed: " + organizer.getID()); // LOGGING
                    System.out.println("Password updated and saved securely!");
                    break;
                case "4":
                    ActivityLogger.log("Organizer Logged Out: " + organizer.getID()); // LOGGING
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
            System.out.println();
        }
    }

    public static void user_login() {
        System.out.println("========== Player LOGIN ==========");
        System.out.print("Enter your Player ID: ");

        String id = sc.nextLine().trim();
        Player loggedIn = null;

        for (Player p : players) {
            if (p.getID().equalsIgnoreCase(id)) {
                loggedIn = p;
                break;
            }
        }

        if (loggedIn != null) {
            ActivityLogger.log("Player Login Success: " + loggedIn.getName()); // LOGGING
            System.out.println("Login successful!");
            viewUserProfile(loggedIn);

            System.out.print("Retake personality survey? (yes/no): ");
            String ans = sc.nextLine().toLowerCase().trim();

            if (ans.equals("yes")) {
                PersonalitySurvey.runSurvey(loggedIn, sc);
                CSVManager.exportPlayersToCSV("participants_sample.csv", players);
                ActivityLogger.log("Player Retook Survey: " + loggedIn.getID()); // LOGGING
            }
            return;
        }

        ActivityLogger.logWarning("Player Login Failed: Unknown ID " + id); // LOGGING
        System.out.println("No user found with ID: " + id);
        System.out.print("Would you like to register? (yes/no): ");

        String choice = sc.nextLine().trim().toLowerCase();

        if (!choice.equals("yes")) {
            return;
        }

        System.out.println("===== NEW USER REGISTRATION =====");

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        System.out.print("Enter Preferred Game: ");
        String game = sc.nextLine();

        int skill = 0;
        boolean validSkill = false;

        while (!validSkill) {
            System.out.print("Enter Skill Level (1-100): ");
            String input = sc.nextLine();

            try {
                skill = Integer.parseInt(input);

                // Optional: Check if the number is actually within 1-100
                if (skill >= 1 && skill <= 100) {
                    validSkill = true; // Input is good, exit loop
                } else {
                    System.out.println("Error: Please enter a number between 1 and 100.");
                }

            } catch (NumberFormatException e) {
                // This block runs if the user enters non-numeric text
                System.out.println("Invalid input! Please enter a numeric value (e.g., 50).");
            }
        }

        System.out.print("Enter Preferred Role: ");
        String role = sc.nextLine();

        System.out.println("\nRegistration Complete for " + name + "!");
        Player newPlayer = new Player(id, name, email, game, skill, role, 0, "Unknown");
        players.add(newPlayer);

        System.out.println("\nYou must now complete a Personality Survey.");
        PersonalitySurvey.runSurvey(newPlayer, sc);

        CSVManager.exportPlayersToCSV("participants_sample.csv", players);

        ActivityLogger.log("New Player Registered: " + name + " (" + id + ")"); // LOGGING
        System.out.println("Registration complete!");
        viewUserProfile(newPlayer);
    }

    public static void viewUserProfile(Player user) {
        System.out.println("=========== USER PROFILE ===========");
        System.out.println(user.toString());
        System.out.println("====================================");
    }
}