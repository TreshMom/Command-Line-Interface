package mse.sd.bash.commands;

public class Pwd extends Command {
    @Override
    public void eval(String fileName) {
        if (fileName.isEmpty()) {
            System.out.println(System.getProperty("user.dir"));
        } else {
            System.out.println("Too many arguments");
        }
    }
}