package mse.sd.bash.commands;

import java.io.*;
import java.util.stream.Collectors;

public class Echo extends Command {

    /**
     * Evaluates the command, reading input from the provided reader.
     * If the next command in the chain is not null, passes the reader to it.
     * Otherwise, prints the input to the standard output.
     *
     * @param reader The reader to read input from.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public void eval(Reader reader) throws IOException {
        if (nextCommand != null) {
            nextCommand.eval(reader);
        } else {
            System.out.println(new BufferedReader(reader).lines().collect(Collectors.joining("\n")));
        }
    }

    /**
     * Starts the execution of the command.
     * If arguments are provided, evaluates the command using the first argument as input.
     *
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public void start() throws IOException {
        if (args.length > 0) {
            eval(new StringReader(args[0]));
        }
    }
    
    /**
     * Returns a new instance of the Echo command.
     *
     * @return A new instance of the Echo command.
     */
    @Override
    public Echo getNew() {
        return new Echo();
    }
}
