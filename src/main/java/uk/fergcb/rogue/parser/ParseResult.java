package uk.fergcb.rogue.parser;

public class ParseResult<T> {

    public static <U> ParseResult<U> match(U value, String rest) {
        return new ParseResult<>(value, rest, false);
    }

    public static <U> ParseResult<U> empty(String rest) {
        return new ParseResult<>(null, rest, false);
    }

    public static <U> ParseResult<U> fail(String rest) {
        return new ParseResult<>(null, rest, true);
    }

    private final T value;
    private final String rest;
    private final Boolean isFail;

    private ParseResult(T value, String rest, boolean isFail) {
        this.value = value;
        this.rest = rest;
        this.isFail = isFail;
    }

    public T value() {
        return this.value;
    }

    public String rest() {
        return this.rest;
    }

    public boolean isFail() {
        return this.isFail;
    }
}
