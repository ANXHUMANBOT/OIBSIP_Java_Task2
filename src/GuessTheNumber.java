import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GuessTheNumber {

    public static void main(String[] args) {
        // 🔧 Apply custom theme
        UIManager.put("OptionPane.background", Color.BLACK);
        UIManager.put("Panel.background", Color.BLACK);
        UIManager.put("OptionPane.messageForeground", Color.WHITE);
        UIManager.put("Button.background", new Color(0, 128, 0)); // Dark Green
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("OptionPane.messageFont", new Font("Consolas", Font.BOLD, 14));
        UIManager.put("Button.font", new Font("Consolas", Font.BOLD, 13));

        showIntro();

        Random rand = new Random();
        boolean playAgain = true;

        while (playAgain) {
            int totalScore = 0;
            int maxRounds = 3;
            int maxAttempts = 5;

            for (int round = 1; round <= maxRounds; round++) {
                int numberToGuess = rand.nextInt(100) + 1;
                int attempts = 0;
                boolean guessedCorrectly = false;

                JOptionPane.showMessageDialog(null,
                        "🌀 Round " + round + " begins! 🔮\nGuess wisely, hero!",
                        "🎲 Round " + round,
                        JOptionPane.INFORMATION_MESSAGE);

                while (attempts < maxAttempts) {
                    String input = JOptionPane.showInputDialog(null,
                            "🎯 Attempt " + (attempts + 1) + "/" + maxAttempts +
                                    "\nType your guess (1–100):\n🎮 Mario: Let’s-a go!");

                    if (input == null) {
                        JOptionPane.showMessageDialog(null, "❌ Game exited by user.");
                        System.exit(0);
                    }

                    int guess;
                    try {
                        guess = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "🚫 That's not a number, buddy!");
                        continue;
                    }

                    if (guess < 1 || guess > 100) {
                        JOptionPane.showMessageDialog(null, "🚫 Stay in bounds (1–100)!");
                        continue;
                    }

                    attempts++;

                    if (guess == numberToGuess) {
                        int roundScore = (maxAttempts - attempts + 1) * 10;
                        if (attempts == 1) roundScore += 20;
                        totalScore += roundScore;

                        JOptionPane.showMessageDialog(null,
                                "🎉 WAHOO! You guessed it in " + attempts + " tries!\n" +
                                        "💎 Score this round: " + roundScore,
                                "✅ Mario says: Excellent!", JOptionPane.PLAIN_MESSAGE);
                        guessedCorrectly = true;
                        break;
                    } else {
                        String hint = generateHint(guess, numberToGuess);
                        JOptionPane.showMessageDialog(null,
                                (guess < numberToGuess ? "⬆️ Too low!" : "⬇️ Too high!") + "\n" + hint,
                                "🧠 Hint from Mario", JOptionPane.WARNING_MESSAGE);
                    }
                }

                if (!guessedCorrectly) {
                    JOptionPane.showMessageDialog(null,
                            "😵 Oof! Out of guesses!\nThe secret number was: " + numberToGuess,
                            "😬 Mario is disappointed", JOptionPane.ERROR_MESSAGE);
                }

                // Random tease
                String[] marioLines = {
                        "😆 Mario: Next round will be-a tougher!",
                        "😉 Mario: Maybe eat a mushroom next time 🍄",
                        "🤣 Mario: You call that guessing?",
                        "😎 Mario: You got style, kid.",
                        "🫡 Mario: Respect for trying, let's go!"
                };
                String randomLine = marioLines[rand.nextInt(marioLines.length)];

                JOptionPane.showMessageDialog(null,
                        "🌌 Score so far: " + totalScore + "\n\n" + randomLine,
                        "🎭 Mario reacts!", JOptionPane.PLAIN_MESSAGE);
            }

            int again = JOptionPane.showConfirmDialog(null,
                    "🔁 Wanna play again and impress Mario?",
                    "Continue?", JOptionPane.YES_NO_OPTION);

            playAgain = (again == JOptionPane.YES_OPTION);
        }

        JOptionPane.showMessageDialog(null, "👋 Mario says goodbye! Thanks for playing!");
    }

    public static void showIntro() {
        String intro = """
                🕹️ WELCOME TO NEON GUESS-A-NUMBER 🎮

                👨‍🔧 Mario is here to guide you!
                Guess the number between 1 and 100
                Earn points, collect glory 💎
                Use hints. Beat the clock. Make Mario proud!

                Ready? Let’s-a GO!
                """;

        JOptionPane.showMessageDialog(null, intro, "🌟 NEON GUESS GAME", JOptionPane.INFORMATION_MESSAGE);
    }

    public static String generateHint(int guess, int target) {
        StringBuilder hint = new StringBuilder("💡 Hints:\n");

        int diff = Math.abs(guess - target);
        if (diff <= 5) hint.append("🔥 Very close!\n");

        hint.append("🔢 It's ").append(target % 2 == 0 ? "EVEN" : "ODD").append("\n");

        int lower = Math.max(1, target - 10);
        int upper = Math.min(100, target + 10);
        hint.append("📍 The number lies between ").append(lower).append(" and ").append(upper).append("\n");

        return hint.toString();
    }
}
