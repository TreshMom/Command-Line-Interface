package mse.sd.bash.commands;

import java.io.*;
import java.nio.file.*;

public class Cd extends Command {
    public String newCwd = null;

    @Override
    public void eval(Reader reader) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            newCwd = Paths.get(bufferedReader.readLine()).normalize().toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start() throws IOException {
        if (args.length == 0) {
            System.out.println(cwd);
            return;
        }
        try {
            Path inputPath = Paths.get(args[0]);
            if (!inputPath.isAbsolute()) {
                inputPath = Paths.get(cwd).resolve(inputPath);
            }
            File f = inputPath.toFile();
            if (!f.exists() || !f.isDirectory()) {
                throw new IllegalArgumentException();
            }
            eval(new StringReader(inputPath.toString()));
        } catch (Exception e) {
            throw new IllegalArgumentException("error args");
        }
    }

    @Override
    public Command getNew() {
        return new Cd();
    }
}
