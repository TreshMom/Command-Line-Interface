package mse.sd.bash.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.File;
import java.nio.file.*;

public class Ls extends Command {
    @Override
    public void eval(Reader reader) throws IOException {
        final int lineLengthLimit = 80;

        BufferedReader br = new BufferedReader(reader);
        Path p = Paths.get(br.readLine());
        File f = p.toFile();
        int lineLength = 0;
        StringBuilder sb = new StringBuilder();
        String result;
        if (f.isDirectory()) {
            for (File ff : f.listFiles()) {
                String n = ff.getName();
                if (lineLength + n.length() + 1 > lineLengthLimit) {
                    sb.append("\n");
                    lineLength = 0;
                }
                if (lineLength != 0) {
                    sb.append("\t");
                    ++lineLength;
                }
                sb.append(ff.getName());
                lineLength += n.length();
            }
            result = sb.toString();
        } else {
            result = f.getName();
        }
        if(nextCommand != null)
        {
            nextCommand.eval(new StringReader(result));
        }
        else
        {
            System.out.println(result);
        }
    }

    @Override
    public void start() throws IOException {
        if (args.length == 0) {
            eval(new StringReader(cwd));
        } else {
            Path inputPath = Paths.get(args[0]);
            if (!inputPath.isAbsolute()) {
                inputPath = Paths.get(cwd).resolve(inputPath);
            }
            File f = inputPath.toFile();
            if (!f.exists()) {
                throw new IllegalArgumentException();
            }
            eval(new StringReader(inputPath.toString()));
        }
    }

    @Override
    public Command getNew() {
        return new Ls();
    }
}