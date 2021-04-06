package de.geolykt.specials;

import snoddasmannen.galimulator.EmpireSpecial;
import snoddasmannen.galimulator.GalColor;

public class Special extends EmpireSpecial {

    private static final long serialVersionUID = 528262918578136633L;

    protected final GalColor gColor;

    public Special(String enumName, int ordinal, String name, String abbreviation, String description, GalColor color) {
        super(enumName, ordinal, name, abbreviation, description);
        gColor = color;
    }

    @Override
    public GalColor j() {
        return gColor;
    }

}
