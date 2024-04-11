package mse.sd.bash.commands;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grep extends Command {
    private boolean wholeWord = false;
    private boolean ignoreCase = false;
    private boolean customLinePrint = false;
    private int linesAfterMatch = 0;
    private String regex;
    private String fileName;

    private void parseInput() {
        for (String arg : args) {
            if (arg.equals("-w")) {
                wholeWord = true;
            } else if (arg.equals("-i")) {
                ignoreCase = true;
            } else if (arg.equals("-A")) {
                customLinePrint = true;
            } else if (customLinePrint && linesAfterMatch == 0) {
                try {
                    linesAfterMatch = Integer.parseInt(arg);
                } catch (NumberFormatException e) {
                    linesAfterMatch = (int) Math.round(Double.parseDouble(arg));
                }
            } else if (arg.matches("\".*?\"")) {
                regex = arg.substring(1, arg.length() - 1);
            } else {
                fileName = arg;
            }
        }
    }

    private void innerLoop(int lines, BufferedReader bufReader, Pattern pattern, StringBuilder result) {
        for (int i = 0; i < lines; i++) {
            String nextLine = null;
            try {
                nextLine = bufReader.readLine();
            } catch (IOException e) {
                System.err.println(e);
            }
            if (nextLine == null) {
                return;
            }
            Matcher innerMatcher = pattern.matcher(nextLine);
            boolean innerFound = innerMatcher.find();
            result.append(nextLine).append("\n");
            if (innerFound) {
                innerLoop(lines, bufReader, pattern, result);
                break;
            }
        }
        String nLine = null;
        try {
            nLine = bufReader.readLine();
        } catch (IOException e) {
            System.err.println(e);
        }
        if (nLine == null) {
            return;
        }
        Matcher m = pattern.matcher(nLine);
        boolean f = m.find();
        if (f) {
            result.append(nLine).append("\n");
            innerLoop(lines, bufReader, pattern, result);
        } else {
            result.append("--\n");
        }
    }

    /**
     * Evaluates the command, reading input from the provided reader.
     * Matches lines against the regular expression, prints matched lines, and optionally prints additional lines after each match.
     * If the next command in the chain is not null, passes the result to it.
     *
     * @param reader The reader to read input from.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public void eval(Reader reader) throws IOException {
        parseInput();
        StringBuilder result = new StringBuilder();
        try (BufferedReader bufReader = new BufferedReader(reader)) {
            String line;
            regex = wholeWord ? String.format("\\b%s\\b", regex) : regex;
            Pattern pattern = ignoreCase ? Pattern.compile(regex,
                    Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS) :
                    Pattern.compile(regex, Pattern.UNICODE_CHARACTER_CLASS);
            while ((line = bufReader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                boolean found = matcher.find();
                if (found) {
                    result.append(line).append("\n");
                    if (customLinePrint && linesAfterMatch > 0) {
                        innerLoop(linesAfterMatch, bufReader, pattern, result);
                    }
                }
            }
            linesAfterMatch = 0;
            customLinePrint = false;
            wholeWord = false;
            ignoreCase = false;
            if (!result.isEmpty()) {
                result.deleteCharAt(result.length() - 1);
                while (result.toString().endsWith("--")) {
                    result.setLength(result.length() - 3);
                }
            }
            if (nextCommand != null) {
                nextCommand.eval(new StringReader(result.toString()));
            } else {
                System.out.print(result);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Starts the execution of the command.
     * Reads the file name and command line arguments, and evaluates the command.
     *
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public void start() throws IOException {
        parseInput();
//        System.err.println(linesAfterMatch);
        eval(new FileReader(fileName, StandardCharsets.UTF_8));
    }

    /**
     * Returns a new instance of the Grep command.
     *
     * @return A new instance of the Grep command.
     */
    @Override
    public Grep getNew() {
        return new Grep();
    }
}