package uk.fergcb.rogue.entities.items;

import uk.fergcb.rogue.Text;

import java.util.Random;
import java.util.function.Function;

public enum GemType {
    PERIDOT(Text::green),
    AMETHYST(Text::magenta),
    CITRINE(Text::yellow),
    RUBY(Text::red),
    SAPPHIRE(Text::blue);

    public final Function<String, String> colorize;

    GemType(Function<String, String> colorize) {
        this.colorize = colorize;
    }

    public static GemType random() {
        Random random = new Random();
        return GemType.values()[random.nextInt(GemType.values().length)];
    }
}
