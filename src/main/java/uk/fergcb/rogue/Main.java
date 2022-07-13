package uk.fergcb.rogue;

import uk.fergcb.rogue.entities.Interactable;
import uk.fergcb.rogue.entities.Player;
import uk.fergcb.rogue.map.rooms.EmptyRoom;
import uk.fergcb.rogue.map.rooms.Room;
import uk.fergcb.rogue.map.rooms.StarterRoom;

public class Main {

    private static final InputParser input = new InputParser();

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

            Interaction action = input.nextInteraction(player.currentRoom, player);
            shouldDraw = handleInteraction(action);
        }
    }

    private static boolean handleInteraction(Interaction interaction) {

        Interactable target = interaction.target();
        if (target != null && target.canInteract(interaction)) {
            return target.onInteract(interaction);
        }

        System.out.println("Umm...");

        return false;
    }
}
