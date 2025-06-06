package utils;

import java.io.OutputStream;
import java.io.PrintStream;

public class NativeOutputSilencer {

    private static final PrintStream originalErr = System.err;

    public static void mute() {
        System.setErr(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                // discard
            }
        }));
    }

    public static void restore() {
        System.setErr(originalErr);
    }
}
