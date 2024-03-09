package mse.sd.bash.commands;

public class Echo extends Command {
    @Override
    public void eval() {
        System.out.println("echo");
    }
}
