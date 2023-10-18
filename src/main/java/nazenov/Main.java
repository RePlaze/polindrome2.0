package nazenov;

import java.util.Scanner;
import static nazenov.Print.*;

public class Main {
    public static void main(String[] args) {
        // Initialize the Game classes.
        ILeaderboard leaderboard = new Leaderboard();
        PalindromeGame game = new PalindromeGame(leaderboard);
        Scanner scanner = new Scanner(System.in);
        int currentPlayerIndex = 0;

        printCommands();
        createNewPlayer(game,scanner);

        // Main game loop
        while (true) {
            String input = getUserInput("Enter a word or phrase: ", scanner).toLowerCase();

            switch (input) {
                case "1" -> exitGame();
                case "2" -> displayLeaderboard(game, currentPlayerIndex);
                case "3" -> {createNewPlayerAndResetGame(game, scanner, currentPlayerIndex);currentPlayerIndex++;}
                default -> processInput(game, input, currentPlayerIndex);
            }
        }
    }


    // Display the available commands.
    private static void printCommands() {
        println("Commands:");
        println("'1' to exit");
        println("'2' to view leaderboard");
        println("'3' to create a new player");
    }

    private static void createNewPlayerAndResetGame(PalindromeGame game, Scanner scanner, int currentPlayerIndex) {
        game.resetGame(currentPlayerIndex);
        createNewPlayer(game, scanner);
    }

    private static void createNewPlayer(PalindromeGame game, Scanner scanner) {
        String playerName = getUserInput("Enter the player's name: ", scanner);
        Player newPlayer = new Player(playerName);
        game.addPlayer(newPlayer);
        println("Player '" + playerName + "' created. ✔️");
    }

    private static void processInput(PalindromeGame game, String input, int currentPlayerIndex) {
        int score = game.processPhrase(input, currentPlayerIndex);
        println("Score: " + score + "\n");
    }

    private static String getUserInput(String prompt, Scanner scanner) {
        print(prompt);
        return scanner.nextLine().replaceAll(" ", "");
    }

    private static void displayLeaderboard(PalindromeGame game, int count) {
        game.resetGame(count);
        println("Top Scores:");
        game.getTopScores().entrySet()
                .stream()
                .sorted((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue()))
                .forEach(entry -> println(entry.getValue() + " points achieved by " + entry.getKey()) );
    }
    private static void exitGame() {
        System.exit(0);
    }
}
