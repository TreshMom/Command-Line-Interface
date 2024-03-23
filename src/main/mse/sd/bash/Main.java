package main.mse.sd.bash;

import main.mse.sd.bash.analyze.Parser;
import main.mse.sd.bash.commands.Command;
import main.mse.sd.bash.commands.Pwd;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static void testMain(String rs) throws IOException, IllegalArgumentException {
//        Parser parser = new Parser(
//                "  cat ' C:\Users\Ilya\IdeaProjects\SD\project\src\mse\sd\bash\commands\example.txt'" +
//                "&& cat C:\Users\Ilya\IdeaProjects\SD\project\src\mse\sd\bash\commands\example.txt" +
//                "&& wc C:\Users\Ilya\IdeaProjects\SD\project\src\mse\sd\bash\commands\example.txt");
        Parser parser = new Parser(rs);
        parser.parse();
        Command[] commands = parser.getCommands();
        String[][] commandsArgs = parser.getCommandsArgs();

        for (int i = 0; i < commands.length; i++) {
            if (commandsArgs[i].length > 0) {
                commands[i].eval(commandsArgs[i][0]);
            } else if (commands[i] instanceof Pwd) {
                commands[i].eval("");
            }
        }
        // cat, cat, echo
    }

    public static void main(String[] args) throws IOException, IllegalArgumentException {
//        testMain();
        while (true) {
            Scanner sc = new Scanner(System.in);
            try {
                testMain(sc.nextLine());
            } catch (IOException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}