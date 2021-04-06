package de.geolykt.specials;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.geolykt.starloader.api.NamespacedKey;
import de.geolykt.starloader.api.event.EventManager;
import de.geolykt.starloader.api.registry.Registry;
import de.geolykt.starloader.mod.Extension;
import snoddasmannen.galimulator.GalColor;

public class Dataspecials extends Extension {

    @Override
    public void initialize() {
        EventManager.registerListener(new DataspecialsListener(this));
    }

    void start() {
        File specialsDir = new File("extensions", "specials");
        specialsDir.mkdir();
        File specialsFile = new File(specialsDir, "specials.json");

        if (!specialsFile.exists()) {
            try {
                specialsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Unable to create the specials.json file!", e);
            }
        }

        byte[] specialsData = null;
        try (FileInputStream fis = new FileInputStream(specialsFile)) {
            specialsData = fis.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to read the specials.json file!", e);
        }

        JSONArray specials = null;
        try {
            specials = new JSONArray(new String(specialsData, StandardCharsets.UTF_8));
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to read the specials.json file!", e);
        }
        if (specials.length() == 0) {
            getLogger().warn("No specials to create!");
            return;
        }

        for (int i = 0; i < specials.length(); i++) {
            JSONObject special = specials.getJSONObject(i);
            String name = special.getString("name");
            String abbreviation = special.getString("abbreviation");
            String description = special.getString("description");
            JSONObject color = special.getJSONObject("color");
            Number r = color.getNumber("r");
            Number g = color.getNumber("g");
            Number b = color.getNumber("b");
            Number a = color.optNumber("a", Float.valueOf(1.0F));
            float rf = 0.0F;
            float gf = 0.0F;
            float bf = 0.0F;
            float af = 0.0F;
            if (r instanceof Float || r instanceof Double) {
                rf = r.floatValue();
            } else {
                rf = r.intValue() / 255;
            }
            if (rf < 0.0 || rf > 1.0) {
                getLogger().info("Red is out of bounds for " + name + "; Skipping special.");
            }
            if (g instanceof Float || g instanceof Double) {
                gf = g.floatValue();
            } else {
                gf = g.intValue() / 255;
            }
            if (gf < 0.0 || gf > 1.0) {
                getLogger().info("Green is out of bounds for " + name + "; Skipping special.");
            }
            if (b instanceof Float || b instanceof Double) {
                bf = b.floatValue();
            } else {
                bf = b.intValue() / 255;
            }
            if (bf < 0.0 || bf > 1.0) {
                getLogger().info("Blue is out of bounds for " + name + "; Skipping special.");
            }
            if (a instanceof Float || a instanceof Double) {
                af = a.floatValue();
            } else {
                af = a.intValue() / 255;
            }
            if (bf < 0.0 || bf > 1.0) {
                getLogger().info("Alpha is out of bounds for " + name + "; Skipping special.");
            }
            GalColor internalColor = new GalColor(rf, gf, bf, af);
            int ordinal = Registry.EMPIRE_SPECIALS.getValues().length;
            String internalName = name.toUpperCase();
            NamespacedKey registryKey = new NamespacedKey(this, "SPECIAL_" +  internalName);

            // Modifiers
            float techMod = special.optFloat("technology", 1.0F);
            float indMod = special.optFloat("industry", 1.0F);
            float stabillityMod = special.optFloat("stabillity", 1.0F);
            float peaceMod = special.optFloat("peacefullness", 1.0F);
            boolean banAlliances = !special.optBoolean("alliances", true);

            Meta meta = new Meta(techMod, indMod, stabillityMod, peaceMod, banAlliances);
            Special builtSpecial = new Special(internalName, ordinal, name, abbreviation, description, internalColor, meta);
            Registry.EMPIRE_SPECIALS.register(registryKey, builtSpecial);
        }
    }
}
