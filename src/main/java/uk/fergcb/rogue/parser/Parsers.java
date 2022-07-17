package uk.fergcb.rogue.parser;

import uk.fergcb.rogue.parser.combinators.Choice;
import uk.fergcb.rogue.parser.combinators.Optional;
import uk.fergcb.rogue.parser.tokens.Phrase;
import uk.fergcb.rogue.parser.tokens.Regex;
import uk.fergcb.rogue.parser.tokens.Str;
import uk.fergcb.rogue.parser.tokens.WS;

import java.util.stream.Stream;

public class Parsers {
    public static WS ws = new WS();
    public static Phrase phrase = new Phrase();

    public static Optional opt(Parser parser) {
        return new Optional(parser);
    }

    public static Str str(String text) {
        return new Str(text);
    }

    public static Regex word(String text) {
        String regex = "^" + text + "\\s+";
        return new Regex(regex, true);
    }

    public static Choice strs(String... texts) {
        Str[] strParsers = Stream.of(texts)
                .map(Parsers::str)
                .toList()
                .toArray(new Str[0]);
        return new Choice(strParsers);
    }
}
