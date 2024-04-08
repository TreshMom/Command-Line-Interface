package mse.sd.bash.commands;

import java.io.FileNotFoundException;
import java.io.Reader;

public class Exit extends Command {

    @Override
    public void eval(Reader reader) {
        System.exit(0);
    }

    @Override
    public void start() {
        System.exit(0);
    }

    @Override
    public Exit getNew() {
        return new Exit();
    }
}
