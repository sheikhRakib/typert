package utility;

public class Input {
    private final StringBuilder text = new StringBuilder();

    public void setInput(String character) {
        text.append(character.toLowerCase());
    }

    public String getInput() {
        return text.toString();
    }

    public void reset() {
        text.setLength(0);
    }
}
