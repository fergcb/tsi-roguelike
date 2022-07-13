package uk.fergcb.rogue.parser;

import uk.fergcb.rogue.Interaction;
import uk.fergcb.rogue.InteractionType;
import uk.fergcb.rogue.Text;
import uk.fergcb.rogue.entities.Container;
import uk.fergcb.rogue.entities.Entity;
import uk.fergcb.rogue.entities.Interactable;
import uk.fergcb.rogue.entities.Player;
import uk.fergcb.rogue.entities.items.Item;
import uk.fergcb.rogue.map.rooms.Room;
import uk.fergcb.rogue.parser.combinators.Some;
import uk.fergcb.rogue.parser.commands.*;
import uk.fergcb.rogue.parser.tokens.Word;

import java.util.*;

public class InputScanner {

    private static final List<String> validDirs = Arrays.asList("NORTH", "EAST", "SOUTH", "WEST");

    private static final List<Command> commands = Arrays.asList(
            new GoCommand(),
            new TakeCommand(),
            new PickUpCommand(),
            new SearchCommand(),
            new DropCommand(),
            new LookCommand()
    );

    private final Scanner scanner = new Scanner(System.in);

    public Interaction nextInteraction(Player player) {
        System.out.print(" > ");
        String line = scanner.nextLine().trim();
        return parseInteraction(line, player);
    }

    private Interaction parseInteraction(String line, Player player) {
        Optional<Interaction> result = Optional.empty();
        for (Command command : commands) {
            result = command.match(line, player);
            if (result.isPresent()) break;
        }

        final Interaction fail = Interaction.fail("I don't know how to " + Text.red(line));

        return result.orElse(fail);
    }

    private Interaction _parseInteraction(String line, Room room, Player player) {
        Parser<List<String>> parser = new Some<>(new Word());
        String[] parts = parser.parse(line).value().toArray(new String[0]);
        String command = parts[0];
        String[] args = Arrays.copyOfRange(parts, 1, parts.length);
        switch(command) {
            case "GO", "MOVE" -> {
                if (args.length != 1) {
                    System.out.println("I don't know how to " + Text.red(command));
                    return null;
                }

                String dir = args[0];
                if (!validDirs.contains(dir)) {
                    System.out.println(dir + " isn't a valid direction...");
                    return null;
                }

                return new Interaction(InteractionType.GO, player, player, dir);
            }
            case "SEARCH" -> {
                if (args.length > 1) {
                    System.out.println("I don't know how to " + Text.red(command));
                    return null;
                }
                if (args.length == 0) {
                    return new Interaction(InteractionType.LOOK, player, player);
                }

                String targetName = args[0];
                Interactable target = room.findInteractableEntity(targetName);
                if (target == null) {
                    System.out.println("I can't see a " + Text.red(targetName) + " to search.");
                    return null;
                }
                return new Interaction(InteractionType.SEARCH, player, target);
            }
            case "LOOK" -> {
                if (args.length == 0) return new Interaction(InteractionType.LOOK, player, player);
            }
            case "TAKE" -> {
                if (args.length != 1 && (args.length != 3 || !args[1].equalsIgnoreCase("from"))) {
                    System.out.println("I don't know how to " + Text.red(command));
                    return null;
                }

                if (args.length == 1) {
                    String itemName = args[0];
                    Entity entity = room.findEntity(itemName);
                    if (entity == null) {
                        System.out.println("I can't see a " + Text.red(itemName) + ".");
                        return null;
                    }
                    if (!(entity instanceof Item)) {
                        System.out.println("I can't carry " + entity.getDefiniteName() + ".");
                        return null;
                    }

                    return new Interaction(InteractionType.PICK_UP, player, player, itemName);
                }

                String targetName = args[2];
                Entity target = room.findEntity(targetName);
                if (target == null) {
                    System.out.println("I can't see a " + Text.red(targetName) + " to take from.");
                    return null;
                }
                if (!(target instanceof Container container)) {
                    System.out.println(target.getDefiniteName() + " has nothing for me to take.");
                    return null;
                }
                String itemName = args[0];
                Item item = container.inventory.searchFor(itemName);
                if (item == null) {
                    System.out.println("I can't find a " + Text.red(itemName) + " in " + container.getDefiniteName());
                    return null;
                }

                return new Interaction(InteractionType.TAKE, player, container, itemName);
            }
            case "DROP" -> {
                if (args.length == 0) {
                    System.out.println("What should I drop?");
                    return null;
                }
                if (args.length > 1) {
                    System.out.println("I don't know how to " + Text.red(command));
                    return null;
                }

                String itemName = args[0];
                Item item = player.inventory.searchFor(itemName);
                if (item == null) {
                    System.out.println("I don't have a " + Text.red(itemName) + ".");
                    return null;
                }

                return new Interaction(InteractionType.DROP, player, player, itemName);
            }
        }

        System.out.println("Bad command.");
        return null;
    }

}
