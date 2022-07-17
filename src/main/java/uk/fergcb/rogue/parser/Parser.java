package uk.fergcb.rogue.parser;


import org.jetbrains.annotations.NotNull;
import uk.fergcb.rogue.parser.combinators.Choice;
import uk.fergcb.rogue.parser.combinators.Not;
import uk.fergcb.rogue.parser.combinators.Sequence;

public abstract class Parser {
    public abstract @NotNull ParseResult parse(String input);

    public Choice or(Parser other) {
        return new Choice(this, other);
    }

    public Sequence then(Parser other) {
        return Sequence.of(this, other);
    }

    public Not not() {
        return new Not(this);
    }
}
