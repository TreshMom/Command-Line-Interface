package mse.sd.bash.commands;

import java.io.*;
import java.nio.file.*;

public class Cd extends Command {

    @Override
    public void eval(Reader reader) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            // bad
            System.setProperty("user.dir", bufferedReader.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start() throws IOException {
        if (args.length == 0) {
            System.out.println(System.getProperty("user.dir"));
            return;
        }
        try {
            Path inputPath = Paths.get(args[0]);
            eval(new StringReader(inputPath.toAbsolutePath()
                                           .normalize()
                                           .toString()
            ));
        } catch (Exception e) {
            throw new IllegalArgumentException("error args");
        }
    }

    @Override
    public Command getNew() {
        return new Cd();
    }
}
