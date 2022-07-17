package uk.fergcb.rogue.parser;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class ParseResult {

    public static ParseResult match(Object value, String rest) {
        return new ParseResult(value, rest, false);
    }

    public static ParseResult empty(String rest) {
        return new ParseResult(null, rest, false);
    }

    public static ParseResult fail(String rest) {
        return new ParseResult(null, rest, true);
    }

    private final Object value;
    private final String rest;
    private final Boolean isFail;

    private ParseResult(Object value, String rest, boolean isFail) {
        this.value = value;
        this.rest = rest;
        this.isFail = isFail;
    }

    public Object value() {
        return this.value;
    }

    public boolean isString() {
        return this.value instanceof String;
    }

    public String asString() {
        if (!isString()) throw new RuntimeException("Failed to cast ParseResult value to String.");
        return (String)this.value;
    }

    public boolean isList() {
        return this.value instanceof List<?>;
    }

    @SuppressWarnings("unchecked")
    public List<Object> asList() {
        if (!isList()) throw new RuntimeException("Failed to cast ParseResult value to List.");
        return (List<Object>)this.value;
    }

    public List<String> asListOfString() {
        if (!isList()) throw new RuntimeException("Failed to cast ParseResult value to List.");
        return flatten(this.value).toList();
    }

    private static Stream<String> flatten(Object obj) {
        if (obj instanceof List<?> list) {
            return list.stream()
                    .filter(Objects::nonNull)
                    .flatMap(ParseResult::flatten);
        }

        return Stream.of(obj.toString());
    }

    public String rest() {
        return this.rest;
    }

    public boolean isFail() {
        return this.isFail;
    }
}
