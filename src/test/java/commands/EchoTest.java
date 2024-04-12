package commands;

import details.OutputStreamWrapper;
import details.RealCommand;
import mse.sd.bash.commands.Command;
import mse.sd.bash.commands.Echo;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EchoTest {

    @Test
    void echoTest() {
        try {
            String[] someText = new String[]{"12n ekn;alkn ;1nnd jkn n12e?>@!#!@$%T^$*@!)_#(@_)#"};
            Command command = new Echo();
            OutputStreamWrapper.setUpStreams();
            command.setArgs(someText);
            command.start();
            String expectedOutput = RealCommand.eval(Collections.singletonList("echo " + someText[0]));
            assertEquals(expectedOutput, OutputStreamWrapper.getOutContent());
            OutputStreamWrapper.restoreStreams();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
