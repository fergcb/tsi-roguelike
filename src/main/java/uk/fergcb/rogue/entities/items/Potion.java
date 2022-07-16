package uk.fergcb.rogue.entities.items;

import java.util.Map;

public class Potion extends Item {

    public final PotionColor color;

    public Potion(PotionColor color) {
        this.color = color;
    }

    @Override
    public String getName() {
        return color.colorize.apply(color.name() + " POTION");
    }

    @Override
    public Map<String, Integer> getValidNames() {
        Map<String, Integer> validNames = super.getValidNames();
        validNames.put("POTION", 50);
        return validNames;
    }

    @Override
    public void tick() {}

    @Override
    public String draw() {
        return color.colorize.apply("b");
    }
}
