package mse.sd.bash.commands;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;

public class Wc extends Command {

    @Override
    public void eval(Reader reader) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            int lines = -1;
            int words = 0;
            int bytes = 0;

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines++;
                bytes += line.getBytes(StandardCharsets.UTF_8).length; // проблемы
                words += new StringTokenizer(line, " ").countTokens();
            }
            stringBuilder.append(String.format("%s %s %s %n", lines, words, bytes));
            if (nextCommand != null) {
                nextCommand.eval(new StringReader(stringBuilder.toString()));
            } else {
                System.out.print(stringBuilder);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start() {
        try {
            String filename = args[0];
            eval(new FileReader(filename));
        } catch (Exception e) {
            throw new IllegalArgumentException("error args");
        }
    }

    @Override
    public Command getNew() {
        return new Wc();
    }
}
