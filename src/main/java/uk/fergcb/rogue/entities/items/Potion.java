package uk.fergcb.rogue.entities.items;

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
    public void tick() {}

    @Override
    public String draw() {
        return color.colorize.apply("b");
    }
}
