package mse.sd.bash.analyze;

import java.util.Arrays;

public class SplitterString {
    String del_ = " ";

    SplitterString(String input) {
        this.del_ = input;
    }

    public String[] split(String input) {
        // split and clean
        return Arrays.stream(input.split(del_))
                .map(String::trim)
                .map(str -> str.replaceAll("\\s+", " "))
                .toList()
                .toArray(new String[0]);
    }

    public String[][] split(String[] input) {
        return Arrays.stream(input)
                .map(this::split)
                .filter(str -> str.length > 0)
                .toList()
                .toArray(new String[0][0]);
    }

    public String[][][] split(String[][] input) {
        return Arrays.stream(input)
                .map(this::split)
                .filter(str -> str.length > 0)
                .toList()
                .toArray(new String[0][0][0]);
    }

}
