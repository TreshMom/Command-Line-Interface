package mse.sd.bash;

import mse.sd.bash.analyze.Parser;
import mse.sd.bash.analyze.StringSource;
import mse.sd.bash.commands.Command;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        while (true) {

            // считали весь ввод
            Scanner sc = new Scanner(System.in);
            StringBuilder sb = new StringBuilder();
            while (sc.hasNext()) {
                sb.append(sc.next());
            }

            // разбираем ввод пользователя
            Parser parser = new Parser(new StringSource(sb.toString()));
            Command command = parser.parse();

            // исполнение команды
            command.eval();

        }
    }
}
