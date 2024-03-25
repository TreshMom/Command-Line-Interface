package mse.sd.bash.analyze;

import mse.sd.bash.commands.*;

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Stream;

public class Parser {
    private final String source;
    private Command[][] commands = null;
    private String[][][] commandsArgs = null;
    private static final Map<String, Command> CMD_MAP = new HashMap<>();

    private static final String SEP = "&&";

    static {
        CMD_MAP.put("cat", new Cat());
        CMD_MAP.put("echo", new Echo());
        CMD_MAP.put("exit", new Exit());
        CMD_MAP.put("pwd", new Pwd());
        CMD_MAP.put("wc", new Wc());
    }

    public String[][][] getCommandsArgs()
    {
        return this.commandsArgs;
    }

    public Command[][] getCommands()
    {
        return this.commands;
    }

    public Parser(String source) {
        this.source = source;
    }

    // разбор строки
    public String[][][] parse() {
        System.out.println(Arrays.deepToString(parseSemanticCommandByPipe(parseCommands(this.source,SEP))));
        String[][][] postProcessing = parseSemanticCommandAll(parseSemanticCommandByPipe(parseCommands(this.source,SEP)));
        System.out.println(Arrays.deepToString(postProcessing));
        this.commands = toCommandsAll(postProcessing);
        this.commandsArgs = toCommandArgs(postProcessing);
        return postProcessing;
    }

    private String[] parseCommands(String inp, String sep) {
        return Arrays.stream(inp.split(sep))
                .map(String::trim)
                .map(str -> str.replaceAll("\\s+"," ")).
                toList().toArray(new String[0]);
    }

    private String[][] parseSemanticCommandByPipe(String[] inp)
    {
        return Arrays.stream(inp)
                .map(str -> parseCommands(str,"\\|"))
                .toList().toArray(new String[0][0]);
    }

    private String[][][] parseSemanticCommandAll(String[][] inp)
    {
        return Arrays.stream(inp)
                .map(this::parseSemanticCommandByOne)
                .toList().toArray(new String[0][0][0]);
    }
    private String[][] parseSemanticCommandByOne(String [] inp)
    {
        return Arrays.stream(inp)
                .map(str -> str.split(" "))
                .filter(commandString ->
                        commandString.length > 0 && !Objects.equals(commandString[0], ""))
                .filter(commandString -> testStringIsCommand(commandString[0]))
                .toList().toArray(new String[0][0]);
    }

    private Command[][] toCommandsAll(String[][][] inp)
    {
        // TODO добавить передачу аргументов в eval
        return Arrays.stream(inp)
                .map(this::toCommandsByOne)
                .toList()
                .toArray(new Command[0][0]);
    }

    private Command[] toCommandsByOne(String[][] inp)
    {
        // TODO добавить передачу аргументов в eval
        return Arrays.stream(inp)
                .map(commandString -> CMD_MAP.get(commandString[0]))
                .toList()
                .toArray(new Command[0]);
    }

    public String[][][] toCommandArgs(String[][][] inp)
    {
        return Arrays.stream(inp)
                .map(this::toCommandArgsByOne)
                .toList()
                .toArray(new String[0][0][0]);

    }

    public String[][] toCommandArgsByOne(String[][] inp)
    {
        return Arrays.stream(inp)
                .map(str -> Arrays.copyOfRange(str,1,str.length))
                .toList()
                .toArray(new String[0][0]);
    }

    private boolean testStringIsCommand(String str)
    {
        if(CMD_MAP.containsKey(str))
        {
            return true;
        }
        throw error("unexpected command : " + str);
    }

    private IllegalArgumentException error(final String message) {
        return new IllegalArgumentException(message);
    }
}
