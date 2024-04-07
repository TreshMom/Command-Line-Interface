package mse.sd.bash.commands;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grep extends Command {
    private boolean wholeWord = false;
    private boolean ignoreCase = false;
    private boolean customLinePrint = false;
    private int linesAfterMatch = 0;
    private String regex;

    private void innerLoop(int lines, BufferedReader bufReader, Pattern pattern, StringBuilder result) {
        for (int i = 0; i < lines; i++) {
            String nextLine = null;
            try {
                nextLine = bufReader.readLine();
            } catch (IOException e) {
                System.err.println(e);
            }
            if (nextLine == null) {
                break;
            }
            Matcher innerMatcher = pattern.matcher(nextLine);
            boolean innerFound = false;
            if (innerMatcher.find()) {
                innerFound = true;
            }
            result.append(nextLine).append("\n");
            if (innerFound) {
                innerLoop(lines - 1, bufReader, pattern, result);
                break;
            }
        }
    }

    @Override
    public void eval(Reader reader) throws IOException {
        StringBuilder result = new StringBuilder();
        try (BufferedReader bufReader = new BufferedReader(reader)) {
            String line;
            regex = wholeWord ? String.format("\\b%s\\b", regex) : regex;
            Pattern pattern = ignoreCase ? Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS) : Pattern.compile(regex, Pattern.UNICODE_CHARACTER_CLASS);
            while ((line = bufReader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                boolean found = false;
                if (matcher.find()) {
                    found = true;
                }
                if (found) {
                    result.append(line).append("\n");
                    if (customLinePrint && linesAfterMatch > 0) {
                        innerLoop(linesAfterMatch, bufReader, pattern, result);
                        // for (int i = 0; i < linesAfterMatch; i++) {
                        //     String nextLine = bufReader.readLine();
                        //     if (nextLine == null) {
                        //         break;
                        //     }
                        //     Matcher innerMatcher = pattern.matcher(nextLine);
                        //     boolean innerFound = false;
                        //     if (innerMatcher.find()) {
                        //         innerFound = true;
                        //     }
                        //     result.append(nextLine).append("\n");
                        //     if (innerFound) {
                        //         break;
                        //     }
                        // }
                    }
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

    @Override
    public void start() throws IOException {
        String fileName = null;
        for (int i = 0; i < args.length; i ++) {
            if (args[i].equals("-w")) {
                wholeWord = true;
            } else if (args[i].equals("-i")) {
                ignoreCase = true;
            } else if (args[i].equals("-A")) {
                customLinePrint = true;
            } else if (customLinePrint == true && linesAfterMatch == 0) {
                try {
                    linesAfterMatch = Integer.parseInt(args[i]);
                } catch (NumberFormatException e) {
                    linesAfterMatch = (int) Math.round(Double.parseDouble(args[i]));
                }
            } else if (args[i].matches("\".*?\"")) {
                regex = args[i].substring(1, args[i].length() - 1);
            } else {
                fileName = args[i];
            }
        }
        eval(new FileReader(fileName, StandardCharsets.UTF_8));
    }

    @Override
    public Grep getNew() {
        return new Grep();
    }
}