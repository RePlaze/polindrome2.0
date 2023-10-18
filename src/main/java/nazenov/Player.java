package nazenov;

import java.util.HashSet;
import java.util.Set;

public class Player {
    private final String name;
    private final Set<String> usedPhrases;

    public Player(String name) {
        this.name = name;
        this.usedPhrases = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public boolean hasUsedPhrase(String phrase) {
        return usedPhrases.contains(phrase);
    }

    public void markPhraseAsUsed(String phrase) {
        usedPhrases.add(phrase);
    }
}
