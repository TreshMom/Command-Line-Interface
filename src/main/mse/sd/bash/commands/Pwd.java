package mse.sd.bash.commands;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class Pwd extends Command {
    @Override
    public void eval(Reader reader) throws IOException {
        String result = System.getProperty("user.dir");
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
        eval(new StringReader(""));
    }

    @Override
    public Command getNew() {
        return new Pwd();
    }
}