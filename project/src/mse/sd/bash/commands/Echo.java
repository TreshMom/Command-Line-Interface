package mse.sd.bash.commands;

public class Echo extends Command {

    //private final String str;

    //public Echo(String str) {
        //this.str = str;
    //}

    @Override
    public void eval(String  fileName) {
        System.out.println(fileName);
    }
}
