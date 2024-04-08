package mse.sd.bash;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.*;

import mse.sd.bash.commands.Ls;

public class LsTest {
    @Test
    public void testCdRelative() {
        // Create a stream to hold the output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        // Save the old System.out
        PrintStream old = System.out;
        // Tell Java to use our special stream
        System.setOut(ps);

        final Ls ls = new Ls();
        String cwd = System.getProperty("user.dir");
        ls.setCWD(cwd);
        ls.setArgs(new String[]{"./src"});
        assertDoesNotThrow(() -> {
            ls.start();
        });
        assertEquals("main\ttest", baos.toString().trim());

        // Put things back
        System.out.flush();
        System.setOut(old);
    }
}
