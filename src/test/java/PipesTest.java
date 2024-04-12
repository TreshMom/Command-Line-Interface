import details.OutputStreamWrapper;
import details.RealCommand;
import org.junit.jupiter.api.Test;
import mse.sd.bash.Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PipesTest {

    @Test
    void pipesTest() {
        try {
            OutputStreamWrapper.setUpStreams();
            for (List<String> command : generateCommands()) {
                String expectedOutput = String.join(
                        " ",
                        Objects.requireNonNull(RealCommand.eval(command)).strip().split("\\s+")
                );
                for (int i = 0; i < command.size(); i++) {
                    if (command.get(i).startsWith("grep")) {
                        String[] gg = command.get(i).split("\\s+");
                        gg[1] = "\"" + gg[1] + "\"";
                        command.set(i, String.join(" ", gg));
                    }
                }
                Main.run(command.stream().reduce((x, y) -> x + " | " + y).get());
                assertEquals(expectedOutput, OutputStreamWrapper.getOutContent());
            }
            OutputStreamWrapper.restoreStreams();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private List<List<String>> generateCommands() {
        List<List<String>> result = new ArrayList<>();
        List<String> fileNames = List.of(
                "./src/test/resources/grep_test_repetitive",
                "./src/test/resources/wc_test_some_text",
                "./src/test/resources/grep_test_all_min",
                "./src/test/resources/cat_test_empty"
        );
        List<String> commands = List.of(
                "wc", "cat",
                "grep"
        );
        List<String> regexForGrep = List.of(
                "Минимальный", "минимальный",
                "Минимал", "минимал",
                "минимальный$", "^минимальный",
                "1", "2", "3", "4", "5",
                "6", "7", "8", "9"
        );

        for (String fileName : fileNames) {
            for (String command : commands) {
                List<String> cmd = new ArrayList<>();
                int index = (int) (Math.random() * (regexForGrep.size()));
                String regex = regexForGrep.get(index);
                if (Objects.equals(command, "grep")) {
                    cmd.add(command + " " + regex + " " + fileName);
                } else {
                    cmd.add(command + " " + fileName);
                }
                for (String otherCommand : commands) {
                    if (!otherCommand.equals(command)) {
                        if (Objects.equals(otherCommand, "grep")) {
                            index = (int) (Math.random() * (regexForGrep.size()));
                            regex = regexForGrep.get(index);
                            cmd.add(otherCommand + " " + regex);
                        } else {
                            cmd.add(otherCommand);
                        }
                    }
                }

                result.add(cmd);
            }
        }
        return result;
    }
}
