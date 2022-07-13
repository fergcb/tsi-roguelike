package uk.fergcb.rogue;

import uk.fergcb.rogue.entities.Interactable;
import uk.fergcb.rogue.entities.Player;
import uk.fergcb.rogue.map.rooms.EmptyRoom;
import uk.fergcb.rogue.map.rooms.Room;
import uk.fergcb.rogue.map.rooms.StarterRoom;
import uk.fergcb.rogue.parser.InputScanner;
import uk.fergcb.rogue.parser.commands.Command;
import uk.fergcb.rogue.parser.commands.SearchCommand;

public class Main {

    private static final InputScanner input = new InputScanner();

    public static void main (String[] args) {
        start();
    }

    private static void start() {
        Room room = new StarterRoom();
        room.addEast(new EmptyRoom());
        room.east.addSouth(new EmptyRoom());
        room.east.addNorth(new EmptyRoom());

        Player player = new Player(room);

        boolean gameOver = false;
        boolean shouldDraw = true;
        while (!gameOver) {
            if (shouldDraw) {
                System.out.print(player.currentRoom.draw(player));
                System.out.print(player.currentRoom.describe());
            }

            Interaction action = input.nextInteraction(player);
            shouldDraw = handleInteraction(action);
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
