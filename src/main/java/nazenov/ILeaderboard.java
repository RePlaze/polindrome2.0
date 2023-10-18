package nazenov;

import java.util.Map;

public interface ILeaderboard {
    void addPlayer(String playerName, int score, int currentPlayerIndex);
    Map<String, Integer> getTopScores();
}
