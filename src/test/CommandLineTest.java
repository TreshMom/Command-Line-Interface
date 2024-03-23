package test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import main.mse.sd.bash.*;
public class CommandLineTest {

    @Test
    public void testPwd() {
        CommandLine commandLine = new CommandLine();
        String expected = System.getProperty("user.dir");
        String result = commandLine.executeCommand("pwd");
        assertEquals(expected, result);
    }

    @Test
    public void testWc() {
        CommandLine commandLine = new CommandLine();
        String input = "This is a test string.";
        int expected = input.split("\\s+").length;
        String result = commandLine.executeCommand("wc", input);
        assertEquals(Integer.toString(expected), result.trim());
    }

    @Test
    public void testCat() {
        CommandLine commandLine = new CommandLine();
        String[] lines = {"Line 1", "Line 2", "Line 3"};
        String expected = String.join(System.lineSeparator(), lines);
        String result = commandLine.executeCommand("cat", lines);
        assertEquals(expected, result);
    }

    @Test
    public void testEcho() {
        CommandLine commandLine = new CommandLine();
        String message = "Hello, world!";
        String result = commandLine.executeCommand("echo", message);
        assertEquals(message, result);
    }

    @Test
    public void testExit() {
        CommandLine commandLine = new CommandLine();
        // Test that exit returns null (as the program should terminate)
        String result = commandLine.executeCommand("exit");
        assertEquals(null, result);
    }
}
