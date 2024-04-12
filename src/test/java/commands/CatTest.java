package commands;

import details.OutputStreamWrapper;
import details.RealCommand;
import mse.sd.bash.commands.Cat;
import mse.sd.bash.commands.Command;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CatTest {

    String[][] fileNames = new String[][]{
            new String[]{"./src/test/resources/cat_test_empty"},
            new String[]{"./src/test/resources/cat_test_only_line_delimiters"},
            new String[]{"./src/test/resources/cat_test_some_text"},
            new String[]{"./src/test/resources/cat_test_strange_formatting"}
    };

    @Test
    void catTest() {
        try {
            Command command = new Cat();
            for (String[] fileName : fileNames) {
                OutputStreamWrapper.setUpStreams();
                command.setArgs(fileName);
                command.start();
                String expectedOutput = RealCommand.eval(Collections.singletonList("cat " + fileName[0]));
                assertEquals(expectedOutput, OutputStreamWrapper.getOutContent());
            }
            OutputStreamWrapper.restoreStreams();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
