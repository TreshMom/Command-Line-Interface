package mse.sd.bash.analyze;

import mse.sd.bash.commands.*;

public class Parser {
    private final String source;
    private Command[][] commands = null;
    private String[][][] commandsArgs = null;

    /**
     * Gets the arguments for the commands.
     * 
     * @return A three-dimensional array of strings containing the arguments for each command.
     */
    public String[][][] getCommandsArgs() {
        return this.commandsArgs;
    }

    /**
     * Gets the commands parsed from the source string.
     * 
     * @return A two-dimensional array of Command objects containing the parsed commands.
     */
    public Command[][] getCommands() {
        return this.commands;
    }

    /**
     * Constructor for the Parser class.
     * 
     * @param source The source string to parse.
     */
    public Parser(String source) {
        this.source = source;
    }

    private final SplitterString splitBySpace = new SplitterString(" ");
    private final SplitterString splitByLine = new SplitterString("\\|");
    private final SplitterString splitByAmpersand = new SplitterString("&&");

    /**
     * Parses the source string.
     */
    public void parse() {
        String[][][] postProcessing = splitBySpace
                .split(splitByLine
                        .split(splitByAmpersand
                                .split(this.source)));
        this.commands = CommandParser.parse(postProcessing);
        this.commandsArgs = CommandParser.getArgs(postProcessing);
    }
}
