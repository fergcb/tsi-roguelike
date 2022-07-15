package uk.fergcb.rogue;

import uk.fergcb.rogue.entities.Interactable;
import uk.fergcb.rogue.entities.Player;
import uk.fergcb.rogue.map.Level;
import uk.fergcb.rogue.map.generation.LevelGenerator;
import uk.fergcb.rogue.map.rooms.Room;
import uk.fergcb.rogue.parser.InputScanner;

public class Main {

    private static final InputScanner input = new InputScanner();

    public static void main (String[] args) {
        start();
    }

    private static void start() {
        LevelGenerator gen = new LevelGenerator(5, 5);
        Level level = gen.generateLevel();
        Room room = level.startRoom();

        Player player = new Player();
        room.addEntity(player);

        boolean gameOver = false;
        boolean shouldDraw = true;
        while (!gameOver) {
            if (shouldDraw) {
                System.out.print(player.currentRoom.draw(player));
                System.out.print(player.currentRoom.describe());
            }

            Interaction action = input.nextInteraction(player);
            shouldDraw = handleInteraction(action);

            level.roomStream().forEachOrdered(Room::tick);
        }
    }

    private static boolean handleInteraction(Interaction interaction) {

        if (interaction.type() == InteractionType.FAIL) {
            System.out.println(interaction.args()[0]);
            return false;
        }

        Interactable target = interaction.target();
        if (target != null && target.canInteract(interaction)) {
            return target.onInteract(interaction);
        }

        System.err.println("Failed to handle interaction: " + interaction);
        return false;
    }
}
