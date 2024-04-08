package mse.sd.bash.analyze;

import mse.sd.bash.commands.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class CommandParser
{
    private static final Map<String, Command> CMD_MAP = new HashMap<>();

    static {
        CMD_MAP.put("cat", new Cat());
        CMD_MAP.put("echo", new Echo());
        CMD_MAP.put("exit", new Exit());
        CMD_MAP.put("pwd", new Pwd());
        CMD_MAP.put("wc", new Wc());
        CMD_MAP.put("cd", new Cd());
        CMD_MAP.put("ls", new Ls());
    }

    static private boolean isCommand(String[] str)
    {
        if(str.length > 0)
        {
            return CMD_MAP.containsKey(str[0]);
        }
        return false;
    }
    static public Command parse(String[] str)
    {
        if(isCommand(str))
        {
            return CMD_MAP.get(str[0]).getNew();
        }
        throw new IllegalArgumentException("unexpected command : " + Arrays.toString(str));
    }

    static public Command[] parse(String[][] str)
    {
        return Arrays.stream(str)
                .map(CommandParser::parse)
                .toList()
                .toArray(new Command[0]);
    }

    static public Command[][] parse(String[][][] str)
    {
        return Arrays.stream(str)
                .map(CommandParser::parse)
                .toList()
                .toArray(new Command[0][0]);
    }

    static public String[] getArgs(String[] args)
    {
        return Arrays.copyOfRange(args,1,args.length);
    }

    static public String[][] getArgs(String [][]args)
    {
        return Arrays.stream(args)
                .map(CommandParser::getArgs)
                .toList()
                .toArray(new String[0][0]);
    }

    static public String[][][] getArgs(String [][][]args)
    {
        return Arrays.stream(args)
                .map(CommandParser::getArgs)
                .toList()
                .toArray(new String[0][0][0]);
    }

}
