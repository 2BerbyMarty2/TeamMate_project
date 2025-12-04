import java.util.Scanner;

public class PersonalitySurvey {

    /**
     * Conducts a 5-question personality survey for a player and assigns
     * a personality type based on the scaled score.
     *
     * <p>Survey details:</p>
     * <ul>
     *   <li>Each question is rated from 1 (Strongly Disagree) to 5 (Strongly Agree).</li>
     *   <li>Total raw score ranges from 5–25, which is then scaled to 20–100.</li>
     * </ul>
     *
     * <p>Personality types based on scaled score:</p>
     * <ul>
     *   <li>90–100: Leader</li>
     *   <li>70–89: Balanced</li>
     *   <li>20–69: Thinker</li>
     * </ul>
     *
     * @param player the Player object to assign survey results to
     * @param sc     Scanner object for reading user input
     */

    public static void runSurvey(Player player, Scanner sc) {
        int totalScore = 0;

        System.out.println("Answer each question from 1 (Strongly Disagree) to 5 (Strongly Agree):");

        String[] questions = {
                "Q1: I enjoy taking the lead and guiding others during group activities.",
                "Q2: I prefer analyzing situations and coming up with strategic solutions.",
                "Q3: I work well with others and enjoy collaborative teamwork.",
                "Q4: I am calm under pressure and can help maintain team morale.",
                "Q5: I like making quick decisions and adapting in dynamic situations."
        };

        // Loop through each question and get validated input
        for (String q : questions) {
            int answer = 0;
            while (answer < 1 || answer > 5) {
                System.out.println(q);
                System.out.print("Your rating (1-5): ");
                if (sc.hasNextInt()) {
                    answer = sc.nextInt();
                    sc.nextLine(); // consume newline
                } else {
                    sc.nextLine(); // discard invalid input
                    System.out.println("Please enter a number 1-5.");
                }
            }
            totalScore += answer;
        }

        // Scale raw score (5–25) to 20–100
        int scaledScore = totalScore * 4;
        player.setPersonalityScore(scaledScore);

        // Assign personality type based on scaled score
        if (scaledScore >= 90) {
            player.setPersonalityType("Leader");
        } else if (scaledScore >= 70) {
            player.setPersonalityType("Balanced");
        } else {
            player.setPersonalityType("Thinker");
        }

        // Display results to the player
        System.out.println("Personality Survey Completed!");
        System.out.println("Scaled Score: " + scaledScore);
        System.out.println("Personality Type: " + player.getPersonalityType());
    } // end runSurvey

} // end class