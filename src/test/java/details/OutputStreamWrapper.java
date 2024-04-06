package details;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class OutputStreamWrapper {

    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//    private static final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;
//    private static final PrintStream originalErr = System.err;

    public static void setUpStreams() {
        System.setOut(new PrintStream(outContent));
//        System.setErr(new PrintStream(errContent));
    }

    public static String getOutContent() {
        String result = outContent.toString();
        outContent.reset();
        return result;
    }

    public static void restoreStreams() {
        System.setOut(originalOut);
//        System.setErr(originalErr);
    }
}
