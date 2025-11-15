import java.util.Scanner;

public class PersonalitySurvey {

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

        for (String q : questions) {
            int answer = 0;
            while (answer < 1 || answer > 5) {
                System.out.println(q);
                System.out.print("Your rating (1-5): ");
                if (sc.hasNextInt()) {
                    answer = sc.nextInt();
                    sc.nextLine(); // consume newline
                } else {
                    sc.nextLine(); // invalid input
                    System.out.println("Please enter a number 1-5.");
                }
            }
            totalScore += answer;
        }

        int scaledScore = totalScore * 4; // scale 5–25 → 20–100
        player.setPersonalityScore(scaledScore);

        // Determine personality type
        if (scaledScore >= 90) {
            player.setPersonalityType("Leader");
        } else if (scaledScore >= 70) {
            player.setPersonalityType("Balanced");
        } else {
            player.setPersonalityType("Thinker");
        }

        System.out.println("Personality Survey Completed!");
        System.out.println("Scaled Score: " + scaledScore);
        System.out.println("Personality Type: " + player.getPersonalityType());
    }
}
