package mse.sd.bash.analyze;

import java.util.Arrays;

public class SplitterString {
    String del_ = " ";

    /**
     * Constructor for the SplitterString class.
     * 
     * @param input The delimiter string.
     */
    SplitterString(String input) {
        this.del_ = input;
    }

    /**
     * Splits the input string using the delimiter and cleans the resulting strings.
     * 
     * @param input The input string to split.
     * @return An array of strings after splitting and cleaning.
     */
    public String[] split(String input) {
        // split and clean
        return Arrays.stream(input.split(del_))
                .map(String::trim)
                .map(str -> str.replaceAll("\\s+", " "))
                .toList()
                .toArray(new String[0]);
    }

    /**
     * Splits each string in the input array using the delimiter and filters out empty strings.
     * 
     * @param input The input array of strings.
     * @return A two-dimensional array of strings after splitting and cleaning.
     */
    public String[][] split(String[] input) {
        return Arrays.stream(input)
                .map(this::split)
                .filter(str -> str.length > 0)
                .toList()
                .toArray(new String[0][0]);
    }
    
    /**
     * Splits each array of strings in the input array using the delimiter and filters out empty arrays.
     * 
     * @param input The input array of arrays of strings.
     * @return A three-dimensional array of strings after splitting and cleaning.
     */
    public String[][][] split(String[][] input) {
        return Arrays.stream(input)
                .map(this::split)
                .filter(str -> str.length > 0)
                .toList()
                .toArray(new String[0][0][0]);
    }

}
