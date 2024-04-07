package mse.sd.bash;

import mse.sd.bash.analyze.Parser;
import mse.sd.bash.commands.Command;
import mse.sd.bash.analyze.PipeManagerCommands;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IllegalArgumentException {
        System.out.print("MyBash> ");
        while (true) {
            Scanner sc = new Scanner(System.in);
            try {
                if (sc.hasNextLine()) {
                    run(sc.nextLine());
                    System.out.print("MyBash> ");
                }
            } catch (IOException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void run(String rs) throws IOException, IllegalArgumentException {
        Parser parser = new Parser(rs);
        parser.parse();
        Command[][] commands = parser.getCommands();
        String[][][] commandsArgs = parser.getCommandsArgs();

        for (int i = 0; i < commands.length; i++) {
            PipeManagerCommands pipeManagerCommands =
                    new PipeManagerCommands(commands[i], commandsArgs[i]);
            pipeManagerCommands.start();
        }
    }
}