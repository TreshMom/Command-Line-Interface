package mse.sd.bash.commands;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;

public class Wc extends Command {
    @Override
    public void eval(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            int lines = -1;
            int words = 0;
            int bytes = 0;

            String line;
            while ((line = reader.readLine()) != null) {
                lines++;
                bytes += line.getBytes(StandardCharsets.UTF_8).length; // проблемы
                words += new StringTokenizer(line, " ").countTokens();
            }

            System.out.printf("%s %s %s %s%n", lines, words, bytes, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
