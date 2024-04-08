package mse.sd.bash.analyze;

import mse.sd.bash.commands.Command;

import java.io.IOException;

public class PipeManagerCommands {
    private final Command[] commands;

    /**
     * Constructor for the PipeManagerCommands class.
     * 
     * @param commands The array of commands to manage.
     * @param args The arguments for each command.
     */
    public PipeManagerCommands(Command[] commands, String[][] args) {
        this.commands = commands;
        for (int i = 0; i < commands.length; i++) {
            commands[i].setArgs(args[i]);
        }

        for (int i = 0; i < commands.length - 1; i++) {
            commands[i].setNext(commands[i + 1]);
        }
    }
    
    /**
     * Starts executing the commands in the pipeline.
     * 
     * @throws IOException If an I/O error occurs.
     */
    public void start() throws IOException {
        commands[0].start();
    }

}
