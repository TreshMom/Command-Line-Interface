package mse.sd.bash.analyze;

import mse.sd.bash.commands.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Parser {
    private String source;
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

    public Parser() {
        this.source = null;
    }

    public void setSource(String source) {
        this.source = source;
    }

    private final SplitterString splitBySpace = new SplitterString(" ");
    private final SplitterString splitByLine = new SplitterString("\\|");
    private final SplitterString splitByAmpersand = new SplitterString("&&");

    private final Map<String, String> substitutions = new HashMap<>();

    /**
     * Parses the source string.
     */
    public void parse() {
        // add between \', #  ('1 2 3' -> '1#2#3')
        //
        String[] splitEquals = this.source.split("=");
        if(splitEquals.length > 1)
        {
            substitutions.put(splitEquals[0], splitEquals[1]);
            return;
        }

        String resourse = this.source;
        for(Map.Entry<String, String> entry : substitutions.entrySet())
        {
            resourse = resourse.replaceAll("\\$" + entry.getKey(), entry.getValue());
        }
        String withFill = String.valueOf(fillByChar(resourse.toCharArray()));
        String[][][] postProcessing = splitBySpace
                .split(splitByLine
                        .split(splitByAmpersand
                                .split(withFill)));
        postProcessing = replaceAll(postProcessing);
        this.commands = CommandParser.parse(postProcessing);
        this.commandsArgs = CommandParser.getArgs(postProcessing);
    }

    private static char[] fillByChar(char[] cv)
    {
        boolean filling = false;
        for(int i = 0;i < cv.length; i++)
        {
            if(cv[i] == '\'')
            {
                filling = !filling;
            }

            if(cv[i] == ' ' && filling)
            {
                cv[i] = '#';
            }
        }
        return cv;
    }

    private static String[] replaceAll(String[] str)
    {
        return Arrays.stream(str)
                .map(str_ -> str_.replaceAll("#"," "))
                .map(str_ -> str_.replaceAll("\'",""))
                .toList()
                .toArray(new String[0]);
    }

    private static String[][] replaceAll(String[][] str)
    {
        return Arrays.stream(str)
                .map(Parser::replaceAll)
                .toList()
                .toArray(new String[0][0]);
    }

    private static String[][][] replaceAll(String[][][] str)
    {
        return Arrays.stream(str)
                .map(Parser::replaceAll)
                .toList()
                .toArray(new String[0][0][0]);
    }

    public static void main(String[] args){
        String ls = "cat 'f f ff f f f '";

    }
}
