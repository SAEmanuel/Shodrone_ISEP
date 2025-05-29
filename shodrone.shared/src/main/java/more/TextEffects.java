package more;

public class TextEffects {
    public static final String RESET = "\u001B[0m";

    // Text styles
    public static final String BOLD = "\u001B[1m";
    public static final String DIM = "\u001B[2m";
    public static final String ITALIC = "\u001B[3m";
    public static final String UNDERLINE = "\u001B[4m";
    public static final String BLINK = "\u001B[5m"; // May not be supported in all terminals
    public static final String INVERT = "\u001B[7m";
    public static final String HIDDEN = "\u001B[8m";
    public static final String STRIKETHROUGH = "\u001B[9m";

    // Reset specific styles
    public static final String RESET_BOLD = "\u001B[21m";
    public static final String RESET_DIM = "\u001B[22m";
    public static final String RESET_ITALIC = "\u001B[23m";
    public static final String RESET_UNDERLINE = "\u001B[24m";
    public static final String RESET_BLINK = "\u001B[25m";
    public static final String RESET_INVERT = "\u001B[27m";
    public static final String RESET_HIDDEN = "\u001B[28m";
    public static final String RESET_STRIKETHROUGH = "\u001B[29m";
}

