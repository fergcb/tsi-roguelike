package uk.fergcb.rogue.enitity;

import uk.fergcb.rogue.Text;

public class Shrine extends Container {
    @Override
    protected boolean isLocked() {
        return false;
    }

    @Override
    public String getName() {
        return Text.magenta("SHRINE");
    }

    @Override
    public String describe() {
        return """
                An ornate stone shrine, engraved with angelic imagery.
                A beam of light escapes through a crack in the stone ceiling,
                illuminating the top of the pedestal.
                """;
    }

    @Override
    public String draw() {
        return Text.magenta("π");
    }
}
