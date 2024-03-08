package mse.sd.bash.commands;

public class Echo extends Command {

    private final String str;

    public Echo(String str) {
        this.str = str;
    }

    @Override
    public void eval() {
        System.out.println(str);
    }
}
