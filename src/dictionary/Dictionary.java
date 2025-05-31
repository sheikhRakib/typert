package dictionary;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        InputStream is = getClass().getResourceAsStream(filePath);        
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line;

        try {
            while ((line = br.readLine()) != null) {
                String[] wordArray = line.trim().split("\\s+");
                Collections.addAll(words, wordArray);
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void generateWord() {
        if(GameWindow.gameState != GameState.PLAYING) return;
        
        if(waitTime > 0) {
            waitTime--;
            return;
        }

        waitTime = (int) (Math.random() * (maxWaitTime - minWaitTime + 1)) + minWaitTime;
        int weight = (int) (Math.random() * tw);

        if (weight < ew) {
            setProbabilities(ew-15, nw+10 , hw+5, sw+1);
            String s =  easyWords.get((int) (Math.random() * easyWords.size()));
            Word newWord = new Word(s, Word.NORMAL);
            GameWindow.words.add(newWord);
        } else if (weight < ew + nw) {
            setProbabilities(ew+5, nw-15 , hw+10, sw+1);
            String s = normalWords.get((int) (Math.random() * normalWords.size()));
            Word newWord = new Word(s, Word.NORMAL);
            GameWindow.words.add(newWord);
        } else if (weight < ew + nw + hw) {
            setProbabilities(ew+10, nw+5 , hw-15, sw+1);
            String s = hardWords.get((int) (Math.random() * hardWords.size()));
            Word newWord = new Word(s, Word.NORMAL);
            GameWindow.words.add(newWord);
        } else {
            setProbabilities(ew, nw , hw, 0);
            String s =  specialWords.get((int) (Math.random() * specialWords.size()));
            Word newWord = new Word(s, Word.SPECIAL);
            GameWindow.words.add(newWord);
        }
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
