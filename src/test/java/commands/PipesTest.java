package commands;

import details.OutputStreamWrapper;
import details.RealCommand;
import mse.sd.bash.commands.Cat;
import mse.sd.bash.commands.Command;
import org.junit.jupiter.api.Test;
import mse.sd.bash.Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PipesTest {

    @Test
    void pipesTest() {
        try {

            List<String> command = List.of(
                    "wc ./src/test/resources/wc_test_some_text",
                    "cat"
            );
            OutputStreamWrapper.setUpStreams();

            Main.run(command.stream().reduce((x, y) -> x + " | " + y).get());
            String expectedOutput = String.join(
                    " ",
                    Objects.requireNonNull(RealCommand.eval(command)).strip().split("\\s+")
            );
            assertEquals(expectedOutput, OutputStreamWrapper.getOutContent());
            OutputStreamWrapper.restoreStreams();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private List<List<String>> generateCommands() {
        List<List<String>> result = new ArrayList<>();
        List<String> fileNames = List.of(

        );

        return result;
    }
}
