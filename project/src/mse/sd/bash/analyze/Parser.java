package mse.sd.bash.analyze;

import mse.sd.bash.commands.Command;
import mse.sd.bash.commands.Echo;

import java.util.Map;

public class Parser {
    private final CharSourse source;
    private static final char END = '\0';
    private char ch = 0xffff;

    public Parser(CharSourse source) {
        this.source = source;
    }

    // разбор строки
    public Command parse() {
        return parseInput();
    }

    private Command parseInput() {
        skipWhitespace();
        
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
