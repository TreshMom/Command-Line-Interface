package mse.sd.bash.commands;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class Pwd extends Command {

    /**
     * Evaluates the command.
     * Retrieves the current working directory and passes it to the next command in the chain or prints it to the standard output.
     *
     * @param reader Ignored.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public void eval(Reader reader) throws IOException {
        String result = System.getProperty("user.dir");
        if (nextCommand != null) {
            nextCommand.eval(new StringReader(result));
        } else {
            System.out.println(result);
        }
    }

    /**
     * Starts the execution of the command.
     * Evaluates the command with an empty input.
     *
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public void start() throws IOException {
        eval(new StringReader(""));
    }
    
    /**
     * Returns a new instance of the Pwd command.
     *
     * @return A new instance of the Pwd command.
     */
    @Override
    public Command getNew() {
        return new Pwd();
    }
}