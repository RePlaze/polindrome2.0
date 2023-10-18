package nazenov;

import java.text.Normalizer;
import java.util.*;

import static nazenov.Print.*;

public class PalindromeGame {
    private final ILeaderboard leaderboard;
    private final List<Player> players = new ArrayList<>();
    private int currentScore;

    public PalindromeGame(ILeaderboard leaderboard) {
        this.leaderboard = leaderboard;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public int calculateScore(String phrase) {
        String lettersOnly = phrase.replaceAll("[^a-zA-Zа-яА-Я]", "");
        return lettersOnly.length();
    }

    public boolean isPalindrome(String phrase) {
        if (phrase.matches(".*\\d.*")) return false;

        String normalizedPhrase = normalize(phrase);
        return normalizedPhrase.equals(new StringBuilder(normalizedPhrase).reverse().toString());
    }

    public int processPhrase(String phrase, int currentPlayerIndex) {
        if (!isPhraseValid(phrase)) {
            println("Invalid input.\nPlease enter a word or phrase containing only letters.");
            return currentScore;
        }

        if (playerHasUsedPhrase(phrase)) {
            print("You already used this phrase,");
        }

        if (isPalindrome(phrase) && !playerHasUsedPhrase(phrase)) {
            int score = calculateScore(phrase);
            markPhraseAsUsed(phrase);
            currentScore += score;
            println("+" + score + " points");
        } else {
            gameOver(currentPlayerIndex);
        }

        return currentScore;
    }

    private String normalize(String phrase) {
        return Normalizer.normalize(phrase, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .replaceAll("[^a-zA-Zа-яА-Я]", "")
                .toLowerCase();
    }

    void gameOver(int currentPlayerIndex) {
        Player currentPlayer = players.get(currentPlayerIndex);
        int maxScore = leaderboard.getTopScores().getOrDefault(currentPlayer.getName(), 0);
        leaderboard.addPlayer(currentPlayer.getName(), Math.max(maxScore, currentScore), currentPlayerIndex);
        println("Game Over!");
        currentScore = 0;
    }

    void resetGame(int currentPlayerIndex) {
        Player currentPlayer = players.get(currentPlayerIndex);
        int maxScore = leaderboard.getTopScores().getOrDefault(currentPlayer.getName(), 0);
        leaderboard.addPlayer(currentPlayer.getName(), Math.max(maxScore, currentScore), currentPlayerIndex);
        currentScore = 0;
    }

    public Map<String, Integer> getTopScores() {
        return leaderboard.getTopScores();
    }

    private boolean isPhraseValid(String phrase) {
        return phrase.matches("^[a-zA-Zа-яА-Я]+$");
    }

    private boolean playerHasUsedPhrase(String phrase) {
        return players.stream().anyMatch(player -> player.hasUsedPhrase(phrase));
    }

    private void markPhraseAsUsed(String phrase) {
        players.forEach(player -> player.markPhraseAsUsed(phrase));
    }
}
