package uk.fergcb.rogue.map.generation;

import uk.fergcb.rogue.map.Direction;
import uk.fergcb.rogue.map.rooms.Room;

import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * A data structure to track the rules for Room adjacency in level generation
 *
 * Keeps a weighted list of outgoing room possibilities for each type of room and in each direction
 */
public class Index {

    private final Map<Class<? extends Room>, Map<Direction, List<Link>>> rules;

    public Index() {
        this.rules = new HashMap<>();
    }

    /**
     * Add a rule that a target room is allowed when leaving an origin room in a given direction,
     * and specify the likelihood (weight) of that target room being chosen.
     * @param origin The current room
     * @param target The possible next room
     * @param weight The likelihood of this link being chosen
     * @param directions The directions in which this transition is allowed
     */
    public LinkCollection addRule(Class<? extends Room> origin, Class<? extends Room> target, int weight, Direction... directions) {
        if (!rules.containsKey(origin)) {
            rules.put(origin, new HashMap<>());
        }

        Map<Direction, List<Link>> outboundRules = rules.get(origin);
        LinkCollection createdLinks = new LinkCollection();

        for (Direction direction : directions) {
            if(!outboundRules.containsKey(direction)) {
                outboundRules.put(direction, new ArrayList<>());
            }

            List<Link> allowedLinks = outboundRules.get(direction);

            Link newLink = new Link(target, weight);
            createdLinks.add(newLink);
            allowedLinks.add(newLink);
        }

        return createdLinks;
    }

    /**
     * Check if a specified transition is allowed
     * @param origin The room type to transition from
     * @param target The room type to transition into
     * @param direction The direction of outbound travel
     * @param targetPoint The location of the room to transition to
     * @param state The current state of the generator
     * @return True if the transition is allowed, otherwise false
     */
    public boolean isAllowed(Class<? extends Room> origin, Class<? extends Room> target, Direction direction, Point targetPoint, GeneratorState state) {
        if (!rules.containsKey(origin)) return false;
        Map<Direction, List<Link>> outboundRules = rules.get(origin);
        if (!outboundRules.containsKey(direction)) return false;
        List<Link> allowedLinks = outboundRules.get(direction);
        return allowedLinks
                .stream()
                .filter(link -> link.validator.apply(targetPoint, state))
                .filter(link -> link.roomClass != null)
                .anyMatch(link -> link.roomClass.equals(target));
    }

    /**
     * Choose a target room type by weighted selection from the list of outbound links for a given origin room type
     * @param origin The room type being transitioned from
     * @param direction The direction of outbound travel
     * @param targetPoint The location of the room to transition to
     * @param state The current state of the generator
     * @return The class of the chosen Room type.
     */
    public Class<? extends Room> chooseNext(Class<? extends Room> origin, Direction direction, Point targetPoint, GeneratorState state) {
        Map<Direction, List<Link>> outboundRules = rules.get(origin);
        if (outboundRules == null) return null;

        List<Link> allowedLinks = outboundRules.get(direction);
        if (allowedLinks == null) return null;

        allowedLinks.retainAll(allowedLinks
                .stream()
                .filter(link -> link.validator.apply(targetPoint, state))
                .toList()
        );

        int sumWeights = allowedLinks
                .stream()
                .mapToInt(link -> link.weight)
                .sum();

        Random random = new Random();
        // Put on a blindfold, spin around and stick a pin in the pile of links
        int pin = random.nextInt(sumWeights);

        // Climb up the pile of links until you find the pin
        Link choice;
        int i = 0;
        do {
            choice = allowedLinks.get(i++);
            pin -= choice.weight;
        } while (pin >= 0);

        if (!choice.validator.apply(targetPoint, state)) return null;

        return choice.roomClass;
    }
}
