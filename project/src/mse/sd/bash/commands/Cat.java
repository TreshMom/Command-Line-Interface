package mse.sd.bash.commands;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Cat {
    public static void cat(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}