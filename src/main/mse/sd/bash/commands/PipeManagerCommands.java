package mse.sd.bash.commands;

import java.io.IOException;

public class PipeManagerCommands {
    private final Command[] commands;
    public PipeManagerCommands(Command[] commands, String[][] args, String cwd)
    {
        this.commands = commands;
        for (int i = 0;i < commands.length; i++)
        {
            commands[i].setArgs(args[i]);
        }

        for (int i = 0;i < commands.length - 1; i++)
        {
            commands[i].setNext(commands[i + 1]);
        }

        for (int i = 0;i < commands.length; i++)
        {
            commands[i].setCWD(cwd);
        }
    }

    public void start() throws IOException {
        commands[0].start();
    }

}
