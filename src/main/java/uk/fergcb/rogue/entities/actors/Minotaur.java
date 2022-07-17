package uk.fergcb.rogue.entities.actors;

import uk.fergcb.rogue.Interaction;
import uk.fergcb.rogue.InteractionType;
import uk.fergcb.rogue.Text;
import uk.fergcb.rogue.map.Direction;

import java.util.List;
import java.util.Random;

public class Minotaur extends Actor {
    @Override
    public String describe() {
        return """
                The creature towers above you, standing at least 9 feet tall.
                Its form is mostly humanoid, but covered in thick, blue-black fur.
                Its huge bovine head bears two sharp horns, dripping with fresh, red blood.
                The monster's rageful black eyes are trained on you.
                """;
    }

    @Override
    public String getName() {
        return Text.red("MINOTAUR");
    }

    @Override
    public void doTick() {
        Random random = new Random();

        if (currentRoom.entities.stream().anyMatch(entity -> entity instanceof Player)) return;

        List<Direction> directions = currentRoom.exits.keySet().stream().toList();
        Direction direction = directions.get(random.nextInt(directions.size()));

        Interaction action = new Interaction(InteractionType.GO, this, this, direction.name());
        handleInteraction(action);
    }

    @Override
    public String draw() {
        return Text.red("Ŏ");
    }
}
