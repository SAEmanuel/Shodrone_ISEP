package more;

public class TextEffectDemo {
    public static void main(String[] args) {
        System.out.println(TextEffects.BOLD + "Text Effects Palette" + TextEffects.RESET + "\n");

        printEffect("BOLD", TextEffects.BOLD);
        printEffect("DIM", TextEffects.DIM);
        printEffect("ITALIC", TextEffects.ITALIC);
        printEffect("UNDERLINE", TextEffects.UNDERLINE);
        printEffect("BLINK (may not work)", TextEffects.BLINK);
        printEffect("INVERT", TextEffects.INVERT);
        printEffect("HIDDEN", TextEffects.HIDDEN);
        printEffect("STRIKETHROUGH", TextEffects.STRIKETHROUGH);

        System.out.println("\n" + TextEffects.DIM + "(All effects reset after each line)" + TextEffects.RESET);
    }

    private static void printEffect(String name, String ansiEffect) {
        System.out.println(ansiEffect + name + TextEffects.RESET);
    }
}
