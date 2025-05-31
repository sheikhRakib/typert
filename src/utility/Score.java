package utility;

public class Score {
    private int correctWord = 0;
    private int totalWord = 0;

    public void addPoint(int point) {
        this.correctWord += point;
        this.totalWord++;
    }

    public void reset() {
        this.correctWord = 0;
        this.totalWord = 0;
    }

    public String getScore() {
        return String.format("%d", correctWord);
    }

    public String getAccuracy() {
        if (totalWord == 0) {
            return String.format("%.2f%%", 0.00);
        }

        double accuracy = (double) correctWord / totalWord * 100;
        return String.format("%.2f%%", accuracy);
    }

}
