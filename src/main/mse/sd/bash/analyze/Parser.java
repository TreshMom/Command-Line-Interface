package mse.sd.bash.analyze;

import mse.sd.bash.commands.*;

public class Parser {
    private final String source;
    private Command[][] commands = null;
    private String[][][] commandsArgs = null;

    public String[][][] getCommandsArgs() {
        return this.commandsArgs;
    }

    public Command[][] getCommands() {
        return this.commands;
    }

    public Parser(String source) {
        this.source = source;
    }

    private final SplitterString splitBySpace = new SplitterString(" ");
    private final SplitterString splitByLine = new SplitterString("\\|");
    private final SplitterString splitByAmpersand = new SplitterString("&&");

    // разбор строки
    public void parse() {
        String[][][] postProcessing = splitBySpace
                .split(splitByLine
                        .split(splitByAmpersand
                                .split(this.source)));
        this.commands = CommandParser.parse(postProcessing);
        this.commandsArgs = CommandParser.getArgs(postProcessing);
    }
}
