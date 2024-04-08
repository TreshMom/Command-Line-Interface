package mse.sd.bash.analyze;

import mse.sd.bash.commands.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class CommandParser {
    private static final Map<String, Command> CMD_MAP = new HashMap<>();

    static {
        CMD_MAP.put("cat", new Cat());
        CMD_MAP.put("echo", new Echo());
        CMD_MAP.put("exit", new Exit());
        CMD_MAP.put("pwd", new Pwd());
        CMD_MAP.put("wc", new Wc());
        CMD_MAP.put("grep", new Grep());
    }

    static private boolean isCommand(String[] str) {
        if (str.length > 0) {
            return CMD_MAP.containsKey(str[0]);
        }
        return false;
    }

    /**
     * Parses a single command from the provided string array.
     *
     * @param str The string array representing a command.
     * @return The Command object corresponding to the parsed command.
     * @throws IllegalArgumentException If the command is unexpected or invalid.
     */
    static public Command parse(String[] str) {
        if (isCommand(str)) {
            return CMD_MAP.get(str[0]).getNew();
        }
        throw new IllegalArgumentException("unexpected command : " + Arrays.toString(str));
    }

    /**
     * Parses multiple commands from the provided string arrays.
     *
     * @param str The array of string arrays representing multiple commands.
     * @return An array of Command objects corresponding to the parsed commands.
     */
    static public Command[] parse(String[][] str) {
        return Arrays.stream(str)
                .map(CommandParser::parse)
                .toList()
                .toArray(new Command[0]);
    }

    /**
     * Parses multiple commands from the provided nested string arrays.
     *
     * @param str The nested array of string arrays representing multiple commands.
     * @return A two-dimensional array of Command objects corresponding to the parsed commands.
     */
    static public Command[][] parse(String[][][] str) {
        return Arrays.stream(str)
                .map(CommandParser::parse)
                .toList()
                .toArray(new Command[0][0]);
    }

    /**
     * Extracts arguments from a single string array representing command arguments.
     *
     * @param args The string array representing command arguments.
     * @return The extracted arguments.
     */
    static public String[] getArgs(String[] args) {
        return Arrays.copyOfRange(args, 1, args.length);
    }

    /**
     * Extracts arguments from multiple string arrays representing command arguments.
     *
     * @param args The array of string arrays representing command arguments.
     * @return A two-dimensional array containing extracted arguments for each command.
     */
    static public String[][] getArgs(String[][] args) {
        return Arrays.stream(args)
                .map(CommandParser::getArgs)
                .toList()
                .toArray(new String[0][0]);
    }
    
    /**
     * Extracts arguments from nested string arrays representing command arguments.
     *
     * @param args The nested array of string arrays representing command arguments.
     * @return A three-dimensional array containing extracted arguments for each command.
     */
    static public String[][][] getArgs(String[][][] args) {
        return Arrays.stream(args)
                .map(CommandParser::getArgs)
                .toList()
                .toArray(new String[0][0][0]);
    }

}
