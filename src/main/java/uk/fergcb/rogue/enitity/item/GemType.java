package uk.fergcb.rogue.enitity.item;

import uk.fergcb.rogue.Text;

import java.util.Random;
import java.util.function.Function;

public enum GemType {
    PERIDOT(Text::green),
    AMETHYST(Text::magenta),
    CITRINE(Text::yellow),
    RUBY(Text::red),
    SAPPHIRE(Text::blue);

    private static final Random random = new Random();

    public final Function<String, String> colorize;

    GemType(Function<String, String> colorize) {
        this.colorize = colorize;
    }

    public static GemType random() {
        return GemType.values()[random.nextInt(GemType.values().length)];
    }
}
