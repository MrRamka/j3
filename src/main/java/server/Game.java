package server;

import java.util.Random;

public class Game {

    private static String currentWord;
    private static final String[] GAME_WORDS = {"Apple", "Banana", "Orange", "Asparagus", "Carrot"};

    public Game() {
        createGame();
    }

    private static synchronized void createGame() {
        Random random = new Random();
        currentWord = GAME_WORDS[Math.abs(random.nextInt() % (GAME_WORDS.length - 1))];
        System.out.println("Current game word is \""+ currentWord + "\"");
    }

    public static synchronized boolean isCorrectWord(String userWord) {
        if (userWord.equals(currentWord)) {
            createGame();
            return true;
        } else {
            return false;
        }

    }
}
