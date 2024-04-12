package mse.sd.bash.commands;

import java.io.IOException;
import java.io.Reader;

public abstract class Command {
    protected String[] args;
    protected Command nextCommand = null;

    /**
     * Sets the arguments for the command.
     *
     * @param args The arguments for the command.
     */
    public void setArgs(String[] args) {
        this.args = args;
    }

    /**
     * Sets the next command in the chain.
     *
     * @param command The next command in the chain.
     */
    public void setNext(Command command) {
        nextCommand = command;
    }

    /**
     * Evaluates the command using the provided reader.
     *
     * @param reader The reader to read from.
     * @throws IOException If an I/O error occurs.
     */
    public abstract void eval(Reader reader) throws IOException;

    /**
     * Starts the execution of the command.
     *
     * @throws IOException If an I/O error occurs.
     */
    public abstract void start() throws IOException;
    
    /**
     * Returns a new instance of the command.
     *
     * @return A new instance of the command.
     */
    public abstract Command getNew();

}
