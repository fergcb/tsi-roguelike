package uk.fergcb.rogue;

import uk.fergcb.rogue.entities.Entity;
import uk.fergcb.rogue.entities.actors.Player;
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

        System.out.println(player.currentRoom.draw(player));
        System.out.println(player.currentRoom.describe());

        boolean gameOver = false;
        while (!gameOver) {
            Interaction action = input.nextInteraction(player);
            boolean shouldDraw = handleInteraction(action);

            level.roomStream().forEachOrdered(Room::preTick);
            level.roomStream().forEachOrdered(Room::tick);

            if (shouldDraw) {
                System.out.println();
                System.out.println(player.currentRoom.draw(player));
                System.out.println(player.currentRoom.describe());
            }

            level.roomStream().forEachOrdered(Room::postTick);
        }
    }

    private static boolean handleInteraction(Interaction interaction) {
        // System.out.println(interaction);
        if (interaction.type() == InteractionType.FAIL || interaction.type() == InteractionType.CLARIFY) {
            System.out.println(interaction.args()[0]);
            return false;
        }

        Entity target = interaction.target();
        if (target != null && target.canReceive(interaction)) {
            return target.handleInteraction(interaction);
        }

        System.err.println("Failed to handle interaction: " + interaction);
        return false;
    }
}
