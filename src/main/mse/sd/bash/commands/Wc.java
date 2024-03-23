package main.mse.sd.bash.commands;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.nio.charset.Charset;

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
                bytes += line.getBytes(Charset.forName("UTF-8")).length; // проблемы
                words += new StringTokenizer(line, " ").countTokens();
            }

            System.out.println("Lines: " + lines);
            System.out.println("Words: " + words);
            System.out.println("Bytes: " + (bytes + lines));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
