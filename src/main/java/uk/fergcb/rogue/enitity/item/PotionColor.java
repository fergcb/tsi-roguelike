package uk.fergcb.rogue.enitity.item;

import uk.fergcb.rogue.Text;

import java.util.Random;
import java.util.function.Function;

public enum PotionColor {
    GREEN(Text::green),
    PURPLE(Text::magenta),
    YELLOW(Text::yellow);

    private static final Random random = new Random();

    public final Function<String, String> colorize;

    PotionColor (Function<String, String> colorize) {
        this.colorize = colorize;
    }

    public static PotionColor random() {
        return PotionColor.values()[random.nextInt(PotionColor.values().length)];
    }
}
