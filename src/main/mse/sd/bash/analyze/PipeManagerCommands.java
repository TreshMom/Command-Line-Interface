package mse.sd.bash.analyze;

import mse.sd.bash.commands.Command;

import java.io.IOException;

public class PipeManagerCommands {
    private final Command[] commands;

    public PipeManagerCommands(Command[] commands, String[][] args) {
        this.commands = commands;
        for (int i = 0; i < commands.length; i++) {
            commands[i].setArgs(args[i]);
        }

        for (int i = 0; i < commands.length - 1; i++) {
            commands[i].setNext(commands[i + 1]);
        }
    }

    public void start() throws IOException {
        commands[0].start();
    }

}
