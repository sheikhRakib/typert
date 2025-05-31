package font;

import java.awt.Font;

public class Arial extends Font {
    public Arial(int style, int size) {
        super("Arial", style, size);
    }

    public Arial(int size) {
        super("Arial", Font.PLAIN, size);
    }

    public Arial() {
        super("Arial", Font.PLAIN, 12);
    }
}
