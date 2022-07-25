package uk.fergcb.rogue.enitity.item;

import java.util.Random;

public enum GemQuality {
    ROUGH,
    CUT,
    POLISHED,
    BRILLIANT,
    SPARKLING;

    private static final Random random = new Random();

    public static GemQuality random() {
        return GemQuality.values()[random.nextInt(GemQuality.values().length)];
    }
}
