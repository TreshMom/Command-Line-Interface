package mse.sd.bash.commands;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;

public class Wc extends Command {

    private String fileName;
    
    /**
     * Evaluates the command, reading input from the provided reader.
     * Counts the number of lines, words, and bytes in the input.
     * If the next command in the chain is not null, passes the result to it.
     * Otherwise, prints the result to the standard output.
     *
     * @param reader The reader to read input from.
     */
    @Override
    public void eval(Reader reader) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            int lines = 0;
            int words = 0;
            int bytes = 0;

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines++;
                bytes += line.getBytes().length + 1;
                words += new StringTokenizer(line, " ").countTokens();
            }
            if (fileName != null) {
                stringBuilder.append(String.format("%s %s %s %s", lines, words, bytes, fileName));
            } else {
                stringBuilder.append(String.format("%s %s %s", lines, words, bytes));
            }
            if (nextCommand != null) {
                nextCommand.eval(new StringReader(stringBuilder.toString()));
            } else {
                System.out.print(stringBuilder);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isLineSeparator(char ch) {
        return ch == System.lineSeparator().charAt(0);
    }

    /**
     * Starts the execution of the command.
     * Reads the filename from the arguments and evaluates the command.
     *
     * @throws IllegalArgumentException If the filename is not provided.
     */
    @Override
    public void start() {
        try {
            fileName = args[0];
            eval(new FileReader(fileName));
        } catch (Exception e) {
            throw new IllegalArgumentException("error args");
        }
    }
    
    /**
     * Returns a new instance of the Wc command.
     *
     * @return A new instance of the Wc command.
     */
    @Override
    public Command getNew() {
        return new Wc();
    }
}
