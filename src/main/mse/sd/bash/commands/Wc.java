package mse.sd.bash.commands;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;

public class Wc extends Command {

    private String fileName;

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
            stringBuilder.append(String.format("%s %s %s %s", lines, words - 1, bytes, fileName));
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

    @Override
    public void start() {
        try {
            fileName = args[0];
            eval(new FileReader(fileName));
        } catch (Exception e) {
            throw new IllegalArgumentException("error args");
        }
    }

    @Override
    public Command getNew() {
        return new Wc();
    }
}
