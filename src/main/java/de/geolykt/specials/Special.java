package de.geolykt.specials;

import snoddasmannen.galimulator.EmpireSpecial;
import snoddasmannen.galimulator.GalColor;

public class Special extends EmpireSpecial {

    private static final long serialVersionUID = 528262918578136633L;

    protected final GalColor gColor;
    final Meta meta;

    public Special(String enumName, int ordinal, String name, String abbreviation, String description, GalColor color, Meta meta) {
        super(enumName, ordinal, name, abbreviation, description);
        gColor = color;
        this.meta = meta;
    }

    @Override
    public float a() {
        return meta.tech;
    }

    @Override
    public float b() {
        return meta.ind;
    }

    @Override
    public float c() {
        return meta.stabillity;
    }

    @Override
    public float d() {
        return meta.peace;
    }

    @Override
    public boolean e() {
        return meta.noAlliances;
    }

    @Override
    public GalColor j() {
        return gColor;
    }

} class Meta {
    final float tech;
    final float ind;
    final float stabillity;
    final float peace;
    final boolean noAlliances;

    public Meta(float techMod, float indMod, float stabMod, float peaceMod, boolean bannedAlliances) {
        tech = techMod;
        ind = indMod;
        stabillity = stabMod;
        peace = peaceMod;
        noAlliances = bannedAlliances;
    }
}
