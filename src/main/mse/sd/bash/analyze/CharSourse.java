package mse.sd.bash.analyze;

public interface CharSourse {
    boolean hasNext();

    char next();

    IllegalArgumentException error(String message);
}
