package main.mse.sd.bash;

import main.mse.sd.bash.analyze.Parser;
import main.mse.sd.bash.commands.Command;

import java.util.Scanner;

public class Main {
    private static void testMain()
    {
        Parser parser = new Parser(
                "  cat ' C:\\Users\\Ilya\\IdeaProjects\\SD\\project\\src\\mse\\sd\\bash\\commands\\example.txt'" +
                "&& cat C:\\Users\\Ilya\\IdeaProjects\\SD\\project\\src\\mse\\sd\\bash\\commands\\example.txt" +
                "&& wc C:\\Users\\Ilya\\IdeaProjects\\SD\\project\\src\\mse\\sd\\bash\\commands\\example.txt");
        parser.parse();
        Command[] commands = parser.getCommands();
        String[][] commandsArgs = parser.getCommandsArgs();

        for(int i = 0;i < commands.length; i++)
        {
            if(commandsArgs[i].length > 0)
            {
                commands[i].eval(commandsArgs[i][0]);
            }
        }
        // cat, cat, echo
    }
    public static void main(String[] args) {
        testMain();
//        while (true) {
//
//            // считали весь ввод
//            Scanner sc = new Scanner(System.in);
//            StringBuilder sb = new StringBuilder();
//           while (!sc.nextLine().isEmpty()) {
//                String vl = sc.nextLine();
//
//                sb.append(vl);
//            }
//            // разбираем ввод пользователя
//            Parser parser = new Parser(sb.toString());
//            String[][] commands = parser.parse();
//            // исполнение команды
//            //commands.eval();
//            break;

       // }
    }
}
