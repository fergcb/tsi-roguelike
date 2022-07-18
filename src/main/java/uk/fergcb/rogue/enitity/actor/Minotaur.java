package uk.fergcb.rogue.enitity.actor;

import uk.fergcb.rogue.Interaction;
import uk.fergcb.rogue.InteractionType;
import uk.fergcb.rogue.Text;
import uk.fergcb.rogue.map.Direction;

import java.util.List;
import java.util.Random;

public class Minotaur extends Actor {

    @Override
    public String getName() {
        return Text.red("MINOTAUR");
    }
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
    public String draw() {
        return Text.red("Ŏ");
    }

    @Override
    public void doTick() {
        Random random = new Random();

        // TODO: attack & follow the player
        if (currentRoom.hasPlayer()) return;

        List<Direction> directions = currentRoom.exits.keySet().stream().toList();
        Direction direction = null;
        for (Direction dir : directions) {
            if (currentRoom.getExit(dir).hasPlayer()) {
                direction = dir;
                break;
            }
        }

        if (direction == null) {
            direction = directions.get(random.nextInt(directions.size()));
        }

        Interaction action = new Interaction(InteractionType.GO, this, this, direction.name());
        handleInteraction(action);
    }
}
