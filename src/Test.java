import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Test {

    private static Scanner sc = new Scanner(System.in);
    private static final HashMap<String, Organizer> organizerMap = loadOrganizerMap();
    private static final ArrayList<Player> players = CSVManager.importPlayersFromCSV("participants_sample.csv");

    /**
    * Loads all organizers from the CSV file and maps them by their unique ID.
    * This allows for efficient lookup of organizers in the system.
    * @return HashMap mapping organizer IDs to Organizer objects.
    */
    private static HashMap<String, Organizer> loadOrganizerMap() {
        // Import organizers from CSV
        ArrayList<Organizer> list = CSVManager.importOrganizersFromCSV("organizers.csv");

        // Convert the list to a HashMap keyed by organizer ID for fast access
        HashMap<String, Organizer> map = new HashMap<>();
        for (Organizer o : list) {
            map.put(o.getID(), o);
        }

        return map;
    }

    /**
    * Main entry point for the TeamMate application.
    * Displays the main menu for Organizer and Player logins,
    * and handles user input in a continuous loop until exit.
    *
    * Logging is performed when the application starts and closes.
    */
    public static void main(String[] args) {
        // Log application start
        ActivityLogger.log("Application Started.");

        // Main menu loop
        while (true) {
            System.out.println("---------------------------MAIN-MENU---------------------------");
            System.out.println("01. Organizer Log in.");
            System.out.println("02. Player Log in.");
            System.out.println("03. EXIT");
            System.out.print("Enter your choice: ");

            String choice = sc.nextLine(); // Read user input

            switch (choice) {
                case "1":
                    // Organizer login flow
                    System.out.println("Organizer Log in");
                    organizerLogin();
                    break;

                case "2":
                    // Player login flow
                    System.out.println("Player Log in");
                    user_login();
                    break;

                case "3":
                    // Exit application
                    System.out.println("EXIT........");
                    ActivityLogger.log("Application Closed by User."); // Log exit
                    System.exit(0);
                    break;

                default:
                    // Invalid input handling
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    } // end main


    /**
    * Handles Organizer login by validating ID and password.
    *
    * Workflow:
    * 1. Prompt the organizer for ID and password.
    * 2. Hash the entered password using SHA-256 for secure comparison.
    * 3. Check credentials against the loaded organizer map.
    * 4. If successful, log the login event and launch the organizer menu.
    * 5. If failed, log a warning and return to the main menu.
    */
    public static void organizerLogin() {
        // Prompt for credentials
        System.out.print("Enter Organizer ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        // Retrieve organizer from map
        Organizer loggedIn = organizerMap.get(id);

        // Hash input password for secure comparison
        String inputHash = PasswordUtils.hashPassword(password);

        // Validate credentials
        if (loggedIn != null && loggedIn.getPassword().equals(inputHash)) {
            // Successful login: log event and proceed to menu
            ActivityLogger.log("Organizer Login Success: " + loggedIn.getName() + " (" + loggedIn.getID() + ")");
            System.out.println("Welcome " + loggedIn.getName() + "!");
            organizerMenu(loggedIn);
        } else {
            // Failed login: log warning and return
            ActivityLogger.logWarning("Organizer Login Failed: Invalid Credentials for ID " + id);
            System.out.println("Invalid ID or password. Returning to main menu.");
        }
    } // end organizerLogin


    /**
    * Displays the Organizer menu and handles all organizer-specific actions.
    *
    * Menu Options:
    * 1. View current teams from CSV.
    * 2. Create new teams using selected strategy.
    * 3. Update organizer password securely (hashed & saved to CSV).
    * 4. Logout from the organizer session.
    *
    * @param organizer The currently logged-in Organizer object.
    */
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
                    // Load teams from CSV and display
                    ArrayList<ArrayList<Player>> loadedTeams = CSVManager.importTeamsFromCSV("teams_output.csv");
                    TeamManager.printTeams(loadedTeams);
                    break;

                case "2":
                    // Run team formation menu
                    System.out.println("Creating teams...");
                    TeamManager.runTeamFormationMenu(players, sc);
                    break;

                case "3":
                    // Update organizer password securely
                    System.out.print("Enter new password: ");
                    String newPass = sc.nextLine();
                    String hashedPass = PasswordUtils.hashPassword(newPass);
                    organizer.setPassword(hashedPass);

                    // Save updated organizers to CSV
                    ArrayList<Organizer> listToSave = new ArrayList<>(organizerMap.values());
                    CSVManager.exportOrganizersToCSV("organizers.csv", listToSave);
                    ActivityLogger.log("Organizer Password Changed: " + organizer.getID()); // LOGGING
                    System.out.println("Password updated and saved securely!");
                    break;
                case "4":
                    // Logout
                    ActivityLogger.log("Organizer Logged Out: " + organizer.getID()); // LOGGING
                    System.out.println("Logging out...");
                    return;
                default:
                    // Invalid input handling
                    System.out.println("Invalid choice! Try again.");
                    break;
            }
            System.out.println();
        }
    } // end organizerMenu


    /**
    * Handles player login and registration.
    *
    * Workflow:
    * 1. Prompt the player for their ID.
    * 2. If ID exists, log in and allow optional retake of the personality survey.
    * 3. If ID does not exist, offer new player registration.
    * 4. For registration:
    *    - Collect name, email, preferred game, skill level, and role.
    *    - Validate skill input (1–100).
    *    - Require completion of the personality survey.
    * 5. Save all changes to CSV and log relevant events.
    */
    public static void user_login() {
        System.out.println("========== Player LOGIN ==========");
        System.out.print("Enter your Player ID: ");
        String id = sc.nextLine().trim();

        Player loggedIn = null;
        // Search for existing player by ID
        for (Player p : players) {
            if (p.getID().equalsIgnoreCase(id)) {
                loggedIn = p;
                break;
            }
        }

        if (loggedIn != null) {
            // Successful login
            ActivityLogger.log("Player Login Success: " + loggedIn.getName());
            System.out.println("Login successful!");
            viewUserProfile(loggedIn);

            // Optional retake of personality survey
            System.out.print("Retake personality survey? (yes/no): ");
            String ans = sc.nextLine().toLowerCase().trim();
            if (ans.equals("yes")) {
                PersonalitySurvey.runSurvey(loggedIn, sc);
                CSVManager.exportPlayersToCSV("participants_sample.csv", players);
                ActivityLogger.log("Player Retook Survey: " + loggedIn.getID());
            }
            return;
        }

        // Failed login
        ActivityLogger.logWarning("Player Login Failed: Unknown ID " + id);
        System.out.println("No user found with ID: " + id);
        System.out.print("Would you like to register? (yes/no): ");
        String choice = sc.nextLine().trim().toLowerCase();

        if (!choice.equals("yes")) return;

        // New player registration
        System.out.println("===== NEW USER REGISTRATION =====");
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        System.out.print("Enter Preferred Game: ");
        String game = sc.nextLine();

        // Validate skill input (1-100)
        int skill = 0;
        boolean validSkill = false;
        while (!validSkill) {
            System.out.print("Enter Skill Level (1-100): ");
            String input = sc.nextLine();
            try {
                skill = Integer.parseInt(input);
                if (skill >= 1 && skill <= 100) {
                    validSkill = true;
                } else {
                    System.out.println("Error: Please enter a number between 1 and 100.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a numeric value (e.g., 50).");
            }
        }

        System.out.print("Enter Preferred Role: ");
        String role = sc.nextLine();

        System.out.println("\nRegistration Complete for " + name + "!");
        Player newPlayer = new Player(id, name, email, game, skill, role, 0, "Unknown");
        players.add(newPlayer);

        // Mandatory personality survey
        System.out.println("\nYou must now complete a Personality Survey.");
        PersonalitySurvey.runSurvey(newPlayer, sc);

        // Save new player to CSV
        CSVManager.exportPlayersToCSV("participants_sample.csv", players);

        // Log registration
        ActivityLogger.log("New Player Registered: " + name + " (" + id + ")");
        System.out.println("Registration complete!");
        viewUserProfile(newPlayer);
    } // end user_login

    /**
    * Displays a player's profile in a formatted manner.
    *
    * <p>This includes key details such as the player's ID, name, email,
    * preferred game, role, skill level, and personality type.</p>
    *
    * @param user the Player object whose profile is to be displayed
    */
    public static void viewUserProfile(Player user) {
        System.out.println("=========== USER PROFILE ===========");
        System.out.println(user.toString()); // Display player details
        System.out.println("====================================");
    } // end viewUserProfile


}// end Main