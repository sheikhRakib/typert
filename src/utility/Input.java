package utility;

public class Input {
    private String text = "";

    public void setInput(String charecter) {
        text += charecter.toLowerCase();
    }

    public String getInput() {
        return text;
    }

    public void reset() {
        text = "";
    }
}
