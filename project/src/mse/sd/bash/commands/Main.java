package mse.sd.bash.commands;

public class Main {
    public static void main (String[] args) {
        String file = "/Users/anatoli_vasileva/IdeaProjects/SD/project/src/mse/sd/bash/commands/example.txt";
        Wc comWc = new Wc();
        comWc.eval(file);
        Cat comCat = new Cat();
        comCat.eval(file);
        Pwd comPwd = new Pwd();
        comPwd.eval(file);
    }
}
