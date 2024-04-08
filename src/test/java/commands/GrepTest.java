package commands;

import details.OutputStreamWrapper;
import details.RealCommand;
import mse.sd.bash.commands.Grep;
import mse.sd.bash.commands.Command;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GrepTest {

    @Test
    void grepTest() {
        try {
            Command command = new Grep();
            List<String> args = createArgs();
            for (String arg : args) {
                OutputStreamWrapper.setUpStreams();
//                System.err.println(arg);
                String expectedOutput = RealCommand.eval(Collections.singletonList("grep " + arg));
                String[] tmp = arg.split("\\s+");
                tmp[tmp.length - 2] = '"' + tmp[tmp.length - 2] + '"';
                System.err.println(Arrays.toString(tmp));
                command.setArgs(tmp);
                command.start();
                String result = OutputStreamWrapper.getOutContent();
                System.err.println(result);
                assertEquals(expectedOutput, result);
            }
            OutputStreamWrapper.restoreStreams();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private List<String> createArgs() {
        List<String> args = new ArrayList<>();
        List<String> fileNames = List.of(
                "./src/test/resources/grep_test_empty",
                "./src/test/resources/grep_test_min1",
                "./src/test/resources/grep_test_min2",
                "./src/test/resources/grep_test_min3",
                "./src/test/resources/grep_test_all_min",
                "./src/test/resources/grep_test_repetitive"
        );
        List<String> flags = List.of(
                "-w", "-i", "-w -i",
                "-A 1", "-A 2", "-A 3",
                "-w -A 1", "-w -A 2", "-w -A 3",
                "-i -A 1", "-i -A 2", "-i -A 3",
                "-w -i -A 1", "-w -i -A 2", "-w -i -A 3"
        );
        List<String> regex = List.of(
                "Минимальный", "минимальный",
                "Минимал", "минимал",
                "минимальный$", "^минимальный"
        );
        for (String fileName : fileNames) {
            for (String reg : regex) {
                for (String flag : flags) {
                    args.add(flag + " " + reg + " " + fileName);
                }
            }
        }
//        System.err.println(Arrays.toString(args.toArray()));
        return args;
    }

}
