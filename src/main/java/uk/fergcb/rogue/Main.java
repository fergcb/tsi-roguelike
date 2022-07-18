package uk.fergcb.rogue;

import uk.fergcb.rogue.enitity.Entity;
import uk.fergcb.rogue.enitity.actor.Player;
import uk.fergcb.rogue.map.Level;
import uk.fergcb.rogue.map.generation.LevelGenerator;
import uk.fergcb.rogue.map.room.Room;
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

        player.messageNow(player.currentRoom.draw(player));
        player.messageNow(player.currentRoom.describe());

        boolean gameOver = false;
        while (!gameOver) {
            Interaction action = input.nextInteraction(player);
            boolean shouldDraw = handleInteraction(action);

            level.roomStream().forEachOrdered(Room::preTick);
            level.roomStream().forEachOrdered(Room::tick);

            if (shouldDraw) {
                player.messageNow("\n" + player.currentRoom.draw(player));
                player.messageNow(player.currentRoom.describe());
            }

            level.roomStream().forEachOrdered(Room::postTick);
        }
    }

    private static boolean handleInteraction(Interaction interaction) {
        // System.out.println(interaction);
        if (interaction.type() == InteractionType.FAIL || interaction.type() == InteractionType.CLARIFY) {
            String msg = (String)(interaction.args()[0]);
            interaction.actor().message(msg);
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
