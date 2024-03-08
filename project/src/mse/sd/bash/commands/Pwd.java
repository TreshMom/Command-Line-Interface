package mse.sd.bash.commands;

public class Pwd extends Command {
    @Override
    public void eval() {
        System.out.println(System.getProperty("user.dir"));
    }
}
