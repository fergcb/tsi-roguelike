package uk.fergcb.rogue.parser;

import uk.fergcb.rogue.Interaction;
import uk.fergcb.rogue.Text;
import uk.fergcb.rogue.enitity.actor.Player;
import uk.fergcb.rogue.parser.command.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class InputScanner {

    private static final List<String> validDirs = Arrays.asList("NORTH", "EAST", "SOUTH", "WEST");

    private static final List<Command> commands = Arrays.asList(
            new GoCommand(),
            new TakeCommand(),
            new PickUpCommand(),
            new SearchCommand(),
            new DropCommand(),
            new LookCommand(),
            new AttackCommand()
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

        final Interaction fail = Interaction.fail(player, "I don't know how to " + Text.red(line));

        return result.orElse(fail);
    }

}
