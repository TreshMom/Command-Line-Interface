package mse.sd.bash;

import mse.sd.bash.analyze.Parser;
import mse.sd.bash.commands.Command;
import mse.sd.bash.commands.PipeManagerCommands;
import mse.sd.bash.commands.Pwd;
import mse.sd.bash.commands.Cd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static String cwd = System.getProperty("user.dir");

    private static void testMain(String rs) throws IOException, IllegalArgumentException {
        Parser parser = new Parser(rs);
        parser.parse();
        Command[][] commands = parser.getCommands();
        String[][][] commandsArgs = parser.getCommandsArgs();

        // by "&&"
        for (int i = 0; i < commands.length; i++) {
            PipeManagerCommands pipeManagerCommands =
                    new PipeManagerCommands(commands[i],commandsArgs[i], cwd);
            pipeManagerCommands.start();
            if (commands[i][0] instanceof Cd) {
                Cd cd = (Cd)commands[i][0];
                if (cd.newCwd != null) {
                    cwd = cd.newCwd;
                }
            }
        }
    }

    public static void main(String[] args) throws IllegalArgumentException {
        while (true) {
            System.out.print(cwd + ">");
            Scanner sc = new Scanner(System.in);
            try {
                testMain(sc.nextLine());
                System.out.println();
            } catch (IOException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}