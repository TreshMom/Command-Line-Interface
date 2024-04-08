package commands;

import details.OutputStreamWrapper;
import details.RealCommand;
import mse.sd.bash.commands.Pwd;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PwdTest {
    @Test
    public void pwdTest() {
        try {
            OutputStreamWrapper.setUpStreams();
            Pwd pwd = new Pwd();
            pwd.start();
            String expectedOutput = RealCommand.eval(Collections.singletonList("pwd"));
            assertEquals(expectedOutput, OutputStreamWrapper.getOutContent());
            OutputStreamWrapper.restoreStreams();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
