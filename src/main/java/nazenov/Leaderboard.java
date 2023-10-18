package nazenov;

import java.util.HashMap;
import java.util.Map;

public class Leaderboard implements ILeaderboard {
    private final Map<String, Integer> playerScores = new HashMap<>();

    @Override
    public void addPlayer(String playerName, int score, int currentPlayerIndex) {
        playerScores.putIfAbsent(playerName, 0);
        int currentMaxScore = playerScores.get(playerName);

        if (score > currentMaxScore) {
            playerScores.put(playerName, score);
        }
    }

    @Override
    public Map<String, Integer> getTopScores() {
        return new HashMap<>(playerScores);
    }
}
