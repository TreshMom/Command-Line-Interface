package mse.sd.bash.commands;

import java.io.*;

public class Cat extends Command {
    /**
     * Evaluates the command, reading from the provided reader.
     * Concatenates all lines into a single string and prints the result.
     * If the next command in the chain is not null, passes the result to it.
     *
     * @param reader The reader to read from.
     */
    @Override
    public void eval(Reader reader) {
        StringBuilder result = new StringBuilder();
        try (BufferedReader BufReader = new BufferedReader(reader)) {
            String line;
            while ((line = BufReader.readLine()) != null) {
                result.append(line).append("\n");
            }
            if (!result.isEmpty()) {
                result.deleteCharAt(result.length() - 1);
            }
            if (nextCommand != null) {
                nextCommand.eval(new StringReader(result.toString()));
            } else {
                System.out.print(result);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Starts the execution of the command.
     * Reads the filename from the arguments and evaluates the command.
     * Throws an IllegalArgumentException if no filename is provided.
     */
    @Override
    public void start() {
        try {
            String filename = args[0];
            eval(new FileReader(filename));
        } catch (Exception e) {
            throw new IllegalArgumentException("error args");
        }
    }

    /**
     * Returns a new instance of the Cat command.
     *
     * @return A new instance of the Cat command.
     */
    @Override
    public Cat getNew() {
        return new Cat();
    }
}
