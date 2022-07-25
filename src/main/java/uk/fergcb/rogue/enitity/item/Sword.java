package uk.fergcb.rogue.enitity.item;

import uk.fergcb.rogue.Text;

public class Sword extends Item implements Weapon {
    @Override
    public String getName() {
        return Text.cyan("SWORD");
    }

    @Override
    public String describe() {
        return """
                The blade is a little rusted in places,
                but it looks like it could do some damage.
                """;
    }

    @Override
    public String draw() {
        return Text.cyan("!");
    }

    @Override
    public int getDamage() {
        return random.nextInt(1,6);
    }
}
