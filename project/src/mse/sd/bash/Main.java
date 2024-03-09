package mse.sd.bash;

import mse.sd.bash.analyze.Parser;
import mse.sd.bash.analyze.StringSource;
import mse.sd.bash.commands.Command;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static void testMain()
    {
        Parser parser = new Parser("  cat && exit 12  32 21 312 && cat fd fds gfd -g   &&  && echo && echo");
        parser.parse();
        Command[] commands = parser.getCommands();
        String[][] commandsArgs = parser.getCommandsArgs();
        Arrays.stream(commands).forEach(Command::eval);

        System.out.println("------args-------");
        System.out.println(Arrays.deepToString(commandsArgs));
        // cat, cat, echo
    }
    public static void main(String[] args) {
        testMain();
//        while (true) {
//
//            // считали весь ввод
////            Scanner sc = new Scanner(System.in);
////            StringBuilder sb = new StringBuilder();
////           while (!sc.nextLine().isEmpty()) {
////                String vl = sc.nextLine();
////
////                sb.append(vl);
//            }
//            // разбираем ввод пользователя
//            Parser parser = new Parser(sb.toString());
//            //String[][] commands = parser.parse();
//            // исполнение команды
//            // commands.eval();
//            break;
//
//        }
    }
}
