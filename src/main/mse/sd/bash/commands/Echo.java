package mse.sd.bash.commands;

import java.io.*;
import java.util.stream.Collectors;

public class Echo extends Command {

    @Override
    public void eval(Reader reader) throws IOException {
        if (nextCommand != null) {
            nextCommand.eval(reader);
        } else {
            System.out.println(new BufferedReader(reader).lines().collect(Collectors.joining("\n")));
        }
    }

    @Override
    public void start() throws IOException {
        if (args.length > 0) {
            eval(new StringReader(args[0]));
        }
    }

    @Override
    public Echo getNew() {
        return new Echo();
    }
}
