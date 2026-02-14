package dictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import entity.Word;
import main.GameWindow;
import utility.GameState;

public class Dictionary {
    private int ew; // Easy Probability Weight
    private int nw; // Normal Probability Weight
    private int hw; // Hard Probability Weight
    private int sw; // Special Probability Weight
    private int tw; // Total Probability Weight

    private int maxWaitTime = 180; // Maximum wait time in frames
    private int minWaitTime = 50; // Minimum wait time in frames
    private int waitTime = 0; // Current wait time in frames

    // Lists to hold words of different difficulty levels
    private List<String> easyWords = new ArrayList<>();
    private List<String> normalWords = new ArrayList<>();
    private List<String> hardWords = new ArrayList<>();
    private List<String> specialWords = new ArrayList<>();

    public Dictionary() {
        this.reset();
        this.loadDictionary("/files/easy.txt", easyWords);
        this.loadDictionary("/files/normal.txt", normalWords);
        this.loadDictionary("/files/hard.txt", hardWords);
        this.loadDictionary("/files/special.txt", specialWords);
    }

    private void loadDictionary(String filePath, List<String> words) {
        try (InputStream is = openResourceStream(filePath);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] wordArray = line.trim().split("\\s+");
                Collections.addAll(words, wordArray);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private InputStream openResourceStream(String filePath) throws IOException {
        InputStream classpathStream = getClass().getResourceAsStream(filePath);
        if (classpathStream != null) {
            return classpathStream;
        }

        Path fileFallback = Path.of("res", filePath.replaceFirst("^/", ""));
        if (Files.exists(fileFallback)) {
            return Files.newInputStream(fileFallback, StandardOpenOption.READ);
        }

        throw new IOException("Dictionary file not found: " + filePath + " or " + fileFallback);
    }
    
    public void generateWord() {
        if(GameWindow.gameState != GameState.PLAYING) return;
        
        if(waitTime > 0) {
            waitTime--;
            return;
        }

        ThreadLocalRandom random = ThreadLocalRandom.current();
        waitTime = random.nextInt(minWaitTime, maxWaitTime + 1);
        int weight = random.nextInt(tw);

        if (weight < ew) {
            setProbabilities(ew-15, nw+10 , hw+5, sw+1);
            GameWindow.words.add(new Word(randomWord(easyWords), Word.NORMAL));
        } else if (weight < ew + nw) {
            setProbabilities(ew+5, nw-15 , hw+10, sw+1);
            GameWindow.words.add(new Word(randomWord(normalWords), Word.NORMAL));
        } else if (weight < ew + nw + hw) {
            setProbabilities(ew+10, nw+5 , hw-15, sw+1);
            GameWindow.words.add(new Word(randomWord(hardWords), Word.NORMAL));
        } else {
            setProbabilities(ew, nw , hw, 0);
            GameWindow.words.add(new Word(randomWord(specialWords), Word.SPECIAL));
        }
    }

    private String randomWord(List<String> words) {
        return words.get(ThreadLocalRandom.current().nextInt(words.size()));
    }
    
    public void reset() {
        ew = 1000;
        nw = 0;
        hw = 0;
        sw = 0;

        tw = ew + nw + hw + sw;
    }

    public void setProbabilities(int easy, int normal, int hard, int special) {
        this.ew = (easy >= 0) ? easy : 0;
        this.nw = (normal >= 0) ? normal : 0;
        this.hw = (hard >= 0) ? hard : 0;
        this.sw = (special >= 0) ? special : 0;

        this.tw = ew + nw + hw + sw;
    }
}
