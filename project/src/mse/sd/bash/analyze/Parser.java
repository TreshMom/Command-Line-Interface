package mse.sd.bash.analyze;

import mse.sd.bash.commands.*;
import java.util.Map;
import java.util.HashMap;

public class Parser {
    private final CharSourse source;
    private static final char END = '\0';
    private char ch = 0xffff;
    private static final Map<String, Command> CMD_MAP = new HashMap<>();

    static {
        CMD_MAP.put("cat", new Cat());
        CMD_MAP.put("echo", new Echo());
        CMD_MAP.put("exit", new Exit());
        CMD_MAP.put("pwd", new Pwd());
        CMD_MAP.put("wc", new Wc());
    }

    public Parser(CharSourse source) {
        this.source = source;
    }

    // разбор строки
    public String[] parse() {
        return parseInput();
    }

    private String[] parseInput() {
        skipWhitespace();
        if (test(END)) {
            throw error("No command found");
        }
        StringBuilder commandNameBuilder = new StringBuilder();
        while (!test(END) && !Character.isWhitespace(ch)) {
            commandNameBuilder.append(take());
        }
        String[] commands = commandNameBuilder.toString().split(" && ");
        return commands;
    }


    // helper methods
    private char take() {
        final char result = ch;
        ch = source.hasNext() ? source.next() : END;
        return result;
    }

    private boolean test(final char expected) {
        return ch == expected;
    }

    private boolean take(final char expected) {
        if (test(expected)) {
            take();
            return true;
        }
        return false;
    }

    private void expect(final char expected) {
        if (!take(expected)) {
            throw error("Expected '" + expected + "', found '" + ch + "'");
        }
    }

    private IllegalArgumentException error(final String message) {
        return source.error(message);
    }

    private void expect(final String value) {
        for (final char c : value.toCharArray()) {
            expect(c);
        }
    }

    private boolean eof() {
        return take(END);
    }

    private void skipWhitespace() {
        while (Character.isWhitespace(ch)) {
            take();
        }
    }

}
