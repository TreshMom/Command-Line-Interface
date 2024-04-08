package mse.sd.bash;

import mse.sd.bash.analyze.Parser;
import mse.sd.bash.commands.Command;
import mse.sd.bash.analyze.PipeManagerCommands;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Scanner;

public class Main {
    
    /**
     * Main method of the MyBash application.
     * Prompts the user with "MyBash> ", reads input from the console, and processes commands.
     * Continuously runs until the program is terminated.
     * 
     * @param args Command-line arguments (not used).
     */
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

    static Parser parser = new Parser();
    private static void run(String rs) throws IOException, IllegalArgumentException {
        parser.setSource(rs);
        parser.parse();
        if(parser.getCommands() != null) {
            Command[][] commands = parser.getCommands();
            String[][][] commandsArgs = parser.getCommandsArgs();

            for (int i = 0; i < commands.length; i++) {
                PipeManagerCommands pipeManagerCommands =
                        new PipeManagerCommands(commands[i], commandsArgs[i]);
                pipeManagerCommands.start();
            }
        }
    }
}