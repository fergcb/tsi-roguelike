package uk.fergcb.rogue.entities;

import uk.fergcb.rogue.map.rooms.Room;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Entity {

    public Room currentRoom = null;
    private boolean hasTicked = false;

    public static String stripArticle(String nounPhrase) {
        Pattern pattern = Pattern.compile("^(the|a|an)\\s+");
        Matcher matcher = pattern.matcher(nounPhrase);
        return matcher.replaceFirst("");
    }

    public String getDefiniteName() {
        return "the " + getName();
    }

    public String getIndefiniteName() {
        String vowels = "aeiou";
        if(vowels.indexOf(getName().charAt(0)) == -1) return "a " + getName();
        return "an " + getName();
    }

    public Map<String, Integer> getValidNames() {
        Map<String, Integer> validNames = new HashMap<>();
        validNames.put(getName(), 0);
        return validNames;
    }

    public int matchName(String name) {
        Map<String, Integer> validNames = new HashMap<>();

        getValidNames()
                .entrySet()
                .stream()
                .map(entry -> new AbstractMap.SimpleEntry<>(
                        entry.getKey().replaceAll("\u001B\\[[;\\d]*m", "").toUpperCase(Locale.ROOT),
                        entry.getValue())
                )
                .forEach(entry -> validNames.put(entry.getKey(), entry.getValue()));

        name = name.toUpperCase(Locale.ROOT);

        if (validNames.containsKey(name)) return validNames.get(name);

        return -1;
    }

    public final void tick() {
        if (hasTicked) return;
        this.doTick();
        hasTicked = true;
    }

    public final void prime() {
        hasTicked = false;
    }

    public abstract String describe();
    public abstract String getName();
    public abstract void doTick();
    public abstract String draw();
}
