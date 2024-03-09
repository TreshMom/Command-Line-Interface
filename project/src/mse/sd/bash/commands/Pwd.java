package mse.sd.bash.commands;

public class Pwd extends Command {
    @Override
    public void eval(String  fileName) {
        System.out.println(System.getProperty("user.dir"));
    }
}
