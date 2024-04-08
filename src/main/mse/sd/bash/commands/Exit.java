package mse.sd.bash.commands;

import java.io.FileNotFoundException;
import java.io.Reader;

public class Exit extends Command {

    /**
     * Evaluates the command.
     * Exits the program with status code 0.
     *
     * @param reader Ignored.
     */
    @Override
    public void eval(Reader reader) {
        System.exit(0);
    }

    /**
     * Starts the execution of the command.
     * Exits the program with status code 0.
     */
    @Override
    public void start() {
        System.exit(0);
    }
    
    /**
     * Returns a new instance of the Exit command.
     *
     * @return A new instance of the Exit command.
     */
    @Override
    public Exit getNew() {
        return new Exit();
    }
}
