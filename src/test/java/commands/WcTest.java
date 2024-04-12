package commands;

import details.OutputStreamWrapper;
import details.RealCommand;
import mse.sd.bash.commands.Command;
import mse.sd.bash.commands.Wc;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WcTest {
    String[][] fileNames = new String[][]{
            new String[]{"./src/test/resources/wc_test_empty"},
            new String[]{"./src/test/resources/wc_test_some_text"},
            new String[]{"./src/test/resources/wc_test_only_line_delimiters"},
    };

    @Test
    void wcTest() {
        try {
            Command command = new Wc();
            for (String[] fileName : fileNames) {
                OutputStreamWrapper.setUpStreams();
                command.setArgs(fileName);
                command.start();
                String expectedOutput = String.join(
                        " ",
                        Objects.requireNonNull(
                                RealCommand.eval(Collections.singletonList("wc " + fileName[0]))
                        ).strip().split("\\s+")
                );
                assertEquals(expectedOutput, OutputStreamWrapper.getOutContent());
            }
            OutputStreamWrapper.restoreStreams();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
