package commands;

import mse.sd.bash.commands.Cat;
import mse.sd.bash.commands.Command;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CatTest {

    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;
    private static final PrintStream originalErr = System.err;

    @BeforeAll
    static void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterAll
    public static void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    String[][] fileNames = new String[][]{
//            new String[]{"./src/test/resources/cat-test-1"},
            new String[]{"./src/test/resources/cat-test-2"},
            new String[]{"./src/test/resources/cat-test-3"}
    };

    @Test
    void catTest() {
        try {
            Command command = new Cat();
            for (String[] fileName : fileNames) {
                command.setArgs(fileName);
                command.start();
                String expectedOutput = RealCommand.eval("cat " + fileName[0]);
                assertEquals(expectedOutput, outContent.toString());
                outContent.reset();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
