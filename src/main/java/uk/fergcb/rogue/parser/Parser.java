package uk.fergcb.rogue.parser;


import org.jetbrains.annotations.NotNull;
import uk.fergcb.rogue.parser.combinators.Choice;
import uk.fergcb.rogue.parser.combinators.Not;
import uk.fergcb.rogue.parser.combinators.Sequence;

public abstract class Parser <T> {
    public abstract @NotNull ParseResult<T> parse(String input);

    public Choice<T> or(Parser<T> other) {
        return new Choice<>(this, other);
    }

    public Sequence<T> then(Parser<T> other) {
        return Sequence.of(this, other);
    }

    public Not<T> not() {
        return new Not<>(this);
    }
}
