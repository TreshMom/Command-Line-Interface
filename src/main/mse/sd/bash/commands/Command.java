package mse.sd.bash.commands;

import java.io.IOException;
import java.io.Reader;

public abstract class Command {
    protected String[] args;
    protected Command nextCommand = null;
    protected String cwd;
    public void setArgs(String[] args)
    {
        this.args = args;
    }
    public void setNext(Command command)
    {
        nextCommand = command;
    }
    public void setCWD(String cwd) {
        this.cwd = cwd;
    }
    public abstract void eval(Reader reader) throws IOException;
    public abstract void start() throws IOException;

    public abstract Command getNew();

}
