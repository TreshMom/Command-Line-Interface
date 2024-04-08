package mse.sd.bash.commands;
import java.io.*;
import java.nio.file.*;

public class Cat extends Command {
    @Override
    public void eval(Reader reader) {
        StringBuilder result = new StringBuilder();
        try (BufferedReader BufReader = new BufferedReader(reader)) {
            String line;
            while ((line = BufReader.readLine()) != null) {
                result.append(line).append("\n");
            }
            if(nextCommand != null)
            {
                nextCommand.eval(new StringReader(result.toString()));
            }
            else
            {
                System.out.println(result);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void start() {
        try {
            Path inputPath = Paths.get(args[0]);
            if (!inputPath.isAbsolute()) {
                inputPath = Paths.get(cwd).resolve(inputPath);
            }
            File f = inputPath.toFile();
            if (!f.exists() || f.isDirectory()) {
                throw new IllegalArgumentException();
            }
            eval(new FileReader(f));
        } catch (Exception e) {
            throw new IllegalArgumentException("error args");
        }
    }

    @Override
    public Cat getNew() {
        return new Cat();
    }
}
